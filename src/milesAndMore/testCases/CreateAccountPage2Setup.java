package milesAndMore.testCases;


import java.util.Map;
import java.util.TreeMap;

import base.Driver;
import base.TestCase;
import base.failures.TestCaseFailure;
import milesAndMore.pages.CreateAccountPage2;


public class CreateAccountPage2Setup extends TestCase{
	private CreateAccountPage2 createAccountPage2;
	// static data
	private String birthDateText;
	private String streetAddressText;
	private String zipCodeText;
	private String cityText;
	private String countryText;
	private String nextButtonText;


	// dynamic data
	private String birthDate;
	private Map<String, String> birthDateMap;
	private String streetAddress;
	private String zipCode;
	private String city;
	private String country;

	@Override
	public void run() {
		birthDate = evalAttribute("birthDate");
		if( birthDate != null ){
			String[] birthDateArray = nullToEmptyString(birthDate).split("/");
			birthDateMap = new TreeMap<>();
			if (birthDateArray.length == 3){
				birthDateMap.put("day", birthDateArray[0]);
				birthDateMap.put("month", birthDateArray[1]);
				birthDateMap.put("year", birthDateArray[2]);
			}
			else {
				throw new TestCaseFailure("Wrong Birth date format: " + birthDate
						+ "! e.g. 09/December/1982");
			}
		}

		streetAddress = evalAttribute("streetAddress");
		zipCode = evalAttribute("zipCode");
		city = evalAttribute("city");
		country = evalAttribute("country");


		createAccountPage2 = new CreateAccountPage2(Driver.driver);	
		validateStaticText();


		if ( isAvailable(birthDate) ){
			createAccountPage2.setBirthDateInput(birthDateMap.get("day"),
					birthDateMap.get("month"),
					birthDateMap.get("year"));

			createAccountPage2.assertTextBirthDayInput(formatDate(birthDateMap.get("day"),
					birthDateMap.get("month"),
					birthDateMap.get("year")));
		}


		if ( isAvailable(streetAddress) ){
			createAccountPage2.sendTextToStreetAdressInput(streetAddress);
			createAccountPage2.assertTextStreetAdressInput(streetAddress);
			createAccountPage2.closeKeyboard();
		}


		if ( isAvailable(zipCode) ){
			createAccountPage2.sendTextToZipCodeInput(zipCode);
			createAccountPage2.assertTextZipCodeInput(zipCode);
			createAccountPage2.closeKeyboard();
		}


		if ( isAvailable(city) ){
			createAccountPage2.sendTextToCityInput(city);
			createAccountPage2.assertTextCityInput(city);
			createAccountPage2.closeKeyboard();
		}

		if ( isAvailable(country) ){
			createAccountPage2.setCountryInput(country);
			createAccountPage2.assertTextCountryInput(country);
		}
	}


	// 04 December 1983 -> Dec, 4 1983
	private String formatDate(String day, String month, String year) {

		return month.substring(0, 3) + " " 
				+ Integer.parseInt(day) + ", "
				+ year;
	}



	public void validateStaticText() {
		// static data initialization
		birthDateText = evalAttribute("birthDateText");
		streetAddressText = evalAttribute("streetAddressText");
		zipCodeText = evalAttribute("zipCodeText");
		cityText = evalAttribute("cityText");
		country = evalAttribute("country");
		nextButtonText = evalAttribute("nextButtonText");

		// Asserts
		assertStaticText(birthDateText,
				createAccountPage2.getBirthDateText(),
				"Verify Birth date text");

		assertStaticText(streetAddressText,
				createAccountPage2.getStreetAddressText(),
				"Verify Street Adress text text");
		
		assertStaticText(zipCodeText,
				createAccountPage2.getZipCodeText(),
				"Verify Zip Code text");
		
		assertStaticText(cityText,
				createAccountPage2.getCityText(),
				"Verify City text");
		
		assertStaticText(countryText,
				createAccountPage2.getCountryText(),
				"Verify Country text");
		
		assertStaticText(nextButtonText,
				createAccountPage2.getNextButtonText(),
				"Verify Next button text");


	}

	
	
	@Override
	public String getTestCaseScenario() {
		return "Set Input on Create account page 2.";
	}
}
