import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class InputFilePathTest {

	@Test
	public void readFileRelativePath() {
		File file = new File(".\\src\\main\\resources\\Zoo_database.csv");
		assertTrue(file.exists());	
	}
	
	@Test
	public void listOFFileSize() throws IOException {
		List<String> result = InputFileExtension.pathFile();
		Assert.assertEquals(6, result.size());
	}
	
	@Test
	public void listOFFile() throws IOException {
		List<String> result = InputFileExtension.pathFile();
		List<String> expectedListOfFile = new ArrayList<String>();
		expectedListOfFile.add("\\.\\src\\main\\resources");
		expectedListOfFile.add(".\\src\\main\\resources\\StudentsPerformance.csv");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_json_file.json");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_richFile.rtf");
		expectedListOfFile.add(".\\src\\main\\resources\\Test_txt.txt");
		expectedListOfFile.add(".\\src\\main\\resources\\Zoo_database.csv");
		Assert.assertEquals(expectedListOfFile, result);
	}
}