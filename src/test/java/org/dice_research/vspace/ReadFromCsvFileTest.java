package org.dice_research.vspace;

import java.io.IOException;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class ReadFromCsvFileTest {
	@Test
	public void test() throws IOException {
		List<List<String>> result = ReadFromcsvFile.csvReader();
		Assert.assertEquals(10, result.size());
	}
}