package org.dice_research.SparqlUseCase;

public class SpabApi {
	
	final protected SpabAlgorithm spab = new SpabAlgorithm();
	
	/**
	 * Adds query to set of negative inputs.
	 * 
	 */
	public void addNegative(String sparqlQuery) {
		spab.getInput().addNegative(sparqlQuery);
	}

	/**
	 * Adds query to set of positive inputs.
	 * 
	 */
	public void addPositive(String sparqlQuery) {
		spab.getInput().addPositive(sparqlQuery);
	}

	
	
	
	

}
