package org.dice_research.SparqlUseCase;

import java.util.Arrays;
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
	
	@Override
	public boolean equals(Object o) {
		if(this.hashCode() == ((Triple)o).hashCode()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		String[] values = new String[] {this.getSubjectValue(), this.getObjectValue(), this.getPredicateValue()};
		return Arrays.deepHashCode(values);
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
	
	public void setOptional(boolean op) {
		this.optional = op;
	}
	
	public boolean isOptional() {
		return this.optional;
	}
	
	/** Triple version of isMoreGeneralThan
	 * 
	 * @param t The triple which this triple is checked against for generality
	 * @return true if this Triple is more general than Triple t, false otherwise
	 * */
	public boolean isMoreGeneralThan(Triple t) {
		if(s.isMoreGeneralThan(t.s) && p.isMoreGeneralThan(t.p) && o.isMoreGeneralThan(t.o)) {
			return true;
		}
		return false;
	}
	
	/** Returns BitSet of booleans where the indices which are set to true indicate where the two triples being compared
	 * dissagre
	 * 
	 * @param t Triple to be compared against
	 * @return a BitSet whose set bits define the position/s (0 for subject, 1 for predicate, 2 for object) at which this triple disagrees with triple t
	 * */
	public BitSet getDifference(Triple t) {
		BitSet res = new BitSet(3);
		
		if(!this.s.isMoreGeneralThan(t.s)) {
			res.set(0);
		}
		if(!this.p.isMoreGeneralThan(t.p)) {
			res.set(1);
		}
		if(!this.o.isMoreGeneralThan(t.o)) {
			res.set(2);
		}
		return res;
	}
	/** Compares this triple against triple t, returns true only if all their triple values are equal
	 * 
	 * @param t The triple which this triple is compared against
	 * @return true if they're they are equal, false otherwise
	 * */
	public boolean hasSameValues(Triple t) {
		if(this.s.toString().equals(t.s.toString()) && this.p.toString().equals(t.p.toString()) && this.o.toString().equals(t.o.toString())) {
			return true;
		}
		return false;
	}
	
	/** Returns the index of a triple in y which is the most similar to triple t (similar in terms of the lowest dissagrement)
	 * 
	 * @param t The tripl for which a most similar triple is searched
	 * @param y The search space of triples
	 * @return index of the most similar triple in list y to triple t
	 * */
	public static int indexOfMostSimilar(Triple t, List<Triple> y) {
		int score = Integer.MAX_VALUE;
		int index = -1;
		for(Triple i: y) {
			int currentScore = 0;
			BitSet difference = t.getDifference(i);
			if(difference.get(0)) {
				currentScore++;
				if(!t.getSubject().getType().equals(i.getSubject().getType())) {
				currentScore+=3;
				}
			}
			if(difference.get(1)) {
				currentScore++;
				if(!t.getPredicate().getType().equals(i.getPredicate().getType())) {
					currentScore+=3;
				}
			}
			if(difference.get(2)) {
				currentScore++;
				if(!t.getObject().getType().equals(i.getObject().getType())) {
					currentScore+=3;
				}
			}
			if(currentScore < score) {
				score = currentScore;
				index = y.indexOf(i);
			}
		}
		return index;
	}
}