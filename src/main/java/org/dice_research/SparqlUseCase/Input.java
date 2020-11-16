package org.dice_research.SparqlUseCase;

import java.io.FileReader;
import java.util.List;

public class Input {
	protected List<SparqlUnit> negatives;
	protected List<SparqlUnit> positives;
	protected Set<String> resources;

	/**
	 * Initializes model and sets namespace prefixes for RDF, RDFS, and SPIN.
	 */
	public Input() {
		positives = new LinkedList<SparqlUnit>();
		negatives = new LinkedList<SparqlUnit>();
	}

	/**
	 * Adds query to set of negative inputs.
	 * 
	 */
	public void addNegative(String sparqlQuery) {
		addQuery(sparqlQuery, false);
	}

	/**
	 * Adds query to set of positive inputs.
	 * 
	 */
	public void addPositive(String sparqlQuery) {
		addQuery(sparqlQuery, true);
	}
	
	
	protected void addQuery(String sparqlQuery, boolean positive)  {

		
			if (positive) {
				positives.add(new SparqlQuery(sparqlQuery, this));
			} else {
				negatives.add(new SparqlQuery(sparqlQuery, this));
			
	
	/**
	 * Gets set of negative inputs.
	 */
	public List<SparqlUnit> getNegatives() {
		return negatives;
	}

	/**
	 * Gets set of positive inputs.
	 */
	public List<SparqlUnit> getPositives() {
		return positives;
	}

	/**
	 * Gets all used resources in inputs. Uses cache.
	 */
}

