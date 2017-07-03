package unitTest.testCases;

import base.TestCase;

/**
 * Unit test for a successful test case run.
 * @author Dan.Rusu
 *
 */
public class BlankTestCase extends TestCase{

	public BlankTestCase(){
		super();
	}


	@Override
	public void run(){
		logger.log("Empty test case used for unit testing!");
	}
	
	
	@Override
	public String getTestCaseScenario(){
		return "\nEmpy test case used just for unit testing!";
	}
}
