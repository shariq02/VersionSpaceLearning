package org.dice_research.vspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
* Here, we are trying to find the latest file in a directory, as there can be multiple files of same extension,
* so we are trying to read the latest file of a particular extension 
* 
* @author  Md Sharique 
* 
* 
* Below method will call the latest file in the directory,
* At present, this code has a bug, 
*/

public class FileUtils {
	 public static String pickLatestFile(String directoryFilePath) {


	        String Folder = new String(directoryFilePath);

	        File dir = new File(Folder);
	        File[] files = dir.listFiles();
	        if (files == null || files.length == 0) {
	            System.out.println("There is no file in the folder");
	        }

	        File lastModifiedFile = files[0];
	        for (int i = 1; i < files.length; i++) 
	        {
//	        	String extns = "";
//	        	int len = files[i].getName().lastIndexOf('.');
//				if (len >= 0) { 
//					extns = files[i].getName().substring(i);
//				//	System.out.println("extns: "+extns);
//					}
//				if(extns.equals("csv"))
//					System.out.println("File with csv="+files[i]);
//				else
//					System.out.println("File with other ext="+files[i]);
	        	
	            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	                lastModifiedFile = files[i];
	            }
	        }
	        String latestFile = lastModifiedFile.toString();
	        return latestFile;

	    }
	 
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
