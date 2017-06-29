package base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.android.AndroidDriver;

public abstract class Page implements PageValidation{
	public Logger logger = Logger.getLogger();
	public AndroidDriver<?> driver;

	

	public Page(AndroidDriver<?> driver) {
		this.driver = driver;
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



	public void closeKeyboard() {
		logger.log("Navigate back");
		driver.navigate().back();
	}

}

