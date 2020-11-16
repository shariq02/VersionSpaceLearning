package org.dice_research.SparqlUseCase;

import java.io.FileReader;

public class SpabExample {
	public static final float LAMBDA = .1f;

	/**
	 * Configuration: Number of iterations. Test run with 1000 iterations took about
	 * 9 seconds
	 */
	public static final int MAX_ITERATIONS = 100;

	/**
	 * Configuration: Use Fuseki or Virtuoso file.
	 */
	public static final boolean USE_FUSEKI = true;

	/**
	 * Configuration: Number of SPARQL queries added to set of negative examples.
	 */
	public static final int NUMBER_OF_NEGATIVES = 10;

	/**
	 * Configuration: Number of SPARQL queries added to set of positives examples.
	 */
	public static final int NUMBER_OF_POSITIVES = 10;
	
	
	public static final String RESOURCE_IGUANA_FUSEKI_NEGATIVE = "D:\\Workspace\\SparqlUseCase\\src\\main\\java\\org\\dice_research\\SparqlUseCase\\Fuseki - Negative.txt";
	public static final String RESOURCE_IGUANA_FUSEKI_POSITIVE = "D:\\Workspace\\SparqlUseCase\\src\\main\\java\\org\\dice_research\\SparqlUseCase\\Fuseki - Positive.txt";
	
	
	String negFile = RESOURCE_IGUANA_FUSEKI_NEGATIVE;
	String posFile = RESOURCE_IGUANA_FUSEKI_POSITIVE;
	
	
	List<String> negatives = FileReader.readFileToList(Resources.getResource(negFile).getPath(), true,
			FileReader.UTF8);
	
	List<String> positives = FileReader.readFileToList(Resources.getResource(posFile).getPath(), true,
			FileReader.UTF8);
	
	int n = NUMBER_OF_NEGATIVES;
	for (String query : negatives) {
		spabApi.addNegative(query);
		if (--n == 0) {
			break;
		}
	}

	int p = NUMBER_OF_POSITIVES;
	for (String query : positives) {
		spabApi.addPositive(query);
		if (--p == 0) {
			break;
		}
	} 
}



