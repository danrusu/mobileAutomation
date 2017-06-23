package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.NotARobotPage;


public class NotARobotValidate extends TestCase{

	@Override
	public void run() {
 			new NotARobotPage(Driver.driver).validateContent();
	}

	
	@Override
	public String getTestCaseScenario() {
		return "Validate content of the \"I am not a robot\" page.";
	}
}
