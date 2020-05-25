import java.io.*;

public class CSVReader {
	public static final String DELIMITER = ",";
	   public static void read(String csvFile) {
	      try {
	         File file = new File(csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         while((line = br.readLine()) != null) {
	            tempArr = line.split(DELIMITER);
	            for(String tempStr : tempArr) {
	               System.out.print(tempStr + " ");
	            }
	            System.out.println();
	         }
	         br.close();
	         } 
	      catch(IOException ioe) {
	            ioe.printStackTrace();
	         }
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // csv file to read
	      String csvFile = "C:\\Users\\Sharique\\Desktop\\Zoo_database.csv";
	       CSVReader.read(csvFile);

	}

}
