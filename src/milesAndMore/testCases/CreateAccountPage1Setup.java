package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage1;



public class CreateAccountPage1Setup extends TestCase{
	private CreateAccountPage1 createAcountPage1;
	// static elements
	private String pageTitleText;
	private String salutationTitleMrText;
	private String salutationTitleMrsText;
	private String academicTitleText;
	private String firstNameText;
	private String lastNameText;
	private String signInAsGuestText;
	private String nextButtonText;
	
	private String title;
	private String academicTitle;
	private String firstName;
	private String lastName;

	@Override
	public void run() {
		
		// dynamic data
		title = evalAttribute("title");
		academicTitle = evalAttribute("academicTitle");
		firstName = evalAttribute("firstName");
		lastName = evalAttribute("lastName");


		createAcountPage1 = new CreateAccountPage1(Driver.driver);
		validateStaticText();
		

		// dynamic test data
		if ( isAvailable(title) ){
			createAcountPage1.clickSalutationTitle(title);
			createAcountPage1.assertSalutationTitleIsChecked(title, true);
		}

		// Academic Title dialog
		if ( isAvailable(academicTitle) ){
			createAcountPage1.clickAcademicTitleRadioButton();
			createAcountPage1.clickAcademicTitleDialogOption(
					createAcountPage1.getAcademicTitleList().get(academicTitle));
			
			createAcountPage1.clickAcademicTitleDialogOK();
			assertStaticText(getAcademicTitle(academicTitle), 
					createAcountPage1.getAcademicTitleInput(), 
					"Verify academic tile");
					
		}

		if ( isAvailable(firstName) ){
			createAcountPage1.sendTextToFirstNameInput(firstName);
			
			assertStaticText(createAcountPage1.maxChars(firstName, 15), 
					createAcountPage1.getFirstNameInput(), 
					"Verify First Name");
			createAcountPage1.closeKeyboard();
		}

		if ( isAvailable(lastName) ){
			createAcountPage1.sendTextToLastNameInput(lastName);
			
			assertStaticText(createAcountPage1.maxChars(lastName, 31), 
					createAcountPage1.getLastNameInput(), 
					"Verify Last Name");
			createAcountPage1.closeKeyboard();
		}

	}

	
	private Object getAcademicTitle(String academicTitle) {
		if ( academicTitle.equals("None") ) {
			return "";
		}
		else{
			return academicTitle;
		}
	}

	
	public void validateStaticText() {
		// static data initialization
		pageTitleText = evalAttribute("pageTitleText");
		salutationTitleMrText = evalAttribute("salutationTitleMrText");
		salutationTitleMrsText = evalAttribute("salutationTitleMrsText");
		academicTitleText = evalAttribute("academicTitleText");
		firstNameText = evalAttribute("firstNameText");
		lastNameText = evalAttribute("lastNameText");
		signInAsGuestText = evalAttribute("signInAsGuestText");
		nextButtonText = evalAttribute("nextButtonText");
		
		// Asserts
		assertStaticText(pageTitleText,
				createAcountPage1.getPageTitleText(),
				"Verify Page title text");

		assertStaticText(salutationTitleMrText,
				createAcountPage1.getSalutationTitleMrText(),
				"Verify Salutation title Mr text");

		assertStaticText(salutationTitleMrsText,
				createAcountPage1.getSalutationTitleMrsText(),
				"Verify Salutation title Mrs text");

		assertStaticText(academicTitleText,
				createAcountPage1.getAcademicTitleText(),
				"Verify Academic title text");

		assertStaticText(firstNameText,
				createAcountPage1.getFirstNameText(),
				"Verify First name text");

		assertStaticText(lastNameText,
				createAcountPage1.getLastNameText(),
				"Verify Last name text");

		assertStaticText(signInAsGuestText,
				createAcountPage1.getSignInAsGuestText(),
				"Verify Sign in as guest text");

		assertStaticText(nextButtonText,
				createAcountPage1.getNextButtonText(),
				"Verify Next button text");

	}
	
	
	@Override
	public String getTestCaseScenario() {
		return "Set Input on Create account page 1.";
	}

}
