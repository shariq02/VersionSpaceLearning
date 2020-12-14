package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.List;

public class GeneralizeQuery {
	public static ArrayList<Triple> query1Triples = new ArrayList<Triple>();
	public static ArrayList<Triple> query2Triples = new ArrayList<Triple>();
	public static ArrayList<Triple> generalizedTriples = new ArrayList<Triple>();
	public static List<Statement> statements1 = new ArrayList<Statement>();
	public static List<Statement> statements2 = new ArrayList<Statement>();
	private String subject = new String();
	private String predicate = new String();
	private String object = new String();
	
	public GeneralizeQuery(String query1, String query2) {
		SPARQLQueryParser q1Parser = new SPARQLQueryParser();
		SPARQLQueryParser q2Parser = new SPARQLQueryParser();
		
		q1Parser.parse(query1);
		q2Parser.parse(query2);
		
		
		for (Statement s : q1Parser.statements) {
			statements1.add(s);
			//System.out.println(s);
		}
		for (Statement s : q2Parser.statements) {
			statements2.add(s);
			//System.out.println(s);
		}
		
		q1Parser.renameVariables(q1Parser.statements, q1Parser.triples);
		q2Parser.renameVariables(q2Parser.statements, q2Parser.triples);

		
		for (Triple t : q1Parser.triples) {
			query1Triples.add(t);
			
		}
		
		for (Triple t : q2Parser.triples) {
			query2Triples.add(t);
			
		}
		
		int query1TripleCount = query1Triples.size();
		int query2TripleCount = query2Triples.size();
		
		if (query1TripleCount==query2TripleCount || query1TripleCount>query2TripleCount || query1TripleCount<query2TripleCount) {
			
			for(int i = 0; i < Math.min(query1TripleCount, query2TripleCount); i++) {
				
				if(query1Triples.get(i).s.equals(query2Triples.get(i).s)) {
					subject = query1Triples.get(i).s;
				}
				else if(!query1Triples.get(i).s.equals(query2Triples.get(i).s)){
					if(query1Triples.get(i).s.startsWith("?") && query2Triples.get(i).s.startsWith("?"))
						subject = "Variable";
					else if(query1Triples.get(i).s.startsWith("?") && query2Triples.get(i).s.startsWith("<") || query1Triples.get(i).s.startsWith("<") && query2Triples.get(i).s.startsWith("?"))
						subject = "?";
					else if(query1Triples.get(i).s.startsWith("<") && query2Triples.get(i).s.startsWith("<"))
						subject = "URI";
				}
				
				else if(query1Triples.get(i).p.equals(query2Triples.get(i).p)){
					predicate = query1Triples.get(i).p;
				}
				else if(!query1Triples.get(i).p.equals(query2Triples.get(i).p)){
					if(query1Triples.get(i).p.startsWith("?") && query2Triples.get(i).p.startsWith("?"))
						predicate = "Variable";
					else if(query1Triples.get(i).p.startsWith("?") && query2Triples.get(i).p.startsWith("<") || query1Triples.get(i).p.startsWith("<") && query2Triples.get(i).p.startsWith("?"))
						predicate = "?";
					else if(query1Triples.get(i).p.startsWith("<") && query2Triples.get(i).p.startsWith("<"))
						predicate = "URI";
				}
				
				else if(query1Triples.get(i).o.equals(query2Triples.get(i).o)){
					object = query1Triples.get(i).o;
				}
				else if(!query1Triples.get(i).o.equals(query2Triples.get(i).o)){
					if(query1Triples.get(i).o.startsWith("?") && query2Triples.get(i).o.startsWith("?"))
						object = "Variable";
					else if(query1Triples.get(i).o.startsWith("?") && query2Triples.get(i).o.startsWith("<") || query1Triples.get(i).o.startsWith("<") && query2Triples.get(i).o.startsWith("?"))
						object = "?";
					else if(query1Triples.get(i).o.startsWith("<") && query2Triples.get(i).o.startsWith("<"))
						object = "URI";
				}
				generalizedTriples.add(new Triple(subject,predicate,object));
			}
			
			
		}
		else if(query1TripleCount>query2TripleCount) {
			generalizedTriples.add(new Triple("*","*","*"));
		}
		else if(query1TripleCount<query2TripleCount) {
			generalizedTriples.add(new Triple("*","*","*"));
		}

	}
	

	

	public static void main(String aa[]) {

		String query1 = "PREFIX  db:   <http://dbpedia.org/ontology/>\r\n"
				+ "PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\r\n"
				+ "PREFIX  property: <http://dbpedia.org/property/>\r\n" + "\r\n" + "SELECT  *\r\n" + "WHERE\r\n"
				+ "  { ?musician <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> db:MusicalArtist .\r\n"
				+ "    ?musician db:activeYearsStartYear ?activeyearsstartyear .\r\n"
				+ "    ?musician db:associatedBand ?associatedband .\r\n"
				+ "    ?musician db:birthPlace ?birthplace .\r\n" + "    ?musician db:genre ?genre .\r\n"
				+ "    ?musician db:recordLabel ?recordlable .\r\n"
				+ "    ?musician property:voiceType ?voicetype .\r\n" + "    ?artist property:artist ?musician .\r\n"
				+ "    ?starring db:starring ?musician .\r\n" + "    ?voice db:voice ?musician .\r\n"
				+ "    ?writer db:writer ?musician\r\n" + "  }";
		String query2 = "PREFIX  :     <http://dbpedia.org/resource/>\r\n"
				+ "PREFIX  dc:   <http://purl.org/dc/elements/1.1/>\r\n"
				+ "PREFIX  dbpedia2: <http://dbpedia.org/property/>\r\n"
				+ "PREFIX  dbpedia-owl: <http://dbpedia.org/ontology/>\r\n"
				+ "PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\r\n"
				+ "PREFIX  yago: <http://dbpedia.org/class/yago/>\r\n"
				+ "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX  dbo:  <http://dbpedia.org/property/>\r\n" + "PREFIX  dbpedia: <http://dbpedia.org/>\r\n"
				+ "PREFIX  xsd:  <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX  owl:  <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX  skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + "\r\n" + "SELECT  ?x ?f\r\n"
				+ "WHERE\r\n" + "  { ?x rdf:type dbpedia-owl:Mountain .\r\n" + "    ?x dbpedia2:firstAscent ?f .\r\n"
				+ "    ?y rdf:type dbpedia-owl:Person .\r\n" + "    ?y dbpedia2:birthDate ?b\r\n" + "  }";
		
		new GeneralizeQuery(query1, query2);

		
		for (Statement s : statements1) {
			System.out.println(s);
			//System.out.println(s);
		}
		for (Statement s : statements2) {
			
			System.out.println(s);
		}
		for (Triple t : query1Triples) {
			System.out.println(t.s+"  "+ t.p +"  " +t.o);
		}
		System.out.println();
		for (Triple t : generalizedTriples) {
			System.out.println(t);
		}

	}

}
