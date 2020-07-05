package org.dice_research.vspace;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;

public class SpecializeRemoveMemberTest {
  @SuppressWarnings("resource")
  @Test
  public void removeMemberTest() throws IOException {
    ArrayList<HashSet<String>> featureValues = new ArrayList<>();
	BufferedReader br;
	String line = "";
	ArrayList<Instance> instances = new ArrayList<>();
	ArrayList<Ontology> featureGraph = new ArrayList<>();
	String[] datas;
	int datalen = 0;
    HashSet<Hypothesis> S = new HashSet<>();
    HashSet<Hypothesis> G = new HashSet<>();
    br = new BufferedReader(new FileReader(new File("C:\\Users\\Sharique\\Desktop\\Study_Materials"
            + "\\Master\\Data_Science_Project\\VersionSpaceLearning\\code\\src"
            + "\\test\\resources\\datafile\\data.csv")));
    while((line = br.readLine()) != null) {
      datas = line.split(",");
      instances.add(new Instance(datas));
      if (datalen == 0) {
        datalen = datas.length-1;
        S.add(new Hypothesis(datalen, "S"));
        G.add(new Hypothesis(datalen, "G"));
        for (int i = 1; i <= datalen; i++) {
          featureValues.add(new HashSet<>());
        }
      }
      for (int i = 0; i < datalen; i++) {
        featureValues.get(i).add(datas[i]);
      }
      }	
	HashSet<Hypothesis> outputList;
    SpecializeGBoundary specializeObject = new SpecializeGBoundary();
    HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
    specializedHypothesisSet.addAll(S);
    featureGraph = CandidateElimination.makeGraph(featureValues);
    outputList =  specializeObject.removeMember(S, G, featureGraph);
    String[] expectedFeatureSet1 = {"?", "?", "?", "?", "?", "?"};
    HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();
    expectedOutput.add(new Hypothesis(expectedFeatureSet1));
    assertEquals(expectedOutput, outputList);
  }
}