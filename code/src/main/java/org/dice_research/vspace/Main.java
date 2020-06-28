package org.dice_research.vspace;

/*
 * Here we are creating two objects for CandidateElimination class 
 * one is for Simple Categorical data; that is "Normal" case
 * and another is Hierarchical data; which is "Hierarchical" case
 * We are passing the data file path for the normal case
 * and data file path along with ontology relation file path for the hierarchical case
 * 
 * @author Abhratanu Surai
 */

public class Main {

    public static void main(String[] argas) {
        CandidateElimination ce = new CandidateElimination("Normal", "C:\\Users\\Abhratanu Surai\\IdeaProjects\\VersionSpaceLearning4\\src\\data.csv");
        ce.performElimication();
		  System.out.println("S boundary is:");
        System.out.println(ce.getS());
        System.out.println("G boundary is:");
        System.out.println(ce.getG());

        System.out.println("================================================");

        CandidateElimination ceh = new CandidateElimination("Hierarchical", "C:\\Users\\Abhratanu Surai\\IdeaProjects\\VersionSpaceLearning4\\src\\hierarchicalInstance1.csv",
                "C:\\Users\\Abhratanu Surai\\IdeaProjects\\VersionSpaceLearning4\\src\\dataOntology.csv");
        ceh.performElimication();
        System.out.println("S boundary is:");
        System.out.println(ceh.getS());
        System.out.println("G boundary is:");
        System.out.println(ceh.getG());

    }

}
