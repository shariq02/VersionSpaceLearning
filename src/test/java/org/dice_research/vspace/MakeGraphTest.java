package org.dice_research.vspace;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;

public class MakeGraphTest {
  @SuppressWarnings({ "resource", "static-access" })
  @Test
  public void test() throws IOException {
    ArrayList<HashSet<String>> featureValues = new ArrayList<>();
    BufferedReader br;
    String line = "";
    ArrayList<Instance> instances = new ArrayList<>();
    String[] datas;
    int datalen = 0;
    HashSet<Hypothesis> sp = new HashSet<>();
    HashSet<Hypothesis> gn = new HashSet<>();
    br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/DataTest1.csv")));
    while ((line = br.readLine()) != null) { 
      datas = line.split(",");
      instances.add(new Instance(datas));
      if (datalen == 0) {
        datalen = datas.length - 1;
        sp.add(new Hypothesis(datalen, "S"));
        gn.add(new Hypothesis(datalen, "G"));
        for (int i = 1; i <= datalen; i++) {
          featureValues.add(new HashSet<>());
        }
      }
      for (int i = 0; i < datalen; i++) {
        featureValues.get(i).add(datas[i]);
      }
    }
    ArrayList<Ontology> output;
    ArrayList<Ontology> expected = new ArrayList<>();
    CandidateElimination candidateObject = new CandidateElimination();
    output = candidateObject.makeGraph(featureValues);
    assertEquals(expected, output);
  }
}
