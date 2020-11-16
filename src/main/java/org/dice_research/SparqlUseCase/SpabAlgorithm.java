package org.dice_research.SparqlUseCase;

public class SpabAlgorithm {
	
	/**
	 * Initializes data structures.
	 */
	public SpabAlgorithm() {
		configuration = new Configuration();
		input = new Input();
		graph = new CandidateGraph();
		queue = new CandidateQueue();
	}
	
	public Input getInput() {
		return input;
	}

}
