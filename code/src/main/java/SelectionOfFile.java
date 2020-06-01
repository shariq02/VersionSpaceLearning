import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* Here, selectionOfFile() method will processed the file according to its extension
* We have used switch, so that in future we can add more file format like ".txt", ".data" etc 
* 
* 
* @author  Md Sharique 
*/

public class SelectionOfFile 
{
	public static void selectionOfFile() throws IOException 
	{
		
		ArrayList<String> extensionResults = InputFileExtension.extensionOfFile();
		@SuppressWarnings("rawtypes")
		Iterator i = extensionResults.iterator();
	    while (i.hasNext()) 
	    {
	         String extres = i.next().toString().trim();
	         switch(extres) 
	         {
	      		case "json":
	      		//Just to check the functionality, will be removed in next version of code
	      			System.out.println("i m fired, json"); 
	      			break;
	      		case "csv":
	      			List<List<String>> recordsList = new ArrayList<List<String>>();
	      			recordsList = ReadFromCSVFile.csvReader();
	      		//Just to check the functionality, will be removed in next version of code
	      			System.out.println("recordsList............"+recordsList);
	      			break;
	      		default:
	      		//Just to check the functionality, will be removed in next version of code
	      			System.out.println("File Extension ." +extres +" is not supported as of now. " );
	      			break;
	         }
	    }
	}
}
