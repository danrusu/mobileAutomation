package milesAndMore.testCases;


import base.Driver;
import base.TestCase;
import milesAndMore.pages.NotARobotPage;


public class NotARobotCancel extends TestCase{

	@Override
	public void run() {
			
 			new NotARobotPage(Driver.driver).clickCancelButton();
	}

	
	@Override
	public String getTestCaseScenario() {
		return "Press Cancel button on Create Account -> I am not a robot dialog.";
	}
}
