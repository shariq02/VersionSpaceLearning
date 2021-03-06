package org.dice_research.vspace;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;

public class TestSpecializeGBoundary_removeMember {

	@SuppressWarnings("resource")
	@Test
	public void compareGRemoveTest() throws IOException {
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		String[] datas;
		int datalen = 0;
		HashSet<Hypothesis> S = new HashSet<>();
		HashSet<Hypothesis> G = new HashSet<>();
		br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/DataTest1.csv")));
		while ((line = br.readLine()) != null) {
			datas = line.split(",");
			instances.add(new Instance(datas));
			if (datalen == 0) {
				datalen = datas.length - 1;
				for (int i = 1; i <= datalen; i++) {
					featureValues.add(new HashSet<>());
				}
			}
			for (int i = 0; i < datalen; i++) {
				featureValues.get(i).add(datas[i]);
			}
		}
		SpecializeGBoundary specializeObject = new SpecializeGBoundary();
		CandidateElimination ceh = new CandidateElimination("Hierarchical",
				"/src/test/resources/datafile/DataTest1.csv", "/src/test/resources/datafile/OntologyTest1.csv");
		ceh.makeGraph(featureValues);
		String[] a = { "small", "cube" };
		String[] b = { "small", "?" };
		String[] c = { "large", "cube" };
		S.add(new Hypothesis(a));
		G.add(new Hypothesis(b));
		G.add(new Hypothesis(c));
		HashSet<Hypothesis> outputList = new HashSet<Hypothesis>();
		outputList.add(new Hypothesis(b));
		HashSet<Hypothesis> finalRes = specializeObject.removeMember(S, G, ceh.getFeatureGraph());
		assertEquals(finalRes, outputList);
	}

}
