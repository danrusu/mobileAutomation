package milesAndMore.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import base.Assert;
import base.BasePage;
import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.android.AndroidDriver;

public class CreateAccountPage3 extends BasePage{
	// Locators
	private By pageTitleText =  By.xpath("//*[@resource-id='com.plannet.milesandmoreapp:id/register_layout']"
			+ "/following-sibling::android.widget.RelativeLayout"
			+ "/android.widget.TextView");
	
	
	private By backArrow = By.xpath("//*[@resource-id='com.plannet.milesandmoreapp:id/register_layout']"
			+ "/following-sibling::android.widget.RelativeLayout"
			+ "/android.widget.ImageView");
	
	
	private By usernameText = By.id(prefixed("username"));
	private By usernameInput = By.id(prefixed("username_text"));
	 
	
	private By passwordVerticalBarText = By.id(prefixed("passwordVerticalBar"));
	private By hideShowPassword = By.id(prefixed("hideShowPassword")); 
	
	private By newPasswordText = By.id(prefixed("new_password")); 
	private By newPasswordInput = By.id(prefixed("new_password_text")); 
	
	private By registerInfoImage = By.id(prefixed("register_info_image"));
	
	private By confirmPasswordText = By.id(prefixed("confirm_password"));
	private By confirmPasswordInput = By.id(prefixed("confirm_password_text"));
	
	
	private By emailText = By.id(prefixed("email"));
	private By emailInput = By.id(prefixed("email_text"));
	
	
	private By temporaryCardNumberText = By.id(prefixed("temporary_card_number"));
	private By temporaryCardNumberInput = By.id(prefixed("temporary_card_number_text"));
	
	private By registerTermConditionsText = By.id(prefixed("register_terms_conditions"));
	private By termsRegisterSwitch = By.id(prefixed("terms_read_switch"));
	
	
	private By createAccountButton = By.id(prefixed("button_register_next"));

	
/*	
 * <android.widget.ImageView>
<android.widget.TextView>
 * 
 * <TextInputLayout resource-id="com.plannet.milesandmoreapp:id/username">
	Username must not be empty.
	
	<android.widget.TextView resource-id="com.plannet.milesandmoreapp:id/passwordVerticalBar">
	<android.widget.TextView resource-id="com.plannet.milesandmoreapp:id/hideShowPassword"
	Password must be between 8 and 32 characters.Password must contain at least one upper-case letter.
	Password must contain at least one lower-case letter. 
	Password must contain at least one digit. 
	Password must contain at least one special character (excluding §).
	
	<TextInputLayout resource-id="com.plannet.milesandmoreapp:id/new_password">
	<android.widget.ImageView resource-id="com.plannet.milesandmoreapp:id/register_info_image">
	
	<TextInputLayout resource-id="com.plannet.milesandmoreapp:id/confirm_password">

	<TextInputLayout resource-id="com.plannet.milesandmoreapp:id/confirm_password">
	
	<TextInputLayout resource-id="com.plannet.milesandmoreapp:id/email">
	Invalid email.
	
	<android.widget.EditText resource-id="com.plannet.milesandmoreapp:id/temporary_card_number_text">

	? dialog:
		<android.widget.TextView resource-id="com.plannet.milesandmoreapp:id/alertTitle">
		<android.widget.TextView resource-id="android:id/message">
		<android.widget.Button resource-id="android:id/button1">
		Invalid card number. Please try again.
	
	
		
		
		
	<android.widget.TextView resource-id="com.plannet.milesandmoreapp:id/register_terms_conditions">
	<android.widget.ToggleButton resource-id="com.plannet.milesandmoreapp:id/terms_read_switch">
	
	*/
	
	
	public CreateAccountPage3(AndroidDriver<?> driver) {
		super(driver);
	}

	public void validateContent() {
		try{
			findElements( Arrays.asList(
					pageTitleText,
					backArrow,
					usernameText,
					usernameInput,
					passwordVerticalBarText,
					hideShowPassword,
					newPasswordText,
					newPasswordInput,
					registerInfoImage,
					confirmPasswordText,
					confirmPasswordInput,
					emailText,
					emailInput,
					temporaryCardNumberText,
					temporaryCardNumberInput,
					registerTermConditionsText,
					termsRegisterSwitch,
					createAccountButton));
		}
		catch (Exception e){
			throw new TestCaseFailure("Create account page 3 validation failed!" , e);
		}
	}

	
	public void clickCreateAccountButton(){
		findElement(createAccountButton).click();
		ThreadUtils.sleepQuiet(2000);
	}
	
	
	public void goBack() {
		click(backArrow);
	}
	
	
	
