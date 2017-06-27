package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage1;


public class CreateAccountPage1Next extends TestCase{

	
	
	@Override
	public void run() {
			new CreateAccountPage1(Driver.driver).clickNextButton();
	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Press Next button on Create Account page 1.";
	}

}
