package org.dice_research.vspace;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.QueryReader;
import org.dice_research.SparqlUseCase.SPARQLQueryParser;
import org.dice_research.SparqlUseCase.Triple;

public class CandidateElimination {
    private ArrayList<Instance> instances;
    private ArrayList<HashSet<String>> featureValues;
    private BufferedReader br;
    private String line = "";
    private String[] datas ;
    private int datalen;
    private GeneralizeS genS ;
    private SpecializeGBoundary spclG ;
    private HashSet<Hypothesis> S ;
    private HashSet<Hypothesis> G ;
    private String filePath;
    private String ontPath;
    private static ArrayList<Ontology> featureGraph;
    private String mode;
    private String graphPath;
    
    //spab use case dependencies
    private List<Query> positiveQueries;
    private List<Query> negativeQueries;
    private Set<Query> mostSpecialBoundary;
    private Set<Query> mostGeneralBoundary;

    public CandidateElimination(String mode, String path)
    {
        initialize(mode,path);
    }

    public CandidateElimination(String mode, String path, String graphPath)
    {
    	if(mode.toLowerCase().equals("spab")) {
        	//give the paths to the positive and negative lists of queries to the initializer
    		spabInit(path, graphPath);
        } else {
        	initialize(mode,path);
            this.graphPath = graphPath;
        }
        
    }
    
