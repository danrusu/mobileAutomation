package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage1;
import milesAndMore.pages.LoginPage;


public class CreateAccount extends TestCase{
	private LoginPage loginPage;
	private CreateAccountPage1 createAcountPage1;
	
	// static elements' text
	private String onBoardTitleText;
	private String  onBoardText;
	private String  createAccountButtonText;
	private String  signInButtonText;
	

	@Override
	public void run() {
		  	onBoardTitleText = evalAttribute("onBoardTitleText");
		  	onBoardText = evalAttribute("onBoardText");
		  	createAccountButtonText = evalAttribute("createAccountButtonText");
		  	signInButtonText = evalAttribute("signInButtonText");
			
			
			loginPage = new LoginPage(Driver.driver);
			
			// validate static texts
			assertStaticText(onBoardTitleText,
					loginPage.getOnBoardTitleText(),
					"Verify On Board Text title text");
			
			assertStaticText(onBoardText,
					loginPage.getOnBoardText(),
					"Verify On Board Text");
			
			assertStaticText(createAccountButtonText,
					loginPage.getCreateAccountButtonText(),
					"Verify Create Account button text");
			
			assertStaticText(signInButtonText,
					loginPage.getSignInButtonText(),
					"Verify Sign in button text");
			
			
			loginPage.clickCreateAccount();
			
			
			createAcountPage1 = new CreateAccountPage1(Driver.driver);
			createAcountPage1.validateContent();

	}

	@Override
	public String getTestCaseScenario() {
		return "Click create account button on Miles & More Login page.";
	}


	

}
