package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.android.AndroidDriver;

public abstract class BasePage implements PageValidation{
	public Logger logger = Logger.getLogger();
	public AndroidDriver<?> driver;

	// error locator is generic
	private By textInputError =  By.id(prefixed("textinput_error"));
	// loading progress locator
	private By loadingProgress = By.id("android:id/progress");

	private final String milesPrefix = "com.plannet.milesandmoreapp:id/";


	public BasePage(AndroidDriver<?> driver) {
		this.driver = driver;
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


	public WebElement findElement(By by){
		logger.log("Find " + by);
		return driver.findElement(by);

	}


	public void findElements(List<By> locators){
		for (By locator : locators){
			findElement(locator);
		}
	}

	

	public void click(By by){
		logger.log("Click " + by);
		findElement(by).click();
	}

	
	
	// TO DO - issue here; need better implementation
	public void sendText(By inputBy, String text){
		logger.log("Clear + Send text \"" + text + "\" to " + inputBy );
		WebElement input = findElement(inputBy);
		
		for (int i=1; i<=3; i++){
			input.clear();
			ThreadUtils.sleepQuiet(500);
			if(input.getText().isEmpty()){
				break;
			}
		}
				
		input.sendKeys(text);
		ThreadUtils.sleepQuiet(500);		
	}
	
	

	public boolean isChecked(By by){
		return  findElement(by).getAttribute("checked").equalsIgnoreCase("true");
	}


	
	public void assertText(By by, String expectedtext, String detailsMessage){
		String currentText = getText(by);
		Assert.assertTrue(
				currentText.equals(expectedtext),
				detailsMessage,
				expectedtext,
				currentText);
	}


	public String getText(By by) {
		logger.log("Get text from " + by);
		return findElement(by).getText().trim();
	}


	public void assertIsChecked(By by, boolean expectedState, String detailsMessage){
		boolean isChecked = isChecked(by);
		Assert.assertTrue(
				isChecked==expectedState,
				detailsMessage,
				""+expectedState,
				""+isChecked);
	}



	// listItemLocator - is a locator for multiple list items 
	public void clickListElement(By listItemLocator, String elementText) {
		boolean found = false;
		for(int i=0;i<100;i++)
		{

			@SuppressWarnings("unchecked")
			List<WebElement> elements = (List<WebElement>) driver
			.findElements(listItemLocator);

			for (WebElement elem : elements){
				//logger.log(elem.getText());
				if (elem.getText().equals(elementText)){
					found = true;
					logger.log("Element with locator " + listItemLocator 
							+ " and text \"" + elementText + "\" found!");

					elem.click();
					break;
				}
			}
			if (found){
				break;
			}
			Driver.verticalSwipeDown();
			}
		if (! found){
			throw new TestCaseFailure("No element with text " + elementText 
					+ "was found in the list - " + listItemLocator);
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


	public void closeKeyboard() {
		logger.log("Navigate back");
		driver.navigate().back();
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
