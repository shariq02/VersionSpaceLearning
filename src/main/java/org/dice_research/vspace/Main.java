package org.dice_research.vspace;

import java.io.IOException;
import java.util.Scanner;

public class Main {  public static void main(String[] argas) throws IOException {
	
	String filePath = InputFileExtension.csvFilePath();
	
	System.out.println("===============================");
	System.out.println("Enter the choice from below (1 or 2): \n");
	System.out.println("1. Hiearchical Data \n");
	System.out.println("2. SPAB USECASES \n");
	
	Scanner console = new Scanner(System.in);
	
	int choice;
	choice = console.nextInt();
	console.close();
	
	switch (choice)
	{
			//For Calling Hierarchical data
		    //For calling modified version space
		case 1: CandidateElimination ceh = new CandidateElimination("Hierarchical", 
		    		"./src/test/resources/datafile/DataTest4.csv",
		    		"./src/test/resources/datafile/OntologyTest4.csv");
		    ceh.performElimication();
			break;
			
			   //For calling SPAB usecases
		case 2: CandidateElimination ces = new CandidateElimination("spab", 
		    		"./src/test/resources/datafile/PositiveQueries.txt",
					"./src/test/resources/datafile/NegativeQueries.txt");
		  		//ces.spabElimination(); // at present it is not in current branch
		  		break;
			
	
			//For Calling Normal data
		case 3:  CandidateElimination ce = new CandidateElimination("Normal", filePath);
                 ce.performElimication();
                 break;
                 
		default :
	         System.out.println("Invalid input");
	}
  }
}