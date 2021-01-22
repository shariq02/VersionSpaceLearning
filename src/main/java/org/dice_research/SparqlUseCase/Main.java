package org.dice_research.SparqlUseCase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.apache.jena.query.Query;
import org.dice_research.spab.SpabApi;
import org.dice_research.spab.input.SparqlUnit;

/**
 * Query strings are read from the file and Jena parser is called to parse the
 * queries.
 * 
 * @author Abhratanu Surai
 * 
 */
public class Main {
	public static void main(String[] args) throws IOException {

		String line = "";
		ArrayList<String> query = new ArrayList<>();
		Query jenaQuery,originalQuery;
		

		/*
		 * Taking input from user to understand what type of queries are provided as
		 * input
		 */

		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Press N if Input file contains Negative queries \nOR \nPress P if Input file contains Positive queries :");
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
			if (queryType.equals("N"))
				spab.addNegative(query.get(i));
			else if (queryType.equals("P"))
				spab.addPositive(query.get(i));
			else
				System.out.println(" Please enter a valid File type. P for positive or N for negative.");

		}

		for (int i = 0; i < query.size(); i++) {
		//for (int i = 0; i < 3; i++) {
			if (queryType.equals("N"))
				sparqlUnit = spab.getInput().getNegatives().get(i);
			else
				sparqlUnit = spab.getInput().getPositives().get(i);

			JenaParser jp = new JenaParser();
			jenaQuery = jp.getJenaQueryRepresentation(sparqlUnit.getJenaStringRepresentation());
			originalQuery = jp.getJenaQueryRepresentation(sparqlUnit.getOriginalString());
			
			System.out.println("----<<<<Query No.: " + (i+1) + ">>>>----\n");
			System.out.println("----Original Query----\n");
			System.out.println(originalQuery.toString());
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----Original Variables----\n");
			System.out.println(originalQuery.getResultVars());
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----Replaced Variables----\n");
			System.out.println(jenaQuery.getResultVars());
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----Prefix Mapping----\n");
			Map<String, String> prefixes = jp.getPrefixes(jenaQuery);
			for (Map.Entry<String, String> entry : prefixes.entrySet())
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----All Triples----\n");
			jp.createTriple(jenaQuery);
			System.out.println(jp.getTriple());
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----All Triple elements----\n");
			System.out.println("Subjects -->" + jp.subjects);
			System.out.println("Predicates -->" + jp.predicates);
			System.out.println("Objects -->" + jp.objects);
			System.out.println("-----------------------------------------------------------------------");
			
			System.out.println("----Our Query representation----\n");
			System.out.println(jp.getNonJenaQueryRepresentation(sparqlUnit.getOriginalString()).query);
			JenaParser.parse(jp.getNonJenaQueryRepresentation(sparqlUnit.getOriginalString()));
			System.out.println("=======================================================================\n\n");
		}
	}

}
