package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jena.graph.Node;
import org.apache.jena.query.*;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

import org.dice_research.spab.SpabApi;
import org.dice_research.spab.input.SparqlUnit;

/**
 * converts the query string into Jena queries and triples
 * 
 * @author Abhratanu Surai
 * 
 */
public class JenaParser extends SPARQLQueryParser {

	//public static String line = "";
	public static ArrayList<String> query = new ArrayList<>();
	public static Query jenaQuery, originalQuery;
	List<Node> subjects = new ArrayList<Node>();
	List<Node> predicates = new ArrayList<Node>();
	List<Node> objects = new ArrayList<Node>();
	TriplePath triple;
	List<TriplePath> allTriples = new ArrayList<TriplePath>();
	org.dice_research.SparqlUseCase.Query q;

	// list of queries that will be read
	static List<Query> queries;

	public JenaParser() {
		queries = new ArrayList<Query>();
	}
	
	/**
	 * Get jena query from string
	 **/
	public Query getJenaQueryRepresentation(String queryString) {

		jenaQuery = QueryFactory.create(queryString);
		return jenaQuery;

	}

	/**
	 * Creating triples from query using Jena
	 */
	public void createTriple(Query jenaQuery) {
		ElementWalker.walk(jenaQuery.getQueryPattern(),
				// For each element
				new ElementVisitorBase() {
					// When it's a block of triples
					public void visit(ElementPathBlock elementBlock) {
						// Go through all the triples
						//System.out.println(elementBlock.toString());
						Iterator<TriplePath> triples = elementBlock.patternElts();
						int i = 0;
						while (triples.hasNext()) {
							// storing each triple of the query
							//triple = triples.next();
							allTriples.add(triples.next());
							// stores all the subjects,predicates and objects of the query
							subjects.add(allTriples.get(i).getSubject());
							predicates.add(allTriples.get(i).getPredicate());
							objects.add(allTriples.get(i).getObject());
							i++;
						}
					}
				});
	}

	public List<TriplePath> getTriple() {
		return allTriples;
	}

	/**
	 * Returns Key-Value mapping of prefixes in terms of prefix variables and URIs
	 */
	public Map<String, String> getPrefixes(Query jenaQuery) {
		Map<String, String> prefixes = jenaQuery.getPrefixMapping().getNsPrefixMap();
		return prefixes;
	}

	/**
	 * This method creates a query that we have designed for this project, from
	 * input string
	 * 
	 * @param queryString
	 * @return Query
	 */
	public org.dice_research.SparqlUseCase.Query getNonJenaQueryRepresentation(String queryString) {
		q = new org.dice_research.SparqlUseCase.Query();
		q.setQuery(queryString);
		return q;
	}

	/**
	 * Overrides the parse method. This method bypasses the logic used for parse()
	 * method in the parent class, instead this method will feed jena parsed
	 * informations to adequate with the existing query structure and parser.
	 * 
	 * @param query
	 */

	public static void parse(org.dice_research.SparqlUseCase.Query query) {
		String queryStr = query.query.toString();
		SpabApi spab = new SpabApi();
		SparqlUnit sparqlUnit;
		spab.addNegative(queryStr);
		sparqlUnit = spab.getInput().getNegatives().get(0);
		jenaQuery = QueryFactory.create(sparqlUnit.getJenaStringRepresentation());
		originalQuery = QueryFactory.create(sparqlUnit.getOriginalString());
		
		List<String> qSubjects = new ArrayList<String>();
		List<String> qPredicates = new ArrayList<String>();
		List<String> qObjects = new ArrayList<String>();
		
		List<String> originalVariables = new ArrayList<String>();
		List<String> replacedVariables = new ArrayList<String>();
		Statement s = new SelectStatement();
		
		
		/**
		 * Jena parsed triple values are passed to our Query class
		 * (This Query class is created by us and not a Jena type Query)
		 */
		ElementWalker.walk(jenaQuery.getQueryPattern(),
				// For each element
				new ElementVisitorBase() {
					// When it's a block of triples
					public void visit(ElementPathBlock elementBlock) {
						// Go through all the triples
						Iterator<TriplePath> triples = elementBlock.patternElts();
						while (triples.hasNext()) {
							// storing each triple of the query
							TriplePath triplet = triples.next();

							// stores all the subjects,predicates and objects of the query
							qSubjects.add(triplet.getSubject().toString());
							qPredicates.add(triplet.getPredicate().toString());
							qObjects.add(triplet.getObject().toString());
						}
					}
				});

		for (int i = 0; i < qSubjects.size(); i++) {
			query.triples.add(new Triple(qSubjects.get(i), qPredicates.get(i), qObjects.get(i)));

		}

		/**
		 * Jena Parsed variable values are stored. Both the values of variables before
		 * and after parsing are stored as a map.
		 */
		originalVariables = originalQuery.getResultVars();
		replacedVariables = jenaQuery.getResultVars();
		for (int i = 0; i < originalVariables.size(); i++) {
			((SelectStatement) s).putVariable(originalVariables.get(i), replacedVariables.get(i));
		}
		query.statements.add(s);

		query.prefixes = jenaQuery.getPrefixMapping().getNsPrefixMap();

	}

}
