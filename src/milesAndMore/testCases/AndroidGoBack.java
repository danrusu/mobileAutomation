package milesAndMore.testCases;


import base.Driver;
import base.TestCase;


public class AndroidGoBack extends TestCase{

	@Override
	public void run() {
			Driver.goBack();
	}

	@Override
	public String getTestCaseScenario() {
		return "Press Back button on Android device..";
	}


	

}
