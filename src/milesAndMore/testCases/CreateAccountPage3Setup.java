package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage3;


public class CreateAccountPage3Setup extends TestCase{
	private CreateAccountPage3 createAccountPage3;
	// static elements
	private String pageTitleText;
	private String usernameText;
	private String passwordVerticalBarText;
	private String newPasswordText;
	private String confirmPasswordText;
	private String emailText;
	private String temporaryCardNumberText;
	private String createAccountButtonText;
	private String registerTermConditionsText;
	
	// dynamic data
	private String username;
	private String newPassword;
	@SuppressWarnings("unused")
	private Boolean hideShowPassword;
	private String confirmPassword;
	private String email;
	private String temporaryCardNumber;
	private Boolean termsRegisterSwitch;

	@Override
	public void run() {
		
		
		// dynamic data
		username = evalAttribute("userName");
		newPassword = evalAttribute("newPassword");
		hideShowPassword = evalBooleanAttribute("hideShowPassword");
		confirmPassword = evalAttribute("confirmPassword");
		String termsRegisterSwitchString = evalAttribute("termsRegisterSwitch");
		if (termsRegisterSwitchString != null){
			termsRegisterSwitch = evalBooleanAttribute("termsRegisterSwitch");
		}
		email = evalAttribute("email");
		temporaryCardNumber = evalAttribute("temporaryCardNumber");
		


		createAccountPage3 = new CreateAccountPage3(Driver.driver);
		validateStaticText();
		
	

		// dynamic test data
		if ( isAvailable(username) ){
			createAccountPage3.sendTextToUsernameInput(username);

			createAccountPage3.assertUsernameInput(username);
			
			createAccountPage3.closeKeyboard();
		}
		
		
		if ( isAvailable(newPassword) ){
			
			createAccountPage3.sendTextToNewPasswordInput(newPassword);
			
			createAccountPage3.showPassword();

			createAccountPage3.assertNewPasswordInput(newPassword);
			
			createAccountPage3.closeKeyboard();
		}
		
		
		if ( isAvailable(confirmPassword) ){
			createAccountPage3.sendTextToConfirmPasswordInput(confirmPassword);

			createAccountPage3.assertConfirmPasswordInput(confirmPassword);
			
			createAccountPage3.closeKeyboard();
		}
		
		
		if ( isAvailable(email) ){
			createAccountPage3.sendTextToEmailInput(email);

			createAccountPage3.assertEmailInput(email);
			
			createAccountPage3.closeKeyboard();
		}
		
		
		if ( isAvailable(temporaryCardNumber) ){
			createAccountPage3.sendTextToTemporaryCardNumberInput(temporaryCardNumber);

			createAccountPage3.assertTemporaryCardNumberInput(temporaryCardNumber);
			
			createAccountPage3.closeKeyboard();
		}
		
		if ( isAvailable(termsRegisterSwitch) ){
			createAccountPage3.setTermsRegisterSwitch(termsRegisterSwitch);
		}
		
	}



	public void validateStaticText() {
		// static data initialization
		pageTitleText = evalAttribute("pageTitleText");
		usernameText = evalAttribute("usernameText");
		passwordVerticalBarText = evalAttribute("passwordVerticalBarText");
		newPasswordText = evalAttribute("newPasswordText");
		confirmPasswordText = evalAttribute("confirmPasswordText");
		emailText = evalAttribute("emailText"); 
		temporaryCardNumberText = evalAttribute("temporaryCardNumberText");
		createAccountButtonText = evalAttribute("createAccountButtonText");
		registerTermConditionsText = evalAttribute("registerTermConditionsText");
		
		// Asserts
		assertStaticText(pageTitleText,
				createAccountPage3.getPageTitleText(),
				"Verify Page title text");

		assertStaticText(usernameText,
				createAccountPage3.getUsernameText(),
				"Verify User name text");
		
		assertStaticText(passwordVerticalBarText,
				createAccountPage3.getPasswordVerticalBarText(),
				"Verify Password Vertical bar text");
		
		assertStaticText(newPasswordText,
				createAccountPage3.getNewPasswordText(),
				"Verify New password text");
		
		
		assertStaticText(confirmPasswordText,
				createAccountPage3.getConfirmPasswordText(),
				"Verify Confirm Password text");
		
		assertStaticText(emailText,
				createAccountPage3.getEmailText(),
				"Verify Email text");
		
		assertStaticText(temporaryCardNumberText,
				createAccountPage3.getTemporaryCardNumberText(),
				"Verify Temporary Card Number Text  text");
		
		
		assertStaticText(registerTermConditionsText,
				createAccountPage3.getRegisterTermConditionsText(),
				"Verify Register Term Conditions text");
		
		
		assertStaticText(createAccountButtonText,
				createAccountPage3.getCReateAccountButtonText(),
				"Verify Create Account Button text");
	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Set Input on Create account page 3.";
	}
}
