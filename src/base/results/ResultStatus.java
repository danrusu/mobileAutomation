package base.results;

/**
 * Test/Test-case result status.
 * 
 * @author dan.rusu
 *
 */
public enum ResultStatus {

	TestRunning,
	TestPass, 
	TestFail,
	TestCrash,
	
	TestCasePass,
	TestCaseFail,
	TestCaseCrash,
	
	UnknownError;

}
