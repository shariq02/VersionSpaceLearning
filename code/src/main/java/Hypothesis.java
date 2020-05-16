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
		this.features = f;
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
	
	
	/*  int equals(Hypothesis h), returns:
	 * 		true if the two hypotheses have the same values for each feature
	 * 		false if they differ on at least one feature
	 * */
	boolean equals(Hypothesis h) {
		for(int i=0;i<this.features.length; i++) {
			if(!this.features[i].equals(h.features[i])) {
				return false;
			}
		}
		return true;
	}
	
	/*  int[] compareTo(Hypothesis h) returns an array of integers, where each integer represents the result of the equality
	 *  check of a specific feature, the values of the integers can be:
	 * 		true if the features of the two hypotheses differ at that index
	 * 		false if the features of the two hypotheses are the same at that index
	 * */
	
	boolean[] compareTo(Hypothesis h) {
		boolean[] result = new boolean[this.features.length];
		for(int i=0;i<this.features.length; i++) {
			if(!this.features[i].equals(h.features[i])) {
				result[i]=true;
			}
		}
		return result;
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
	
	//the negation of isMoreGeneralThan can also be used to check for specificity
	boolean isMoreSpecifcThan(Hypothesis h) {
		int s1=0, s2=0;
		for(int i=0;i<this.features.length;i++) {
			if(this.features[i].equals(ANY)) {
				s1++;
			}
			if(h.features[i].equals(ANY)) {
				s2++;
			}
			if(!this.features[i].equals(h.features[i])) {
				return false;
			}
			if(this.features[i].equals(ANY) && !h.features[i].equals(ANY)) {
				return false;
			}
		}
		return s1<s2? true: false;
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
	boolean isMoreGeneralThan(Hypothesis h) {
		int s1=0, s2=0;
		for(int i=0;i<this.features.length;i++) {
			if(this.features[i].equals(ANY)) {
				s1++;
			}
			if(h.features[i].equals(ANY)) {
				s2++;
			}
			if(!this.features[i].equals(ANY) && !this.features[i].equals(h.features[i])) {
				return false;
			}
			if(!this.features[i].equals(ANY) && h.features[i].equals(ANY)) {
				return false;
			}
		}
		return s1>s2? true: false;
	}
	
	/*
	 * checks whether a given point's classification is consistent (agreeability) with the classification
	 * of this hypotheisis
	 * 
	 * returns:
	 * 		true if the classifications are consistent
	 * 		false if the classifications are not consistent
	 * */
	boolean isConsistentWithDataPoint(String[] d, boolean classification) {
		if(classifyPoint(d) != classification) {
			return false;
		}
		return true;
	}
	
	/*
	 * returns:
	 * 		true if the point was classified as positive (true)
	 * 		false if the point was classified as negative (false)
	 * */
	boolean classifyPoint(String[] d) {
		for(int i =0; i<this.features.length;i++) {
			if(!this.features[i].equals(d[i]) && !this.features[i].equals(ANY)) {
				return false;
			}
		}
		return true;
	}
}
