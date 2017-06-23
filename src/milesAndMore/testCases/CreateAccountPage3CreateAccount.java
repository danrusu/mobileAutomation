package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage3;


public class CreateAccountPage3CreateAccount extends TestCase{

	@Override
	public void run() {
		CreateAccountPage3 createAccountPage3 = new CreateAccountPage3(Driver.driver);
		createAccountPage3.clickCreateAccountButton();
		createAccountPage3.waitForLoading(10);
	}

	@Override
	public String getTestCaseScenario() {
		return "Press Create account button on Create Account page 3.";
	}

}
