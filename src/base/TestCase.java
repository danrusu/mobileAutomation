package base;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.failures.TestCaseFailure;
import base.tools.DynamicCheck;
import base.xml.XmlDynamicData;

/**
 * Generic web page test.
 * 
 * @author Dan Rusu
 *
 */
abstract public class TestCase implements Runnable, TestCaseScenario{	
	public Logger logger;
	public WebDriver driver = null;
	public static WebDriver currentDriver = null;
	private boolean internalTest;


	private String startWindowHandle;


	private DynamicCheck dc;

	private Map<String, String> testCaseAttributes;



	/**
	 * Constructor 
	 */
	public TestCase() {		
		this.internalTest = false;
		logger = Logger.getLogger();
		dc = new DynamicCheck(logger);
		this.driver = Driver.driver;
	}


	/**
	 * Constructor 
	 */
	public TestCase(WebDriver driver) {
		this.internalTest = true;
		logger = Logger.getLogger();
		this.driver = driver;
		dc = new DynamicCheck(logger);
	}

	/**
	 * Close all opened browser windows; kill driver.   
	 * 
	 */
	public void quit(){
		try {
			logger.log("Close driver");
			if (driver != null){
				driver.quit();
				driver = null;
			}
		}
		catch (Exception e){
			logger.log(e.getMessage());
		}
	}



	/**
	 * Get the test attributes.
	 * 
	 * @return - a map of test's attributes
	 */
	public Map<String, String> getTestCaseAttributes() {
		return testCaseAttributes;
	}





	// Get an instance for dynamic waits 
	public DynamicCheck getDynamicCheck() {
		return dc;
	}


	public boolean checkPageTitle(String title, 
			long totalMilisTimeout,
			long stepTimeout){

		return dc.waitUntilFunctionReturnsExpectedValue(
				totalMilisTimeout, 
				stepTimeout, 
				object -> ((WebDriver)object).getTitle(), 
				driver, 
				title);
	}



	public boolean checkPageUrl(String url, 
			long totalMilisTimeout,
			long stepTimeout){

		return dc.waitUntilFunctionReturnsExpectedValue(
				totalMilisTimeout, 
				stepTimeout, 
				object -> ((WebDriver)object).getCurrentUrl(), 
				driver, 
				url);
	}


	public boolean isAlertPresent(){
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, 0 /*timeout in seconds*/);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}


	/**
	 * Set the test's attributes.
	 * 
	 * @param testAttributes - test's attributes map 
	 */
	public void setTestCaseAttributes(Map<String, String> testAttributes) {
		this.testCaseAttributes = testAttributes;
	}


	public void addAttribute(String name, String value){
		this.testCaseAttributes.put(name, value);
	}

	public String getStartWindowHandle() {
		return this.startWindowHandle;
	}


	public boolean isInternalTest() {
		return internalTest;
	}



	public void runInternalTestCase(TestCase test,
			Map<String, String> testAttributes){

		test.setTestCaseAttributes(testAttributes);
		test.run();
	}











	public void setStartWindowHandle(String startWindowHandle) {
		this.startWindowHandle = startWindowHandle;
	}


	public void dynamicEval(Map<String, String> testCaseAttributes) {
		//Map<String, String> attributes = testCaseAttributes;

		// eval "save" first
		String save = evalAttribute("save");
		saveAll(save);



		testCaseAttributes.keySet().forEach(

				key -> {
					if (! key.equals("save")){
						final String value = TestConfig.nullToEmptyString(testCaseAttributes.get(key));
						final String newValue = XmlDynamicData.getDynamicValue(
								XmlDynamicData.getSavedData(), value);

						if ( ! value.equals(newValue) ){
							testCaseAttributes.replace(
									key, newValue);
							logger.log("Attribute replaced: " + key + "=" + newValue);
						}
					}
				}
				);

	}



	/**
	 * Save String variable into memory so that 
	 * they can be used for following test cases. 
	 * 
	 * @param saveString
	 */
	public void saveAll(String saveString) {
		if (! saveString.isEmpty()){

			List<String> entries = Arrays.asList(saveString.split(";"));
			entries.forEach(
					entry -> {
						final String savedKey =  entry.split("=")[0];
						final String savedValue =  entry.split("=")[1];
						XmlDynamicData.saveData(savedKey, 
								XmlDynamicData.getDynamicValue(XmlDynamicData.getSavedData(),savedValue));

					});

			String saved = XmlDynamicData.getSavedData().toString(); 
			testCaseAttributes.put("save", saved);
			logger.log("Saved data: save=" + saved);
		}
	}

	

	/**
	 * Save test case results (field values) into memory 
	 * at the end of a test case so that they can be reported 
	 * or used by the following test cases.
	 * 
	 * Fields that can be saved will be listed in the 
	 * getTestScenario method, Results section.
	 * 
	 * Use the "saveResult" attribute for this.
	 * 
	 */
	public void saveResults(){
		String saveString = evalAttribute("saveResult");
		
		if (!saveString.isEmpty()){
			List<String> entries = Arrays.asList(saveString.split(";"));
			entries.forEach(
					entry -> {
						final String savedKey =  entry.split("=")[0];
						final String fieldName =  entry.split("=")[1];

						try {
							Field field;
							field = this.getClass().getDeclaredField(fieldName);
							field.setAccessible(true);					
							Object objectValue = field.get(this);
							
							if (objectValue!=null){
								XmlDynamicData.saveData(savedKey, objectValue.toString());
							}
							else {
								throw new TestCaseFailure("Saving results failed! Field " 
										+ this.getClass().getSimpleName() + "." + fieldName +" is null!");
							}

							 
						}catch (NoSuchFieldException
								|SecurityException
								|IllegalArgumentException
								|IllegalAccessException e) {

							throw new TestCaseFailure("Saving results failed!", e);
						}
					});
			String saved = XmlDynamicData.getSavedData().toString(); 
			testCaseAttributes.put("save", saved);
			logger.log("Saved data: save=" + saved);
		}
	}

	
	public void addFailure(Throwable th) {
		logger.logLines( TestCaseFailure.stackToString(th) );
		//testCase.addAttribute("failure", th.getMessage());
		if (th instanceof TestCaseFailure){
			addAttribute("failure", th.toString() + th.getCause());
		}
		else{
			addAttribute("failure", 
					th.toString());
		}
	}
	
	
	
	public String nullToEmptyString(String s){
		return TestConfig.nullToEmptyString(s);
	}


	public String evalAttribute(String attribute){
		//return nullToEmptyString(getTestCaseAttributes().get(attribute));
		return getTestCaseAttributes().get(attribute);
	}
	
	public boolean isAvailable(Object attributeField){
		if ( attributeField == null ){
			return false;
		}
		else {
			return true;
		}
		
	}

	
	public Boolean evalBooleanAttribute(String attribute){
		return nullToEmptyString(getTestCaseAttributes().get(attribute)).equalsIgnoreCase("true");
	}


	public void assertStaticText(Object staticTextField, 
			String currentText,
			String message){
		
		if ( isAvailable(staticTextField) ){
			Assert.assertTrue(currentText.equals(staticTextField.toString()),
					message,
					staticTextField.toString(),
					currentText);
		}
	}

	public void goBack() {
		driver.navigate().back();
	}
}
