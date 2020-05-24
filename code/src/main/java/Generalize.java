import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class Generalize {
	
	
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
	HashSet<Hypothesis> min_generalizations(Hypothesis s ,String[] dataPoint) {
		HashSet<Hypothesis> res = new HashSet<Hypothesis>();
		Hypothesis h = new Hypothesis(dataPoint);
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
	
	public static void main(String[] args) {
		Generalize g = new Generalize();
		HashSet<Hypothesis> moreGeneralHypotheses = new HashSet<Hypothesis>();
		ArrayList<String[]> listOfPositiveExamples = new ArrayList<String[]>();
		//add the most specific example
		moreGeneralHypotheses.add(new Hypothesis(6, "S"));
		
		//examples from book
		listOfPositiveExamples.add(new String[]{"sunny", "warm", "normal", "strong", "warm", "same"});
		listOfPositiveExamples.add(new String[]{"sunny", "warm", "high", "strong", "warm", "same"});
		listOfPositiveExamples.add(new String[]{"sunny", "warm", "high", "strong", "cool", "change"});
		
		
		//part of candidate elimination algorithm
		for(String[] dataPoint: listOfPositiveExamples) {
			//one step where access to the most general set of hypotheses was required has been skipped
			Iterator<Hypothesis> iter = moreGeneralHypotheses.iterator();
			while(iter.hasNext()) {
				Hypothesis s = iter.next();
				if(!s.classifyPoint(dataPoint)) {
					moreGeneralHypotheses.remove(s);
					HashSet<Hypothesis> generalizations = g.min_generalizations(s, dataPoint);
					//another step where access to the most general set of hypotheses was required has been skipped
					moreGeneralHypotheses.addAll(generalizations);
					for(Hypothesis j: moreGeneralHypotheses) {
						if(!s.equals(j) && s.isMoreGeneralThan(j)) {
							System.out.println(s+" "+j);
							moreGeneralHypotheses.remove(s);
						}
					}
				}
			}
		}
		
		for(Hypothesis h: moreGeneralHypotheses) {
			System.out.println(h);
		}
	}
}