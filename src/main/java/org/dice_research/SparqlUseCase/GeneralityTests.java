package org.dice_research.SparqlUseCase;

public class GeneralityTests {
	
	public static void main(String[] args) {
		Query[] queries = new Query[2];
		SPARQLQueryParser p = new SPARQLQueryParser();
		
		String q1 = "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>\r\n" + 
				"SELECT *\r\n" + 
				"WHERE {\r\n" + 
				"    ?person foaf:name ?name .\r\n" + 
				"    ?person foaf:mbox ?email .\r\n" + 
				"}";
		String q2 = "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>\r\n" + 
				"SELECT ?name\r\n" + 
				"WHERE {\r\n" + 
				"    ?person foaf:name ?name .\r\n" + 
				"}";
		String q3 = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\r\n" + 
				"SELECT ?craft ?homepage\r\n" + 
				"{\r\n" + 
				"  ?craft foaf:name ?craft .\r\n" + 
				"  ?craft foaf:homepage ?homepage .\r\n" + 
				"  ?person foaf:mbox ?email .\r\n" + 
				"}";
		
		queries[0]= new Query(q1);
		queries[1]= new Query(q3);
		
		p.parse(queries[0]);
		p.parse(queries[1]);
		
		queries[0].renameVariables(queries[0].statements, queries[0].triples);
		queries[1].renameVariables(queries[1].statements, queries[1].triples);
		
		queries[0].replacePrefixVariables();
		queries[1].replacePrefixVariables();
		
		System.out.println("--- 1st query triples ---");
		for(Triple t: queries[0].triples) {
			System.out.println(t);
		}
		System.out.println();
		
		System.out.println("--- 2nd query triples ---");
		for(Triple t: queries[1].triples) {
			System.out.println(t);
		}
		System.out.println();
		
		System.out.println(queries[1].isMoreGeneralThan(queries[0]));
		System.out.println(queries[1].isMoreGeneralThan2(queries[0],0,null));
	}

}
