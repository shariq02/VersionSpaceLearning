package org.dice_research.SparqlUseCase;

import java.util.BitSet;
import java.util.List;

public class Triple {
	TripleValue s, p, o;
	
	public Triple(String s, String p, String o) {
		this.s = new TripleValue(s);
		this.p = new TripleValue(p);
		this.o = new TripleValue(o);
	}
	
	@Override
	public String toString() {
		return s+" "+p+" "+o;
	}
	
	public TripleValue getSubject() {
		return this.s;
	}
	
	public TripleValue getPredicate() {
		return this.p;
	}
	
	public TripleValue getObject() {
		return this.o;
	}
	
	public String getSubjectValue() {
		return this.s.toString();
	}
	
	public String getPredicateValue() {
		return this.p.toString();
	}
	
	public String getObjectValue() {
		return this.o.toString();
	}
	
	/** Triple version of isMoreGeneralThan*/
	public boolean isMoreGeneralThan(Triple t) {
		if(s.isMoreGeneralThan(t.s) && p.isMoreGeneralThan(t.p) && o.isMoreGeneralThan(t.o)) {
			return true;
		}
		return false;
	}
	
	/* Returns BitSet of booleans where the indices which are set to true indicate where the two triples being compared
	 * dissagre
	 * */
	public BitSet getDifference(Triple t) {
		BitSet res = new BitSet(3);
		
		if(!s.isMoreGeneralThan(t.s)) {
			res.set(0);
		}
		if(!p.isMoreGeneralThan(t.p)) {
			res.set(1);
		}
		if(!o.isMoreGeneralThan(t.o)) {
			res.set(2);
		}
		return res;
	}
	
	/* Returns the index of a triple in y which is the most similar to triple t (similar in terms of the lowest dissagrement)
	 * */
	public static int indexOfMostSimilar(Triple t, List<Triple> y) {
		int score = 0;
		int index = -1;
		for(Triple i: y) {
			int currentScore = 0;
			int difference = t.getDifference(i).cardinality();
			if(t.getSubject().getType().equals(i.getSubject().getType())) {
				currentScore+=3;
			}
			switch(difference) {
				case 1:
					currentScore+=3;
					break;
				case 2:
					currentScore+=2;
					break;
				case 3:
					currentScore+=1;
					break;
			}
			//if the currently being checked triples are of the same type and differ only in one triplevalue
			//return immediately
			if(currentScore == 6) {
				return y.indexOf(i);
			} 
			//keep searching until the best match is found
			else if(currentScore > score) {
				score = currentScore;
				index = y.indexOf(i);
			}
		}
		return index;
	}
}
