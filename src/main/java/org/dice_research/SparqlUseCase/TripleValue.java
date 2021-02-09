package org.dice_research.SparqlUseCase;

import java.util.Arrays;
import java.util.List;

public class TripleValue {
	private String value;
	private String type;

	// possible types
	public static final String VARIABLE = "VARIABLE";
	public static final String IRI = "IRI";
	public static final String LITERAL = "LITERAL";
	public static final String ANY = "ANY";

	// possible values apart from specific values
	public static final List<String> anyValues = Arrays
			.asList(new String[] { "ANY_VARIABLE", "ANY_IRI", "ANY_LITERAL", "ANY_ANY" });

	public TripleValue(String s) {
		determineValue(s);
	}

	public void determineValue(String v) {
		if (anyValues.contains(v)) {
			this.value = v;
			this.type = v.substring(v.indexOf("_") + 1, v.length());
		} else {
			this.value = v;
			if (v.startsWith("?")) {
				this.type = TripleValue.VARIABLE;
			} else if ((v.contains(":")) || (v.startsWith("<") && v.endsWith(">"))) {
				this.type = TripleValue.IRI;
			} else {
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

	/**
	 * TripleValue version of isMoreGeneralThan
	 * 
	 * @param v The triplevalue which this triplevalue is checked against for
	 *          generality
	 * @return true if this triplevalue is more general than v
	 */
	public boolean isMoreGeneralThan(TripleValue v) {
		if (this.type.equals(v.type) || this.type.equals(ANY)) {
			if (this.type.equals(TripleValue.ANY)) {
				return true;
			} else if (this.type.equals(TripleValue.VARIABLE)
					|| (anyValues.contains(this.value) && !anyValues.contains(v.value))) {
				return true;
			} else if (this.type.equals(TripleValue.IRI)
					&& ((anyValues.contains(this.value) && !anyValues.contains(v.value))
							|| this.value.equals(v.value))) {
				return true;
			} else if (this.type.equals(TripleValue.LITERAL)
					&& ((anyValues.contains(this.value) && !anyValues.contains(v.value))
							|| this.value.equals(v.value))) {
				return true;
			}
		}
		return false;
	}

}