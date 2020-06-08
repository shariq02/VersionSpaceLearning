import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* Here, we are trying to find the latest file in a directory, as there can be multiple files of same extension,
* so we are trying to read the latest file of a particular extension 
* 
* @author  Md Sharique 
* 
* 
* It is not completely implemented, still need to figure out how to use is with rest of the code
*/

public class FileUtilsLatestFile {
	 public static String pickLatestFile(String directoryFilePath) {


	        String Folder = new String(directoryFilePath);

	        File dir = new File(Folder);
	        File[] files = dir.listFiles();
	        if (files == null || files.length == 0) {
	            System.out.println("There is no file in the folder");
	        }

	        File lastModifiedFile = files[0];
	        for (int i = 1; i < files.length; i++) {
	            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	                lastModifiedFile = files[i];
	            }
	        }
	        String k = lastModifiedFile.toString();

	        System.out.println(lastModifiedFile);
	        Path p = Paths.get(k);
	        String file = p.getFileName().toString();
	        return file;

	    }
	 
	 public static void main(String[] args) {
//		String result = FileUtilsLatestFile.pickLatestFileFromDownloads();
//		System.out.println(result);
		pickLatestFile(".\\src\\main\\resources\\datafile");
	 }
}
