package unitTest.testCases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import base.Driver;
import base.TestCase;
import base.failures.CodeFailureTest;

/**
 * Unit test for an Exception test case failure.
 * @author Dan.Rusu
 *
 */
public class BlankTestCaseException extends TestCase{

	public BlankTestCaseException(){
		super();
	}


	@Override
	public void run(){
		
		
		CodeFailureTest.functionTest(
				x -> {
					Driver.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
					Driver.driver.findElement(By.cssSelector("unknown"));
				},
				"Test case exception!");
	}
	
	@Override
	public String getTestCaseScenario(){
		return "\nEmpy test case used just for unit testing an exception test failure!";
	}


}
