package unitTest.testCases;

import base.Assert;
import base.TestCase;
import base.failures.CodeFailureTest;

/**
 * Unit test for an Error test case failure.
 * @author Dan.Rusu
 *
 */
public class BlankTestCaseError extends TestCase{

	public BlankTestCaseError(){
		super();
	}


	@Override
	public void run(){
		
		
		CodeFailureTest.functionTest(
				x -> Assert.fail(),
				"Test case error!");
	}
	
	@Override
	public String getTestCaseScenario(){
		return "\nEmpy test case used just for unit testing an Exception test case failure!";
	}


}
