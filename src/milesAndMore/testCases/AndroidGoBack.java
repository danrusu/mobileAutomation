package milesAndMore.testCases;


import base.TestCase;


public class AndroidGoBack extends TestCase{

	@Override
	public void run() {
			goBack();
	}

	@Override
	public String getTestCaseScenario() {
		return "Press Back Arrow button.";
	}


	

}
