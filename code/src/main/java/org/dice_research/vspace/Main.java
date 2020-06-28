package org.dice_research.vspace;

public class Main {

    public static void main(String[] argas) {
        //CandidateElimination ce = new CandidateElimination("Normal", "E:\\project\\VersionSpaceLearning\\VersionSpaceLearning\\src\\data.csv");
       // ce.performElimication();


        CandidateElimination ceh = new CandidateElimination("Hierarchical", "C:\\Users\\Sharique\\Downloads\\VersionSpaceLearning\\VersionSpaceLearning\\VersionSpaceLearning\\src\\hierarchicalInstance1.csv",
                "C:\\Users\\Sharique\\Downloads\\VersionSpaceLearning\\VersionSpaceLearning\\VersionSpaceLearning\\src\\dataOntology.csv");
        ceh.performElimication();


    }

}
