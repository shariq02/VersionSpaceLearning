package org.dice_research.SparqlUseCase;


public class Triple {
	String s, p, o;
	
	public Triple(String s, String p, String o) {
		this.s = s;
		this.p = p;
		this.o = o;
	}
	
	public String toString() {
		return s+" "+p+" "+o;
	}
}
