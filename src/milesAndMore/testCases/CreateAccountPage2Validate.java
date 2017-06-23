package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage2;


public class CreateAccountPage2Validate extends TestCase{

	@Override
	public void run() {
			
 			new CreateAccountPage2(Driver.driver).validateContent();
	}

	@Override
	public String getTestCaseScenario() {
		return "Validate Create Account page 2 content (locate all elements).";
	}
}
