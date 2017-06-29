package milesAndMore.pages;

import java.util.Arrays;

import org.openqa.selenium.By;

import base.failures.TestCaseFailure;
import io.appium.java_client.android.AndroidDriver;

public class NotARobotPage extends BasePage {
	
	private By notARobotCheckbox = By.id("recaptcha-anchor");
	private By cancelButton = By.id(prefixed("button_cancel"));

	public NotARobotPage(AndroidDriver<?> driver) {
		super(driver);
	}

	
	public void clickCancelButton(){
		click(cancelButton);
	}
	
	
	public void validateContent(){
		try{
			findElements( Arrays.asList(
					notARobotCheckbox,
					cancelButton));
		}
		catch (Exception e){
			throw new TestCaseFailure("Not a robot page validation failed!" , e);
		}
	}
}
