package org.dice_research.vspace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class InputFilePathTest {
	@Test
	public void readFileRelativePath() {
		File file = new File(".\\src\\test\\resources\\datafile\\Zoo_database.csv");
		assertTrue(file.exists());
	}

	@Test
	public void listOfFileSize() throws IOException {
		List<String> result = InputFileExtension.pathFile();
		Assert.assertEquals(16, result.size());
	}

	@Test
	public void listOfFile() throws IOException {
		List<String> expectedListOfFile = new ArrayList<String>();
		expectedListOfFile.add("\\.\\src\\main\\resources");
		expectedListOfFile.add(".\\src\\main\\resources\\StudentsPerformance.csv");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_json_file.json");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_richFile.rtf");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_txt.txt");
		expectedListOfFile.add(".\\src\\main\\resources\\Zoo_database.csv");
		List<String> result = InputFileExtension.pathFile();
		Assert.assertEquals(expectedListOfFile, result);
	}

	@Test
	public void csvFilePathTest() throws IOException {
		String result = "";
		String expected = ".\\src\\test\\resources\\datafile\\Zoo_database.csv";
		result = InputFileExtension.csvFilePath();
		assertEquals(expected, result);
	}
}