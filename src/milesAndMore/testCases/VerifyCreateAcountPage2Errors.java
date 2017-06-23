package milesAndMore.testCases;


import java.util.ArrayList;
import java.util.List;

import base.Assert;
import base.Driver;
import base.TestCase;
import milesAndMore.pages.CreateAccountPage2;


public class VerifyCreateAcountPage2Errors extends TestCase{
	private CreateAccountPage2 createAcountPage2;
	private String expectedErrorsCounter;
	private List<String> expectedErrors;

	@Override
	public void run() {
		expectedErrorsCounter = nullToEmptyString(evalAttribute("expectedErrorsCounter"));
		String[] errorsArray = nullToEmptyString(evalAttribute("expectedErrors")).split("\\|");
		expectedErrors = new ArrayList<>();
		
		// add only not empty strings
		for (int i=0; i<errorsArray.length; i++){
			if (! errorsArray[i].isEmpty()){
				expectedErrors.add(errorsArray[i].trim());
			}
		}
			
		createAcountPage2 = new CreateAccountPage2(Driver.driver);
		
		List<String> currentErrors = createAcountPage2.getAllErrors();
		String currentErrorsCounter =  Integer.toString(currentErrors.size());
		
		
		if(! expectedErrorsCounter.isEmpty()){
			Assert.assertTrue( currentErrorsCounter.equals(expectedErrorsCounter),
					"Verify error counter:"
					+ " expected= " + expectedErrorsCounter 
					+ "; found=" + currentErrorsCounter );
		}
		
		int size = expectedErrors.size();
		
		logger.log("Expected errors: " + expectedErrors.toString());
		logger.log("Current errors: " + currentErrors.toString());
		
		// Assert that current errors contain expected errors
		if(  size > 0 ){
			for( String error : expectedErrors ){
				Assert.assertTrue( currentErrors.contains(error),
					"Verify Error \"" + currentErrors + "\" is displayed" );
			}
		}
	}

	
	@Override
	public String getTestCaseScenario() {
		return "Verify errors counter and error messages on the second \"Create account\" page.";
	}

}
