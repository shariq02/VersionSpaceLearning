package org.dice_research.vspace;

import java.io.IOException;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;



@SuppressWarnings("deprecation")
public class InputFileExtensionTest {
  @Test
  public void listOfFileExtensionCount() throws IOException {
    ArrayList<String> result = InputFileExtension.extensionOfFile();
	Assert.assertEquals(16, result.size());
  }
  
  @Test
  public void listOfFileExtension() throws IOException {
    ArrayList<String> expectedValue = new ArrayList<String>();
	expectedValue.add("");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("csv");
	expectedValue.add("rtf");
	expectedValue.add("txt");
	expectedValue.add("json");
	expectedValue.add("csv");
	ArrayList<String> result = InputFileExtension.extensionOfFile();
	Assert.assertEquals(expectedValue, result);
  }
}