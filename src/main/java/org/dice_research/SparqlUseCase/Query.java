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
	boolean mostGeneral = false;
	
	Map<String, String> prefixes;
	List<Statement> statements;
	List<Triple> triples;
	int j=0;
	
	static List<String> reservedWords = Arrays.asList(new String[] { "SELECT", "WHERE", "GROUP", "HAVING", "ORDER",
			"LIMIT", "OFFSET", "VALUES", "OPTIONAL", "MINUS", "GRAPH", "SERVICE", "FILTER", "BIND" });
	
	public Query() {
		this.query = "";
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
	}
	
	public Query(String q) {
		if(q.equals("*")) {
			this.mostGeneral = true;
		}
		this.query = q;
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
	}
	//constructor to construct a query with amount of (ANY_ANY ANY_ANY ANY_ANY) triples
	public Query(int amount) {
		this.query = "";
		this.prefixes  = new HashMap<String, String>();
		this.statements = new ArrayList<Statement>();
		this.triples = new ArrayList<Triple>();
		
		for(int i=0; i<amount; i++) {
			this.triples.add(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY"));
		}
	}
	
	//copy constructor
	public Query(Query q) {
		this.query = q.getOriginalQuery();
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
	
	public boolean isMostGeneral() {
		return this.mostGeneral;
	}
	
	public String getOriginalQuery() {
		return query;
	}
	
	//note: does not consider possible nested statements
	public String getParsedQuery() {
		StringBuilder sb = new StringBuilder();
		if(this.statements.size() > 0) {
			sb.append(this.statements.get(0).getType());
			sb.append(" ");
			if(this.statements.get(0).getType().equals("SELECT")) {
				Map<String, String> vars = ((SelectStatement) this.statements.get(0)).getVariables();
				if(vars.containsValue("*")) {
					sb.append("*  ");
				} else {
					for(Map.Entry<String, String> e: vars.entrySet()) {
						sb.append(e.getValue()+", ");
					}
				}
				if(vars.size() > 0) {
					sb.replace(0, sb.length(), sb.substring(0, sb.length()-2));
				}
				sb.append(" ");
			}
		}
		
		sb.append("{");
		sb.append("\n");
		for(Triple t: this.triples) {
			sb.append(t);
			if(t.isOptional()) {
				sb.append(" ?");
			}
			sb.append("\n");
		}
		sb.append("}");
		sb.append("\n");
		return sb.toString();
	}
	
	public void printTriples() {
		for(Triple t: this.triples) {
			System.out.println(t);
		}
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

	public boolean isMoreGeneralThan(Query q, int start, Map<Integer, Integer> assignedTriples, List<Integer> switched) {
		if(this.mostGeneral) {
			return true;
		}
		if(assignedTriples != null) {
			//System.out.println("assignedTriples size: "+assignedTriples.size()+"  "+"q.triples.size: "+q.triples.size()+"  "+"start: "+start);
			if(assignedTriples.size() == q.triples.size()) {
//				System.out.println("---");
//				for(Map.Entry<Integer, Integer> entry: assignedTriples.entrySet()) {
//					System.out.println(this.triples.get(entry.getKey()) +"  >=  "+ q.triples.get(entry.getValue()));
//				}
				return true;
			}
			if(assignedTriples.containsValue(start)) {
				return isMoreGeneralThan(q, start+1, assignedTriples, null);
			}
		}

		Triple t = q.triples.get(start);
		for(int k=0; k<this.triples.size();k++) {
			if(assignedTriples.containsKey(k)) {
				continue;
			}
			if(this.triples.get(k).isMoreGeneralThan(t)) {
				assignedTriples.put(k, start);
				//System.out.println("1: "+this.triples.get(k)+"  >=  "+t);
				return isMoreGeneralThan(q, start+1, assignedTriples, null);
			} 
		}
		
		for(Map.Entry<Integer, Integer> entry: assignedTriples.entrySet()) {
			if(this.triples.get(entry.getKey()).isMoreGeneralThan(t)) {
				
				if(switched != null) {
					if(switched.contains(entry.getKey())) {
						continue;
					}
				}
				//holds the index of the triple in q whose match has been reassigned,
				//thus we need to find another match for it
				int temp = entry.getValue();
				assignedTriples.put(entry.getKey(), start);
				if(switched != null) {
					switched.add(entry.getKey());
				} else {
					switched = new ArrayList<Integer>();
					switched.add(entry.getKey());
				}
				//System.out.println("2: hash: "+this.triples.get(entry.getKey()).hashCode()+" -"+this.triples.get(entry.getKey())+"  >=  "+t);
				return isMoreGeneralThan(q, temp, assignedTriples, switched);
			}
		}
		//no triple in this.triples is more general than triple t from query q
		return false;
	}

}