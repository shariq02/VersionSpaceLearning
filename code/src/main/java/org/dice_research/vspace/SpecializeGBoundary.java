package org.dice_research.vspace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
 * Here we are trying to specialize the generalized hypotheses
 * specialize method is designed to specialize any hypothesis given to the method
     * it has four inputs, 1) negativeData --> all features of the negative example
     * 					   2) specializedHypotheses --> Set of all specialized Hypotheses
     * 					   3) possibleFeatureValues --> All possible feature values for each attributes
     * 					   4) generalizedHypotheses --> Set of all generalized Hypotheses
     * It will select one generalized hypothesis at a time and specialize it.
     * 
     * @author Abhratanu Surai
 */

public class SpecializeGBoundary {

	public static final String ANY = "?";
	public static final String NONE = "-";

	public HashSet<Hypothesis> specialize(String[] negativeData, HashSet<Hypothesis> specializedHypotheses,
			ArrayList<Ontology> possibleFeatureValues, HashSet<Hypothesis> generalizedHypotheses) {
		/*
		 * spgGList will return the final specialize G boundary
		 */
		HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
		HashSet<Hypothesis> spGListFinal = new HashSet<Hypothesis>();
		Iterator iter;
		for (Hypothesis h : generalizedHypotheses) {
			String[] base = new String[h.features.length];

			String value;
			/*
			 * IF our general hypothesis is already consistent with negative example , no
			 * need to specialize it
			 */
			if (h.isConsistentWithDataPoint(negativeData, false, possibleFeatureValues)) {
				spGList.add(h);
				continue;
			}

			/*
			 * If not then we need to create multiple more specific hypothesis featureGraph
			 * will pick the ontologies one by one from all possibleFeatureValues
			 * currentNode will store the node from which level it will start to specialize
			 * Since my existing hypothesis cannot classify correctly all features, the -ve
			 * data point is matched with the child values. -- if this -ve data is in the
			 * child level then it will search for other child
			 * 
			 */
			for (int i = 0; i < negativeData.length; i++) {
				Ontology featureGraph = possibleFeatureValues.get(i);
				Vertices currentNode = featureGraph.search(featureGraph.getRoot(), h.features[i]);
				for (int j = 0; j < h.features.length; j++) {
					base[j] = h.features[j].toString();
				}
				for (Vertices child : currentNode.getChild()) {
					Vertices result = featureGraph.search(child, negativeData[i]);
					if (result != null) {

						if (!child.getValue().equals(NONE)) {
							Hypothesis dummy = new Hypothesis(base);
							dummy.features[i] = child.getValue();
							HashSet<Hypothesis> dummyG = new HashSet<>();
							dummyG.add(dummy);
							spGList.addAll(
									specialize(negativeData, specializedHypotheses, possibleFeatureValues, dummyG));
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
			if (!check.isConsistentWithDataPoint(negativeData, false, possibleFeatureValues)) {
				iter.remove();
				continue;
			}
			int counter = 0;
			/*
			 * Removing newly created Hypothesis from Generalized hypotheses set which are
			 * inconsistent with Specialized hypotheses
			 */
			for (Hypothesis h : specializedHypotheses) {
				if (check.isMoreGeneralThan(h, possibleFeatureValues)) {
					counter++;
					break;
				}
			}
			if (counter == 0)
				iter.remove();
		}

		/*
		 * Removing hypothesis that are not as general as others
		 */
		for (Hypothesis hyps : spGList) {
			int counter = 0;
			for (Hypothesis hypotheses : spGList) {
				if (hyps == hypotheses)
					continue;
				else if (hyps.isMoreSpecificThan(hypotheses, possibleFeatureValues)) {
					counter++;
					break;
				}
			}
			if (counter == 0)
				spGListFinal.add(hyps);
		}

		return spGListFinal;
	}

	/*
	 * Removing any hypothesis from G which is inconsistent with positive example
	 */

	public HashSet<Hypothesis> removeMember(HashSet<Hypothesis> specializedHypotheses,
			HashSet<Hypothesis> generalizedHypotheses, ArrayList<Ontology> possibleFeatureValues) {
		HashSet<Hypothesis> spGList = new HashSet<Hypothesis>();
		if (specializedHypotheses.isEmpty())
			return spGList;
		for (Hypothesis g : generalizedHypotheses) {
			int counter = 0;
			for (Hypothesis h : specializedHypotheses) {
				if (g.isMoreGeneralThan(h, possibleFeatureValues)) {
					counter++;
					break;
				}
			}
			if (counter != 0)
				spGList.add(g);
		}
		return spGList;
	}

}