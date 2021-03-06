package org.dice_research.vspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dice_research.SparqlUseCase.Query;
import org.dice_research.SparqlUseCase.Triple;

public class SpecializeGBoundary {

	public static final String ANY = "?";
	public static final String NONE = "-";

	public HashSet<Hypothesis> specialize(String[] ne, HashSet<Hypothesis> s, ArrayList<Ontology> f_pssibleValues,
			HashSet<Hypothesis> k, String mode) {
		HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
		HashSet<Hypothesis> spGListFinal = new HashSet<Hypothesis>();
		Iterator iter;
		for (Hypothesis h : k) {
			String[] base = new String[h.features.length];

			String value;
			// IF our general hypothesis is already consistent with -ve example , no need to
			// specialize it
			if (h.isConsistentWithDataPoint(ne, false, f_pssibleValues) && mode.equals("ce")) {
				spGList.add(h);
				continue;
			}

			/*
			 * If not then we ned to create multiple more specific hypothesis 1) For each
			 * possible value of each feature the function will create a specialize
			 * hypothesis 2) The after creation of the complete list, it will check which
			 * hypotheses are more than than at least one of the hypothesis is s. it will
			 * remove others. 3) in order to achieve this all features of the -ve data point
			 * is matched with this hypothesis features. -- if this hypothesis feature is
			 * ANY if can specialize it -- if this hypothesis feature is same as data then
			 * we need to skip and look for other features to generalize 4) Finally if this
			 * function is able to generate any specialize hypothesis if will return with a
			 * arralist of hypotheses
			 */
			for (int i = 0; i < ne.length; i++) {
				Ontology featureGraph = f_pssibleValues.get(i);
				Vertices currentNode = featureGraph.search(featureGraph.getRoot(), h.features[i]);
				for (int j = 0; j < h.features.length; j++) {
					base[j] = h.features[j].toString();
				}
				for (Vertices child : currentNode.getChild()) {
					Vertices result = featureGraph.search(child, ne[i]);
					if (result != null) {
						// if(child != result.getParent() && !child.getValue().equals(NONE))
						if (!child.getValue().equals(NONE)) {
							Hypothesis dummy = new Hypothesis(base);
							dummy.features[i] = child.getValue();
							HashSet<Hypothesis> dummyG = new HashSet<>();
							dummyG.add(dummy);
							spGList.addAll(specialize(ne, s, f_pssibleValues, dummyG, "ce"));
						}
						continue;
					}
					base[i] = child.getValue();
					spGList.add(new Hypothesis(base));
				}
			}
		}

		iter = spGList.iterator();
		while (iter.hasNext()) {
			Hypothesis check = ((Hypothesis) iter.next());
			if (!check.isConsistentWithDataPoint(ne, false, f_pssibleValues)) {
				iter.remove();
				continue;
			}
			int counter = 0;
			for (Hypothesis h : s) {
				if (check.isMoreGeneralThan(h, f_pssibleValues)) {
					counter++;
					break;
				}
			}
			if (counter == 0)
				iter.remove();
		}

		// Removing hypothesis that are not as general as others
		for (Hypothesis hyps : spGList) {
			int counter = 0;
			for (Hypothesis hypotheses : spGList) {
				if (hyps == hypotheses)
					continue;
				else if (hyps.isMoreSpecificThan(hypotheses, f_pssibleValues)) {
					counter++;
					break;
				}
			}
			if (counter == 0)
				spGListFinal.add(hyps);
		}

		return spGListFinal;
	}

	/**
	 * This function removes hypothesis from G boundary that are not more general
	 * than any hypothesis from S boundary.
	 */
	public HashSet<Hypothesis> removeMember(HashSet<Hypothesis> S, HashSet<Hypothesis> G,
			ArrayList<Ontology> f_pssibleValues) {
		HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
		if (S.isEmpty())
			return spGList;
		for (Hypothesis g : G) {
			int counter = 0;
			for (Hypothesis h : S) {
				if (g.isMoreGeneralThan(h, f_pssibleValues)) {
					counter++;
					break;
				}
			}
			if (counter != 0)
				spGList.add(g);
		}
		return spGList;
	}

