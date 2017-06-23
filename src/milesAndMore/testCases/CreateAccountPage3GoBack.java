package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage3;


public class CreateAccountPage3GoBack extends TestCase{

	@Override
	public void run() {
			
 			new CreateAccountPage3(Driver.driver).goBack();
	}

	@Override
	public String getTestCaseScenario() {
		return "Press Back Arrow button on Create Account page 3.";
	}
}
