package org.dice_research.vspace;


import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.Triple;
import org.dice_research.SparqlUseCase.TripleValue;

public class SpecializeGBoundary {

    public static final String ANY= "?";
    public static final String NONE = "-";

    public HashSet<Hypothesis> specialize(String[] ne, HashSet<Hypothesis> s, ArrayList<Ontology> f_pssibleValues,  HashSet<Hypothesis> k, String mode)
    {
        HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
        HashSet<Hypothesis> spGListFinal = new HashSet<Hypothesis>();
        Iterator iter;
        for (Hypothesis h : k) {
            String[] base = new String[h.features.length];

            String value;
            // IF our general hypothesis is already consistent with -ve example , no need to specialize it
            if (h.isConsistentWithDataPoint(ne, false, f_pssibleValues) && mode.equals("ce"))
            {
                spGList.add(h);
                continue;
            }

            /* If not then we ned to create multiple more specific hypothesis
             *  1) For each possible value of each feature the function will create a specialize hypothesis
             *  2) The after creation of the complete list, it will check which hypotheses are more than than at least one of the
             *       hypothesis is s. it will remove others.
             *  3) in order to achieve this all features of the -ve data point is matched with this hypothesis features.
             *        -- if this hypothesis feature is ANY if can specialize it
             *        -- if this hypothesis feature is same as data then we need to skip and look for other features to generalize
             *  4) Finally if this function is able to generate any specialize hypothesis if will return with a arralist of hypotheses */
            for (int i = 0; i < ne.length; i++) {
                Ontology featureGraph = f_pssibleValues.get(i);
                Vertices currentNode = featureGraph.search(featureGraph.getRoot(),h.features[i]);
                for (int j = 0; j < h.features.length; j++) {
                    base[j] = h.features[j].toString();
                }
                for(Vertices child: currentNode.getChild())
                {
                    Vertices result = featureGraph.search(child,ne[i]);
                    if(result != null )
                    {
                        //if(child != result.getParent() && !child.getValue().equals(NONE))
                        if(!child.getValue().equals(NONE))
                        {
                            Hypothesis dummy = new Hypothesis(base);
                            dummy.features[i] = child.getValue();
                            HashSet<Hypothesis> dummyG = new HashSet<>();
                            dummyG.add(dummy);
                            spGList.addAll(specialize(ne, s, f_pssibleValues, dummyG,"ce"));
                        }
                        continue;
                    }
                    base[i] = child.getValue();
                    spGList.add(new Hypothesis(base));
                }
                }
            }

        iter = spGList.iterator();
        while(iter.hasNext())
        {
            Hypothesis check = ((Hypothesis) iter.next());
            if (!check.isConsistentWithDataPoint(ne, false,f_pssibleValues))
            {
                iter.remove();
                continue;
            }
            int counter = 0;
            for (Hypothesis h : s)
            {
                if(check.isMoreGeneralThan(h, f_pssibleValues))
                {
                    counter++;
                    break;
                }
            }
            if (counter == 0) iter.remove();
        }


        // Removing hypothesis that are not as general as others
        for (Hypothesis hyps : spGList)
        {
            int counter = 0;
            for (Hypothesis hypotheses : spGList)
            {
                if(hyps == hypotheses) continue;
                else if (hyps.isMoreSpecificThan(hypotheses, f_pssibleValues))
                {
                    counter ++;
                    break;
                }
            }
            if (counter==0) spGListFinal.add(hyps);
        }

        return spGListFinal;
    }
/**
 * This function removes hypothesis from G boundary that are not more general than any hypothesis from S boundary.
 */
    public HashSet<Hypothesis> removeMember(HashSet<Hypothesis> S, HashSet<Hypothesis> G, ArrayList<Ontology> f_pssibleValues)
    {
        HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
        if (S.isEmpty()) return spGList;
        for (Hypothesis g: G)
        {
            int counter = 0;
            for (Hypothesis h : S)
            {
                if(g.isMoreGeneralThan(h, f_pssibleValues))
                {
                    counter++;
                    break;
                }
            }
            if (counter != 0) spGList.add(g);
        }
        return spGList;
    }

