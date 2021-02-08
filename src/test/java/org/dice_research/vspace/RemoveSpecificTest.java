package org.dice_research.vspace;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the removeSpecific method in the SpecializeGBoundary. To check that the
 * more Specific Hypothesis is removed from the G Boundary.
 * 
 * @author Parth Sharma
 *
 */
public class RemoveSpecificTest {

	CandidateElimination ce;
	SpecializeGBoundary sgb;
	HashSet<Hypothesis> G;
	HashSet<Hypothesis> ExpOut;

	@Before
	public void before() {
		ce = new CandidateElimination();
		sgb = new SpecializeGBoundary();
		G = new HashSet<>();
		ExpOut = new HashSet<>();
	}

	/**
	 * Test for the removal of more specific function using specific Ontology.
	 */
	@Test
	public void test() {
		ce.setGraphPath("src/test/resources/datafile/OntologyTest3.csv");
		ce.setDatalen(2);
		ce.makeGraph();
		G.add(new Hypothesis(new String[] { "Small", "Polyhedron" }));
		G.add(new Hypothesis(new String[] { "?", "Sphere" }));
		G.add(new Hypothesis(new String[] { "?", "Polyhedron" }));
		G = sgb.removeSpecific(G, ce.getFeatureGraph());
		ExpOut.add(new Hypothesis(new String[] { "?", "Polyhedron" }));
		ExpOut.add(new Hypothesis(new String[] { "?", "Sphere" }));

		assertEquals(ExpOut, G);
		ExpOut.clear();

		G.add(new Hypothesis(new String[] { "Large", "?" }));
		G.add(new Hypothesis(new String[] { "?", "Pyramid" }));
		G = sgb.removeSpecific(G, ce.getFeatureGraph());
		ExpOut.add(new Hypothesis(new String[] { "?", "Sphere" }));
		ExpOut.add(new Hypothesis(new String[] { "?", "Polyhedron" }));
		ExpOut.add(new Hypothesis(new String[] { "Large", "?" }));
		assertEquals(ExpOut, G);

	}

	/**
	 * Test using different Ontology and different datalength.
	 */
	@Test
	public void test1() {
		ce.setGraphPath("src/test/resources/datafile/OntologyTest4.csv");
		ce.setDatalen(3);
		ce.makeGraph();
		G.add(new Hypothesis(new String[] { "?", "Vegetables", "Oval" }));
		G.add(new Hypothesis(new String[] { "Large", "Potato", "Oval" }));
		G.add(new Hypothesis(new String[] { "?", "Vegetables", "Round" }));

		ExpOut.add(new Hypothesis(new String[] { "?", "Vegetables", "Oval" }));
		ExpOut.add(new Hypothesis(new String[] { "?", "Vegetables", "Round" }));
		G = sgb.removeSpecific(G, ce.getFeatureGraph());

		assertEquals(ExpOut, G);

		G.add(new Hypothesis(new String[] { "?", "Fruits", "Cube" }));
		G.add(new Hypothesis(new String[] { "?", "Fruits", "Polyhedron" }));
		ExpOut.add(new Hypothesis(new String[] { "?", "Fruits", "Polyhedron" }));
		G = sgb.removeSpecific(G, ce.getFeatureGraph());

		assertEquals(ExpOut, G);
	}

}
