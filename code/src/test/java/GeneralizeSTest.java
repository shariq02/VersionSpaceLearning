import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

//Will be working on it,

public class GeneralizeSTest {

	@Test
	public void Generalize() 
	{
		ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();
        HashSet<Hypothesis> outputList;
        GeneralizeS generalizeObject = new GeneralizeS();
        
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");
        ArrayList<ArrayList<String>> featureList = new ArrayList<ArrayList<String>>();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);
        
        String[] dataPoint = {"Mango","?","Good"};
        
        Hypothesis testHypothesisH = new Hypothesis(3,"H");       //Constructor calling to create the most Generalized Hypothesis
        Hypothesis testHypothesisG = new Hypothesis(3,"G");      //Constructor calling to create the most Specialized Hypothesis
        HashSet<Hypothesis> generalizeHypothesisSet = new HashSet<Hypothesis>();
        generalizeHypothesisSet.add(testHypothesisH);
        System.out.println("generalizeHypothesisSet"+generalizeHypothesisSet);
        outputList = generalizeObject.min_generalizations(generalizeHypothesisSet, dataPoint);
      // outputList = generalizeObject.min_generalizations(testHypothesisS, testHypothesisH);
        		//.specialize(negativeData, generalizeHypothesisSet, featureList, testHypothesisG);
        System.out.println("output" +outputList);
        
        String[] expectedFeatureSet1 =  {"Mango","?","?"};
        String[] expectedFeatureSet2 =  {"?","Yes","?"};
        String[] expectedFeatureSet3 =  {"?","No","?"};
        String[] expectedFeatureSet4 =  {"?","?","Good"};
        String[] expectedFeatureSet5 =  {"?","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));
        expectedOutput.add(new Hypothesis(expectedFeatureSet2));
        expectedOutput.add(new Hypothesis(expectedFeatureSet3));
        expectedOutput.add(new Hypothesis(expectedFeatureSet4));
        expectedOutput.add(new Hypothesis(expectedFeatureSet5));
        
        System.out.println("Expected" +expectedOutput);

        assertEquals(expectedOutput +" " , outputList +" ");
	}
	
	@Test
	public void GeneralizeS()
	{
		
		HashSet<Hypothesis> min_generalizations = new HashSet<Hypothesis>();
		HashSet<Hypothesis> s = new HashSet<Hypothesis>();
		HashSet<Hypothesis> outputList;
		GeneralizeS generalizeObject = new GeneralizeS();
		String[] dataPoint = {""};
		outputList = generalizeObject.min_generalizations(s, dataPoint);
	}

}