	public String getPageTitleText(){
		return getText(pageTitleText);
	}
	
	public String getUsernameText(){
		return getText(usernameText);
	}
	
	public String getUsernameInput(){
		return getText(usernameInput);
	}
	
	public String getPasswordVerticalBarText() {
		return getText(passwordVerticalBarText);
	}
	
	public String getNewPasswordText(){
		return getText(newPasswordText);
	}
	public String getNewPasswordInput(){
		return getText(newPasswordInput);
	}
	
	public String getConfirmPasswordText(){
		return getText(confirmPasswordText);
	}
	public String getConfirmPasswordInput(){
		return getText(confirmPasswordInput);
	}
	
	public String getEmailText(){
		return getText(emailText);
	}
	public String getEmailInput(){
		return getText(emailInput);
	}
	
	public String getTemporaryCardNumberText(){
		return getText(temporaryCardNumberText);
	}
	public String getTemporaryCardNumberInput(){
		return getText(temporaryCardNumberInput);
	}
	
	
	public String getRegisterTermConditionsText(){
		return getText(registerTermConditionsText);
	}
	
	public String getCReateAccountButtonText(){
		return getText(createAccountButton);
	}

	
	
	// elements setters
	public void sendTextToUsernameInput(String text){
		sendText(usernameInput, text);
	}
	public void sendTextToNewPasswordInput(String text){
		sendText(newPasswordInput, text);
	}
	public void sendTextToConfirmPasswordInput(String text){
		sendText(confirmPasswordInput, text);
	}
	public void sendTextToEmailInput(String text){
		sendText(emailInput, text);
	}
	public void sendTextToTemporaryCardNumberInput(String text){
		sendText(temporaryCardNumberInput, text);
	}
	
	public void setTermsRegisterSwitch(boolean state){
		if (state = false){
			if (isChecked(termsRegisterSwitch)){
				click(termsRegisterSwitch);
			}
		}
		else{
			if ( ! isChecked(termsRegisterSwitch)){
				click(termsRegisterSwitch);
			}
		}
	}
	
	
	
	
	public void assertUsernameInput(String text) {
		assertText(usernameInput,
				text,
				"Verify Username adress input");
	}
	public void assertNewPasswordInput(String text) {
		assertText(newPasswordInput,
				text,
				"Verify New Paswword input");
	}
	public void assertConfirmPasswordInput(String text) {
		assertText(confirmPasswordInput,
				text,
				"Verify Confirm Paswword input");
	}
	public void assertEmailInput(String text) {
		assertText(emailInput,
				text,
				"Verify Email input");
	}
	public void assertTemporaryCardNumberInput(String text) {
		assertText(temporaryCardNumberInput,
				text,
				"Verify Temporary Card Number Input");
	}
	
	public void assertTermsRegisterSwitch(boolean state) {
		boolean currentState = findElement(createAccountButton).isEnabled();
		Assert.assertTrue(Boolean.toString(currentState).equals(Boolean.toString(state)), 
				"Check Terms Register Switch", 
				""+state, 
				""+currentState);
	}

	public void clickShowPassword() {
		click(hideShowPassword);
		
	}

	public void clickTermsRegisterSwitch() {
		click(termsRegisterSwitch);
		
	}

	
	@Override
	public List<String> getAllErrors(){
		return getErrors(Arrays.asList(
				usernameText,
				newPasswordText,
				confirmPasswordText,
				emailText,
				temporaryCardNumberText));
	}

	public void showPassword() {
		String showText = findElement(hideShowPassword).getText();
		if ( showText.equals("Show")){
			clickShowPassword();
		}
		
	}
	
/*	
 * Errors
	Username must not be empty.
	Username may only consist of letters or numbers.
	Username must be between 4 and 16 characters.
	
	
	Password must be between 8 and 32 characters.Password must contain at least one upper-case letter.Password must contain at least one lower-case letter. Password must contain at least one digit. Password must contain at least one special character (excluding §).
	Password must be between 8 and 32 characters.Password must contain at least one upper-case letter.Password must contain at least one digit. Password must contain at least one special character (excluding §).
	
	The passwords you entered in the "Password" and "Confirm Password" fields do not match.
	
	Invalid email.	
	
	Invalid card number. Please try again.
		
	*/
}
