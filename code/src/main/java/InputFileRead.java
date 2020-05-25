//import java.io.FileReader;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Arrays;
//import static java.lang.System.*;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

//import au.com.bytecode.opencsv.CSVReader;



public class InputFileRead {
	
	/**
	 * Identify file type of file with provided path and name
	 * using JDK 7's Files.probeContentType(Path).
	 *
	 * @param fileName Name of file whose type is desired.
	 * @return String representing identified type of file with provided name.
	 */
	
	//below method is to identify different file extension
	/*public String identifyFileType(final String fileName)
	{
	   String fileType = "Undetermined";
	   final File file = new File(fileName);
	   try
	   {
	      fileType = Files.probeContentType(file.toPath());
	   }
	   catch (IOException ioException)
	   {
	      out.println(
	           "ERROR: Unable to determine file type for " + fileName
	              + " due to exception " + ioException);
	   }
	   return fileType;
	}*/
	
	

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Build reader instance
	      //Read all rows at once
		/*List<String[]> allRows = null;
		try {
			CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Sharique\\Desktop\\Zoo_database.csv")); //, ',', '"', 1); //"data.csv" is at present hard-coded, needs to be change for dynamic file processing
			   
			  allRows = reader.readAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	      //Read CSV line by line and use the string array as you want
	     for(String[] row : allRows){
	        System.out.println(Arrays.toString(row));
	}*/
		
		ReadFromFile.setArray();
	}
}
