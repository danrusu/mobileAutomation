package milesAndMore.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import base.Driver;
import base.Page;
import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.android.AndroidDriver;

public abstract class BasePage extends Page{

	// error locator is generic
	private By textInputError =  By.id(prefixed("textinput_error"));
	// loading progress locator
	private By loadingProgress = By.id("android:id/progress");

	private final String milesPrefix = "com.plannet.milesandmoreapp:id/";



	public BasePage(AndroidDriver<?> driver) {
		super(driver);
	}


	public String prefixed(String text){
		return milesPrefix + text; 
	}



	public String maxChars(String text, int max){
		if (text.length()<=max){
			return text;
		}
		else{
			return text.substring(0, max);
		}
	}




	protected String getError(By by){
		String error = new String();
		try{
			logger.log("Find error in " + by + "->" + textInputError);
			Driver.resetImplicitWait();
			error =  findElement(by).findElement(textInputError).getText().trim();

		}
		catch(Exception e){
			logger.log("Error not found in " + by + "->" + textInputError ); 
		}

		Driver.setDefaultImplicitWait();
		return error;
	}
	
	
	
	protected List<String> getErrors(List<By> elementsThatCanHaveErrors){
		List<String> errorsList = new ArrayList<>();
		for (By by: elementsThatCanHaveErrors){
			if (! getError(by).isEmpty()){
				errorsList.add(getError(by));
			}
		}
		return errorsList;
	}

	

	// Other pages will override this input text errors can appear on the page.
	// This is not abstract to provide a template.
	public List<String> getAllErrors(){
		return getErrors(Arrays.asList());
	}


	
	public String getErrorsNumber(){
		return Integer.toString(getAllErrors().size());
	}


	
	public boolean isErrorState(){
		return getErrorsNumber().equals("0");
	}
	
	
	
	public void waitForLoading(long timeoutInSeconds){
		boolean loaded = false;
		logger.log("Wait for loading to finish for " + timeoutInSeconds + " seconds.");
		Driver.resetImplicitWait();
		for (int i=0; i<=timeoutInSeconds; i++){
			try{
				findElement(loadingProgress);
			}
			catch(Exception e){
				loaded = true;
				logger.log("Loading finished after " + i + " seconds.");
				break;
			}
			ThreadUtils.sleep(1000);
		}
		Driver.setDefaultImplicitWait();
		if (! loaded){
			throw new TestCaseFailure("Loading didn't finish after " + timeoutInSeconds + " seconds.");
		}

	}
}



