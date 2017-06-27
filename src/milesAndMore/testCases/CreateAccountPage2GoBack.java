package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage2;


public class CreateAccountPage2GoBack extends TestCase{

	
	@Override
	public void run() {
			
 			new CreateAccountPage2(Driver.driver).goBack();
			
	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Press Back Arrow button on Create Account page 2.";
	}

}
