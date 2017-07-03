package unitTest.testCases;

import java.util.TreeMap;

import base.TestCase;
import base.failures.CodeFailureTest;

/**
 * Unit test for an RuntimeException test case failure.
 * @author Dan.Rusu
 *
 */
public class BlankTestCaseRuntimeException extends TestCase{

	public BlankTestCaseRuntimeException(){
		super();
	}


	@Override
	public void run(){
		
		
		// NullPointerException
		CodeFailureTest.functionTest(
				x -> {
					new TreeMap<Integer, String>().get(1).equals("Failure");
				},
				"Test case runtime exception!");
	}
	
	@Override
	public String getTestCaseScenario(){
		return "\nEmpy test case used just for unit testing an RuntimeException test case failure!";
	}


}
