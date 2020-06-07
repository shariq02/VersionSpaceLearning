import static org.junit.Assert.*;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class HypothesisTest {
	static ArrayList<Object[]> data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<String> readData = new ArrayList<String>();
		data = new ArrayList<Object[]>();
		File file = new File("./src/test/java/data.txt");
		if(file.exists()) {
			try {
				readData = Files.readAllLines(file.toPath(), Charset.defaultCharset());
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesnt exist");
		}
		for(String line: readData) {
			String[] temp = line.split(",");
			Object[] example = new Object[3];
			example[0] = Arrays.copyOfRange(temp, 0, 3);
			example[1] = Arrays.copyOfRange(temp, 3, 6);
			example[2] = Boolean.valueOf(temp[6]);
			data.add(example);
		}
	}
	
	@Test
	public void isMoreGeneralThanTest1() {
		Hypothesis a = new Hypothesis(new String[] {"A", "C", "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, "E"});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest2() {
		Hypothesis a = new Hypothesis(new String[] {"A", Hypothesis.ANY, "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, "E"});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest3() {
		Hypothesis a = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, "E"});
		assertEquals(a.isMoreGeneralThan(b), true);
	}
	
	@Test
	public void isMoreGeneralThanTest4() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest5() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest6() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), true);
	}
	
	@Test
	public void isMoreGeneralThanTest7() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest8() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, Hypothesis.NONE});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest9() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest10() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE});
		assertEquals(a.isMoreGeneralThan(b), true);
	}
	
	@Test
	public void isMoreGeneralThanTest11() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE});
		Hypothesis b = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTest12() {
		Hypothesis a = new Hypothesis(new String[] {"A", "C", "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", "D", "F"});
		assertEquals(a.isMoreGeneralThan(b), false);
	}
	
	@Test
	public void isMoreGeneralThanTestFromFile() {
		System.out.println(data.size());
		for(int i=0; i<data.size(); i++) {
			Hypothesis first = new Hypothesis((String[])data.get(i)[0]);
			Hypothesis second = new Hypothesis((String[])data.get(i)[1]);
			boolean classification = (Boolean)data.get(i)[2];
			assertEquals(first.isMoreGeneralThan(second), classification);
		}
		
	}
	
	@Test
	public void compareToTest1() {
		Hypothesis a = new Hypothesis(new String[] {"A", "C", "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", "D", "F"});
		List<Integer> expected = Arrays.asList(1,2);
		assertEquals(a.compareTo(b), expected);
	}
	
	@Test
	public void compareToTest2() {
		Hypothesis a = new Hypothesis(new String[] {"A", "C", "E"});
		Hypothesis b = new Hypothesis(new String[] {"A", "C", "E"});
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}
	
	@Test
	public void compareToTest3() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE});
		Hypothesis b = new Hypothesis(new String[] {"A", "C", "E"});
		List<Integer> expected = Arrays.asList(0,1,2);
		assertEquals(a.compareTo(b), expected);
	}
	
	@Test
	public void compareToTest4() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", "C", "E"});
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}
	
	@Test
	public void compareToTest5() {
		Hypothesis a = new Hypothesis(new String[] {Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY});
		Hypothesis b = new Hypothesis(new String[] {"A", Hypothesis.ANY, "E"});
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}

}
