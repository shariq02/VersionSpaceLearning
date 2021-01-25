package org.dice_research.SparqlUseCase;

import java.util.Arrays;
import java.util.List;

public class TripleValue {
	String value;
	String type;
	
	//possible types
	static public String VARIABLE = "VARIABLE";
	static public String IRI = "IRI";
	static public String LITERAL = "LITERAL";
	static public String ANY = "ANY";
	
	//possible values apart from specific values
	static List<String> anyValues = Arrays.asList(new String[] {"ANY_VARIABLE", "ANY_IRI", "ANY_LITERAL", "ANY_ANY"});
	
	public TripleValue(String s) {
		determineValue(s);
	}
	
	public TripleValue() {
		// TODO Auto-generated constructor stub
	}

	public void determineValue(String v) {
		if(anyValues.contains(v)) {
			this.value = v;
			this.type = v.substring(v.indexOf("_")+1, v.length());
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
		determineValue(v);
	}
	
	/** TripleValue version of isMoreGeneralThan */
	public boolean isMoreGeneralThan(TripleValue v) {
		if(this.type.equals(v.type) || this.type.equals(ANY)) {
			if(this.type.equals(TripleValue.ANY)) {
				return true;
			}
			else if(this.type.equals(TripleValue.VARIABLE) || (anyValues.contains(this.value) && !anyValues.contains(v.value))) {
				return true;
			}
			else if(this.type.equals(TripleValue.IRI) && ((anyValues.contains(this.value) && !anyValues.contains(v.value)) || this.value.equals(v.value))) {
				return true;
			}
			else if(this.type.equals(TripleValue.LITERAL) && ((anyValues.contains(this.value) && !anyValues.contains(v.value)) || this.value.equals(v.value))) {
				return true;
			}
		}
		return false;
	}

}
