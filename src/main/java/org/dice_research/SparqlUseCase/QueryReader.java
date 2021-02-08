package org.dice_research.SparqlUseCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryReader {
	public static List<String> readQueries(String filepath) throws FileNotFoundException{
		String fileContent = "";
		File file = new File(filepath);
		if(file.exists()) {
			try {
				fileContent = Files.readString(file.toPath());
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new FileNotFoundException();
		}
		return new ArrayList<String>(Arrays.asList(fileContent.split("\n")));
	}
}
