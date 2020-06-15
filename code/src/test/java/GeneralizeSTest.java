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
        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);
        String[] negativeData = {"Orange","Maybe","OK"};
        
        Hypothesis testHypothesisG = new Hypothesis(3,"G");       //Constructor calling to create the most Generalized Hypothesis
        Hypothesis testHypothesisS = new Hypothesis(3,"S");      //Constructor calling to create the most Specialized Hypothesis
        HashSet<Hypothesis> generalizeHypothesisSet = new HashSet<Hypothesis>();
        generalizeHypothesisSet.add(testHypothesisS);
        outputList = generalizeObject.min_generalizations(testHypothesisG, testHypothesisS);
        		//.specialize(negativeData, generalizeHypothesisSet, featureList, testHypothesisG);
	}

}
