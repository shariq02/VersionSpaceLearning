import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class SpecializeGBoundaryTest {

    @Test
    public void specialize() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         * which will contain all the previously defined array lists
         * containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};

        Hypothesis testHypothesisG = new Hypothesis(3,"G");       //Constructor calling to create the most Generalized Hypothesis
        Hypothesis testHypothesisS = new Hypothesis(3,"S");      //Constructor calling to create the most Specialized Hypothesis
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList = specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","?"};
        String[] expectedFeatureSet2 =  {"?","Yes","?"};
        String[] expectedFeatureSet3 =  {"?","No","?"};
        String[] expectedFeatureSet4 =  {"?","?","Good"};
        String[] expectedFeatureSet5 =  {"?","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }



    @Test
    public void specialize1() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};

        String[] genaralisedHypothesis = {"?", "Maybe","?"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);
        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","Maybe","?"};
        String[] expectedFeatureSet2 =  {"?","Yes","?"};
        String[] expectedFeatureSet3 =  {"?","No","?"};
        String[] expectedFeatureSet4 =  {"?","Maybe","Good"};
        String[] expectedFeatureSet5 =  {"?","Maybe","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }


    @Test
    public void specialize2() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};
        String[] genaralisedHypothesis = {"?", "?","OK"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);

        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);


        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","OK"};
        String[] expectedFeatureSet2 =  {"?","Yes","OK"};
        String[] expectedFeatureSet3 =  {"?","No","OK"};
        String[] expectedFeatureSet4 =  {"?","?","Good"};
        String[] expectedFeatureSet5 =  {"?","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }
    @Test
    public void specialize3() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};
        String[] genaralisedHypothesis = {"Orange", "?","?"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);

        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","?"};
        String[] expectedFeatureSet2 =  {"Orange","Yes","?"};
        String[] expectedFeatureSet3 =  {"Orange","No","?"};
        String[] expectedFeatureSet4 =  {"Orange","?","Good"};
        String[] expectedFeatureSet5 =  {"Orange","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }
    @Test
    public void specialize4() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};
        String[] genaralisedHypothesis = {"Orange", "?","OK"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);

        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","OK"};
        String[] expectedFeatureSet2 =  {"Orange","Yes","OK"};
        String[] expectedFeatureSet3 =  {"Orange","No","OK"};
        String[] expectedFeatureSet4 =  {"Orange","?","Good"};
        String[] expectedFeatureSet5 =  {"Orange","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }
    @Test
    public void specialize5() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};
        String[] genaralisedHypothesis = {"Orange", "?","?"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);

        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","?"};
        String[] expectedFeatureSet2 =  {"Orange","Yes","?"};
        String[] expectedFeatureSet3 =  {"Orange","No","?"};
        String[] expectedFeatureSet4 =  {"Orange","?","Good"};
        String[] expectedFeatureSet5 =  {"Orange","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }

    @Test
    public void specialize6() {
        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};
        String[] genaralisedHypothesis = {"?", "?","OK"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);

        Hypothesis testHypothesisS = new Hypothesis(3,"S");
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);


        /* Creating String type arrays which contains the expected output Hypotheses and
         *  putting all these hypotheses into a HashSet called expectedOutput
         *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/
        String[] expectedFeatureSet1 =  {"Mango","?","OK"};
        String[] expectedFeatureSet2 =  {"?","Yes","OK"};
        String[] expectedFeatureSet3 =  {"?","No","OK"};
        String[] expectedFeatureSet4 =  {"?","?","Good"};
        String[] expectedFeatureSet5 =  {"?","?","Bad"};


        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));expectedOutput.add(new Hypothesis(expectedFeatureSet5));

        assertEquals(expectedOutput +" " , outputList +" ");
    }



    @Test
    public void specialize7() {

        /* Declaring the Feature sets to put all possible feature values for each features*/
        ArrayList<String> featureSet1 = new ArrayList<>();
        ArrayList<String> featureSet2 = new ArrayList<>();
        ArrayList<String> featureSet3 = new ArrayList<>();

        HashSet<Hypothesis> outputList;
        SpecializeGBoundary specializeObject = new SpecializeGBoundary();

        /* Assigning all possible feature values in their respective feature sets */
        featureSet1.add("Mango");featureSet1.add("Orange");
        featureSet2.add("Yes");featureSet2.add("No");featureSet2.add("Maybe");
        featureSet3.add("OK");featureSet3.add("Good");featureSet3.add("Bad");

        /* Now Creating an array list named featureList
         which will contain all the previously defined array lists
         containing all possible feature values */

        ArrayList featureList = new ArrayList();
        featureList.add(featureSet1);featureList.add(featureSet2);featureList.add(featureSet3);

        /* Declaring a negative data which is to be compared with the generalized hypothesis */
        String[] negativeData = {"Orange","Maybe","OK"};

        String[] genaralisedHypothesis = {"?","?","?"};
        Hypothesis testHypothesisG = new Hypothesis(genaralisedHypothesis);
        String[] specializedHypothesis = {"Mango","Yes","-"};
        Hypothesis testHypothesisS = new Hypothesis(specializedHypothesis);
        HashSet<Hypothesis> specializedHypothesisSet = new HashSet<Hypothesis>();
        specializedHypothesisSet.add(testHypothesisS);
        outputList= specializeObject.specialize(negativeData, specializedHypothesisSet, featureList, testHypothesisG);

        /* Creating String type arrays which contains the expected output Hypotheses and
        *  putting all these hypotheses into a HashSet called expectedOutput
        *  We want to match this expectedOutput with the output we are getting from the Specialized boundary class*/

        String[] expectedFeatureSet1 =  {"Mango","?","?"};
        String[] expectedFeatureSet2 =  {"?","Yes","?"};
        String[] expectedFeatureSet3 =  {"?","?","Good"};
        String[] expectedFeatureSet4 =  {"?","?","Bad"};

        HashSet<Hypothesis> expectedOutput = new HashSet<Hypothesis>();

        expectedOutput.add(new Hypothesis(expectedFeatureSet1));expectedOutput.add(new Hypothesis(expectedFeatureSet2));expectedOutput.add(new Hypothesis(expectedFeatureSet3));expectedOutput.add(new Hypothesis(expectedFeatureSet4));

        assertEquals(expectedOutput +" " , outputList +" ");
    }

}