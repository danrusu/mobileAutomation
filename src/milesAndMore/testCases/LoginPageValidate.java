package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.LoginPage;


public class LoginPageValidate extends TestCase{

	@Override
	public void run() {
		LoginPage loginPage = new LoginPage(Driver.driver);	
		
		loginPage.waitForLoading(30);
		
		loginPage.validateContent();
	}

	@Override
	public String getTestCaseScenario() {
		return "Validate Login Page content (locate all elements).";
	}
}
