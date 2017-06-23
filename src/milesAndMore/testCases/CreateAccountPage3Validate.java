package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage3;


public class CreateAccountPage3Validate extends TestCase{

	@Override
	public void run() {
			
 			new CreateAccountPage3(Driver.driver).validateContent();
	}

	@Override
	public String getTestCaseScenario() {
		return "Validate Create Account page 3 content (locate all elements).";
	}
}
