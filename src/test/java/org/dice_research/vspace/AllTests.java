package org.dice_research.vspace;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HypothesisTest.class, InputFileExtensionTest.class, InputFilePathTest.class, MakeGraphTest.class,
		ReadFromcsvFile.class, SpecializeGTest.class, SpecializeRemoveMemberTest.class })
public class AllTests {

}
