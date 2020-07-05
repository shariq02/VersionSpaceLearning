package org.dice_research.vspace;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class CandidateElimination {
    private ArrayList<Instance> instances;
    private ArrayList<HashSet<String>> featureValues;
    private BufferedReader br;
    private String line = "";
    private String[] datas ;
    private int datalen;
    private GeneralizeS genS ;
    private SpecializeGBoundary spclG ;
    private HashSet<Hypothesis> S ;
    private HashSet<Hypothesis> G ;
    private String filePath;
    private String ontPath;
    private static ArrayList<Ontology> featureGraph;
    private String mode;
    private String graphPath;

    public CandidateElimination(String mode, String path)
    {
        initialize(mode,path);
    }

    public CandidateElimination(String mode, String path, String graphPath)
    {
        initialize(mode,path);
        this.graphPath = graphPath;
    }

    public CandidateElimination() {
		// TODO Auto-generated constructor stub
	}

	public void initialize(String mode, String path)
    {
        this.mode = mode;
        this.instances = new ArrayList<>();
        this.featureValues = new ArrayList<>();
        this.datalen = 0;
        this.genS = new GeneralizeS();
        this.spclG = new SpecializeGBoundary();
        this.S = new HashSet<>();
        this.G = new HashSet<>();
        this.filePath = path;
    }


    public void performElimication()
    {
        try {
        this.br = new BufferedReader(new FileReader(new File(this.filePath)));
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

        } catch (
        FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (
        IOException e) {
            e.printStackTrace();
        }
        if (this.mode.equals("Normal")) makeGraph(featureValues);
        else makeGraph();

        for(Instance inst: instances)
        {
            if(inst.getLabel().equals("Yes"))
            {
                S = genS.min_generalizations(S, inst.getAttribs(),featureGraph);
                G = spclG.removeMember(S,G, featureGraph);
            }
            else
            {
                S = genS.removeMember(S, inst.getAttribs(), featureGraph);
                G = spclG.specialize(inst.getAttribs(), S, featureGraph, G);
            }
            System.out.println("S boundary is:");
            System.out.println(this.S);
            System.out.println("G boundary is:");
            System.out.println(this.G);
            System.out.println("#######################################################################");
        }
    }

    public HashSet<Hypothesis> getS() {
        return S;
    }

    public HashSet<Hypothesis> getG() {
        return G;
    }

    public static ArrayList<Ontology> makeGraph(ArrayList<HashSet<String>> fValues)
    {
        featureGraph = new ArrayList<>();
        for ( HashSet<String> hsets : fValues)
        {
            Ontology adg = new Ontology();
            for (String uniqFeatures: hsets)
            {
                adg.getRoot().addChild(uniqFeatures);
                adg.search(adg.getRoot(),uniqFeatures).addChild("-");
            }

            featureGraph.add(adg);
        }
        return featureGraph;
    }

    private void makeGraph()
    {
        featureGraph = new ArrayList<>();
        for (int i = 1; i <= this.datalen; i++)
        {
            featureGraph.add(new Ontology());
        }
        try {
            this.br = new BufferedReader(new FileReader(new File(this.graphPath)));
            while ((line = br.readLine()) != null) {
                datas = line.split(",");
                Ontology grph = featureGraph.get(Integer.parseInt(datas[0]));
                Vertices targetNode = grph.search(grph.getRoot(), datas[1]);
                if (targetNode == null)
                {
                    grph.getRoot().addChild(datas[1]);
                    targetNode = grph.search(grph.getRoot(), datas[1]);
                }
                for (int i = 2; i < datas.length; i ++)
                {
                    targetNode.addChild(datas[i]);
                }
            }
        }
        catch(FileNotFoundException ex){
                ex.printStackTrace();
        }
        catch(IOException e){
                e.printStackTrace();
        }

        for(Ontology graphs : featureGraph) graphs.addStopper(graphs.getRoot());

    }

}
