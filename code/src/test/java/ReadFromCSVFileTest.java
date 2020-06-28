import java.io.IOException;
import java.util.List;

import org.dice_research.vspace.Instance;
import org.dice_research.vspace.ReadFromCSVFile;
import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ReadFromCSVFileTest {

	@Test
	public void test() throws IOException {
		List<Instance> result = ReadFromCSVFile.csvReader();
		Assert.assertEquals(10, result.size());
	}

}
