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
	
	public String getSubject() {
		return this.s.toString();
	}
	
	public String getPredicate() {
		return this.p.toString();
	}
	
	public String getObject() {
		return this.o.toString();
	}
	
	/** Triple version of isMoreGeneralThan*/
	public boolean isMoreGeneralThan(Triple t) {
		if(s.isMoreGeneralThan(t.s) && p.isMoreGeneralThan(t.p) && o.isMoreGeneralThan(t.o)) {
			return true;
		}
		return false;
	}
	
	/* Returns BitSet of integers indicating the indices in which this triple's values disagree with the input
	 * triple's values*/
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
	
	/* Returns the index of a triple in y which is the most similar to triple t
	 * */
	public static int indexOfMostSimilar(Triple t, List<Triple> y) {
		int similarity = Integer.MAX_VALUE;
		int index = -1;
		for(Triple i: y) {
			int tmp = t.getDifference(i).size();
			if(tmp == 1) {
				return y.indexOf(i);
			}
			else if(tmp < similarity) {
				similarity = tmp;
				index = y.indexOf(i);
			}
		}
		return index;
	}
}
