package org.dice_research.vspace;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.QueryReader;
import org.dice_research.SparqlUseCase.SPARQLQueryParser;

/**
 * @author Susmita Goswami, Parth sharma
 */

/**
 * variable declaration
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
	private HashSet<Hypothesis> inst_S;
	private HashSet<Hypothesis> inst_G;
	private HashSet<Hypothesis> merged_S;
	private HashSet<Hypothesis> merged_G;
	private HashSet<Hypothesis> placeholder;
	private HashSet<Hypothesis> placeholder_S;
	private HashSet<Hypothesis> placeholder_G;
	private HashSet<Hypothesis> consistentG;
	private HashSet<Hypothesis> placeholder_incnstc;
	private String filePath;
	private ArrayList<Ontology> featureGraph;
	private String mode;
	private String graphPath;
	private Boolean inconsistancy;
	private ArrayList<VersionSpace> VS_hSet;

	// spab use case dependencies
	private List<Query> positiveQueries;
	private List<Query> negativeQueries;
	private Set<Query> mostSpecialBoundary;
	private Set<Query> mostGeneralBoundary;

	/**
	 * Constructor initialization and it does the candidate elimination on basis of
	 * the mode selection.
	 */
	public CandidateElimination(String mode, String path) {
		initialize(mode, path);
	}

	public CandidateElimination(String mode, String path, String graphPath) {
		if (mode.toLowerCase().equals("spab")) {
			// give the paths to the positive and negative lists of queries to the
			// initializer
			spabInit(path, graphPath);
		} else {
			initialize(mode, path);
			this.setGraphPath(graphPath);
		}

	}

	public CandidateElimination() {
		// TODO Auto-generated constructor stub
	}

	// constructor for the spab use case
	public void spabInit(String posQueriesPath, String negQueriesPath) {
		this.mostSpecialBoundary = new HashSet<Query>();
		this.mostGeneralBoundary = new HashSet<Query>();
		this.positiveQueries = new ArrayList<Query>();
		this.negativeQueries = new ArrayList<Query>();
		this.setGenS(new GeneralizeS());
		this.setSpclG(new SpecializeGBoundary());

		// add the most specific/general query (query which has no triples) to the
		// respective set
		this.mostSpecialBoundary.add(new Query());
		this.mostGeneralBoundary.add(new Query("*"));

		// should read the queries here, and add the parsed version in the positive or
		// negative list
		List<String> posReadQueries = null;
		List<String> negReadQueries = null;

		try {
			posReadQueries = QueryReader.readQueries(posQueriesPath);
			for (String s : posReadQueries) {
				Query q = new Query(s);
				SPARQLQueryParser.parse(q);
				this.positiveQueries.add(q);
			}
			negReadQueries = QueryReader.readQueries(negQueriesPath);
			for (String s : negReadQueries) {
				Query q = new Query(s);
				SPARQLQueryParser.parse(q);
				this.negativeQueries.add(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Variable initialization
	 */
	public void initialize(String mode, String path) {
		this.mode = mode;
		this.instances = new ArrayList<>();
		this.setFeatureValues(new ArrayList<>());
		this.setDatalen(0);
		this.setGenS(new GeneralizeS());
		this.setSpclG(new SpecializeGBoundary());
		this.S = new HashSet<>();
		this.G = new HashSet<>();
		this.setInst_S(new HashSet<>());
		this.setInst_G(new HashSet<>());
		this.merged_S = new HashSet<>();
		this.merged_G = new HashSet<>();
		this.placeholder = new HashSet<>();
		this.placeholder_S = new HashSet<>();
		this.placeholder_G = new HashSet<>();
		this.placeholder_incnstc = new HashSet<>();
		this.setConsistentG(new HashSet<>());
		this.setVS_hSet(new ArrayList<>());
		this.filePath = path;
		this.inconsistancy = false;
	}

	public void spabElimination() {
		// process positive examples first

		for (ListIterator<Query> positivePointsIter = positiveQueries.listIterator(); positivePointsIter.hasNext();) {
			Query currentPosPoint = positivePointsIter.next();
			Set<Query> newSMembers = new HashSet<Query>();
			for (Iterator<Query> sBoundaryIter = this.mostSpecialBoundary.iterator(); sBoundaryIter.hasNext();) {
				Query currentS = sBoundaryIter.next();
				if (!currentS.isMoreGeneralThan(currentPosPoint, 0, new HashMap<Integer, Integer>(), null)) {
					sBoundaryIter.remove();
					Set<Query> minGeneralizations = getGenS().min_generalizations(currentS, currentPosPoint);
					for (Query q : minGeneralizations) {
						if (q.isMoreGeneralThan(currentPosPoint, 0, new HashMap<Integer, Integer>(), null)) {
							for (Query w : this.mostGeneralBoundary) {
								if (w.isMoreGeneralThan(q, 0, new HashMap<Integer, Integer>(), null)) {
									newSMembers.add(q);
									break;
								}
							}
						}
					}
				}
			}
			this.mostSpecialBoundary.addAll(newSMembers);
		}

		// process the negative examples
		if (this.mostSpecialBoundary.size() > 0) {
			for (ListIterator<Query> negativePointsIter = negativeQueries.listIterator(); negativePointsIter
					.hasNext();) {
				Query currentNegPoint = negativePointsIter.next();
				Set<Query> newGMembers = new HashSet<Query>();
				for (Iterator<Query> gBoundaryIter = this.mostGeneralBoundary.iterator(); gBoundaryIter.hasNext();) {
					Query currentG = gBoundaryIter.next();
					if (currentG.isMoreGeneralThan(currentNegPoint, 0, new HashMap<Integer, Integer>(), null)) {
						gBoundaryIter.remove();
						Set<Query> minSpecializations = getSpclG().recursiveMinSpecialization(null, currentG,
								currentNegPoint, this.mostSpecialBoundary);
						for (Query q : minSpecializations) {
							if (!q.isMoreGeneralThan(currentNegPoint, 0, new HashMap<Integer, Integer>(), null)) {
								for (Query w : this.mostSpecialBoundary) {
									if (!w.isMoreGeneralThan(q, 0, new HashMap<Integer, Integer>(), null)) {
										newGMembers.add(q);
										break;
									}
								}
							}
						}
					}
				}
				this.mostGeneralBoundary.addAll(newGMembers);
			}
		}
		System.out.println("S:[");
		System.out.println("");
		if (this.mostSpecialBoundary.size() > 0) {
			for (Query q : this.mostSpecialBoundary) {
				System.out.println(q.getParsedQuery());
			}
		}
		System.out.println("]");
		System.out.println("");
		System.out.println("G:[");
		System.out.println("");
		if (this.mostGeneralBoundary.size() > 0) {
			for (Query q : this.mostGeneralBoundary) {
				System.out.println(q.getParsedQuery());
			}
		}
		System.out.println("]");
	}

	/**
	 * This piece of function performs candidate elimination. It reads and splits
	 * the data from file. Initially, after reading the first instance the S and G
	 * boundaries are initialized and datalength is fixed. In addition to this, here
	 * we have maintained a variable consistent G to maintain all the hypotheses for
	 * G. It will help us to compare G boundaries later. Initial S and G hypotheses
	 * gets added to Version Space set. From data a unique value set for all
	 * features are prepared.
	 */
	public void performElimination() {
		try {
			this.br = new BufferedReader(new FileReader(new File(this.filePath)));
			while ((line = br.readLine()) != null) {
				datas = line.split(",");
				instances.add(new Instance(datas));
				if (getDatalen() == 0) {
					setDatalen(datas.length - 1);
					S.add(new Hypothesis(getDatalen(), false));
					G.add(new Hypothesis(getDatalen(), true));
					getConsistentG().add(new Hypothesis(getDatalen(), true));
					getVS_hSet().add(new VersionSpace(S, G));
					for (int i = 1; i <= getDatalen(); i++) {
						getFeatureValues().add(new HashSet<>());
					}
				}
				for (int i = 0; i < getDatalen(); i++) {
					getFeatureValues().get(i).add(datas[i]);
				}
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * It checks the mode and makes the corresponding feature graph.
		 */
		if (this.mode.equals("Normal"))
			makeGraph(getFeatureValues());
		else
			makeGraph();

		/**
		 * placeholder_S and placeholder_G used to store temporary hypothesis
		 */

		placeholder_S.add(new Hypothesis(getDatalen(), false));
		placeholder_G.add(new Hypothesis(getDatalen(), true));

		/**
		 * inst_S and inst_G is single instance specific S and G boundary If the label
		 * is positive, the algorithm does generalization else it does specialization.
		 * In addition to this, in consistent G it holds the G boundary that is
		 * consistent with all negative instances the algorithm has seen till now Then
		 * it calls the mergeVersionSpace function and after merging or failed merging
		 * the algorithm prints the number of version space it has found and the
		 * corresponding S and G boundary. Lastly, inst_S and inst_G get cleared for
		 * reuse.
		 */

		for (Instance inst : instances) {
			getInst_S().add(new Hypothesis(getDatalen(), false));
			getInst_G().add(new Hypothesis(getDatalen(), true));
			System.out.println("The instance is");
			System.out.println(inst.toString());
			if (inst.getLabel().equals("Yes")) {
				setInst_S(getGenS().min_generalizations(getInst_S(), inst.getAttribs(), featureGraph, getInst_G()));
				setInst_G(getSpclG().removeMember(getInst_S(), getInst_G(), featureGraph));
			} else {
				setConsistentG(
						getSpclG().specialize(inst.getAttribs(), getInst_S(), featureGraph, getConsistentG(), "ce"));
				setInst_S(getGenS().removeMember(getInst_S(), inst.getAttribs(), featureGraph));
				setInst_G(getSpclG().specialize(inst.getAttribs(), getInst_S(), featureGraph, getInst_G(), "ce"));

			}
			mergeVersionSpace(inst);
			System.out.println("Total Version space found: " + Integer.toString(getVS_hSet().size()));
			int indx = 1;
			for (VersionSpace vs : getVS_hSet()) {
				System.out.println("S boundary of VS" + String.valueOf(indx) + " is: ");
				System.out.println(vs.getS());
				System.out.println("G boundary of VS" + String.valueOf(indx) + " is: ");
				System.out.println(vs.getG());
				System.out.println("#######################################################################");
				indx++;
			}
			getInst_S().clear();
			getInst_G().clear();
		}
	}

	public HashSet<Hypothesis> getS() {
		return S;
	}

	public HashSet<Hypothesis> getG() {
		return G;
	}

	/**
	 * Graph formation on depending upon the mode. Recent changes make graph
	 * functions made public. featureGraph variable made public.
	 */

	public void makeGraph(ArrayList<HashSet<String>> fValues) {
		featureGraph = new ArrayList<>();
		for (HashSet<String> hsets : fValues) {
			Ontology adg = new Ontology();
			for (String uniqFeatures : hsets) {
				adg.getRoot().addChild(uniqFeatures);
				adg.search(adg.getRoot(), uniqFeatures).addChild("-");
			}

			featureGraph.add(adg);
		}

	}

	public void makeGraph() {
		featureGraph = new ArrayList<>();
		for (int i = 1; i <= this.getDatalen(); i++) {
			featureGraph.add(new Ontology());
		}
		try {
			this.br = new BufferedReader(new FileReader(new File(this.getGraphPath())));
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

	/**
	 * After a single instance coming,the algorithm tries to merge instance specific
	 * s and g boundaries with one of the existing versionspaces. If merging is
	 * successful,the existing version space is modified and the algorithm continues
	 * with next instance. If the merge failed i.e. produces null s and g boundary
	 * then a new version space is created. s boundary for the new version space is
	 * the instances and the G boundary is the consistent G.
	 * 
	 */

	void mergeVersionSpace(Instance inst) {
		int versionSpaceLength = getVS_hSet().size();
		int counter = 1;
		for (VersionSpace vs : getVS_hSet()) {
			this.S = vs.getS();
			this.G = vs.getG();
			for (Hypothesis hypo_s1 : S) {
				for (Hypothesis hypo_s2 : getInst_S()) {
					if (hypo_s1.isMoreGeneralThan(hypo_s2, featureGraph))
						merged_S.add(hypo_s1);
					else if (hypo_s1.isMoreSpecificThan(hypo_s2, featureGraph))
						merged_S.add(hypo_s2);
					else if (hypo_s1.equals(hypo_s2))
						merged_S.add(hypo_s1);
					else {
						placeholder.add(hypo_s1);
						placeholder = getGenS().min_generalizations(placeholder, hypo_s2.features, featureGraph,
								placeholder_G);
						for (Hypothesis pholder_hypo : placeholder)
							merged_S.add(pholder_hypo);
						placeholder.clear();
					}
				}
			}

			for (Hypothesis hypo_g1 : G) {
				for (Hypothesis hypo_g2 : getInst_G()) {
					if (hypo_g1.isMoreSpecificThan(hypo_g2, featureGraph))
						merged_G.add(hypo_g1);
					else if (hypo_g1.isMoreGeneralThan(hypo_g2, featureGraph))
						merged_G.add(hypo_g2);
					else if (hypo_g1.equals(hypo_g2))
						merged_G.add(hypo_g1);
					else {
						placeholder.add(hypo_g1);
						placeholder = getSpclG().specialize(hypo_g2.features, placeholder_S, featureGraph, placeholder,
								"ivsm");
						for (Hypothesis pholder_gHypo : placeholder)
							merged_G.add(pholder_gHypo);
						placeholder.clear();
					}
				}
			}

			/**
			 * merged_G and merged_S finally are formed by removing more specific and more
			 * general hypothesis. S and G boundaries are formed from the merged_S and
			 * merged_G by removing hypotheses which are more general or more specific than
			 * itself and it does it by using the following functions.
			 */
			merged_G = getSpclG().removeMember(S, merged_G, featureGraph);
			merged_G = getSpclG().removeMember(getInst_S(), merged_G, featureGraph);

			merged_S = getGenS().compareG_Remove(merged_S, G, featureGraph);
			merged_S = getGenS().compareG_Remove(merged_S, getInst_G(), featureGraph);

			G = getSpclG().removeSpecific(merged_G, featureGraph);
			S = getGenS().removeGeneric(merged_S, featureGraph);
			merged_S.clear();
			merged_G.clear();
			/**
			 * S and G boundary of Version space can get empty if the algorithm fails to
			 * cover the instance hence the inconsistency flag is set.
			 */
			if (!(S.isEmpty() || G.isEmpty())) {
				vs.setS(getGenS().removeGeneric(S, featureGraph));
				vs.setG(getSpclG().removeSpecific(G, featureGraph));
				inconsistancy = false;
				if (inst.getLabel().equals("Yes") || counter == versionSpaceLength) // loop continues only for negative
																					// label
				{
					counter++;
					break;
				}
			}
			inconsistancy = true;
			counter++;
		}

		/**
		 * If the inconsistency flag is set then the new version space has been created.
		 */

		if (inconsistancy) {
			setConsistentG(getSpclG().removeSpecific(getConsistentG(), featureGraph));
			/**
			 * Hypothesis are removed from g boundary which is not consistent with s
			 * boundary.
			 */
			getVS_hSet().add(new VersionSpace(getGenS().removeGeneric(getInst_S(), featureGraph), getSpclG()
					.removeMember(getGenS().removeGeneric(getInst_S(), featureGraph), getConsistentG(), featureGraph)));
		}

	}

	public ArrayList<Ontology> getFeatureGraph() {
		return featureGraph;
	}

	public int getDatalen() {
		return datalen;
	}

	public void setDatalen(int datalen) {
		this.datalen = datalen;
	}

	public ArrayList<HashSet<String>> getFeatureValues() {
		return featureValues;
	}

	public void setFeatureValues(ArrayList<HashSet<String>> featureValues) {
		this.featureValues = featureValues;
	}

	public String getGraphPath() {
		return graphPath;
	}

	public void setGraphPath(String graphPath) {
		this.graphPath = graphPath;
	}

	public HashSet<Hypothesis> getConsistentG() {
		return consistentG;
	}

	public void setConsistentG(HashSet<Hypothesis> consistentG) {
		this.consistentG = consistentG;
	}

	public HashSet<Hypothesis> getInst_S() {
		return inst_S;
	}

	public void setInst_S(HashSet<Hypothesis> inst_S) {
		this.inst_S = inst_S;
	}

	public HashSet<Hypothesis> getInst_G() {
		return inst_G;
	}

	public void setInst_G(HashSet<Hypothesis> inst_G) {
		this.inst_G = inst_G;
	}

	public GeneralizeS getGenS() {
		return genS;
	}

	public void setGenS(GeneralizeS genS) {
		this.genS = genS;
	}

	public SpecializeGBoundary getSpclG() {
		return spclG;
	}

	public void setSpclG(SpecializeGBoundary spclG) {
		this.spclG = spclG;
	}

	public ArrayList<VersionSpace> getVS_hSet() {
		return VS_hSet;
	}

	public void setVS_hSet(ArrayList<VersionSpace> vS_hSet) {
		VS_hSet = vS_hSet;
	}
}