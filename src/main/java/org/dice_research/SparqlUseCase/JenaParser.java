package org.dice_research.SparqlUseCase;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import org.apache.jena.graph.Node;
import org.apache.jena.query.* ;
import org.apache.jena.query.Query;
import org.apache.jena.rdf.model.*;

import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.syntax.ElementPathBlock;
import org.apache.jena.sparql.syntax.ElementVisitorBase;
import org.apache.jena.sparql.syntax.ElementWalker;

/*import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;*/
import org.dice_research.spab.*;
import org.dice_research.spab.candidates.six.Expression;

import org.apache.jena.query.*;
import org.apache.jena.query.Query;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//import javax.management.Query;

import org.dice_research.spab.SpabApi;
import org.dice_research.spab.input.SparqlUnit;

/**
 * Reads the query strings from input files 
 * and converts it to Jena queries and triples
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
	
	


	public static void main(String[] args) throws IOException {
		
		/*
		 * Taking input from user to understand what type of queries are provided as
		 * input
		 */
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Press N if Input file contains Negative queries \nOR \nPress P if Input file contains Positive queries :");
		String queryType = sc.nextLine();
		sc.close();
		
		try {

			/*
			 * Reading the queries from .txt files and storing it
			 */
			BufferedReader bufReader = new BufferedReader(new FileReader(
					"D:\\SPAB-master\\SPAB-master\\src\\main\\resources\\iguana-2018-01-20\\Fuseki-positive.txt"));
			line = bufReader.readLine();
			while (line != null) {
				query.add(line);
				line = bufReader.readLine();
			}
			bufReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		SpabApi spab = new SpabApi();
		SparqlUnit sparqlUnit;

		for (int i = 0; i < query.size(); i++) {
			if(queryType.equals("N"))
				spab.addNegative(query.get(i));
			else if(queryType.equals("P"))
				spab.addPositive(query.get(i));
			else
				System.out.println(" Please enter a valid File type; P for positive");
			

		}

		
		for (int i = 0; i < query.size(); i++) {
			if(queryType .equals("N"))
				sparqlUnit = spab.getInput().getNegatives().get(i);
			else
				sparqlUnit = spab.getInput().getPositives().get(i);
			
			jenaQuery = QueryFactory.create(sparqlUnit.getJenaStringRepresentation());
			
			System.out.println("----------------------------------------------");
			System.out.println("All the triples for Query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------");
			
			ElementWalker.walk(jenaQuery.getQueryPattern(),
				    // For each element
				    new ElementVisitorBase() {
				        // When it's a block of triples
				        public void visit(ElementPathBlock el) {
							// Go through all the triples
				            Iterator<TriplePath> triples = el.patternElts();
				            while (triples.hasNext()) {
				                // Printing all the triples
				            	System.out.println(triples.next().toString());
				            }
				        }
				    }
				);
			
			System.out.println("\n");
			
			System.out.println("----------------------------------------------");
			System.out.println("Parsed query representation for query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------");
			System.out.println(jenaQuery.toString());
			System.out.println("\n");
			
			
			System.out.println("========================================================================================\n\n");

		}

	}
}
