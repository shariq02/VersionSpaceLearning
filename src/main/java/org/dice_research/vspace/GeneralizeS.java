package org.dice_research.vspace;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.Triple;
import org.dice_research.SparqlUseCase.TripleValue;
public class GeneralizeS {
	/*
	 *  converts dataPoint to hypothesis then proceeds the same way as above
	 *  this change was needed in order to be able to compare dataPoint values
	 *  to hypothesis feature values (might need to further change the hypothesis
	 *class to omit this change)
	 */
	HashSet<Hypothesis> min_generalizations(HashSet<Hypothesis> s, String[] dataPoint, ArrayList<Ontology> fGraphList) {
		HashSet<Hypothesis> res = new HashSet<Hypothesis>();
		Hypothesis newH = null;
		Vertices parent;
		for (Hypothesis sp : s)
			{
				if (sp.isConsistentWithDataPoint(dataPoint, true, fGraphList)) res.add(sp);
				else {
					newH = new Hypothesis(sp.features);
					for (int i = 0; i < dataPoint.length; i++) {
						if (sp.features[i].equals(dataPoint[i])) continue;
						Ontology fGraph = fGraphList.get(i);
						parent = fGraph.findCommonParent(sp.features[i], dataPoint[i]);
						newH.features[i] = parent.getValue();
					}
					if (newH != sp) res.add(newH);
				}
			}
		return res;
	}


	public HashSet<Hypothesis> removeMember(HashSet<Hypothesis> S, String[] data, ArrayList<Ontology> f_pssibleValues)
	{
		HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
		if (S.isEmpty()) return spGList;
		for (Hypothesis s: S)
		{
			if (s.isConsistentWithDataPoint(data, false, f_pssibleValues)) spGList.add(s);
		}
		return spGList;
	}
	
	/*
	 * finds the minimal generalization for h such that it is more general than q
	 * */
	public Set<Query> min_generalizations(Query h, Query q){
		//find all the triples in q, that h has no generalizations for, and remove all the triples
		//from h which are a generalization for some triple q. what is left will be the candidate
		//triples which will be used later on for potential generalization
		List<Triple> candidateTriples = h.getCopyOfTriples();
		List<Triple> qTriples = q.getCopyOfTriples();
		
		for(Iterator<Triple> qIter = qTriples.listIterator(); qIter.hasNext();) {
			Triple qTriple = qIter.next();
			for(Iterator<Triple> cIter = candidateTriples.listIterator(); cIter.hasNext();) {
				Triple cTriple = cIter.next();
				if(cTriple.isMoreGeneralThan(qTriple)) {
					cIter.remove();
					qIter.remove();
					break;
				}
			}
		}
		
		//check if we can generalize any triple in candidate triples such that it will be more general
		//than some other triple in the unmatched triples. if no such triple is found a new one will be
		//constructed to match the unmatched triple
		
		//to be continued
		for(Triple t: candidateTriples) {
			BitSet missmatches = t.getDifference(qTriples.get(Triple.indexOfMostSimilar(t, qTriples)));
			
		}
		
		
		
		return null;
	}

}
