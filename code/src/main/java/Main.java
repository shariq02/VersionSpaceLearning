package VersionSpaceLearning.VersionSpaceLearning.src;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashSet;

public class Main {

    public static void main(String[] argas) {
        CandidateElimination ce = new CandidateElimination("Normal", "E:\\EclipseWorspaces\\JavaTraining\\udemytraining\\src\\VersionSpaceLearning\\VersionSpaceLearning\\src\\data.csv");
        ce.performElimication();
        System.out.println("S boundary is:");
        System.out.println(ce.getS());
        System.out.println("G boundary is:");
        System.out.println(ce.getG());

        System.out.println("================================================");

        CandidateElimination ceh = new CandidateElimination("Hierarchical", "E:\\EclipseWorspaces\\JavaTraining\\udemytraining\\src\\VersionSpaceLearning\\VersionSpaceLearning\\src\\hierarchicalInstance1.csv",
                "E:\\EclipseWorspaces\\JavaTraining\\udemytraining\\src\\VersionSpaceLearning\\VersionSpaceLearning\\src\\dataOntology.csv");
        ceh.performElimication();
        System.out.println("S boundary is:");
        System.out.println(ceh.getS());
        System.out.println("G boundary is:");
        System.out.println(ceh.getG());

    }

}
