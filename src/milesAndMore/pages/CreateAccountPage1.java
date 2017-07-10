package milesAndMore.pages;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.android.AndroidDriver;

public class CreateAccountPage1 extends BasePage{
	private Map<String, Integer>  academicTitleList = new TreeMap<>();

	// Locators
	private By backArrow = By.xpath("//*[@resource-id='com.plannet.milesandmoreapp:id/register_info_image']"
			+ "/preceding-sibling::android.widget.ImageView");
	private By registerInfoImage = By.id(prefixed("register_info_image"));
	private By pageTitleText = By.id(prefixed("title_text"));

	private By mrRadioButton = By.id(prefixed("salutation_mr"));
	private By mrRadioButtonText = By.id(prefixed("salutation_mr_text"));
	private By mrsRadioButton = By.id(prefixed("salutation_mrs"));
	private By mrsRadioButtonText = By.id(prefixed("salutation_mrs_text"));


	private By academicTitle = By.id(prefixed("academic_title"));
	private By academicTitleInput = By.id(prefixed("academic_title_text"));
	private By academicTitleOptions = By.xpath("//*[@resource-id=\"android:id/text1\"]");
	private By academicTitleDialogOK = By.id("android:id/button1");
	private By academicTitleDialogCancel = By.id("android:id/button2");

	private By firstName = By.id(prefixed("first_name"));
	private By firstNameInput = By.id(prefixed("first_name_text"));
	private By lastName = By.id(prefixed("last_name"));
	private By lastNameInput = By.id(prefixed("last_name_text"));

	private By signInAsGuest = By.id(prefixed("sign_in_guest_text"));


	private By firstPageIndicator = By.id(prefixed("first_page_indicator"));
	private By secondPageIndicator = By.id(prefixed("second_page_indicator"));
	private By thirdPageIndicator = By.id(prefixed("third_page_indicator"));

	private By nextButton = By.id(prefixed("button_register_next"));





	public CreateAccountPage1(AndroidDriver<?> driver){
		super(driver);

		academicTitleList.put("None", 0);
		academicTitleList.put("Dr.", 1);
		academicTitleList.put("Prof.", 2);
		academicTitleList.put("Prof. Dr.", 3);
	}


	public Map<String, Integer> getAcademicTitleList() {
		return academicTitleList;
	}

	// Actions
	public void clickMrsRadioButton(){
		findElement(mrsRadioButton).click();
	}


	public void clickMrRadioButton(){
		findElement(mrRadioButton).click();
	}


	public void clickAcademicTitleRadioButton(){
		findElement(academicTitleInput).click();
	}


	public void sendTextToFirstNameInput(String text){
		sendText(firstNameInput, text);
	}


	public void sendTextToLastNameInput(String text){
		sendText(lastNameInput, text);
	}


	public void clickNextButton(){
		findElement(nextButton).click();
		ThreadUtils.sleepQuiet(2000);
	}


	@SuppressWarnings("unchecked")
	public List<WebElement> findAcademicTitleDialogOptions(){
		//List<WebElement> x = driver.findElements(By.xpath("//android.widget.CheckedTextView"));
		return (List<WebElement>) driver.findElements(academicTitleOptions);
	}


	public void clickAcademicTitleDialogOption(int i) {
		findAcademicTitleDialogOptions().get(i).click();
	}


	public void clickAcademicTitleDialogOK() {
		findElement(academicTitleDialogOK ).click();

	}


	public void clickAcademicTitleDialogCancel() {
		findElement(academicTitleDialogCancel ).click();

	}


	public void closeKeyboard() {
		driver.navigate().back();
	}


	public void validateContent() {
		try{
			findElements( Arrays.asList(
					registerInfoImage,
					//pageTitleText,
					mrRadioButtonText,
					mrRadioButton,
					mrsRadioButtonText,
					mrsRadioButton,
					academicTitle,
					academicTitleInput,
					firstName,
					firstNameInput,
					lastName,
					lastNameInput,
					firstPageIndicator,
					secondPageIndicator,
					thirdPageIndicator,
					nextButton));
		}
		catch (Exception e){
			throw new TestCaseFailure("Create account page 1 validation failed!" , e);
		}
	}


	public void assertMrsRadioButtonIsChecked(boolean checked) {
		assertIsChecked(mrsRadioButton, 
				checked,
				"Verify Mrs button is checked");
	}

	public void assertMrRadioButtonIsChecked(boolean checked) {
		assertIsChecked(mrRadioButton, 
				checked,
				"Verify Mr button is checked");
	}




	public void clickSalutationTitle(String title) {
		switch (title){
		case "Mr.":
			clickMrRadioButton();
			break;
		case "Mrs.":
			clickMrsRadioButton();
			break;

		default:
			throw new TestCaseFailure("Invalid Salutation title!");
		}
	}


	public void assertSalutationTitleIsChecked(String title, boolean checked) {
		switch (title){
		case "Mr.":
			assertMrRadioButtonIsChecked(checked);
			break;
		case "Mrs.":
			assertMrsRadioButtonIsChecked(checked);
			break;

		default:
			throw new TestCaseFailure("Invalid Salutation title!");
		}

	}

	// Errors
	public String getFirstNameError(){
		return getError(firstName);
	}

	public String getLastNameError(){
		return getError(lastName);
	}


	@Override
	public List<String> getAllErrors(){
		return getErrors(Arrays.asList(firstName, lastName));
	}


	public String getAcademicTitleText() {
		return getTrimmedText(academicTitle);
	}
	public String getAcademicTitleInput() {
		return getTrimmedText(academicTitleInput);
	}


	public String getFirstNameText() {
		return getTrimmedText(firstName);
	}
	public String getFirstNameInput() {
		return getTrimmedText(firstNameInput);
	}


	public String getLastNameText() {
		return getTrimmedText(lastName);
	}
	public String getLastNameInput() {
		return getTrimmedText(lastNameInput);
	}


	public String getSalutationTitleMrText() {
		return getTrimmedText(mrRadioButtonText);
	}

	public String getSalutationTitleMrsText() {
		return getTrimmedText(mrsRadioButtonText);
	}


	public String getSignInAsGuestText() {
		return getTrimmedText(signInAsGuest);
	}


	public String getNextButtonText() {
		return getTrimmedText(nextButton);
	}


	public String getPageTitleText() {
		return getTrimmedText(pageTitleText);
	}


	public void goBack() {
		click(backArrow);
	}

}
