import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* Main method is still in its initial stage, and this version is just to check the flow 
* from directory path till sorted data,* here, calling  method for hypothesis class , 
* generalization and specialization classes is still to do
* 
* @author  Md Sharique 
*/

public class MainMethodToCallAllMethod {

	public static void main(String[] args) throws IOException {
		List<String> pathList = InputFileExtension.pathFile();
		
		@SuppressWarnings("rawtypes")
		Iterator i = pathList.iterator();
	      while (i.hasNext()) {
//	         System.out.println(i.next());
	         String extres = i.next().toString().trim();
	         System.out.println("extres "+extres);
	      }
	      //method calling for file extension
	     ArrayList<String> extensionResults = InputFileExtension.extensionOfFile();
			
			@SuppressWarnings("rawtypes")
			Iterator j = extensionResults.iterator();
		      while (j.hasNext()) {
//		         System.out.println(i.next());
		         String extres1 = j.next().toString().trim();
		         System.out.println("extresnw "+extres1);
		      }
		      
		      //method calling to select particular file from directory and processed that file
		      SelectionOfFile.selectionOfFile();
		      //method calling for sorting the data, once it is read from file
		      SortingData.sortedData();

	}

}
