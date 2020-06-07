package VersionSpaceLearning.src;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class GeneralizeS {
	
	
	HashSet<Hypothesis> min_generalizations(Hypothesis s ,Hypothesis h) {
		HashSet<Hypothesis> res = new HashSet<Hypothesis>();
		Hypothesis newH = new Hypothesis(s.features);
		ArrayList<Integer> compare = s.compareTo(h);
		for(int i: compare) {
			if(s.features[i].equals(Hypothesis.NONE)) {
				newH.features[i]=h.features[i];
			}
			else {
				newH.features[i]=Hypothesis.ANY;
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
	HashSet<Hypothesis> min_generalizations(HashSet<Hypothesis> s ,String[] dataPoint) {
		HashSet<Hypothesis> res = new HashSet<Hypothesis>();
		for (Hypothesis sp: s) {
			Hypothesis h = new Hypothesis(dataPoint);
			Hypothesis newH = new Hypothesis(sp.features);
			ArrayList<Integer> compare = sp.compareTo(h);
			for (int i : compare) {
				if (sp.features[i].equals(Hypothesis.NONE)) {
					newH.features[i] = h.features[i];
				} else {
					newH.features[i] = Hypothesis.ANY;
				}
			}
			res.add(newH);
		}
		return res;
	}
}
