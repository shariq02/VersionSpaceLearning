package org.dice_research.vspace;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class SpecializeGTest 
{
	@SuppressWarnings("resource")
	@Test
	public void Specialize() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/data.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"Rainy","Cold","High","Strong","Warm","Change"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 =  {"?","?","?","?","?","Same"};
        String[] expectedFeatureSet2 =  {"?","Warm","?","?","?","?"};
        String[] expectedFeatureSet3 =  {"?","?","Normal","?","?","?"};
        String[] expectedFeatureSet4 =  {"?","?","?","?","Cool","?"};
        String[] expectedFeatureSet5 =  {"Sunny","?","?","?","?","?"};
        String[] expectedFeatureSet6 =  {"?","?","?","-","?","?"};
        
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));    
        assertEquals(expectedOutput +" " , outputList +" ");
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize3() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/negativedata.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"big","red","circle"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        
        String[] expectedFeatureSet2 =  {"?","?","triangle"};
        String[] expectedFeatureSet3 =  {"?","blue","?"};
        String[] expectedFeatureSet4 =  {"small","?","?"};
        String[] expectedFeatureSet1 =  {"big","?","?"};
        
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        assertNotEquals(expectedOutput +" " , outputList +" ");
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize2() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/negativedata.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"small","red","triangle"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"big","?","?"};
        String[] expectedFeatureSet2 = {"?","?","circle"};
        String[] expectedFeatureSet3 = {"?","blue","?"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        assertNotEquals(expectedOutput +" " , outputList +" ");
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize1() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/fish_database.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"chicken","2","bird"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"catfish","?","?"};
        String[] expectedFeatureSet2 = {"carp","?","?"};
        String[] expectedFeatureSet3 = {"bass","?","?"};
        String[] expectedFeatureSet4 = {"?","?","fish"};
        String[] expectedFeatureSet5 = {"dolphin","?","?"};
        String[] expectedFeatureSet6 = {"?","0","?"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        assertNotEquals(expectedOutput, outputList);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize4() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/StudentsPerformance.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"male","associate","free","44"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"female","?","?","?"};
        String[] expectedFeatureSet2 = {"?","master","?","?"};
        String[] expectedFeatureSet3 = {"?","?","?","93"};
        String[] expectedFeatureSet4 = {"?","some college","?","?"};
        String[] expectedFeatureSet5 = {"?","?","?","78"};
        String[] expectedFeatureSet6 = {"?","?","?","88"};
        String[] expectedFeatureSet7 = {"?","?","standard","?"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        expectedOutput.add(new Hypothesis(expectedFeatureSet7));
        assertEquals(expectedOutput, outputList);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize5() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/testdata.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"Visit2","Kim's","Lunch","Friday","Expensive"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        
        String[] expectedFeatureSet1 = {"Visit5","?","?","?","?"};
        String[] expectedFeatureSet2 = {"?","?","?","Sunday","?"};
        String[] expectedFeatureSet3 = {"?","Sam's","?","?","?"};
        String[] expectedFeatureSet4 = {"?","Bob's","?","?","?"};
        String[] expectedFeatureSet5 = {"?","?","?","Saturday","?"};
        String[] expectedFeatureSet6 = {"Visit1","?","?","?","?"};
        String[] expectedFeatureSet7 = {"?","?","?","?","Cheap"};
        String[] expectedFeatureSet8 = {"Visit3","?","?","?","?"};
        String[] expectedFeatureSet9 = {"?","?","Breakfast","?","?"};
        String[] expectedFeatureSet10 = {"Visit4","?","?","?","?"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();
        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        expectedOutput.add(new Hypothesis(expectedFeatureSet7));
        expectedOutput.add(new Hypothesis(expectedFeatureSet8));
        expectedOutput.add(new Hypothesis(expectedFeatureSet9));
        expectedOutput.add(new Hypothesis(expectedFeatureSet10));
		assertNotEquals(expectedOutput, outputList);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize6() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/testdata.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"Visit4","Bob's","Breakfast","Sunday","Cheap"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"Visit5","?","?","?","?"};
        String[] expectedFeatureSet2 = {"?","?","?","?","Expensive"};
        String[] expectedFeatureSet3 = {"?","?","Lunch","?","?"};
        String[] expectedFeatureSet4 = {"?","Sam's","?","?","?"};
        String[] expectedFeatureSet5 = {"?","Kim's","?","?","?"};
        String[] expectedFeatureSet6 = {"?","?","?","Saturday","?"};
        String[] expectedFeatureSet7 = {"Visit1","?","?","?","?"};
        String[] expectedFeatureSet8 = {"?","?","?","Friday","?"};
        String[] expectedFeatureSet9 = {"Visit2","?","?","?","?"};
        String[] expectedFeatureSet10 = {"Visit3","?","?","?","?"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();
        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        expectedOutput.add(new Hypothesis(expectedFeatureSet7));
        expectedOutput.add(new Hypothesis(expectedFeatureSet8));
        expectedOutput.add(new Hypothesis(expectedFeatureSet9));
        expectedOutput.add(new Hypothesis(expectedFeatureSet10));
		assertNotEquals(expectedOutput, outputList);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize7() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/DataTest1.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"E-class","Moderate","Normal","High"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"S-class","?","?","?"};
        String[] expectedFeatureSet2 = {"BMW","?","?","?"};
        String[] expectedFeatureSet3 = {"?","Affordable","?","?"};
        String[] expectedFeatureSet4 = {"?","Expensive","?","?"};
        String[] expectedFeatureSet5 = {"Ford","?","?","?"};
        String[] expectedFeatureSet6 = {"?","?","High","?"};
        String[] expectedFeatureSet7 = {"?","?","?","Moderate"};
        String[] expectedFeatureSet8 = {"Skoda","?","?","?"};
        String[] expectedFeatureSet9 = {"?","?","Low","?"};
        String[] expectedFeatureSet10 = {"?","?","?","Low"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();
        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        expectedOutput.add(new Hypothesis(expectedFeatureSet7));
        expectedOutput.add(new Hypothesis(expectedFeatureSet8));
        expectedOutput.add(new Hypothesis(expectedFeatureSet9));
        expectedOutput.add(new Hypothesis(expectedFeatureSet10));
		assertEquals(expectedOutput, outputList);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void Specialize8() throws IOException 
	{
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		ArrayList<Ontology> featureGraph = new ArrayList<>();
		BufferedReader br;
		String line = "";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        br = new BufferedReader(new FileReader(new File("/src/test/resources/datafile/DataTest1.csv")));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
	    }	
		HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();
        String[] negativeData = {"S-class","Expensive","High","High"};
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.addAll(S);
        featureGraph = CandidateElimination.makeGraph(featureValues);
        outputList =  specializeObject.specialize(negativeData, specializedHypothesisSet, featureGraph, G, line);
        String[] expectedFeatureSet1 = {"BMW","?","?","?"};
        String[] expectedFeatureSet2 = {"?","Affordable","?","?"};
        String[] expectedFeatureSet3 = {"E-class","?","?","?"};
        String[] expectedFeatureSet4 = {"?","?","Normal","?"};
        String[] expectedFeatureSet5 = {"Ford","?","?","?"};
        String[] expectedFeatureSet6 = {"?","Moderate","?","?"};
        String[] expectedFeatureSet7 = {"?","?","?","Moderate"};
        String[] expectedFeatureSet8 = {"Skoda","?","?","?"};
        String[] expectedFeatureSet9 = {"?","?","Low","?"};
        String[] expectedFeatureSet10 = {"?","?","?","Low"};
        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();
        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        expectedOutput.add(new Hypothesis(expectedFeatureSet6));
        expectedOutput.add(new Hypothesis(expectedFeatureSet7));
        expectedOutput.add(new Hypothesis(expectedFeatureSet8));
        expectedOutput.add(new Hypothesis(expectedFeatureSet9));
        expectedOutput.add(new Hypothesis(expectedFeatureSet10));
		assertEquals(expectedOutput, outputList);
	}
}