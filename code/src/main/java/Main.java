package VersionSpaceLearning.src;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main(String[] argas) {
        ArrayList<Instance> instances = new ArrayList<>();
        ArrayList<HashSet<String>> featureValues = new ArrayList<>();
        BufferedReader br;
        String line = "";
        String[] datas ;
        int datalen = 0;
        GeneralizeS genS = new GeneralizeS();
        SpecializeGBoundary spclG = new SpecializeGBoundary();
        HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        try {
            br = new BufferedReader(new FileReader(new File("E:\\EclipseWorspaces\\JavaTraining\\udemytraining\\src\\VersionSpaceLearning\\src\\data.csv")));//File has been read from this path//
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

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Instance inst: instances)
        {
            if(inst.getLabel().equals("Yes"))
            {
                S = genS.min_generalizations(S, inst.getAttribs());
                G = spclG.removeMember(S,G);
            }
            else
            {
            	//S = genS.removeHypothesis(inst.getAttribs(),S);
            	G = spclG.specialize(inst.getAttribs(), S, featureValues, G);
            	
            }
        }
        System.out.println("S boundary is:");
        System.out.println(S);
        System.out.println("G boundary is:");
        System.out.println(G);


    }

}
