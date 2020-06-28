package org.dice_research.vspace;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * Here we are handling 2 types of data, 1) Simple Categorical Data
 * 										 2) Hierarchical Categorical Data
 * We are reading the Data file and create a List of Hashset containing all the feature values
 * Create the Hierarchical structure by makeGraph() method
 * calling the min_generalizations() and specialize() method to create the version space
 * 
 * @author Susmita Goswami
 */

public class CandidateElimination {
	private ArrayList<Instance> instances;
	private ArrayList<HashSet<String>> featureValues;
	private BufferedReader br;
	private String line = "";
	private String[] datas;
	private int datalen;
	private GeneralizeS genS;
	private SpecializeGBoundary spclG;
	private HashSet<Hypothesis> S;
	private HashSet<Hypothesis> G;
	private String filePath;
	private String ontPath;
	private static ArrayList<Ontology> featureGraph;
	private String mode;
	private String graphPath;

	public CandidateElimination(String mode, String path) {
		initialize(mode, path);
	}

	public CandidateElimination(String mode, String path, String graphPath) {
		initialize(mode, path);
		this.graphPath = graphPath;
	}

	public CandidateElimination() {
		// TODO Auto-generated constructor stub
	}

	public void initialize(String mode, String path) {
		this.mode = mode;
		this.instances = new ArrayList<>();
		this.featureValues = new ArrayList<>();
		this.datalen = 0;
		this.genS = new GeneralizeS();
		this.spclG = new SpecializeGBoundary();
		this.S = new HashSet<>();
		this.G = new HashSet<>();
		this.filePath = path;
	}

	/*
	 * In the performElimination() method it is reading the data file line by line
	 * and call instance class to divide it into attributes and labels finally a
	 * list featureValues is created which stores the possible values for every
	 * attributes
	 */
	public void performElimication() {
		try {
			this.br = new BufferedReader(new FileReader(new File(this.filePath)));
			while ((line = br.readLine()) != null) {
				datas = line.split(",");
				instances.add(new Instance(datas));
				if (datalen == 0) {
					datalen = datas.length - 1;
					S.add(new Hypothesis(datalen, "S"));
					G.add(new Hypothesis(datalen, "G"));
					for (int i = 1; i <= datalen; i++) {
						featureValues.add(new HashSet<>());
					}
				}
				for (int i = 0; i < datalen; i++) {
					featureValues.get(i).add(datas[i]);
				}
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Creating acyclic hierarchical structure for both normal and hierarchical
		 * cases 1) for Normal case the hierarchy is build based on the feature values
		 * 2) for Hierarchical case the hierarchy is build based on the .csv file
		 * containing the hierarchical relation
		 */
		if (this.mode.equals("Normal"))
			makeGraph(featureValues);
		else
			makeGraph();

		for (Instance inst : instances) {
			System.out.print("The instance is : ");
			System.out.println(inst.toString());
			if (inst.getLabel().equals("Yes")) {
				S = genS.min_generalizations(S, inst.getAttribs(), featureGraph);
				G = spclG.removeMember(S, G, featureGraph);
			} else {
				S = genS.removeMember(S, inst.getAttribs(), featureGraph);
				G = spclG.specialize(inst.getAttribs(), S, featureGraph, G);
			}
			/*
			 * Printing the S and G boundary for every instance
			 */
			System.out.println("S boundary :");
			System.out.println(getS());
			System.out.println("G boundary :");
			System.out.println(getG());
			System.out.println("================================================");
		}
	}

	public HashSet<Hypothesis> getS() {
		return S;
	}

	public HashSet<Hypothesis> getG() {
		return G;
	}

	/*
	 * Creating Acyclic Hierarchical structure based on the feature values for
	 * Normal case
	 */
	public static ArrayList<Ontology> makeGraph(ArrayList<HashSet<String>> fValues) {
		featureGraph = new ArrayList<>();
		for (HashSet<String> hsets : fValues) {
			Ontology adg = new Ontology();
			for (String uniqFeatures : hsets) {
				adg.getRoot().addChild(uniqFeatures);
				adg.search(adg.getRoot(), uniqFeatures).addChild("-");
			}

			featureGraph.add(adg);
		}
		return featureGraph;
	}

	/*
	 * Creating Acyclic Hierarchical structure based on the .csv file containing the
	 * hierarchical relation for Hierarchical case
	 */

	private void makeGraph() {
		featureGraph = new ArrayList<>();
		for (int i = 1; i <= this.datalen; i++) {
			featureGraph.add(new Ontology());
		}
		try {
			this.br = new BufferedReader(new FileReader(new File(this.graphPath)));
			while ((line = br.readLine()) != null) {
				datas = line.split(",");
				Ontology grph = featureGraph.get(Integer.parseInt(datas[0]));
				Vertices targetNode = grph.search(grph.getRoot(), datas[1]);
				if (targetNode == null) {
					grph.getRoot().addChild(datas[1]);
					targetNode = grph.search(grph.getRoot(), datas[1]);
				}
				for (int i = 2; i < datas.length; i++) {
					targetNode.addChild(datas[i]);
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Ontology graphs : featureGraph)
			graphs.addStopper(graphs.getRoot());

	}

}
