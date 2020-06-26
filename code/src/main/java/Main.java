import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main(String[] argas) {
        //CandidateElimination ce = new CandidateElimination("Normal", "E:\\project\\VersionSpaceLearning\\VersionSpaceLearning\\src\\data.csv");
       // ce.performElimication();


        CandidateElimination ceh = new CandidateElimination("Hierarchical", "E:\\project\\VersionSpaceLearning\\VersionSpaceLearning\\src\\hierarchicalInstance1.csv",
                "E:\\project\\VersionSpaceLearning\\VersionSpaceLearning\\src\\dataOntology.csv");
        ceh.performElimication();


    }

}
