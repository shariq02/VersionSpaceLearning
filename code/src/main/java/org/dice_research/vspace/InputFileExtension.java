package org.dice_research.vspace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

/**
* Here, we are trying to find all the files present in a directory from pathFile() method
* and using that result, we are finding the extension of the file, so that we can processed
* for any particular file. as of now, data from ".csv" and ".json" file can be 
* read and stored in list for further use
* 
* @author  Md Sharique 
*/


public class InputFileExtension 
{
	
	@SuppressWarnings("resource")
	
	//Below method is used to find all the files in a given directory.
	public static List<String> pathFile() throws IOException
	{
		Stream<Path> paths = Files.walk(Paths.get(".\\src\\test\\resources\\datafile"));
		
		List<String> pathList = paths.map(p -> {
            if (Files.isDirectory(p)) {
            	return "\\" + p.toString();
            }
            return p.toString();
        }).collect(Collectors.toList());
		return pathList;
	}	
	
	//After finding all the files in a given directory, below function is used to determined
	//the extension of the file, so that on the basis of extension (".csv" and ".json")
	//readFilemethod will be called
	public static ArrayList<String> extensionOfFile() throws IOException
	{	
		List<String> pathOfFile = new ArrayList<String>();
		pathOfFile = pathFile();
		
		ListIterator<String> Itr = pathOfFile.listIterator();
		
		ArrayList<String> extensionResult = new ArrayList<String>();
		
		String extension = "";
		while(Itr.hasNext())
		{
			extension = FilenameUtils.getExtension(Itr.next());
			extensionResult.add(extension);
		}
		return extensionResult;
	}
}
