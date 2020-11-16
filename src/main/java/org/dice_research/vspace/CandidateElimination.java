import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
    private HashSet<Hypothesis> inst_S ;
    private HashSet<Hypothesis> inst_G ;
    private HashSet<Hypothesis> merged_S ;
    private HashSet<Hypothesis> merged_G ;
    private HashSet<Hypothesis> placeholder;
    private HashSet<Hypothesis> placeholder_S;
    private HashSet<Hypothesis> placeholder_G;
    private String filePath;
    private String ontPath;
    private ArrayList<Ontology> featureGraph;
    private String mode;
    private String graphPath;
    private Boolean inconsistancy;

    public CandidateElimination(String mode, String path)
    {
        initialize(mode,path);
    }

    public CandidateElimination(String mode, String path, String graphPath)
    {
        initialize(mode,path);
        this.graphPath = graphPath;
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
        this.inst_S = new HashSet<>();                    
        this.inst_G = new HashSet<>();
        this.merged_S = new HashSet<>();
        this.merged_G = new HashSet<>();
        this.placeholder = new HashSet<>();
        this.placeholder_S = new HashSet<>();
        this.placeholder_G = new HashSet<>();       
        this.filePath = path;
        this.inconsistancy = false;
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

        placeholder_S.add(new Hypothesis(datalen,"S"));    
        placeholder_G.add(new Hypothesis(datalen,"G"));

        for(Instance inst: instances)
        {
            inst_S.add(new Hypothesis(datalen,"S"));
            inst_G.add(new Hypothesis(datalen,"G"));   
            System.out.println("The instance is");
            System.out.println(inst.toString());
            if(inst.getLabel().equals("Yes"))
            {
                if (S.toString().equals(G.toString()) && inconsistancy)
                {
                    System.out.println("Single Classfier achieved. No more classifier can be added.");
                    break;
                }
                inst_S = genS.min_generalizations(inst_S, inst.getAttribs(),featureGraph, inst_G);
                inst_G = spclG.removeMember(inst_S,inst_G, featureGraph);    
            }
            else
            {
                inst_S = genS.removeMember(inst_S, inst.getAttribs(), featureGraph);
                inst_G = spclG.specialize(inst.getAttribs(), inst_S, featureGraph, inst_G, "ce");
            }
            mergeVersionSpace(inst);
            System.out.println("S boundary is:");
            System.out.println(this.S);
            System.out.println("G boundary is:");
            System.out.println(this.G);
            System.out.println("#######################################################################");
            inst_S.clear();
            inst_G.clear();
        }
    }

    public HashSet<Hypothesis> getS() {
        return S;
    }

    public HashSet<Hypothesis> getG() {
        return G;
    }

    private void makeGraph(ArrayList<HashSet<String>> fValues)
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

    private void mergeVersionSpace(Instance inst)
    {
        for (Hypothesis hypo_s1 : S)
        {
            for (Hypothesis hypo_s2: inst_S)
            {
                if (hypo_s1.isMoreGeneralThan(hypo_s2,featureGraph)) merged_S.add(hypo_s1);
                else if(hypo_s1.isMoreSpecificThan(hypo_s2,featureGraph)) merged_S.add(hypo_s2);
                else if (hypo_s1.equals(hypo_s2)) merged_S.add(hypo_s1);
                else
                {
                    placeholder.add(hypo_s1);
                    placeholder = genS.min_generalizations(placeholder, hypo_s2.features, featureGraph, placeholder_G);
                    for( Hypothesis pholder_hypo : placeholder) merged_S.add(pholder_hypo);
                    placeholder.clear();
                }
            }
        }

        for (Hypothesis hypo_g1 : G)
        {
            for (Hypothesis hypo_g2 : inst_G)
            {
                if (hypo_g1.isMoreSpecificThan(hypo_g2,featureGraph)) merged_G.add(hypo_g1);
                else if (hypo_g1.isMoreGeneralThan(hypo_g2,featureGraph)) merged_G.add(hypo_g2);
                else if (hypo_g1.equals(hypo_g2)) merged_G.add(hypo_g1);
                else
                {
                    placeholder.add(hypo_g1);
                    placeholder = spclG.specialize(hypo_g2.features, placeholder_S, featureGraph, placeholder, "ivsm");
                    for (Hypothesis pholder_gHypo : placeholder) merged_G.add(pholder_gHypo);
                    placeholder.clear();
                }
            }
        }

       merged_G = spclG.removeMember(S, merged_G, featureGraph);
       merged_G = spclG.removeMember(inst_S, merged_G, featureGraph);

       merged_S = genS.compareG_Remove(merged_S,G,featureGraph);
       merged_S = genS.compareG_Remove(merged_S,inst_G,featureGraph);

       G = spclG.removeSpecific(merged_G, featureGraph);
       S = genS.removeGeneric(merged_S, featureGraph);
        merged_S.clear();
        merged_G.clear();
    }

}
