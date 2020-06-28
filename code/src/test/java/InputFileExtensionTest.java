import java.io.IOException;
import java.util.ArrayList;

import org.dice_research.vspace.InputFileExtension;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class InputFileExtensionTest {

	@Test
	public void listOfFileExtensionCount() throws IOException {
		ArrayList<String> result = InputFileExtension.extensionOfFile();
		Assert.assertEquals(6, result.size());
	}
	
	@Test
	public void listOfFileExtension() throws IOException {
		ArrayList<String> result = InputFileExtension.extensionOfFile();
		ArrayList<String> expectedValue = new ArrayList<String>();
		expectedValue.add("");
		expectedValue.add("csv");
		expectedValue.add("json");
		expectedValue.add("rtf");
		expectedValue.add("txt");
		expectedValue.add("csv");
		Assert.assertEquals(expectedValue, result);
	}
}