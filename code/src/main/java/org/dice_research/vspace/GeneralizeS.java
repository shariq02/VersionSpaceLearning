package org.dice_research.vspace;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class GeneralizeS {


	HashSet<Hypothesis> min_generalizations(Hypothesis s, Hypothesis h) {
		HashSet<Hypothesis> res = new HashSet<Hypothesis>();
		Hypothesis newH = new Hypothesis(s.features);
		ArrayList<Integer> compare = s.compareTo(h);
		for (int i : compare) {
			if (s.features[i].equals(Hypothesis.NONE)) {
				newH.features[i] = h.features[i];
			} else {
				newH.features[i] = Hypothesis.ANY;
			}
		}
		res.add(newH);
		return res;
	}

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

}
