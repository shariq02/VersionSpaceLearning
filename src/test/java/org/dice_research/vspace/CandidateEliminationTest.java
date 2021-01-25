package org.dice_research.vspace;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import org.junit.Test;
import org.junit.*;

public class CandidateEliminationTest {
	ArrayList<HashSet<String>> fValues;
	HashSet<String> set;
	HashSet<String> setB;
	CandidateElimination ce;
	ArrayList<Ontology> graph;
	Instance inst;
	String[] instanceTestCase;
	String[] hypothesisTestCase;

	Hypothesis hypo;

	/**
	 * @author Parth Sharma Values are explicitly defined and a graph is created.
	 */
	@Before
	public void beforeClass() {
		set = new HashSet<String>();
		setB = new HashSet<String>();
		graph = new ArrayList<>();
		fValues = new ArrayList<HashSet<String>>();
		set.add("Apple");
		set.add("Orange");
		set.add("Grape");
		setB.add("Green");
		setB.add("Yellow");
		setB.add("Red");
		fValues.add(set);
		fValues.add(setB);

	}

	/**
	 * Testing makeGraph(ArrayList<HashSet<String>> fValues) method for the checking
	 * the featureGraph. The above named method is called in case of
	 * non-hierarchical data.
	 */

	@Test
	public void testMakeGraph() {

		ce = new CandidateElimination();
		ce.makeGraph(fValues);
		for (String str : set) {
			assertEquals(ce.featureGraph.get(0).search(ce.featureGraph.get(0).getRoot(), str).getValue(), str);
		}
		for (String str1 : setB) {
			assertEquals(ce.featureGraph.get(1).search(ce.featureGraph.get(1).getRoot(), str1).getValue(), str1);
		}

	}

	/**
	 * Testing makeGraph() method for checking the generated featureGraph by using
	 * the findCommonParent method in the Ontology class
	 */

	@Test
	public void testMakeGraph2() {
		ce = new CandidateElimination("Hierarchical", "src/test/resources/datafile/DataTest4.csv",
				"src/test/resources/datafile/OntologyTest4.csv");
		ce.performElimication();
		assertTrue((ce.featureGraph.get(1).findCommonParent("Cauliflower", "Tomato").getValue()).equals("Vegetables"));
		assertFalse((ce.featureGraph.get(1).findCommonParent("Potato", "Tomato").getValue()).equals("Polyhedron"));
		assertTrue(
				(ce.featureGraph.get(1).findCommonParent("Broccoli", "Cauliflower").getValue()).equals("Vegetables"));
		assertTrue((ce.featureGraph.get(1).findCommonParent("Apple", "Orange").getValue()).equals("Fruits"));
		assertTrue((ce.featureGraph.get(1).findCommonParent("Fruits", "Vegetables").getValue()).equals("?"));
		assertTrue((ce.featureGraph.get(2).findCommonParent("Cube", "Pyramid").getValue()).equals("Polyhedron"));
		assertTrue((ce.featureGraph.get(2).findCommonParent("Round", "Oval").getValue()).equals("?"));
		assertTrue((ce.featureGraph.get(0).findCommonParent("Large", "Small").getValue()).equals("?"));
		assertTrue((ce.featureGraph.get(0).findCommonParent("Large", "Small").getValue()).equals("?"));
		assertTrue((ce.featureGraph.get(2).findCommonParent("Oval", "Cube").getValue()).equals("?"));

	}

	/**
	 * Testing the mergeVersionSpace method which performs incremental merging of
	 * the version spaces and it creates a new version Space when necessary.
	 * 
	 */