    //constructor for the spab use case
  	public void spabInit(String posQueriesPath, String negQueriesPath) {
  		this.mostSpecialBoundary = new HashSet<Query>();
  		this.mostGeneralBoundary = new HashSet<Query>();
  		this.positiveQueries = new ArrayList<Query>();
  		this.negativeQueries = new ArrayList<Query>();
  		this.genS = new GeneralizeS();
        this.spclG = new SpecializeGBoundary();
  		
  		//add the most specific/general query (query which has no triples) to the respective set
  		this.mostSpecialBoundary.add(new Query());
  		this.mostGeneralBoundary.add(new Query("*"));
  		
  		//should read the queries here, and add the parsed version in the positive or negative list
  		List<String> posReadQueries = null;
  		List<String> negReadQueries = null;
		
		try {
			posReadQueries = QueryReader.readQueries(posQueriesPath);
			for(String s: posReadQueries) {
				Query q = new Query(s);
				SPARQLQueryParser.parse(q);
				this.positiveQueries.add(q);
			}
			negReadQueries = QueryReader.readQueries(negQueriesPath);
			for(String s: negReadQueries) {
				Query q = new Query(s);
				SPARQLQueryParser.parse(q);
				this.negativeQueries.add(q);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
  	}

	public void initialize(String mode, String path)
    {
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
	
	
	public void spabElimination() {
		//process positive examples first
		
		for(ListIterator<Query> positivePointsIter = positiveQueries.listIterator(); positivePointsIter.hasNext();) {
			Query currentPosPoint = positivePointsIter.next();
			Set<Query> newSMembers = new HashSet<Query>();
			for(Iterator<Query> sBoundaryIter = this.mostSpecialBoundary.iterator(); sBoundaryIter.hasNext();) {
				Query currentS = sBoundaryIter.next();
				if(!currentS.isMoreGeneralThan(currentPosPoint, 0, new HashMap<Integer, Integer>(), null)) {
					sBoundaryIter.remove();
					Set<Query> minGeneralizations = genS.min_generalizations(currentS, currentPosPoint);
					for(Query q: minGeneralizations) {
						if(q.isMoreGeneralThan(currentPosPoint, 0, new HashMap<Integer, Integer>(), null)){
							for(Query w: this.mostGeneralBoundary) {
								if(w.isMoreGeneralThan(q, 0, new HashMap<Integer, Integer>(), null)) {
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
		
		//process the negative examples
		if(this.mostSpecialBoundary.size() > 0) {
			this.mostGeneralBoundary.clear();
			this.mostGeneralBoundary.add(new Query(this.mostSpecialBoundary.iterator().next().getTriples().size()));
			for(ListIterator<Query> negativePointsIter = negativeQueries.listIterator(); negativePointsIter.hasNext();) {
				Query currentNegPoint = negativePointsIter.next();
				Set<Query> newGMembers = new HashSet<Query>();
				for(Iterator<Query> gBoundaryIter = this.mostGeneralBoundary.iterator(); gBoundaryIter.hasNext();) {
					Query currentG = gBoundaryIter.next();
					if(currentG.isMoreGeneralThan(currentNegPoint, 0, new HashMap<Integer, Integer>(), null)) {
						gBoundaryIter.remove();
						
						Set<Query> minSpecializations = spclG.min_specializations(currentG, currentNegPoint, this.mostSpecialBoundary);
						
						Query k = minSpecializations.iterator().next();
						System.out.println(currentNegPoint.getParsedQuery());
						System.out.println(k.getParsedQuery());
						System.out.println("HOW?: "+k.isMoreGeneralThan(currentNegPoint, 0, new HashMap<Integer, Integer>(), null));
//						for(Triple u: k.getTriples()) {
//							System.out.println(u.getSubject().getType()+", "+u.getPredicate().getType()+", "+u.getObject().getType());
//						}
						
//						for(Query q: minSpecializations) {
//							System.out.println(q.getParsedQuery());
//						}
						for(Query q: minSpecializations) {
							if(!q.isMoreGeneralThan(currentNegPoint, 0, new HashMap<Integer, Integer>(), null)){
								for(Query w: this.mostSpecialBoundary) {
									System.out.println(q.getParsedQuery());
									if(!w.isMoreGeneralThan(q, 0, new HashMap<Integer, Integer>(), null)) {
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
		if(this.mostSpecialBoundary.size() > 0) {
			System.out.println(this.mostSpecialBoundary.iterator().next().getParsedQuery());
		}
		System.out.println("]");
		System.out.println("G: [");
		if(this.mostGeneralBoundary.size() > 0) {
			for(Query q: this.mostGeneralBoundary) {
				System.out.println(q.getParsedQuery());
				System.out.println(q.hashCode());
			}
		}
		System.out.println("]");
		
		
//		for(Query q: this.positiveQueries) {
//			System.out.println(q.getParsedQuery());
//		}
//		System.out.println(this.positiveQueries.size());
//		System.out.println("Removed: "+this.positiveQueries.get(15).getTriples().remove(3));
//		System.out.println("Removed: "+this.positiveQueries.get(15).getTriples().remove(2));
//		System.out.println("Removed: "+this.positiveQueries.get(15).getTriples().remove(0));
//		for(Query q: this.positiveQueries) {
//			System.out.println(q.
//						isMoreGeneralThan(this.mostSpecialBoundary.iterator().next(), 0, new HashMap<Integer, Integer>(), null));
//		}

		
		
	}

    public void performElimication()
    {
        try {
        this.br = new BufferedReader(new FileReader(new File(this.filePath)));
        while ((line = br.readLine()) != null) {
            datas = line.split(",");
            instances.add(new Instance(datas));
            if (datalen == 0)
            {
                datalen = datas.length -1;
                S.add(new Hypothesis(datalen,"S"));
                G.add(new Hypothesis(datalen,"G"));
                for (int i = 1; i <= datalen; i++)
                {
                    featureValues.add(new HashSet<>());
                }
            }
            for (int i = 0; i < datalen ; i ++)
            {
                featureValues.get(i).add(datas[i]);
            }
        }

        } catch (
        FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (
        IOException e) {
            e.printStackTrace();
        }
        if (this.mode.equals("Normal")) makeGraph(featureValues);
        else makeGraph();

        for(Instance inst: instances)
        {
            if(inst.getLabel().equals("Yes"))
            {
                S = genS.min_generalizations(S, inst.getAttribs(),featureGraph);
                G = spclG.removeMember(S,G, featureGraph);
            }
            else
            {
                S = genS.removeMember(S, inst.getAttribs(), featureGraph);
                G = spclG.specialize(inst.getAttribs(), S, featureGraph, G);
            }
            System.out.println("S boundary is:");
            System.out.println(this.S);
            System.out.println("G boundary is:");
            System.out.println(this.G);
            System.out.println("#######################################################################");
        }
    }

    public HashSet<Hypothesis> getS() {
        return S;
    }

    public HashSet<Hypothesis> getG() {
        return G;
    }

    public static ArrayList<Ontology> makeGraph(ArrayList<HashSet<String>> fValues)
    {
        featureGraph = new ArrayList<>();
        for ( HashSet<String> hsets : fValues)
        {
            Ontology adg = new Ontology();
            for (String uniqFeatures: hsets)
            {
                adg.getRoot().addChild(uniqFeatures);
                adg.search(adg.getRoot(),uniqFeatures).addChild("-");
            }

            featureGraph.add(adg);
        }
        return featureGraph;
    }

    private void makeGraph()
    {
        featureGraph = new ArrayList<>();
        for (int i = 1; i <= this.datalen; i++)
        {
            featureGraph.add(new Ontology());
        }
        try {
            this.br = new BufferedReader(new FileReader(new File(this.graphPath)));
            while ((line = br.readLine()) != null) {
                datas = line.split(",");
                Ontology grph = featureGraph.get(Integer.parseInt(datas[0]));
                Vertices targetNode = grph.search(grph.getRoot(), datas[1]);
                if (targetNode == null)
                {
                    grph.getRoot().addChild(datas[1]);
                    targetNode = grph.search(grph.getRoot(), datas[1]);
                }
                for (int i = 2; i < datas.length; i ++)
                {
                    targetNode.addChild(datas[i]);
                }
            }
        }
        catch(FileNotFoundException ex){
                ex.printStackTrace();
        }
        catch(IOException e){
                e.printStackTrace();
        }

        for(Ontology graphs : featureGraph) graphs.addStopper(graphs.getRoot());

    }

}
