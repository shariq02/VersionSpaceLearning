package org.dice_research.vspace;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HypothesisTest.class, InputFileExtensionTest.class, InputFilePathTest.class, 
    MakeGraphTest.class, ReadFromcsvFile.class, SpecializeGTest.class, 
    SpecializeRemoveMemberTest.class, abc.class, CandidateEliminationTest.class,
    Generalise_S_Test_DataTest1.class, Generalise_S_Test_DataTest2.class, RemoveSpecificTest.class,
    TestGeneralizeS_compareGRemove.class, TestRunner.class, VersionSpaceTest.class})
public class AllTests {

}
