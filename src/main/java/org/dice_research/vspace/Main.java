package org.dice_research.vspace;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] argas) throws IOException {

		String filePath = InputFileExtension.csvFilePath();

		System.out.println("===============================");
		System.out.println("Enter the choice from below : \n");
		System.out.println("1. Categorical Data \n");
		System.out.println("2. Hiearchical Categorical Data \n");
		System.out.println("3. SPARQL Queries as Data \n");

		Scanner console = new Scanner(System.in);

		int choice;
		choice = console.nextInt();
		console.close();

		switch (choice) {
		// For Calling Normal data
		case 1:
			CandidateElimination ce = new CandidateElimination("Normal", filePath);
			ce.performElimination();
			break;

		// For Calling Hierarchical data
		// For calling modified version space
		case 2:
			CandidateElimination ceh = new CandidateElimination("Hierarchical",
					"./src/test/resources/datafile/DataTest4.csv", "./src/test/resources/datafile/OntologyTest4.csv");
			ceh.performElimination();
			break;

		// For calling SPAB usecase
		case 3:
			CandidateElimination ces = new CandidateElimination("spab",
					"./src/test/resources/datafile/virtuoso-pos.txt", "./src/test/resources/datafile/virtuoso-neg.txt");
			ces.spabElimination();
			break;

		default:
			System.out.println("Invalid input");
		}
	}
}