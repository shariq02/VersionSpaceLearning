package org.dice_research.vspace;

import java.lang.reflect.Array;
import java.util.Arrays;

/*
 * After reading the file from CAndidateElimination class 
 * when each line is passed to Instance class
 * it will divide every instance into attributes and labels 
 * 
 * @author Susmita Goswami
 */

public class Instance {
	private String[] attribs;
	private String label;

	public Instance(String[] rawData) {
		this.attribs = new String[rawData.length];
		this.attribs = Arrays.copyOf(rawData, rawData.length - 1);
		label = (String) Array.get(rawData, rawData.length - 1);
	}

	public String getLabel() {
		return label;
	}

	public String[] getAttribs() {
		return attribs;
	}

	public String toString() {
		String result = "< ";
		for (String s : this.attribs) {
			result += s + ", ";
		}
		result += this.label + " >";
		return result;
	}
}