	@Test
	public void testMergeVersionSpace() {
		ce = new CandidateElimination("Hierarchical", "src/test/resources/datafile/DataTestMergeVersionSpace.csv",
				"src/test/resources/datafile/OntologyTest3.csv");
		ce.performElimication();
		/**
		 * Testing here for the instance which results in both the S and G boundary to
		 * be the same. The prerequisites for the negative Instance (Specialization and
		 * removal) are done first. Some Instances from the file are run and then
		 * Instances are fed to test merging. Then the version spaces can be merged for
		 * the new Instance.
		 */
		instanceTestCase = new String[] { "Large", "Octahedron", "No" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setConsistentG(ce.getSpclG().specialize(inst.getAttribs(), ce.getInst_S(), ce.featureGraph, ce.getConsistentG(), "ce"));
		ce.setInst_S(ce.getGenS().removeMember(ce.getInst_S(), inst.getAttribs(), ce.featureGraph));
		ce.setInst_G(ce.getSpclG().specialize(inst.getAttribs(), ce.getInst_S(), ce.featureGraph, ce.getInst_G(), "ce"));

		ce.mergeVersionSpace(inst);
		hypothesisTestCase = new String[] { "Small", "Polyhedron" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getG())
			assertEquals(hypo, hyp);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getS())
			assertEquals(hypo, hyp);
		/**
		 * Testing the creation of New Version Space when the new instance can no more
		 * be accommodated within the present Version Space. With the instance
		 * {"Large","Cube","Yes"}, new version space has to be created. Boundaries of
		 * both version Spaces have to be checked.
		 */
		instanceTestCase = new String[] { "Large", "Cube", "Yes" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		/**
		 * Check if G or S is null. They should not be null at this point but may be
		 * nullified due to the Instance not being able to fit in the Version Space. If
		 * this happens, a new Version space can be created.
		 */

		assertNotNull(ce.getG());
		assertNotNull(ce.getS());

		ce.setInst_S(ce.getGenS().min_generalizations(ce.getInst_S(), inst.getAttribs(), ce.featureGraph, ce.getInst_G()));
		ce.setInst_G(ce.getSpclG().removeMember(ce.getInst_S(), ce.getInst_G(), ce.featureGraph));

		ce.mergeVersionSpace(inst);

		hypothesisTestCase = new String[] { "Large", "Cube" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getS())
			assertEquals(hypo, hyp);

		hypothesisTestCase = new String[] { "?", "Cube" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getG())
			assertEquals(hypo, hyp);

		hypothesisTestCase = new String[] { "Small", "Polyhedron" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getG())
			assertEquals(hypo, hyp);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getS())
			assertEquals(hypo, hyp);

		/**
		 * Testing with different data file
		 */
		ce = new CandidateElimination("Hierarchical", "src/test/resources/datafile/DataTestMergeVersionSpace2.csv",
				"src/test/resources/datafile/OntologyTest4.csv");
		ce.performElimication();

		/**
		 * A new second Version Space is created from the new Positive instance because
		 * it can't be accommodated in the already present Version Space.
		 */
		instanceTestCase = new String[] { "Large", "Apple", "Round", "Yes" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setInst_S(ce.getGenS().min_generalizations(ce.getInst_S(), inst.getAttribs(), ce.featureGraph, ce.getInst_G()));
		ce.setInst_G(ce.getSpclG().removeMember(ce.getInst_S(), ce.getInst_G(), ce.featureGraph));

		ce.mergeVersionSpace(inst);
		hypothesisTestCase = new String[] { "Large", "Vegetables", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getS())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "?", "Vegetables", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getG())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "Large", "Apple", "Round" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getS())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "?", "?", "Round" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getG())
			assertEquals(hypo, hyp);

		/**
		 * The new Instance is fit into the first version Space and therefore the second
		 * version space remains unaltered.
		 */

		instanceTestCase = new String[] { "Small", "Cauliflower", "Pyramid", "No" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setConsistentG(ce.getSpclG().specialize(inst.getAttribs(), ce.getInst_S(), ce.featureGraph, ce.getConsistentG(), "ce"));
		ce.setInst_S(ce.getGenS().removeMember(ce.getInst_S(), inst.getAttribs(), ce.featureGraph));
		ce.setInst_G(ce.getSpclG().specialize(inst.getAttribs(), ce.getInst_S(), ce.featureGraph, ce.getInst_G(), "ce"));

		ce.mergeVersionSpace(inst);

		hypothesisTestCase = new String[] { "Large", "Vegetables", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getS())
			assertEquals(hypo, hyp);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getG())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "Large", "Apple", "Round" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getS())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "?", "?", "Round" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getG())
			assertEquals(hypo, hyp);

		/**
		 * A new Third Version Space is created because the new positive instance cannot
		 * be fit into any of the two VS. The boundaries can contain multiple
		 * hypothesis.
		 */

		instanceTestCase = new String[] { "Large", "Pineapple", "Oval", "Yes" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setInst_S(ce.getGenS().min_generalizations(ce.getInst_S(), inst.getAttribs(), ce.featureGraph, ce.getInst_G()));
		ce.setInst_G(ce.getSpclG().removeMember(ce.getInst_S(), ce.getInst_G(), ce.featureGraph));

		ce.mergeVersionSpace(inst);

		hypothesisTestCase = new String[] { "Large", "Pineapple", "Oval" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(2).getS())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "?", "Pineapple", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		HashSet<Hypothesis> holder = new HashSet<Hypothesis>();
		holder.add(hypo);
		hypothesisTestCase = new String[] { "?", "?", "Oval" };
		Hypothesis hypo1 = new Hypothesis(hypothesisTestCase);
		holder.add(hypo1);
		assertEquals(holder, ce.getVS_hSet().get(2).getG());
		holder.clear();
		/**
		 * Testing with different data file OntologyTest6.csv
		 */
		ce = new CandidateElimination("Hierarchical", "src/test/resources/datafile/DataTestMergeVersionSpace3.csv",
				"src/test/resources/datafile/OntologyTest6.csv");
		ce.performElimication();
		instanceTestCase = new String[] { "Medium", "Volvo", "Hannover", "Yes" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setInst_S(ce.getGenS().min_generalizations(ce.getInst_S(), inst.getAttribs(), ce.featureGraph, ce.getInst_G()));
		ce.setInst_G(ce.getSpclG().removeMember(ce.getInst_S(), ce.getInst_G(), ce.featureGraph));
		ce.mergeVersionSpace(inst);

		hypothesisTestCase = new String[] { "Large", "?", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getS())
			assertEquals(hypo, hyp);
		for (Hypothesis hyp : ce.getVS_hSet().get(0).getG())
			assertEquals(hypo, hyp);

		/**
		 * The new second Version Space generation is being tested.
		 */

		hypothesisTestCase = new String[] { "Medium", "Volvo", "Hannover" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(1).getS())
			assertEquals(hypo, hyp);
		hypothesisTestCase = new String[] { "?", "LKW", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		holder.add(hypo);
		hypothesisTestCase = new String[] { "?", "?", "Hannover" };
		hypo = new Hypothesis(hypothesisTestCase);
		holder.add(hypo);
		hypothesisTestCase = new String[] { "Medium", "?", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		holder.add(hypo);
		assertEquals(holder, ce.getVS_hSet().get(1).getG());

		/**
		 * The third generated version space is tested.
		 */

		instanceTestCase = new String[] { "Small", "Mini", "Paderborn", "Yes" };
		inst = new Instance(instanceTestCase);
		ce.getInst_S().add(new Hypothesis(ce.getDatalen(), "S"));
		ce.getInst_G().add(new Hypothesis(ce.getDatalen(), "G"));
		System.out.println("The instance is");
		System.out.println(inst.toString());
		ce.setInst_S(ce.getGenS().min_generalizations(ce.getInst_S(), inst.getAttribs(), ce.featureGraph, ce.getInst_G()));
		ce.setInst_G(ce.getSpclG().removeMember(ce.getInst_S(), ce.getInst_G(), ce.featureGraph));
		ce.mergeVersionSpace(inst);

		// checking that the boundary of the previous Version Spaces is unchanged
		assertEquals(holder, ce.getVS_hSet().get(1).getG());
		holder.clear();
		hypothesisTestCase = new String[] { "Small", "Mini", "Paderborn" };
		hypo = new Hypothesis(hypothesisTestCase);
		for (Hypothesis hyp : ce.getVS_hSet().get(2).getS())
			assertEquals(hypo, hyp);

		hypothesisTestCase = new String[] { "?", "Mini", "?" };
		hypo = new Hypothesis(hypothesisTestCase);
		holder.add(hypo);
		hypothesisTestCase = new String[] { "?", "?", "Paderborn" };
		hypo = new Hypothesis(hypothesisTestCase);
		holder.add(hypo);
		assertEquals(holder, ce.getVS_hSet().get(2).getG());

	}

}
