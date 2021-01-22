package org.dice_research.SparqlUseCase;

import java.util.BitSet;
import java.util.List;

public class Triple {
	TripleValue s, p, o;
	boolean optional = false;
	
	public Triple(String s, String p, String o) {
		this.s = new TripleValue(s);
		this.p = new TripleValue(p);
		this.o = new TripleValue(o);
	}
	
	public Triple(String s, String p, String o, boolean op) {
		this.s = new TripleValue(s);
		this.p = new TripleValue(p);
		this.o = new TripleValue(o);
		this.optional = true;
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
	public boolean isOptional() {
		return this.optional;
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
	
	public boolean hasSameValues(Triple t) {
		if(this.s.toString().equals(t.s.toString()) && this.p.toString().equals(t.p.toString()) && this.o.toString().equals(t.o.toString())) {
			return true;
		}
		return false;
	}
	
	/* Returns the index of a triple in y which is the most similar to triple t (similar in terms of the lowest dissagrement)
	 * */
	public static int indexOfMostSimilar(Triple t, List<Triple> y) {
		int score = 0;
		int index = -1;
		for(Triple i: y) {
			int currentScore = 0;
			BitSet difference = t.getDifference(i);
			if(difference.get(0)) {
				currentScore++;
				if(t.getSubject().getType().equals(i.getSubject().getType())) {
				currentScore+=3;
				}
			}
			if(difference.get(1)) {
				currentScore++;
				if(t.getPredicate().getType().equals(i.getPredicate().getType())) {
					currentScore+=3;
				}
			}
			if(difference.get(2)) {
				currentScore++;
				if(t.getObject().getType().equals(i.getObject().getType())) {
					currentScore+=3;
				}
			}
			if(currentScore > score) {
				score = currentScore;
				index = y.indexOf(i);
			}
		}
		return index;
	}
}
