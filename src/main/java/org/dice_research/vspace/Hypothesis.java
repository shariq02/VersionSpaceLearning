package org.dice_research.vspace;

import java.util.ArrayList;
import java.util.Arrays;

public class Hypothesis {

	/*
	 * possible values of the features: NONE ANY values specified by the data
	 */
	public static final String ANY = "?";
	public static final String NONE = "-";

	String[] features;

	/*
	 * constructor
	 */
	public Hypothesis(String[] f) {
		this.features = Arrays.copyOf(f, f.length);
	}

	/*
	 * constructor for easier initialization of the most specific and most general
	 * hypotheses. accepts the number of features (int l) and a boolean type, where
	 * false indicates most specific, and true most general.
	 */
	public Hypothesis(int length, boolean type) {
		String def = Hypothesis.ANY;
		if (!type) {
			def = Hypothesis.NONE;
		}
		this.features = new String[length];
		for (int i = 0; i < length; i++) {
			this.features[i] = def;
		}
	}

	public Hypothesis(int datalen, String string) {
		// TODO Auto-generated constructor stub
	}

	public String[] getFeatures() {
		return features;
	}

	/*
	 * prints the features it represents
	 */
	public String toString() {
		return Arrays.toString(this.features);
	}

	/*
	 * overriden boolean equals(Hypothesis h), returns: true if the two hypotheses
	 * have the same values for each feature false if they differ on at least one
	 * feature
	 */
	@Override
	public boolean equals(Object o) {
		for (int i = 0; i < this.features.length; i++) {
			if (!this.features[i].equals(((Hypothesis) o).features[i])) {
				return false;
			}
		}
		return true;
	}

	/*
	 * overriden needed for comparison in sets calculates the hash of a hypothesis,
	 * it does so by adding the decimal representation of each character of each
	 * feature value for all the features
	 */
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(features);
	}

	/*
	 * ArrayList<Integer> compareTo(Hypothesis h) returns an array of integers,
	 * where each integer represents the index at which the two compared hypotheses
	 * differ if the value of a feature of either this or the h hypothesis is the
	 * ANY value, then the other feature being compared can be thought of as more
	 * specialized than the ANY value and thus not considered inequal
	 */

	ArrayList<Integer> compareTo(Hypothesis h) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i < this.features.length; i++) {
			if (!this.features[i].equals(h.features[i]) && !this.features[i].equals(ANY)
					&& !h.features[i].equals(ANY)) {
				res.add(i);
			}
		}
		return res;
	}

	/*
	 * checks whether this hypothesis is more specific than another hypothesis given
	 * as a parameter returns: true if this hypothesis is more specific than the one
	 * given as a parameter false otherwise
	 */

	boolean isMoreSpecificThan(Hypothesis h, ArrayList<Ontology> fGraphList) {
		for (int i = 0; i < this.features.length; i++) {
			Ontology fGraph = fGraphList.get(i);
			Vertices currNode = fGraph.search(fGraph.getRoot(), h.features[i]);
			if (fGraph.search(currNode, this.features[i]) == null)
				return false;
		}
		return true;
	}

	/*
	 * checks whether this hypothesis is more general than the one given as a
	 * parameter returns: true if this hypothesis is more general than the one given
	 * as a parameter false otherwise
	 */
	boolean isMoreGeneralThan(Hypothesis h, ArrayList<Ontology> fGraphList) {
		for (int i = 0; i < this.features.length; i++) {
			Ontology fGraph = fGraphList.get(i);
			Vertices currNode = fGraph.search(fGraph.getRoot(), this.features[i]);
			if (fGraph.search(currNode, h.features[i]) == null)
				return false;
		}

		return true;
	}

	/*
	 * checks whether a given point's classification is consistent (agreeability)
	 * with the classification of this hypotheisis
	 * 
	 * returns: true if the classifications are consistent false if the
	 * classifications are not consistent
	 */
	boolean isConsistentWithDataPoint(String[] d, boolean classification, ArrayList<Ontology> fGraphList) {
		if (classifyPoint(d, fGraphList) != classification) {
			return false;
		}
		return true;
	}

	/*
	 * returns: true if the point was classified as positive (true) false if the
	 * point was classified as negative (false)
	 */
	boolean classifyPoint(String[] d, ArrayList<Ontology> fGraphList) {
		for (int i = 0; i < this.features.length; i++) {
			Ontology fGraph = fGraphList.get(i);
			Vertices currentNode = fGraph.search(fGraph.getRoot(), this.features[i]);
			if (fGraph.search(currentNode, d[i]) == null)
				return false;
		}
		return true;
	}
}