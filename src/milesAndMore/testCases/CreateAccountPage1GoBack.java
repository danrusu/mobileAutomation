package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage1;


public class CreateAccountPage1GoBack extends TestCase{

	
	
	@Override
	public void run() {
			
		new CreateAccountPage1(Driver.driver).goBack();
			
	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Press Back Arrow button on Create Account page 1.";
	}

}
