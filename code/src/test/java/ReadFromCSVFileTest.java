import java.io.IOException;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ReadFromCSVFileTest {

	@Test
	public void test() throws IOException {
//		ReadFromCSVFile csv = new ReadFromCSVFile();
		List<List<String>> result = ReadFromCSVFile.csvReader();
		System.out.println(result.size());
		
		Assert.assertEquals(102, result.size());
	}

}
