package org.dice_research.SparqlUseCase;

import org.apache.jena.query.*;
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

public class RepresentationFormat {

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
		System.out.println("Press N if Input file contains Negative queries \n OR \n Press P if Input file contains Positive queries :");
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
			System.out.println("Variable names after parsing for Query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------");
			System.out.println(jenaQuery.getResultVars());
			System.out.println("\n");
			
			System.out.println("----------------------------------------------");
			System.out.println("Original query representation for query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------\n");
			System.out.println(sparqlUnit.getOriginalString());

			System.out.println("----------------------------------------------");
			System.out.println("Parsed query representation for query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------\n");
			System.out.println(sparqlUnit.getJenaStringRepresentation());

			System.out.println("----------------------------------------------");
			System.out.println("Triples and Constraintsfor Query_" + (i + 1) + ":");
			System.out.println("----------------------------------------------\n");
			System.out.println(jenaQuery.getQueryPattern());
			System.out.println("========================================================================================\n\n");

		}

	}
}