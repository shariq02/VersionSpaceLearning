package src.test.java.org.dice_research.vspace;

/*The test case DataTest2.csv contains different instances for shapes and their features respectively.
 * OntologyTest2.csv contains the Hierarchical Data for the instances in DataTest2.csv
 * This is to test the output of the min_generalisations method and compare it with the expected output.
 * Also, the removeMember method inside the GeneralizeS class is being tested here using the same test cases. 
 * @author Parth Sharma
 */
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.io.*;
class Generalise_S_Test_DataTest2 {
	
	CandidateElimination ceh=new CandidateElimination("Hierarchical", "/Users/parthsharma/Desktop/DataTest2.csv",
            "/Users/parthsharma/Desktop/OntologyTest2.csv");
	int datalen = 0;
	HashSet<Hypothesis> S = new HashSet<>();
    HashSet<Hypothesis> G = new HashSet<>();
    ArrayList<Instance> instances = new ArrayList<>();
    ArrayList<HashSet<String>> featureValues = new ArrayList<>();
    GeneralizeS gs=new GeneralizeS();
	@SuppressWarnings("resource")
	@Before
	public void init() throws IOException
	{	
		BufferedReader br;
		
		
		br=new BufferedReader(new FileReader(new File("/Users/parthsharma/Desktop/DataTest2.csv")));
		
		String line="";
		
		String[] datas;
		               
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
            
            ceh.featureValues=featureValues;
		}
		
		ceh.datalen=datalen;
	}
	@SuppressWarnings("resource")
	@Test
	public void testGeneralizeS() throws IOException{
		init();
		HashSet<Hypothesis> expOut = new HashSet<>();
        HashSet<Hypothesis> Output = new HashSet<>();  
            ceh.makeGraph();
            for(Instance inst: instances)
            {
                System.out.println(inst.toString());
                if(inst.getLabel().equals("Yes"))
                {
                    S = gs.min_generalizations(S, inst.getAttribs(),ceh.featureGraph, G);
                    for(Hypothesis hyp:S)
                    	Output.add(hyp);
                }
            }
            
	    
		String[] out2= {"Shapes","Small","?"};
		String[] out1= {"Rhombus","Small","Green"};
		expOut.add(new Hypothesis(out1));
		expOut.add(new Hypothesis(out2));
		
		assertEquals(expOut,Output);		
	}
	
	@Test
	void testRemMem() throws IOException {
		init();
		HashSet<Hypothesis> expOut = new HashSet<>();
		GeneralizeS genS = new GeneralizeS();
		ceh.makeGraph();
		for (Instance inst : instances) {
			System.out.println(inst.toString());
			if (inst.getLabel().equals("Yes")) {
				S = gs.min_generalizations(S, inst.getAttribs(), ceh.featureGraph, G);
			} else {
				S = genS.removeMember(S, inst.getAttribs(), ceh.featureGraph);
			}
		}
		String[] out2 = { "Shapes", "Small", "?" };

		expOut.add(new Hypothesis(out2));
		assertEquals(expOut, S);
	}
	
}


