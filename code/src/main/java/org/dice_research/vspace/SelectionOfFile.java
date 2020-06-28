package org.dice_research.vspace;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* Here, selectionOfFile() method will processed the file according to its extension
* We have used switch, so that in future we can add more file format like ".txt", ".data" etc 
* 
* 
* @author  Md Sharique 
*/

public class SelectionOfFile 
{
	@SuppressWarnings("resource")
	public static void selectionOfFile() throws IOException 
	{
		
		ArrayList<String> extensionResults = InputFileExtension.extensionOfFile();
		Scanner input = new Scanner(System.in);
		if (extensionResults.contains("csv") && extensionResults.contains("json"))
	    {
	    	 System.out.println("Choose the data file (csv or json): ");
	    	 String fileExtension = input.next();
	    	 if (fileExtension.equals("csv"))
	    	 {
	    		 //To read data from CSV file
	    		 ReadFromCSVFile.csvReader();
	    	 }
	    	 else if (fileExtension.equals("json"))
	    	 {
	    		 System.out.println("json is fired"); //Json file reader method to be added here
	    	 }
	    }	
	    else if (!extensionResults.contains("csv") && extensionResults.contains("json"))
	    {
	    	System.out.println("only json is fired"); //Json file reader method to be added here

	    }
	    else if (extensionResults.contains("csv") && !extensionResults.contains("json"))
	    {
	    	ReadFromCSVFile.csvReader();

	    }
	    else 
	    {
	    	System.out.println("Other File formats are not supported yet.");
	    }
	}
}
