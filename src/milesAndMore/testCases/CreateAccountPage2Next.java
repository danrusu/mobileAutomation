package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage2;


public class CreateAccountPage2Next extends TestCase{

	
	
	@Override
	public void run() {
			new CreateAccountPage2(Driver.driver).clickNextButton();
	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Press Next button on Create Account page 2.";
	}

}
