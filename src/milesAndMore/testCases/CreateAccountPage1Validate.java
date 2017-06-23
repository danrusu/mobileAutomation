package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage1;


public class CreateAccountPage1Validate extends TestCase{

	@Override
	public void run() {
			
 			new CreateAccountPage1(Driver.driver).validateContent();
	}

	@Override
	public String getTestCaseScenario() {
		return "Validate Create Account page 1 content (locate all elements).";
	}
}
