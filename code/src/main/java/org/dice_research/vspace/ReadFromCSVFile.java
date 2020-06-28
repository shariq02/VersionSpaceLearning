package org.dice_research.vspace;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.opencsv.*;


/**
* Here, csvReader() method is trying to read the ".csv" file and storing it to Arraylist of Instance class
* 
* 
* @author  Md Sharique 
*/

public class ReadFromCSVFile 
{
	
	public static List<List<String>> csvReader() throws IOException 
	{
		String xfileLocation = "";
		List<String> pathOfFile = new ArrayList<String>();
		pathOfFile = InputFileExtension.pathFile();   //file path is returned from InputFileExtension.pathFile() method.
		ListIterator<String> Itr = pathOfFile.listIterator();	
		String fileName = "";
		while(Itr.hasNext())
		{
			fileName = Itr.next();
			if (fileName.endsWith(".csv"))
			{
				xfileLocation = fileName; //storing the file path of ".csv" file
			}
		}
		List<List<String>> records = new ArrayList<List<String>>();
		Reader reader = Files.newBufferedReader(Paths.get(xfileLocation));
		@SuppressWarnings("resource")
		CSVReader csvReader = new CSVReader(reader);
	    String[] values = null;
	    while ((values = csvReader.readNext()) != null) 
	    {
	        records.add(Arrays.asList(values));
	    }	
	    return records;
	}
}