    /**
     * This function removes hypothesis from G boundary which is specific than any other hypothesis in the boundary.
     */
    public HashSet<Hypothesis> removeSpecific(HashSet<Hypothesis> G, ArrayList<Ontology> f_pssibleValues)
    {
        HashSet<Hypothesis> spGListFinal= new HashSet<Hypothesis>();
        for (Hypothesis hyps : G)
        {
            List<String> featureList = Arrays.asList(hyps.features);   //new addition to remove hypothesis which contains <-,?> 
            if(featureList.contains("-")) continue;
            int counter = 0;
            for (Hypothesis hypotheses : G)
            {
                if(hyps == hypotheses) continue;
                else if (hyps.isMoreSpecificThan(hypotheses, f_pssibleValues))
                {
                    counter ++;
                    break;
                }
            }
            if (counter==0) spGListFinal.add(hyps);
        }

        return spGListFinal;
    }
    
    /*
	 * Minimally specialize a query depending on the set of most special queries
	 * */
	public Set<Query> min_specializations(Query h, Query negPoint, Set<Query> q){
		Set<Query> res = new HashSet<Query>();
		//main loop
		System.out.println("NEW RUN: negPoint size="+negPoint.getTriples().size());
		for(Query e: q) {
			if(h.isMostGeneral()) {
				h = new Query(negPoint.getTriples().size());
			}
			
			//list of triples that we already considered (non-optional triples)
			List<Integer> requiredTriplesToSkip = new ArrayList<Integer>();
			for(int i=0; i<negPoint.getTriples().size(); i++) {
				Query toSpecialize = new Query();
				//copy all triples from h that do not have on all positions the ANY_ANY value
				for(Triple w: h.getTriples()) {
					if(!w.hasSameValues(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY"))) {
						toSpecialize.getTriples().add(new Triple(w.getSubjectValue(), w.getPredicateValue(), w.getObjectValue()));
					}
				}
				//add enough ANY_ANY value triples until size of triples of toSpecialize is the size of triples of negPoint
				if(toSpecialize.getTriples().size() < negPoint.getTriples().size()) {
					int size =toSpecialize.getTriples().size();
					for(int m=0;m<negPoint.getTriples().size()-size;m++) {
						toSpecialize.getTriples().add(new Triple("ANY_ANY","ANY_ANY","ANY_ANY"));
					}
				} else {
					res.add(toSpecialize);
					for(Query o: res) {
						if(e.getTriples().size() > o.getTriples().size()) {
							int size = o.getTriples().size();
							for(int c=0; c< e.getTriples().size()-size;c++) {
								o.getTriples().add(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY", true));
							}
						}
					}
					return res;
				}
				Triple t = null;
				for(int j=0; j<e.getTriples().size(); j++) {
					if(!requiredTriplesToSkip.contains(j)
						&& !e.getTriples().get(i).isOptional() 
						&& !toSpecialize.getTriples().get(i).hasSameValues(e.getTriples().get(j))) {
						t=e.getTriples().get(i);
						requiredTriplesToSkip.add(j);
						break;
					}
				}
				
				Triple qTriple = t;
				Triple tmpTriple = null;
				for(Triple w: toSpecialize.getTriples()) {
					if(w.hasSameValues(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY"))) {
						tmpTriple = w;
						break;
					}
				}
				
				if(!qTriple.getSubject().isMoreGeneralThan(tmpTriple.getSubject())) {
					tmpTriple.getSubject().setValue(qTriple.getSubjectValue());
				}
				
				if(!qTriple.getPredicate().isMoreGeneralThan(tmpTriple.getPredicate())) {
					tmpTriple.getPredicate().setValue(qTriple.getPredicateValue());
					
				}
				
				if(!qTriple.getObject().isMoreGeneralThan(tmpTriple.getObject())) {
					tmpTriple.getObject().setValue(qTriple.getObjectValue());
				}
				res.add(toSpecialize);
			}
			
			for(Query o: res) {
				if(e.getTriples().size() > o.getTriples().size()) {
					int size = o.getTriples().size();
					for(int c=0; c< e.getTriples().size()-size;c++) {
						o.getTriples().add(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY", true));
					}
				}
				
			}
		}
		
		return res;
	}

}