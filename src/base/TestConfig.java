package base;

import java.util.Map;
import java.util.Optional;

public class TestConfig {

	private Map<String, String> testAttributes;

	private Map<Integer, Map<String, String>> testCases;


	public String getName(){
		return testAttributes.get("name");
	}

	public String getBrowser(){
		return testAttributes.get("browser");
	}

	public TestConfig(Map<String, String> testAttributes,
			Map<Integer, Map<String, String>> testCases) {
		super();
		this.testAttributes = testAttributes;
		this.testCases = testCases;
	}


	public Map<String, String> getTestAttributes() {
		return testAttributes;
	}

	public void setTestAtributes(Map<String, String> testAttributes) {
		this.testAttributes = testAttributes;
	}

	public Map<Integer, Map<String, String>> getTestCases() {
		return testCases;
	}

	public void setTestCases(Map<Integer, Map<String, String>> testCases) {
		this.testCases = testCases;
	}



	/**
	 * It returns the passed string if it it is not null
	 * or an empty string if it is null. 
	 * 
	 * @param string - characters string or null 
	 * @return - non null string
	 */
	public static String nullToEmptyString(String s){
		return Optional.ofNullable(s)
				.orElse(new String());
	}

	public static boolean stringToBoolean(String s) {
		return nullToEmptyString(s)
				.equalsIgnoreCase(Boolean.toString(true));
	}


    // save screenshots defaults to false
	public static boolean getSaveScreenShots(Map<Integer, TestConfig> tests, int testId){
		return stringToBoolean(nullToEmptyString(tests
				.get(testId)
				.getTestAttributes()
				.get("saveScreenShots")));
	}

	// close defaults to true
	public static boolean getCloseBrowserAtEnd(Map<Integer, TestConfig> tests, int testId) {
		String close = tests.get(testId)
				.getTestAttributes()
				.get("closeBrowserAtEnd");
		
		return nullToEmptyString(close).isEmpty() ? true : stringToBoolean(close);
	}

	// test case retries defaults to 1
	public static int getTestCaseRetries(
			Map<Integer, Map<String, String>> testCases, 
			Integer testCaseId) {
		try{
			return Integer.parseInt(testCases.get(testCaseId)
					.get("retries"));
		}
		catch(Exception e){
			return 1;
		}
	}
	
	// test case stopAtFailure defaults to true
	public static boolean getTestCaseStopAtFailure(
			Map<Integer, 
			Map<String, String>> testCases, Integer testCaseId) {
		
		String stopAtFailure = nullToEmptyString(
				testCases.get(testCaseId)
				.get("stopAtFailure"));
		
		return stopAtFailure.equalsIgnoreCase("false") ?
			false : true;
	}
	
}
