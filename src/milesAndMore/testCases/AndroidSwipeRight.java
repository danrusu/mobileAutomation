package milesAndMore.testCases;


import base.Driver;
import base.TestCase;


public class AndroidSwipeRight extends TestCase{

	@Override
	public void run() {
			Driver.verticalSwipeRight();
	}

	@Override
	public String getTestCaseScenario() {
		return "Android swipe right.";
	}


	

}
