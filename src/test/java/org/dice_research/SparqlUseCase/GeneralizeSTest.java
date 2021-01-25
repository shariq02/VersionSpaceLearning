package org.dice_research.SparqlUseCase;

import static org.junit.Assert.*;

import java.util.Set;

import org.dice_research.vspace.GeneralizeS;
import org.junit.Test;

public class GeneralizeSTest {

	@Test
	public void test() {
		Query[] queries = new Query[3];
		String qq1 = "SELECT ?v0, ?v2, ?v1, ?v3 {\r\n" + 
				"?v3 <http://xmlns.com/foaf/0.1/user> ?v0 .\r\n" + 
				"?v3 <http://dbpedia.org/property/birthPlace> <http://dbpedia.org/resource/Prague>\r\n" + 
				"}";
		String qq2 = "SELECT ?v2, ?v1 {\r\n" + 
				"ANY_ANY ANY_ANY ANY_ANY .\r\n" + 
				"?v2 <http://xmlns.com/foaf/0.1/depiction> ?v1\r\n" + 
				"}";
		queries[0]= new Query(qq1);
		queries[1]= new Query(qq2);
		SPARQLQueryParser.parse(queries[0]);
		SPARQLQueryParser.parse(queries[1]);
		queries[1].triples.add(0, new Triple("ANY_VARIABLE", "ANY_IRI", "ANY_ANY"));		
		System.out.println(queries[0].triples);
		System.out.println(queries[1].triples);
		GeneralizeS min_query = new GeneralizeS();
		Set<Query> outputlist;
		Set<Query> expoutput;
		Query h = (Query) queries[0].triples; //need to change, not correct
		Query q = (Query) queries[1].triples; //need to change, not correct
		outputlist = min_query.min_generalizations(h, q);
	}

}
