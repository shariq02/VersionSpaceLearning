package org.dice_research.SparqlUseCase;

import java.util.Arrays;
import java.util.List;

public class TripleValue {
	String value;
	String type;
	static String VARIABLE = "VARIABLE";
	static String IRI = "IRI";
	static String LITERAL = "LITERAL";
	
	static List<String> anyValues = Arrays.asList(new String[] {"ANY_VARIABLE", "ANY_IRI", "ANY_LITERAL"});
	
	public TripleValue(String v) {
		if(anyValues.contains(v)) {
			this.value = v;
			this.type = v.substring(v.indexOf("_"), v.length());
		}
		else {
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
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setValue(String v) {
		this.value = v;
	}
	
	/** TripleValue version of isMoreGeneralThan */
	public boolean isMoreGeneralThan(TripleValue v) {
		if(this.type.equals(TripleValue.VARIABLE) || (anyValues.contains(this.value) && !anyValues.contains(v.toString()))) {
			return true;
		}
		else if(this.value.equals(v.toString()) || (anyValues.contains(this.value) && !anyValues.contains(v.toString()))) {
			return true;
		}
		return false;
	}

}
