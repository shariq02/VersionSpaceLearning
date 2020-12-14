package org.dice_research.SparqlUseCase;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class SPARQLQueryParser{
	//list of queries that will be read
	static List<Query> queries;
	
	public SPARQLQueryParser() {
		queries = new ArrayList<Query>();
	}
	
	public String removePrefixes(Query query){
		String toReturn = "";
		//remove all newlines and unnecessary whitespace from the query
		query.setQuery(query.getQuery().replaceAll("\\s{2,}", " ").trim());
		String[] tmp = query.getQuery().split(" ");
		int i=0;
		while(i<tmp.length){
			//if this holds we know that the next two words will be the prefix variable and uri
			if(tmp[i].toLowerCase().equals("prefix")){
				String v = tmp[i+1].substring(0,tmp[i+1].length()-1);
				String u = tmp[i+2].substring(1,tmp[i+2].length()-1);
				query.prefixes.put(v,u);
				i+=3;
			}
			else{
				//return the query without the prefixes
				for(int j =i;j<tmp.length;j++) {
					toReturn = toReturn+tmp[j]+" ";
				}
				break;
			}
		}
		return toReturn;
	}
	
	//parses the given query
	public void parse(Query query){
		query.setQuery(removePrefixes(query));
		String[] tmp = query.getQuery().trim().split(" ");

		boolean searchForTriples = false;
		for(int i=0;i<tmp.length;i++) {
			//if next word is a SELECT statement add and rename all variables present in the statement to the vars map
			if(tmp[i].toLowerCase().equals("select")) {
				i++;
				Statement s = new SelectStatement();
				while(!tmp[i].equals("{")) {
					if(tmp[i].equals("*")) {
						s.putVariable(tmp[i], "*");
					}
					else if(tmp[i].startsWith("?")){
						s.putVariable(tmp[i], "?v"+query.j);
						query.j++;
					}
					i++;
				}
				query.statements.add(s);
			}
			
			if(searchForTriples) {
				if(tmp[i].equals("}")) {
					//nothing, empty brackets
				}
				else if(tmp[i+3].equals(".") || tmp[i+3].equals("}") || tmp[i+3].equals("{") || Query.reservedWords.contains(tmp[i+3])) {
					query.triples.add(new Triple(tmp[i], tmp[i + 1], tmp[i + 2]));
					i += 3;
				}
				else if(tmp[i].equals("{")) {
					//embedded brackets
				}
				else {
					System.out.println("Some kind of error happened while parsing: " + tmp[i-1] + " " +tmp[i] + " " +tmp[i+1]);
				}
				
				if(!tmp[i].equals(".")) {
						searchForTriples = false;
				}
				
			}
			else {
				if(tmp[i].equals("{") && !Query.reservedWords.contains(tmp[i+1])) {
					searchForTriples = true;
				}
				
				if((i<tmp.length-1) && tmp[i+1].startsWith("?")){
					searchForTriples = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SPARQLQueryParser p = new SPARQLQueryParser();
		String query1 = "PREFIX  db:   <http://dbpedia.org/ontology/>\r\n" + 
				"PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX  property: <http://dbpedia.org/property/>\r\n" + 
				"\r\n" + 
				"SELECT  *\r\n" + 
				"WHERE\r\n" + 
				"  { ?musician <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> db:MusicalArtist .\r\n" + 
				"    ?musician db:activeYearsStartYear ?activeyearsstartyear .\r\n" + 
				"    ?musician db:associatedBand ?associatedband .\r\n" + 
				"    ?musician db:birthPlace ?birthplace .\r\n" + 
				"    ?musician db:genre ?genre .\r\n" + 
				"    ?musician db:recordLabel ?recordlable .\r\n" + 
				"    ?musician property:voiceType ?voicetype .\r\n" + 
				"    ?artist property:artist ?musician .\r\n" + 
				"    ?starring db:starring ?musician .\r\n" + 
				"    ?voice db:voice ?musician .\r\n" + 
				"    ?writer db:writer ?musician\r\n" + 
				"  }";
		String query2 = "PREFIX  :     <http://dbpedia.org/resource/>\r\n" + 
				"PREFIX  dc:   <http://purl.org/dc/elements/1.1/>\r\n" + 
				"PREFIX  dbpedia2: <http://dbpedia.org/property/>\r\n" + 
				"PREFIX  dbpedia-owl: <http://dbpedia.org/ontology/>\r\n" + 
				"PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX  yago: <http://dbpedia.org/class/yago/>\r\n" + 
				"PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX  dbo:  <http://dbpedia.org/property/>\r\n" + 
				"PREFIX  dbpedia: <http://dbpedia.org/>\r\n" + 
				"PREFIX  xsd:  <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX  owl:  <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX  skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + 
				"\r\n" + 
				"SELECT  ?x ?f\r\n" + 
				"WHERE\r\n" + 
				"  { ?x rdf:type dbpedia-owl:Mountain .\r\n" + 
				"    ?x dbpedia2:firstAscent ?f .\r\n" + 
				"    ?y rdf:type dbpedia-owl:Person .\r\n" + 
				"    ?y dbpedia2:birthDate ?b\r\n" + 
				"    FILTER ( ?y = :Tim_Berners-Lee )\r\n" + 
				"    FILTER ( ?f <= xsd:dateTime(?b) )\r\n" + 
				"  }";
		String query3 = "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX  foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"PREFIX  dbp:  <http://dbpedia.org/property/>\r\n" + 
				"PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"\r\n" + 
				"SELECT DISTINCT  ?resource ?uri ?wtitle ?comment ?image\r\n" + 
				"WHERE\r\n" + 
				"  { {   { ?resource foaf:page <http://en.wikipedia.org/wiki/Sellafield> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/AMEC> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Construction> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Page> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Position_(team_sports)> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Engineering> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Liquid_(The_Rasmus_song)> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Scouting> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Hell_of_a_Tester> }\r\n" + 
				"      UNION\r\n" + 
				"        { ?resource foaf:page <http://en.wikipedia.org/wiki/Scout_Leader> }\r\n" + 
				"    }\r\n" + 
				"    ?resource foaf:page ?uri .\r\n" + 
				"    ?resource rdfs:comment ?wtitle\r\n" + 
				"    FILTER langMatches(lang(?wtitle), \"en\")\r\n" +
				"    ?test rdfs:something ?test2" +
				"    OPTIONAL\r\n" + 
				"      { ?resource rdfs:comment ?comment\r\n" + 
				"        FILTER langMatches(lang(?comment), \"en\")\r\n" + 
				"      }\r\n" + 
				"    OPTIONAL\r\n" + 
				"      { ?resource foaf:depiction ?image }\r\n" + 
				"  }";
		
		queries.add(new Query(query1));
		queries.add(new Query(query2));
		queries.add(new Query(query3));
		int i = 1;
		p.parse(queries.get(i));
		System.out.println(queries.get(i).getQuery());
		System.out.println();
		System.out.println("--- prefixes ---");
		for(Map.Entry<String, String> e: queries.get(i).prefixes.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
		System.out.println();
		System.out.println("--- variables ---");
		for(Statement s: queries.get(i).statements) {
			System.out.println(s);
		}
		System.out.println();
		System.out.println("--- triples before renaming ---");
		for(Triple t: queries.get(i).triples) {
			System.out.println(t);
		}
		System.out.println();
		System.out.println("--- triples after renaming ---");
		System.out.println("Renamed: " + queries.get(i).renameVariables(queries.get(i).statements, queries.get(i).triples));
		for(Triple t: queries.get(i).triples) {
			System.out.println(t);
		}
		
		System.out.println();
		System.out.println("--- variables after extra variables renamed ---");
		for(Statement s: queries.get(i).statements) {
			System.out.println(s);
		}
		
		
		queries.get(i).replacePrefixVariables();
		System.out.println();
		System.out.println();
		System.out.println("--- triples after prefix replacement ---");
		for(Triple t: queries.get(i).triples) {
			System.out.println(t);
		}
	}
}