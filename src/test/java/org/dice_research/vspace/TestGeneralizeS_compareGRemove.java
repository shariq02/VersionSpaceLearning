package org.dice_research.vspace;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;

public class TestGeneralizeS_compareGRemove {
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
        br = new BufferedReader(new FileReader(new File("E:\\Gitrepo\\IncrementalVersionSpaceMerging\\IncrementalVersionSpaceMerging\\src\\hierarchicalInstance_robot.csv")));
        while((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0) {
                datalen = datas.length-1;
                for (int i = 1; i <= datalen; i++) {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen; i++) {
                featureValues.get(i).add(datas[i]);
            }
        }
        HashSet<Hypothesis> outputList;
        GeneralizeS generalizeObject = new GeneralizeS();
        CandidateElimination ceh = new CandidateElimination("Hierarchical", "E:\\Gitrepo\\IncrementalVersionSpaceMerging\\IncrementalVersionSpaceMerging\\src\\hierarchicalInstance_robot.csv",
                "E:\\Gitrepo\\IncrementalVersionSpaceMerging\\IncrementalVersionSpaceMerging\\src\\dataOntology_robot.csv");
        ceh.makeGraph(featureValues);
        S.add(new Hypothesis(datalen,"G"));
        G.add(new Hypothesis(datalen, "S"));
        HashSet<Hypothesis> finalRes = generalizeObject.compareG_Remove(S, G, ceh.featureGraph);
        assertEquals(finalRes.size(), 0);
    }
}