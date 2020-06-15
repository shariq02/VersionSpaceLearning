import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* After reading the data from the file, It is sorted here, so it will be used in further
* methods (as input for generalization and specialization classes)
* 
* 
* @author  Md Sharique 
*/

//Having error, we will resolve it later, as this class is not needed as of now

public class SortingData 
{
	
	public static ArrayList<Instance> sortedData() throws IOException
	{
		ArrayList<Instance> recordsList = new ArrayList<>();
		ArrayList<Instance> sortedRecordsList = new ArrayList<>();
		recordsList = ReadFromCSVFile.csvReader();
		for (int i = 0; i <= recordsList.size() - 1; i++)
		{
			Collections.sort(recordsList.get(i), Collections.reverseOrder());
			sortedRecordsList.add(recordsList.get(i));
		}
		//Just to check the functionality, will be removed in next version of code
		System.out.println("Sorted List: "+sortedRecordsList);
		return sortedRecordsList;
	}
}