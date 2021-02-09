package org.dice_research.vspace;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class HypothesisTest {
	static ArrayList<Object[]> data;
	static ArrayList<Ontology> featureGraph;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		/*
		 * Reads data about hypotheses and their isMoreGeneralThan result from file in
		 * order to test later whether the isMoreGeneralThan method produces the same
		 * result. Each line of the file, from which the data is read has the following
		 * structure: 1st feature value of 1st hypothesis, 2nd feature value of 1st
		 * hypothesis, 3rd feature value of 1st hypothesis, 1st feature value of 2nd
		 * hypothesis, 2nd feature value of 2nd hypothesis, 3rd feature value of 2nd
		 * hypothesis, result (true or false) of the (1st
		 * hypothesis).isMoreGeneralThan(2nd hypothesis) call
		 */
		List<String> readData = new ArrayList<String>();
		data = new ArrayList<Object[]>();
		File file = new File("./src/test/resources/datafile/data.txt");
		if (file.exists()) {
			try {
				readData = Files.readAllLines(file.toPath(), Charset.defaultCharset());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File doesnt exist");
			throw new FileNotFoundException();
		}
		for (String line : readData) {
			String[] temp = line.split(",");
			Object[] example = new Object[3];
			example[0] = Arrays.copyOfRange(temp, 0, 3);
			example[1] = Arrays.copyOfRange(temp, 3, 6);
			example[2] = Boolean.valueOf(temp[6]);
			data.add(example);
		}

		/*
		 * Constructs the hierarchies of the three features, where each feature has two
		 * possible values: feature 1 can have values A and B, feature 2 can have values
		 * C and D, feature 3 can have values E and F
		 */
		featureGraph = new ArrayList<Ontology>();
		ArrayList<String[]> featureValues = new ArrayList<String[]>();
		featureValues.add(new String[] { "A", "B" });
		featureValues.add(new String[] { "C", "D" });
		featureValues.add(new String[] { "E", "F" });

		for (String[] f : featureValues) {
			Ontology adg = new Ontology();
			for (String value : f) {
				adg.getRoot().addChild(value);
				adg.search(adg.getRoot(), value).addChild("-");
			}
			featureGraph.add(adg);
		}
	}

	@Test
	public void isMoreGeneralThanTest1() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTest2() {
		Hypothesis a = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest3() {
		Hypothesis a = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest4() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTest5() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest6() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest7() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTest8() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.NONE });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTest9() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest10() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), true);
	}

	@Test
	public void isMoreGeneralThanTest11() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTest12() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", "D", "F" });
		assertEquals(a.isMoreGeneralThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan1() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), true);
	}

	@Test
	public void isMoreSpecificThan2() {
		Hypothesis a = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), true);
	}

	@Test
	public void isMoreSpecificThan3() {
		Hypothesis a = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan4() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan5() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan6() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan7() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), true);
	}

	@Test
	public void isMoreSpecificThan8() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, Hypothesis.NONE });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan9() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), true);
	}

	@Test
	public void isMoreSpecificThan10() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreSpecificThan11() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		Hypothesis b = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), true);
	}

	@Test
	public void isMoreSpecificThan12() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", "D", "F" });
		assertEquals(a.isMoreSpecificThan(b, featureGraph), false);
	}

	@Test
	public void isMoreGeneralThanTestFromFile() {
		for (int i = 0; i < data.size(); i++) {
			Hypothesis first = new Hypothesis((String[]) data.get(i)[0]);
			Hypothesis second = new Hypothesis((String[]) data.get(i)[1]);
			boolean classification = (Boolean) data.get(i)[2];
			assertEquals(first.isMoreGeneralThan(second, featureGraph), classification);
		}

	}

	@Test
	public void compareToTest1() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", "D", "F" });
		List<Integer> expected = Arrays.asList(1, 2);
		assertEquals(a.compareTo(b), expected);
	}

	@Test
	public void compareToTest2() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		Hypothesis b = new Hypothesis(new String[] { "A", "C", "E" });
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}

	@Test
	public void compareToTest3() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		Hypothesis b = new Hypothesis(new String[] { "A", "C", "E" });
		List<Integer> expected = Arrays.asList(0, 1, 2);
		assertEquals(a.compareTo(b), expected);
	}

	@Test
	public void compareToTest4() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", "C", "E" });
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}

	@Test
	public void compareToTest5() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		Hypothesis b = new Hypothesis(new String[] { "A", Hypothesis.ANY, "E" });
		List<Integer> expected = Arrays.asList();
		assertEquals(a.compareTo(b), expected);
	}

	@Test
	public void classifyPointTest1() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		assertEquals(a.classifyPoint(new String[] { "A", "C", "E" }, featureGraph), true);
	}

	@Test
	public void classifyPointTest2() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", "E" });
		assertEquals(a.classifyPoint(new String[] { "A", "C", "F" }, featureGraph), false);
	}

	@Test
	public void classifyPointTest3() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "A", "C", "F" }, featureGraph), true);
	}

	@Test
	public void classifyPointTest4() {
		Hypothesis a = new Hypothesis(new String[] { "A", "C", Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "B", "C", "F" }, featureGraph), false);
	}

	@Test
	public void classifyPointTest5() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, "C", Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "B", "C", "F" }, featureGraph), true);
	}

	@Test
	public void classifyPointTest6() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "B", "D", "E" }, featureGraph), true);
	}

	@Test
	public void classifyPointTest7() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.ANY, Hypothesis.ANY, Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "A", "C", "F" }, featureGraph), true);
	}

	@Test
	public void classifyPointTest8() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, "C", Hypothesis.ANY });
		assertEquals(a.classifyPoint(new String[] { "B", "C", "F" }, featureGraph), false);
	}

	@Test
	public void classifyPointTest9() {
		Hypothesis a = new Hypothesis(new String[] { Hypothesis.NONE, Hypothesis.NONE, Hypothesis.NONE });
		assertEquals(a.classifyPoint(new String[] { "B", "C", "F" }, featureGraph), false);
	}
}