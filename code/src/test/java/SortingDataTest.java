import java.io.IOException;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class SortingDataTest {

	@Test
	public void test() throws IOException {
		List<List<String>> result = SortingData.sortedData();		
		Assert.assertEquals(102, result.size());
	}

}
