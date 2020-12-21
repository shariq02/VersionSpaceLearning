package org.dice_research.SparqlUseCase;

import java.util.ArrayList;
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
	
	/** Triple version of isMoreGeneralThan*/
	public boolean isMoreGeneralThan(Triple t) {
		if(s.isMoreGeneralThan(t.s) && p.isMoreGeneralThan(t.p) && o.isMoreGeneralThan(t.o)) {
			return true;
		}
		return false;
	}
	
	/* Returns list of integers indicating the indices in which this triple's values disagree with the input
	 * triple's values*/
	public List<Integer> difference(Triple t) {
		List<Integer> res = new ArrayList<Integer>();
		
		if(!s.isMoreGeneralThan(t.s)) {
			res.add(0);
		}
		if(!p.isMoreGeneralThan(t.p)) {
			res.add(1);
		}
		if(!o.isMoreGeneralThan(t.o)) {
			res.add(2);
		}
		
		return res;
	}
}
