package org.dice_research.vspace;

import java.io.IOException;

public class Main {  public static void main(String[] argas) throws IOException {
	String filePath = InputFileExtension.csvFilePath();
    CandidateElimination ce = new CandidateElimination("Normal", filePath);
    ce.performElimication();


    CandidateElimination ceh = new CandidateElimination("Hierarchical", 
        "C:\\Users\\Sharique\\Downloads\\VersionSpaceLearning\\VersionSpaceLearning"
        + "\\VersionSpaceLearning\\src\\hierarchicalInstance1.csv",
        "C:\\Users\\Sharique\\Downloads\\VersionSpaceLearning\\VersionSpaceLearning"
        + "\\VersionSpaceLearning\\src\\dataOntology.csv");
    ceh.performElimication();
  }
}