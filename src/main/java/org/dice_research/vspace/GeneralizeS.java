package org.dice_research.vspace;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.Triple;
public class GeneralizeS {
	/*
	 * Finds the minimal generalizations for the hypotheses in s such that they are more general the input data point
	 * dataPoint
	 * */
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
	 * Finds the minimal generalization for query h such that it is more general than query q
	 * */
	public Set<Query> min_generalizations(Query h, Query q){
		Set<Query> res = new HashSet<Query>();
		
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
		
		//nothing to generalize
		if(qTriples.size() == 0) {
			return null;
		}
		
		Query tmpQuery = new Query(h);
		//no candidates exist, so add all triples from q as new triples in the query to return
		if(candidateTriples.size() == 0) {
			tmpQuery.addAllTriples(qTriples);
			res.add(tmpQuery);
			return res;
		}
		
		//check if we can generalize any triple in candidate triples such that it will be more general
		//than some other triple in the unmatched triples. if no such triple is found a new one will be
		//constructed to match the unmatched triple
		//for(Triple cand: candidateTriples) {
		for(ListIterator<Triple> iter = candidateTriples.listIterator(); iter.hasNext();) {
			//no unmatched triples left
			if(qTriples.size() == 0) {
				break;
			}
			Triple cand = iter.next();
			Triple mostSimilarTriple = qTriples.get(Triple.indexOfMostSimilar(cand, qTriples));
			BitSet missmatches = cand.getDifference(mostSimilarTriple);
			
			//remove the current candidate since it is about to be generalized, generalization will be added later
			tmpQuery.getTriples().remove(cand);
			
			if(missmatches.get(0)) {
				//same type
				if(cand.getSubject().getType().equals(mostSimilarTriple.getSubject().getType())) {
					cand.getSubject().setValue("ANY_"+cand.getSubject().getType());
				}
				//not same type
				else {
					cand.getSubject().setValue("ANY_ANY");
				}
			}
			if(missmatches.get(1)) {
				if(cand.getPredicate().getType().equals(mostSimilarTriple.getPredicate().getType())) {
					cand.getPredicate().setValue("ANY_"+cand.getPredicate().getType());
				}
				else {
					cand.getPredicate().setValue("ANY_ANY");
				}
			}
			if(missmatches.get(2)) {
				if(cand.getObject().getType().equals(mostSimilarTriple.getObject().getType())) {
					cand.getObject().setValue("ANY_"+cand.getObject().getType());
				}
				else {
					cand.getObject().setValue("ANY_ANY");
				}
			}
			tmpQuery.getTriples().add(new Triple(cand.getSubjectValue(), cand.getPredicateValue(), cand.getObjectValue()));
			
			//remove generalized triple and the triple for which that generalization was found
			iter.remove();
			qTriples.remove(mostSimilarTriple);
		}
		
		//add all still remaining triples from q for which no generalization was found (no candidate was left)
		if(qTriples.size()>0) {
			System.out.println("Really dude!");
			//tmpQuery.addAllTriples(qTriples);
			for(Triple r: qTriples) {
				tmpQuery.getTriples().add(new Triple(r.getSubjectValue(), r.getPredicateValue(), r.getObjectValue(), true));
			}
		}
		res.add(tmpQuery);
		return res;
	}
}
