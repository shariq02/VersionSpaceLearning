import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import java.io.*;
class Test1 {
//CandidateElimination ceh=new CandidateElimination("Hierarchical", "/Users/parthsharma/eclipse-workspace1/DSS/VSL new/src/hierarchicalInstance.csv","/Users/parthsharma/eclipse-workspace1/DSS/VSL new/src/dataOntology.csv");

	@SuppressWarnings("resource")
	@Test
	public void test() throws IOException{
		CandidateElimination ceh=new CandidateElimination("Hierarchical", "/Users/parthsharma/eclipse-workspace1/DSS/VSL new/src/hierarchicalInstance1.csv","/Users/parthsharma/eclipse-workspace1/DSS/VSL new/src/dataOntology.csv");
		GeneralizeS gs=new GeneralizeS();
		BufferedReader br;
		
		ArrayList<HashSet<String>> featureValues = new ArrayList<>();
		br=new BufferedReader(new FileReader(new File("/Users/parthsharma/eclipse-workspace1/DSS/VSL new/src/hierarchicalInstance1.csv")));
		
		String line="";
		ArrayList<Instance> instances = new ArrayList<>();
		String[] datas;
		int datalen = 0;
		HashSet<Hypothesis> S = new HashSet<>();
        HashSet<Hypothesis> G = new HashSet<>();
        HashSet<Hypothesis> expOut = new HashSet<>();

        
		while ((line = br.readLine()) != null) {
            datas = line.split(",");
           //for(String d: datas)
            //System.out.println(d);
            
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
           //   System.out.println(datalen+"datalen");
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
            ceh.makeGraph();
            for(Instance inst: instances)
            {
                System.out.println(inst.toString());
                if(inst.getLabel().equals("Yes"))
                {
                    S = gs.min_generalizations(S, inst.getAttribs(),ceh.featureGraph, G);
                }
            }
            
	    
		String[] out1= {"Tomato","Circle"};
		//String[] out2= {}
		expOut.add(new Hypothesis(out1));
		//expOut.add(new Hypothesis(out2));
		
		assertEquals(expOut,S);
		fail("Not yet implemented");
		
	}

}


