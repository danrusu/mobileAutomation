package base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import base.failures.TestCaseFailure;
import base.results.ResultInfo;
import base.results.Results;
import base.results.SuiteResult;
import base.results.TestCaseResult;
import base.results.TestResult;
import base.tools.ClassUtils;
import base.tools.TimeUtils;
import base.xml.XmlResult.Result;
import base.xml.XmlTestConfig;


/**
 * Class for running tests from a configuration XML file.
 * 
 * @author Dan.Rusu
 */
public class TestRunner {	
	private Logger logger;
	private Path logDirPath;

	private SuiteResult suiteResult;
	private Path resultFilePath;
	private ResultInfo.ResultFileType resultFileType;


	String hostname;

	//private static String jenkinsJobName;
	//private static String jenkinsBuildNr;



	// ( testId , (attributeName, attributeValue) )
	// e.g. 
	// ( 1, ("name", "MyVismaLogin"), ("browser", "firefox"))
	private Map<Integer, TestConfig> tests;
	private List<TestResult> testResultInfo;
	private Results results;


	/**
	 * Test's types enumeration.
	 * 
	 * Tests must be in a package with the 
	 * name contained by this enumeration.
	 *
	 */
	public enum TestType{
		eFLow,
		pmt;
	}

	/**
	 * Constructor - used for non Jenkins runs
	 * 
	 * @param xmlTestFile
	 */
	public TestRunner(String xmlTestFile){
		this(xmlTestFile, "", "");
	}


