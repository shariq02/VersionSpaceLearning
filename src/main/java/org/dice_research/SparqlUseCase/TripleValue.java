package org.dice_research.SparqlUseCase;

public class TripleValue {
	String value;
	String type;
	static String VARIABLE = "VARIABLE";
	static String IRI = "IRI";
	static String LITERAL = "LITERAL";
	
	public TripleValue(String v) {
		this.value = v;
		if(v.startsWith("?")) {
			this.type = TripleValue.VARIABLE;
		}
		else if((v.contains(":")) || (v.startsWith("<") && v.endsWith(">"))) {
			this.type = TripleValue.IRI;
		}
		else {
			this.type = TripleValue.LITERAL;
		}
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		return value;
	}
	
	public void setValue(String v) {
		this.value = v;
	}

}
