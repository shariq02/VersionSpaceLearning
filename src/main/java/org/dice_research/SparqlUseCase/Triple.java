package org.dice_research.SparqlUseCase;


public class Triple {
	TripleValue s, p, o;
	
	public Triple(String s, String p, String o) {
		this.s = new TripleValue(s);
		this.p = new TripleValue(p);
		this.o = new TripleValue(o);
	}
	
	public String toString() {
		return s+" "+p+" "+o;
	}
}
