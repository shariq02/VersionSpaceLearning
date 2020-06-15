import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
* Here, we are trying to move the file one directory to another,
* the main reason behind this method to move the file which is been processed, 
* so that it does not create redundancy. 
* 
* @author  Md Sharique 
* 
* 
* It is not completely implemented, still need to figure out how to use is with rest of the code
* Below method is not called in MainMethodToCallAllMethod (which is the main method for this project).
*/

public class MovingFileFromOneDirToAnother 
{
	public static boolean filemovetoanotherfolder(String sourcefolder, String destinationfolder, String filename) throws IOException 
	{
        boolean ismove = false;
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
	            File sourceFile = new File(sourcefolder + filename);
	            File destinationFile = new File(destinationfolder + filename);
	            inStream = new FileInputStream(sourceFile);
	            outStream = new FileOutputStream(destinationFile);
	            byte[] buffer = new byte[1024 * 4];
	            int length;  // copy the file content in bytes
	            while ((length = inStream.read(buffer)) > 0) 
	            {
	            	outStream.write(buffer, 0, length);
	            }


	            // delete the original file
	            sourceFile.deleteOnExit();
	            ismove = true;
	            System.out.println("File is moved successfully to archive folder!");

        	} 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
           inStream.close();
           outStream.close();
        }
        return ismove;
    }
}