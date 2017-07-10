package milesAndMore.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import base.Driver;
import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;

public class CreateAccountPage2 extends BasePage{

	// Locators
	private By backArrow = By.xpath("//*[@resource-id='com.plannet.milesandmoreapp:id/register_info_image']"
			+ "/preceding-sibling::android.widget.ImageView");
	private By registerInfoImage = By.id(prefixed("register_info_image"));

	private By birthDate = By.id(prefixed("birth_date"));
	private By birthDateInput = By.id(prefixed("birth_date_text"));

	private By headerYear = By.id("android:id/date_picker_header_year");
	private By yearListItems = By.id("android:id/text1");
	private By monthPrev =  By.id("android:id/prev");
	private By monthNext =  By.id("android:id/next");


	private By streetAdress = By.id(prefixed("street_adress"));
	private By streetAdressInput = By.id(prefixed("street_adress_text"));

	private By zipCode = By.id(prefixed("zip_code"));
	private By zipCodeInput = By.id(prefixed("zip_code_text"));

	private By city = By.id(prefixed("city"));
	private By cityInput= By.id(prefixed("city_text"));

	private By country = By.id(prefixed("country"));
	private By countryInput = By.id(prefixed("country_text"));
	private By countriesListItems = By.xpath("//*[@resource-id='com.plannet.milesandmoreapp:id/country_cell_row_id']");


	private By firstPageIndicator = By.id(prefixed("first_page_indicator"));
	private By secondPageIndicator = By.id(prefixed("second_page_indicator"));
	private By thirdPageIndicator = By.id(prefixed("third_page_indicator"));


	private By buttonOK = By.id("android:id/button1");
	private By buttonCancel = By.id("android:id/button2");

	private By nextButton = By.id(prefixed("button_register_next"));


	public CreateAccountPage2(AndroidDriver<?> driver){
		super(driver);
	}


	// Actions
	public void clickOK(){
		click(buttonOK);
	}


	public void clickCancel(){
		click(buttonCancel);
	}


	public void sendTextToBirthDateInput(String text){
		sendText(birthDateInput, text);
	}
	public void sendTextToStreetAdressInput(String text){
		sendText(streetAdress, text);
	}

	public void sendTextToZipCodeInput(String text){
		sendText(zipCode, text);
	}

	public void sendTextToCityInput(String text){
		sendText(city, text);
	}

	public void sendTextToCountryInput(String text){
		sendText(country, text);
	}


	public void clickNextButton(){
		click(nextButton);
		ThreadUtils.sleepQuiet(2000);
	}


	public void validateContent() {
		try{
			findElements( Arrays.asList(
					backArrow,
					registerInfoImage,
					birthDate,
					birthDateInput,
					streetAdress,
					streetAdressInput,
					zipCode,
					zipCodeInput,
					city,
					cityInput,
					country,
					firstPageIndicator,
					secondPageIndicator,
					thirdPageIndicator,
					nextButton));
		}
		catch (Exception e){
			throw new TestCaseFailure("Create account page 2 validation failed!" , e);
		}
		
	}






	public void assertTextNextButton(String text) {
		assertText(nextButton,
				text,
				"Verify Next button text");
	}



	public void assertTextBirthDateInput(String text) {
		assertText(birthDate,
				text,
				"Verify Birth Date text");
	}




	public void assertTextBirthDayInput(String text) {
		// format: MonthStr, dayNr year
		assertText(birthDateInput,
				text,
				"Verify Birth Date input text");
	}


	public void assertTextStreetAdress(String text) {
		assertText(streetAdress,
				text,
				"Verify Street adress text");
	}
	public void assertTextStreetAdressInput(String text) {
		assertText(streetAdressInput,
				text,
				"Verify Street adress input text");
	}


	public void assertTextZipCode(String text) {
		assertText(zipCode,
				text,
				"Verify Zip Code text");
	}
	public void assertTextZipCodeInput(String text) {
		assertText(zipCodeInput,
				text,
				"Verify Zip Code input text");
	}


	public void assertTextCity(String text) {
		assertText(city,
				text,
				"Verify City text");
	}
	public void assertTextCityInput(String text) {
		assertText(cityInput,
				text,
				"Verify City input text");
	}


	public void assertTextCountry(String text) {
		assertText(country,
				text,
				"Verify Country text");
	}
	public void assertTextCountryInput(String text) {
		assertText(countryInput,
				text,
				"Verify Country input text");
	}


	public void setCountryInput(String country) {
		click(countryInput);

		clickListElement(countriesListItems, country);
	}


	public void setBirthDateInput(String day, String month, String year) {
		driver.findElement(birthDateInput).click();
		

		// set year
		setYear(year);

		// set month and day
		String date = day + " " + month + " " + year;
		Driver.resetImplicitWait();
		boolean dateFound = false;
		for (int i=0; i<12; i++){
			try{
				click(MobileBy.AccessibilityId(date));
				dateFound = true;
				logger.log("Date \"" + date + "\" was found and set");
				break;
			}
			catch(Exception e){
				logger.log("Date \"" + date + "\" was not found in this month.");
			}

			clickNextMonth();
		}
		Driver.setDefaultImplicitWait();
		if (! dateFound){
			throw new TestCaseFailure("Date \"" + date + "\" was not found!");
		}

		clickOK();
	}




	public void clickNextMonth() {
		click(monthNext);
	}

	public void clickPrevMonth() {
		click(monthPrev);
	}


	public void setYear(String year){
		click(headerYear);
		clickListElement(yearListItems, year);
	}


	@Override
	public List<String> getAllErrors(){
		return getErrors(Arrays.asList(
				birthDate, 
				streetAdress, 
				zipCode, 
				city, 
				country));
	}


	public void goBack() {
		click(backArrow);
	}

	
	// Elements text getters
	public String getBirthDateText(){
		return getTrimmedText(birthDate);
	}
	public String getBirthDateInput(){
		return getTrimmedText(birthDateInput);
	}
	
	public String getStreetAddressText(){
		return getTrimmedText(streetAdress);
	}
	public String getStreetAddressInput(){
		return getTrimmedText(streetAdressInput);
	}
	
	public String getZipCodeText(){
		return getTrimmedText(zipCode);
	}
	public String getZipCodeInput(){
		return getTrimmedText(zipCodeInput);
	}

	public String getCityText(){
		return getTrimmedText(city);
	}
	public String getCityInput(){
		return getTrimmedText(cityInput);
	}
	
	public String getCountryText(){
		return getTrimmedText(country);
	}
	public String getCountryInput(){
		return getTrimmedText(countryInput);
	}
	
	public String getNextButtonText(){
		return getTrimmedText(nextButton);
	}
	
}