	/**
	 * This function removes hypothesis from G boundary which is specific than any
	 * other hypothesis in the boundary.
	 */
	public HashSet<Hypothesis> removeSpecific(HashSet<Hypothesis> G, ArrayList<Ontology> f_pssibleValues) {
		HashSet<Hypothesis> spGListFinal = new HashSet<Hypothesis>();
		for (Hypothesis hyps : G) {
			List<String> featureList = Arrays.asList(hyps.features); // new addition to remove hypothesis which contains
																		// <-,?>
			if (featureList.contains("-"))
				continue;
			int counter = 0;
			for (Hypothesis hypotheses : G) {
				if (hyps == hypotheses)
					continue;
				else if (hyps.isMoreSpecificThan(hypotheses, f_pssibleValues)) {
					counter++;
					break;
				}
			}
			if (counter == 0)
				spGListFinal.add(hyps);
		}

		return spGListFinal;
	}

	/**
	 * Minimally specialize a query depending on the set of most special queries
	 * 
	 * @param h Query to be specialized
	 * @param S Set of most positive queries
	 * @return Set of specialized queries towards S
	 */
	public Set<Query> min_specializations(Query h, Set<Query> q) {
		Set<Query> res = new HashSet<Query>();
		// main loop
		for (Query S : q) {
			if (h.isMostGeneral()) {
				h = new Query(S.getTriples().size());
			}
			List<Query> specialized = new ArrayList<Query>();
			int sizeOfTriples = S.getTriples().size();
			for (int i = 0; i < sizeOfTriples; i++) {
				Query newQ = new Query(h);
				if (!newQ.getTriples().get(i).hasSameValues(new Triple("ANY_ANY", "ANY_ANY", "ANY_ANY"))) {
					continue;
				}
				newQ.getTriples().set(i, new Triple(S.getTriples().get(i).getSubjectValue(),
						S.getTriples().get(i).getPredicateValue(), S.getTriples().get(i).getObjectValue()));
				specialized.add(newQ);
			}
			res.addAll(specialized);
		}
		return res;
	}

	/**
	 * Applies min_specializations recursively to h until the specializations (if
	 * they exist) produced no longer cover negPoint
	 * 
	 * @param toSpecialize Specializations that are recursively passed until goal is
	 *                     achieved (on first call should be null)
	 * @param h            Query to be specialized
	 * @param negPoint     Negatively labeled query
	 * @param S            Set of most positive queries
	 * @return Set of specialized queries towards S
	 */
	public Set<Query> recursiveMinSpecialization(Set<Query> toSpecialize, Query h, Query negPoint, Set<Query> S) {
		Set<Query> specialized = null;
		if (toSpecialize == null) {
			specialized = min_specializations(h, S);
		} else {
			specialized = new HashSet<Query>();
			for (Query cand : toSpecialize) {
				Set<Query> temp = min_specializations(cand, S);
				specialized.addAll(temp);
			}
		}
		boolean goFurther = false;
		Set<Query> toSpecializeFurther = new HashSet<Query>();
		for (Query s : S) {
			for (Query q : specialized) {
				if (q.isMoreGeneralThan(negPoint, 0, new HashMap<Integer, Integer>(), null) && canSpecialize(q, s)) {
					goFurther = true;
				}
				toSpecializeFurther.add(new Query(q));
			}
		}
		if (goFurther) {
			return recursiveMinSpecialization(toSpecializeFurther, h, negPoint, S);
		}
		return specialized;
	}

	/**
	 * Checks whether query from can be further specialized to to
	 * 
	 * @param from Query
	 * @param to   Query
	 * @return true if from can be further specialized towards to, false otherwise
	 */
	public boolean canSpecialize(Query from, Query to) {
		if (from.getTriples().size() != to.getTriples().size()) {
			return false;
		}
		int counter = 0;
		for (Triple i : from.getTriples()) {
			for (Triple j : to.getTriples()) {
				if (i.hasSameValues(j)) {
					counter++;
					break;
				}
			}
		}
		if (counter == to.getTriples().size()) {
			return false;
		}
		return true;
	}
}