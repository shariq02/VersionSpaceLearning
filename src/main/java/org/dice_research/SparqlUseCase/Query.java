package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
	//original query
	String query;
	
	Map<String, String> prefixes;
	List<Statement> statements;
	List<Triple> triples;
	int j=0;
	
	static List<String> reservedWords = Arrays.asList(new String[] { "SELECT", "WHERE", "GROUP", "HAVING", "ORDER",
			"LIMIT", "OFFSET", "VALUES", "OPTIONAL", "MINUS", "GRAPH", "SERVICE", "FILTER", "BIND" });
	
	public Query(String q) {
		this.query = q;
		prefixes  = new HashMap<String, String>();
		statements = new ArrayList<Statement>();
		triples = new ArrayList<Triple>();
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String s) {
		query = s;
	}
	
	public boolean renameVariables(List<Statement> statements, List<Triple> triples) {
		for(Statement s: statements) {
			Map<String, String> vars;
			if(s.getType().equals("SELECT")) {
				vars = s.getVariables();
				for(Triple t: triples) {
					if(vars.containsKey(t.s.toString())) {
						t.s.setValue(vars.get(t.s.toString()));
					} else if(t.s.toString().startsWith("?")) {
						s.putVariable(t.s.toString(), "?v"+j);
						t.s.setValue("?v"+j);
						j++;
					}
					if(vars.containsKey(t.p.toString())) {
						t.p.setValue(vars.get(t.p.toString()));
					} else if(t.p.toString().startsWith("?")) {
						s.putVariable(t.p.toString(), "?v"+j);
						t.p.setValue("?v"+j);
						j++;
					}
					if(vars.containsKey(t.o.toString())) {
						t.o.setValue(vars.get(t.o.toString()));
					} else if(t.o.toString().startsWith("?")) {
						s.putVariable(t.o.toString(), "?v"+j);
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
	
	public boolean isMoreGeneralThan(Query q) {
		
		System.out.println(triples.size()+" "+q.triples.size());
		int nrOfTriples = q.triples.size();
		int nrOfFoundTriples = 0;
		
		for(Triple x: q.triples) {
			boolean check1 = false;
			boolean check2 = false;
			boolean check3 = false;
			String xSType = x.s.getType();
			String xPType = x.p.getType();
			String xOType = x.o.getType();
			
			for(Triple y: triples) {
				String ySType = y.s.getType();
				String yPType = y.p.getType();
				String yOType = y.o.getType();
				
				//sub
				if(xSType.equals(TripleValue.VARIABLE) && !xSType.equals(ySType)) {
					continue;
				}
				else if(xSType.equals(TripleValue.VARIABLE) && xSType.equals(ySType)) {
					check1 = true;
				}
				
				if(xSType.equals(TripleValue.IRI) && !xSType.equals(ySType)) {
					continue;
				}
				else if(xSType.equals(TripleValue.IRI) && xSType.equals(ySType) && !x.s.toString().equals(y.s.toString())) {
					continue;
				}
				else if(xSType.equals(TripleValue.IRI) && xSType.equals(ySType) && x.s.toString().equals(y.s.toString())) {
					check1 = true;
				}
				
				//pred
				if(xPType.equals(TripleValue.VARIABLE) && !xPType.equals(yPType)) {
					continue;
				}
				else if(xPType.equals(TripleValue.VARIABLE) && xPType.equals(yPType)) {
					check2 = true;
				}
				
				if(xPType.equals(TripleValue.IRI) && !xPType.equals(yPType)) {
					continue;
				}
				else if(xPType.equals(TripleValue.IRI) && xPType.equals(yPType) && !x.p.toString().equals(y.p.toString())) {
					continue;
				}
				else if(xPType.equals(TripleValue.IRI) && xPType.equals(yPType) && x.p.toString().equals(y.p.toString())) {
					check2 = true;
				}
				
				//obj
				if(xOType.equals(TripleValue.VARIABLE) && !xOType.equals(yOType)) {
					continue;
				}
				else if(xOType.equals(TripleValue.VARIABLE) && xOType.equals(yOType)) {
					check3 = true;
				}
				
				if(xOType.equals(TripleValue.IRI) && !xOType.equals(yOType)) {
					continue;
				}
				else if(xOType.equals(TripleValue.IRI) && xOType.equals(yOType) && !x.o.toString().equals(y.o.toString())) {
					continue;
				}
				else if(xOType.equals(TripleValue.IRI) && xOType.equals(yOType) && x.o.toString().equals(y.o.toString())) {
					check3 = true;
				}
				
				if(xOType.equals(TripleValue.LITERAL) && !xOType.equals(yOType)) {
					continue;
				}
				else if(xOType.equals(TripleValue.LITERAL) && xOType.equals(yOType) && !x.o.toString().equals(y.o.toString())) {
					continue;
				}
				else if(xOType.equals(TripleValue.LITERAL) && xOType.equals(yOType) && x.o.toString().equals(y.o.toString())) {
					check3 = true;
				}
			}
			System.out.println(check1+" "+check2+" "+check3);
			if(check1 && check2 && check3) {
				nrOfFoundTriples++;
			}
		}
		
		if(nrOfFoundTriples == nrOfTriples) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
