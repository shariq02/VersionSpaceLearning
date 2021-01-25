package org.dice_research.vspace;
import java.util.HashSet;
import org.junit.Before;
import static org.junit.Assert.*;

import org.junit.Test;

public class VersionSpaceTest {
	HashSet<Hypothesis> s;
	HashSet<Hypothesis> g;
	VersionSpace versionSpace;
	 	@Before
	public void before() {
		String[] case1= {"Yellow", "Banana"};
		String[] case2= {"Green", "Apple"};
		Hypothesis hyp1=new Hypothesis(case1);
		Hypothesis hyp2=new Hypothesis(case2);
		
		s=new HashSet<>();
		g=new HashSet<>();
		s.add(hyp1);
		g.add(hyp2);
		versionSpace=new VersionSpace(s,g);
		
	}
	
	@Test
	public void testSetS() {
		versionSpace.setS(g);
		System.out.println("In Test SetS");
		assertEquals(g,versionSpace.getS());
	}
	@Test
	public void testSetG() {
		versionSpace.setG(s);
		System.out.println("In Test SetG");
		assertEquals(s,versionSpace.getG());
	}
	
	@Test
	public void testGetS() {
		System.out.println("In Test GetS");
		assertEquals(s,versionSpace.getS());
	}
	
	@Test
	public void testGetG() {
		System.out.println("In Test GetG");
		assertEquals(g,versionSpace.getG());
	}

}
