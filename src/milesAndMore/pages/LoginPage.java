package milesAndMore.pages;

import java.util.Arrays;

import org.openqa.selenium.By;

import base.failures.TestCaseFailure;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage extends BasePage{


	public LoginPage(AndroidDriver<?> driver){
		super(driver);
	}


	//locators
	private By logo = By.id(prefixed("logo"));
	private By onBoardImage = By.id(prefixed("onboard_image"));
	private By onBoardTitleText = By.id(prefixed("onboard_title_text"));
	private By onBoardText = By.id(prefixed("onboard_text"));


	// Buttons
	private By createAccountButton = By.id(prefixed("button_register"));
	private By signInButton = By.id(prefixed("button_login"));


	// Actions
	public void validateContent(){
		try{
			findElements( Arrays.asList(
					logo,
					onBoardImage,
					onBoardTitleText,
					onBoardText,
					createAccountButton,
					signInButton));
		}
		catch (Exception e){
			throw new TestCaseFailure("Login page validation failed!" , e);
		}
	}


	public String getOnBoardTitleText(){
		return getTrimmedText(onBoardTitleText);
	}


	public String getOnBoardText(){
		return getTrimmedText(onBoardText);
	}


	public String getCreateAccountButtonText(){
		return getTrimmedText(createAccountButton);
	}


	public String getSignInButtonText(){
		return getTrimmedText(signInButton);
	}


	public void clickCreateAccount(){
		findElement(createAccountButton).click();
	}


	public void clickSignIn(){
		findElement(signInButton).click();
	}
}
