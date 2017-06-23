package milesAndMore.testCases;


import base.Driver;
import base.TestCase;


public class AndroidSwipeLeft extends TestCase{

	@Override
	public void run() {
			Driver.verticalSwipeLeft();
	}

	@Override
	public String getTestCaseScenario() {
		return "Android swipe left.";
	}


	

}
