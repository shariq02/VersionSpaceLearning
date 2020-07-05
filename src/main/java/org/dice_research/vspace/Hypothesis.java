package org.dice_research.vspace;

import java.util.ArrayList;
public class Hypothesis {
	
	/*
	 * possible values of the features:
	 * 	NONE
	 * 	ANY
	 * 	values specified by the data
	 * */
	static final String ANY= "?";
	static final String NONE = "-";
	
	String[] features;
	
	/*
	 * constructor
	 * */
	public Hypothesis(String[] f) {
		this.features = new String[f.length];
		for(int i=0; i<f.length;i++) {
			this.features[i]=f[i];
		}
	}
	
	/*
	 * constructor for easier initialization of the most specific and most general
	 * hypotheses.
	 * accepts the number of features (int l) and the value "S" (for most specific)
	 * or "G" (for most general).*/
	public Hypothesis(int l, String type) {
		String def = Hypothesis.ANY;
        if(type.equals("S")) def = Hypothesis.NONE;
        this.features = new String[l];
        for (int i = 0 ; i < l ; i++){
            this.features[i] = def;
        }
	}
	
	public String[] getFeatures() {
		return features;
	}
	
	/*
	 * prints the features it represents
	 * */
	public String toString() {
		String result = "< ";
		for(String s: this.features) {
			result+=s+", ";
		}
		result=result.substring(0,result.length()-2)+" >";
		return result;
	}
	
	
	/* overriden 
	 * boolean equals(Hypothesis h), returns:
	 * 		true if the two hypotheses have the same values for each feature
	 * 		false if they differ on at least one feature
	 * */
	public boolean equals(Object o) {
		for(int i=0;i<this.features.length; i++) {
			if(!this.features[i].equals(((Hypothesis)o).features[i])) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * overriden
	 * needed for comparison in sets
	 * calculates the hash of a hypothesis, it does so by adding the decimal representation
	 * of each character of each feature value for all the features
	 * */
	public int hashCode() {
		int result=0;
		for(int i=0;i<features.length;i++) {
			for(int j=0;j<features[i].length();j++) {
				int k = (int)(features[i].charAt(j));
				result+=k;
			}
		}
		return result;
	}
	
	/*  ArrayList<Integer> compareTo(Hypothesis h) returns an array of integers, where each integer represents the index at which
	 *  the two compared hypotheses differ
	 *  if the value of a feature of either this or the h hypothesis is the ANY value, then the other feature being compared can
	 *  be thought of as more specialized than the ANY value and thus not considered inequal
	 * */
	
	ArrayList<Integer> compareTo(Hypothesis h) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(int i=0;i<this.features.length; i++) {
			if(!this.features[i].equals(h.features[i]) && !this.features[i].equals(ANY) && !h.features[i].equals(ANY)) {
				res.add(i);
			}
		}
		return res;
	}
	
	/*
	 * checks whether this hypothesis is more specific than another hypothesis given as a parameter
	 * it scans the features for "ANY" values, and if the number of "ANY" values of this hypothesis
	 * is less than the number of "ANY" values of the hypothesis given as a parameter then this
	 * hypothesis is more specific
	 * 
	 *  returns:
	 *  	true if this hypothesis is more specific than the one given as a parameter
	 *  	false otherwise
	 * */
	
	//the negation of isMoreGeneralThan NEEDS to be used to check for specificity
	//as inconsistencies were found in the current definition of this method
	boolean isMoreSpecificThan(Hypothesis h, ArrayList<Ontology> fGraphList) {
		for (int i = 0 ; i < this.features.length ; i++ )
		{
			Ontology fGraph  = fGraphList.get(i);
			Vertices currNode = fGraph.search(fGraph.getRoot(), h.features[i]);
			if (fGraph.search(currNode, this.features[i]) == null) return false;
		}
		return true;
	}
	
	/*
	 * checks whether this hypothesis is more general than the one given as a parameter
	 * it scans the features for "ANY" values, and if the number of "ANY" values of this hypothesis
	 * is greater than the number of "ANY" values of the hypothesis given as a parameter then this
	 * hypothesis is more general
	 * 
	 *  returns:
	 *  	true if this hypothesis is more general than the one given as a parameter
	 *  	false otherwise
	 * */
	boolean isMoreGeneralThan(Hypothesis h, ArrayList<Ontology> fGraphList) {
		for (int i = 0 ; i < this.features.length ; i++ )
		{
			Ontology fGraph  = fGraphList.get(i);
			Vertices currNode = fGraph.search(fGraph.getRoot(), this.features[i]);
			if (fGraph.search(currNode, h.features[i]) == null) return false;
		}

		return true;
	}
	
	/*
	 * checks whether a given point's classification is consistent (agreeability) with the classification
	 * of this hypotheisis
	 * 
	 * returns:
	 * 		true if the classifications are consistent
	 * 		false if the classifications are not consistent
	 * */
	boolean isConsistentWithDataPoint(String[] d, boolean classification, ArrayList<Ontology> fGraphList) {
		if(classifyPoint(d, fGraphList) != classification) {
			return false;
		}
		return true;
	}
	
	/*
	 * returns:
	 * 		true if the point was classified as positive (true)
	 * 		false if the point was classified as negative (false)
	 * */
	boolean classifyPoint(String[] d, ArrayList<Ontology> fGraphList ) {
		for(int i =0; i<this.features.length;i++) {
			Ontology fGraph = fGraphList.get(i);
			Vertices currentNode = fGraph.search(fGraph.getRoot(), this.features[i]);
			if (fGraph.search(currentNode, d[i]) == null) return  false;
		}
		return true;
	}
}