	/**
	 * Generic Constructor - used also for Jenkins runs
	 * 
	 * @param xmlTestFile
	 */
	public TestRunner(String xmlTestFile, String jenkinsJobName, String jenkinsBuildNr){
		logger = Logger.getLogger();
		logDirPath = logger.getLogDirPath();
		suiteResult = new SuiteResult();
		results = new Results();

		logger = Logger.getLogger();		
		logger.log("Starting TestRunner(\"" + xmlTestFile + "\""
				+ ", \"" + jenkinsBuildNr + "\")");


		// read test configuration from test XML file
		XmlTestConfig testXml = new XmlTestConfig();
		tests = testXml.readTestConfig(xmlTestFile);

		suiteResult.setTotalTests(tests.size());

		// default result file type
		resultFileType = XmlTestConfig.getSuiteResultFileType();
		resultFilePath = Paths.get(
				logDirPath.toString() 
				+ "/result"
				+ "." + resultFileType.name());


		testResultInfo =  new ArrayList<>();

		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.logLines("Could not get hostname: " + e);
		}
	}




	/**
	 * Main method for running tests.
	 * It runs all tests from the tests' map. 
	 */
	public void runTests() {
		
		boolean isTestPass = true;
		TestResult currentTestResult;

		Instant startSuiteTime = Instant.now();
		logger.log("Starting Suite: " + XmlTestConfig.getSuiteName());



		// run all tests loaded from XML
		for ( Integer testId : tests.keySet() ){
			Instant startTestTime = Instant.now();
			int testRetries = Integer.parseInt(
					Optional.ofNullable(
							tests.get(testId)
							.getTestAttributes()
							.get("retries"))
					.orElse("1"));

			logger.logHeader("Running " 
					+ "test_" + testId + "(" + testRetries + " attempts): " 
					+ tests.get(testId).getTestAttributes());


			// default: close the browser at each test end
			boolean closeBrowserAtEnd = TestConfig.getCloseBrowserAtEnd(tests, testId);


			currentTestResult = new TestResult(Integer.toString(testId),
					tests.get(testId).getTestAttributes());

			if ( Driver.driver == null ){
				// if driver start fails, try starting it for 3 times
				for (int i=0; i<3; i++){
					try {
						logger.log("Start driver, attempt " + (i+1));
						Driver.driverStart(
								tests.get(testId).getTestAttributes().get("appiumServerUrl"),
								tests.get(testId).getTestAttributes().get("platformName"),
								tests.get(testId).getTestAttributes().get("deviceName"),
								tests.get(testId).getTestAttributes().get("app"));
								
								
						break;
					}catch(Exception e){
						logger.logLines("Failed to launch the browser!\n" + e.getMessage());
						tests.get(testId).getTestAttributes().put("failure", "Cannot launch browser: " + e.getMessage());
					}
				}
			}


			// check if we have a valid driver
			if ( Driver.driver == null ){
				logger.log("test_" + testId + " failed! - null Driver");
				suiteResult.incrementFailedTests();
				results.addTestResult(testResultInfo, 
						currentTestResult, 
						Result.TestFail, 
						startTestTime);

				// stop current test if no driver and continue with the next test
				continue;
			}
			else {

				// run all test cases within the test; retry if needed
				Map<Integer, Map<String, String>> testCases = tests.get(testId).getTestCases();

				for (int i=1; i<=testRetries; i++){
					boolean retry = testRetries - i > 0;  
					logger.log("Test attempt " + i + ":");
					isTestPass = runTestCases(currentTestResult, testCases);


					if (isTestPass){
						logger.log("test_" + testId + " succeeded!");
						suiteResult.incrementSucceededTests();
						results.addTestResult(testResultInfo, 
								currentTestResult, 
								Result.TestPass, 
								startTestTime);
						break;
					}
					else {
						if(!retry){
							logger.log("test_" + testId + " failed!");
							suiteResult.incrementFailedTests();
							results.addTestResult(testResultInfo, 
									currentTestResult, 
									Result.TestFail, 
									startTestTime);
						}
					}
				}// end retries

				// close driver windows after each test
				// strange exceptions sometimes even if browser is closed 
				if ( (Driver.driver != null) && closeBrowserAtEnd ){
					try{

						logger.log("Test finished; quit current driver."); 
						Driver.driver.quit();
						Driver.driver = null;
					}
					catch(Throwable th){
						logger.logLines("Error while quitting driver.\n" 
								+ TestCaseFailure.stackToString(th));
					}
				}
			}
		}// end tests loop


		// Save results locally (results.txt/html) 
		suiteResult.setElapsedSuiteTime(TimeUtils.getElapsedTime(startSuiteTime));
		//String testStatus = (suiteResult.isSucceeded()) ? "Succeeded" : "Failed";

		results.log(testResultInfo, suiteResult);
			
		saveLocalResult(resultFilePath.toString());

		logger.log("Test Suite Finished!");
		logger.log("Log: " + logger.getLogDirPath().toString() + "\\log.txt");
		logger.log("Report: " + resultFilePath.toString());
	}



	/**
	 * Save test result details in logFolder/results.(fileType)
	 * 
	 */
	private void saveLocalResult(String resultFileName) {
		logger.log("Save result to: " + resultFileName);
		String content="error: no result content";

		switch (resultFileType.name()){
		case "txt":
			content = results.getDetailedResults(
					testResultInfo, 
					suiteResult);
			break;
		case "html":
			content = results.getDetailedResultsHtml(
					testResultInfo, 
					suiteResult);
			break;

		}

		try {
			Files.write(resultFilePath, 
					content.getBytes(), 
					StandardOpenOption.CREATE_NEW
					);

		} catch (Exception e) {
			logger.logLines("Could not write to result file! \n" + e);
		}
	}



	/**
	 * Runs a WebPageTestCase.
	 * 
	 * @param testCase	
	 * @return true is test pass, false otherwise
	 */
	private boolean runTestCase(TestCase testCase){
		boolean successfulRun = true; 
		try {
			// remove previous failure it test retries are set
			// previous failures info will be available only in the log 
			testCase.getTestCaseAttributes().remove("failure");

			testCase.run();
		}
		// handle all problems: Exception, Error, RuntimeException
		catch(Throwable th){
			testCase.addFailure(th);

			successfulRun = false;
		}
		finally {
			//testCase.addJsErrors();
		}

		return successfulRun;
	}





	/**
	 * Run all test cases within a test.
	 * 
	 * @param testId - parent test id
	 * @param testCases - test cases map
	 * @return - true if all test cases passed or false otherwise
	 */
	public boolean runTestCases(
			TestResult testResult,
			Map<Integer, Map<String, String>> testCases){

		@SuppressWarnings("unused")
		int succeededTestCases=0;
		@SuppressWarnings("unused")
		int failedTestCases=0;
		@SuppressWarnings("unused")
		int crashedTestCases=0;
		boolean allTestCasesPassed = true;
		String testId = testResult.getId();

		List<String> suitePackages = XmlTestConfig.getSuitePackages();

		// log current test test cases
		logger.log("TestCases: ");
		for (Integer testCaseId : testCases.keySet()){
			logger.log("test_" + testId + "/" 
					+ "testCase_" + testCaseId + " : "
					+ testCases.get(testCaseId));
		}

		for(Integer testCaseId : testCases.keySet()){
			boolean stopTest = false;
			// get current test case retries
			int retries = TestConfig.getTestCaseRetries(testCases, testCaseId);
			boolean stopAtFailure = TestConfig.getTestCaseStopAtFailure(testCases, testCaseId);

			Instant startTestCaseTime = Instant.now();

			Map<String, String> testCaseAttributes = testCases.get(testCaseId);
			TestCaseResult testCaseResult = new TestCaseResult(
					Integer.toString(testCaseId),
					testCaseAttributes);

			boolean isTestCasePass = true;

			String testCaseName = testCaseAttributes.get("name");

			for (int i=1; i<=retries; i++){
				boolean retry = (retries - i) > 0;

				try {
					logger.logHeader("Executing " 
							+ "test_" + testId + "/" 
							+ "testCase_" + testCaseId 
							+ "(attempt " + i + "/" + retries + ")"
							+ " : " + testCaseAttributes);	

					// find the testCaseName class in the packages listed in the suite project list 
					Class<?> testClass = ClassUtils.findClass(testCaseName, suitePackages);


					// TODO - move this to separate method
					// Run a test case
					Object testCase;
					if ( TestCase.class.isAssignableFrom(testClass) ){				
						// instantiate current test
						testCase = testClass.newInstance();

						// initialize test attributes
						((TestCase)testCase).setTestCaseAttributes(testCaseAttributes);

						// @mainBreakPoint
						//************************************************************
						// Run test case
						isTestCasePass = runTestCase( (TestCase)testCase );
						//************************************************************

						// save screenshots at the end of each test
						if (TestConfig.getSaveScreenShots(tests, Integer.parseInt(testId))){
							Driver.saveScreenShot(testId + "_" 
									+ testCaseId + "_" 
									+ testCase.getClass().getSimpleName() + 
									"_screenShot.jpg");
						}


						// handle test case result
						if (isTestCasePass) {
							succeededTestCases++;
							logger.log("test_" + testId + "/" 
									+ "testCase_" + testCaseId + " succeeded !");
							results.addTestCaseResult(testResult, 
									testCaseResult, 
									Result.TestCasePass, 
									startTestCaseTime);
							break;
						}
						else {
							// fail test
							allTestCasesPassed = false;
							failedTestCases++;
							logger.log("test_" + testId + "/" 
									+ "testCase_" + testCaseId + " failed !");
							results.addTestCaseResult(testResult, 
									testCaseResult, 
									Result.TestCaseFail, 
									startTestCaseTime);

							// save screen shot at failure moment 
							Driver.saveScreenShot(testId + "_failure_" 
									+ testCaseId + "_" 
									+ testCase.getClass().getSimpleName() + 
									"_screenShot.jpg");

							// stop current test if no retries and stopAtFailure=true (default)
							// (do not execute any of the following test cases)
							if (!retry && stopAtFailure) {
								stopTest=true; 
								break; 
							}

						}
					}
					else {
						failedTestCases++;
						logger.log("test_" + testId + "/" 
								+ "testCase_" + testCaseId + " failed !");
						// no other tests available yet														
						allTestCasesPassed = false;
						results.addTestCaseResult(testResult, 
								testCaseResult, 
								Result.TestCaseFail, 
								startTestCaseTime);
						logger.log("No runner is available sfor this type of test case! - " + testCaseName );
						stopTest=true; 
						break;
					}

					// handle all cases of code failure: Exception, Error, RuntimeException
				} catch (Throwable th) {	
					// framework bugs/issues
					crashedTestCases++;
					testCaseAttributes.put("failure", th.toString()) ;

					results.addTestCaseResult(testResult,
							testCaseResult, 
							Result.TestCaseCrash, 
							startTestCaseTime);	

					logger.logLines("test_" + testId + "/" 
							+ "testCase_" + testCaseId + " crashed !\n" 
							+ TestCaseFailure.stackToString(th));

					crashedTestCases++;
					if (!retry) { 
						allTestCasesPassed = false;
						stopTest=true; 
						break; 
					}
				}
			}

			if(stopTest){
				break;
			}

		} // end test cases loop

		return allTestCasesPassed;
	}
	


	/**
	 * Get all tests information.
	 * 
	 * @return - a list of executed tests information.
	 */
	public List<TestResult> getTestsInfo() {
		return testResultInfo;
	}



	/**
	 * Get build number for Jenkins runs.
	 * 
	 * @return
	 */
	/*public static String getJenkinsBuildNr() {
		return jenkinsBuildNr;
	}*/

}
