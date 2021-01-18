package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.graph.Node;
import org.apache.jena.query.*;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;



/**
 * converts the query string into Jena queries and triples
 * 
 * @author Abhratanu Surai
 * 
 */
public class JenaParser {

	/*
	 * Declaring variables to store the queries in ArrayList line will store each
	 * query by reading it line by line from the .txt file query will store every
	 * line as one of it's element
	 */
	public static String line = "";
	public static ArrayList<String> query = new ArrayList<>();
	public static Query jenaQuery;
	List<Node> subjects = new ArrayList<Node>();
	List<Node> predicates = new ArrayList<Node>();
	List<Node> objects = new ArrayList<Node>();
	TriplePath triple;

	// list of queries that will be read
	static List<Query> queries;

	public JenaParser() {
		queries = new ArrayList<Query>();
	}

	public Query getJenaQueryRepresentation(String queryString) {

		jenaQuery = QueryFactory.create(queryString);
		return jenaQuery;

	}

	public void createTriple(Query jenaQuery) {
		ElementWalker.walk(jenaQuery.getQueryPattern(),
				// For each element
				new ElementVisitorBase() {
					// When it's a block of triples
					public void visit(ElementPathBlock elementBlock) {
						// Go through all the triples
						System.out.println(elementBlock.toString());
						Iterator<TriplePath> triples = elementBlock.patternElts();
						while (triples.hasNext()) {
							// storing each triple of the query
							triple = triples.next();
							
							//stores all the subjects,predicates and objects of the query
							subjects.add(triple.getSubject());
							predicates.add(triple.getPredicate());
							objects.add(triple.getObject());
						}
					}
				});
	}

	public TriplePath getTriple() {
		return triple;
	}

}
