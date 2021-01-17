package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
	//original query
	String query = null;
	
	Map<String, String> prefixes;
	List<Statement> statements;
	List<Triple> triples;
	int j=0;
	
	static List<String> reservedWords = Arrays.asList(new String[] { "SELECT", "WHERE", "GROUP", "HAVING", "ORDER",
			"LIMIT", "OFFSET", "VALUES", "OPTIONAL", "MINUS", "GRAPH", "SERVICE", "FILTER", "BIND" });
	
	public Query() {
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
	}
	
	public Query(String q) {
		this.query = q;
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
	}
	
	//copy constructor
	public Query(Query q) {
		this.query = q.getQuery();
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
		
		for(Map.Entry<String, String> entry: q.getPrefixes().entrySet()) {
			this.prefixes.put(entry.getKey(), entry.getValue());
		}
		for(Statement s: q.getStatements()) {
			Statement temp = null;
			if(s.getType().equals("SELECT")) {
				temp = new SelectStatement();
				for(Map.Entry<String, String> var: ((SelectStatement)s).getVariables().entrySet()) {
					((SelectStatement)temp).getVariables().put(var.getKey(), var.getValue());
				}
			//any other query type
			} else {
				temp = new Statement() {
					public String getType() {
						return s.getType();
					}
				};
			}
			this.statements.add(temp);
		}
		
		for(Triple t: q.getTriples()) {
			this.triples.add(new Triple(t.getSubjectValue(), t.getPredicateValue(), t.getObjectValue()));
		}
	}
	
	public String getQuery() {
		return query;
	}
	
	public List<Triple> getTriples(){
		return this.triples;
	}
	
	public List<Triple> getCopyOfTriples(){
		List<Triple> temp = new ArrayList<Triple>();
		for(Triple t: this.triples) {
			temp.add(new Triple(t.getSubjectValue(), t.getPredicateValue(), t.getObjectValue()));
		}
		return temp;
	}
	
	public List<Statement> getStatements(){
		return this.statements;
	}
	
	public Map<String, String> getPrefixes(){
		return this.prefixes;
	}
	
	public void setQuery(String s) {
		query = s;
	}
	
	public void addAllTriples(List<Triple> triples) {
		for(Triple t: triples) {
			this.triples.add(new Triple(t.getSubjectValue(), t.getPredicateValue(), t.getObjectValue()));
		}
	}
	
	public boolean renameVariables(List<Statement> statements, List<Triple> triples) {
		for(Statement s: statements) {
			Map<String, String> vars;
			if(s.getType().equals("SELECT")) {
				vars = ((SelectStatement)s).getVariables();
				for(Triple t: triples) {
					if(vars.containsKey(t.s.toString())) {
						t.s.setValue(vars.get(t.s.toString()));
					} else if(t.s.toString().startsWith("?")) {
						((SelectStatement)s).putVariable(t.s.toString(), "?v"+j);
						t.s.setValue("?v"+j);
						j++;
					}
					if(vars.containsKey(t.p.toString())) {
						t.p.setValue(vars.get(t.p.toString()));
					} else if(t.p.toString().startsWith("?")) {
						((SelectStatement)s).putVariable(t.p.toString(), "?v"+j);
						t.p.setValue("?v"+j);
						j++;
					}
					if(vars.containsKey(t.o.toString())) {
						t.o.setValue(vars.get(t.o.toString()));
					} else if(t.o.toString().startsWith("?")) {
						((SelectStatement)s).putVariable(t.o.toString(), "?v"+j);
						t.o.setValue("?v"+j);
						j++;
					}
				}
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	public void replacePrefixVariables() {
		for(Triple t: triples) {
			String[] parts;
			if(t.s.getType().equals(TripleValue.IRI) && !t.s.toString().startsWith("<") && !t.s.toString().endsWith(">")) {
				parts = t.s.toString().split(":");
				if(prefixes.containsKey(parts[0])){
					t.s.setValue("<"+prefixes.get(parts[0])+parts[1]+">");
				}
			}
			if(t.p.getType().equals(TripleValue.IRI) && !t.p.toString().startsWith("<") && !t.p.toString().endsWith(">")) {
				parts = t.p.toString().split(":");
				if(prefixes.containsKey(parts[0])){
					t.p.setValue("<"+prefixes.get(parts[0])+parts[1]+">");
				}
			}
			if(t.o.getType().equals(TripleValue.IRI) && !t.o.toString().startsWith("<") && !t.o.toString().endsWith(">")) {
				parts = t.o.toString().split(":");
				if(prefixes.containsKey(parts[0])){
					t.o.setValue("<"+prefixes.get(parts[0])+parts[1]+">");
				}
			}
		}
	}

	public boolean isMoreGeneralThan(Query q, int start, Map<Integer, Integer> assignedTriples) {
		if(assignedTriples != null) {
			//System.out.println("assignedTriples size: "+assignedTriples.size()+"  "+"q.triples.size: "+q.triples.size()+"  "+"start: "+start);
			if(assignedTriples.size() == q.triples.size()) {
				System.out.println("---");
				for(Map.Entry<Integer, Integer> entry: assignedTriples.entrySet()) {
					System.out.println(this.triples.get(entry.getKey()) +"  >=  "+ q.triples.get(entry.getValue()));
				}
				return true;
			}
			if(assignedTriples.containsValue(start)) {
				return isMoreGeneralThan(q, start+1, assignedTriples);
			}
		}

		Triple t = q.triples.get(start);
		for(int k=0; k<this.triples.size();k++) {
			if(assignedTriples.containsKey(k)) {
				continue;
			}
			if(this.triples.get(k).isMoreGeneralThan(t)) {
				assignedTriples.put(k, start);
				System.out.println("1: "+this.triples.get(k)+"  >=  "+t);
				return isMoreGeneralThan(q, start+1, assignedTriples);
			} 
		}
		
		for(Map.Entry<Integer, Integer> entry: assignedTriples.entrySet()) {
			if(this.triples.get(entry.getKey()).isMoreGeneralThan(t)) {
				//holds the index of the triple in q whose match has been reassigned,
				//thus we need to find another match for it
				int temp = entry.getValue();
				assignedTriples.put(entry.getKey(), start);
				System.out.println("2: "+this.triples.get(entry.getKey())+"  >=  "+t);
				return isMoreGeneralThan(q, temp, assignedTriples);
			}
		}
		//no triple in this.triples is more general than triple t from query q
		return false;
	}

}