package base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import base.failures.TestCaseFailure;
import base.tools.ThreadUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;


public class Driver {

	private static Logger logger = Logger.getLogger();
	public static AndroidDriver<?> driver;
	private static final Long defaultWait = new Long(10);  //seconds


	/**
	 * Get an instance of an WebDriver.
	 * 
	 * @param appiumServerUrl - servers URL
	 * @param platformName - OS name (Android, IOS ...)
	 * @param deviceName - device model name
	 * @param app - mobile application to install/run
	 * @return - an android driver connected via Appium
	 */
	public static AndroidDriver<?> driverStart( String appiumServerUrl, 
			String platformName,
			String deviceName,
			String app){
		
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("app", app);
		
		

		try {
			driver = new AndroidDriver<>(new URL(appiumServerUrl), capabilities);
		} catch (MalformedURLException e) {
			throw new TestCaseFailure("Could not start remote driver! ", e);
		}

		logger.log("WebDriver: " + driver.getClass().getCanonicalName());
		setDefaultImplicitWait();
		logger.log("implicit wait: " + defaultWait + "s");
		
		return driver;
	}
	

	
	public static void resetImplicitWait(){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	
	
	public static void setDefaultImplicitWait(){
		driver.manage().timeouts().implicitlyWait(defaultWait, TimeUnit.SECONDS);
	}
	
	
	
	/**
	 * Save screenshot to file.
	 * 
	 * @param fileName
	 */
	public static void saveScreenShot(String fileName){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {

			Path destPath = Paths.get(
					Logger.getLogger().getLogDirPath().toString() 
					+ "/" + fileName);

			FileUtils.copyFile(scrFile, new File(destPath.toString()));
			logger.log("Saved screenshot: " + destPath.toString());

		} catch (IOException e) {
			logger.logLines(e.getStackTrace().toString());
			logger.log("Failed to save screenshot!");
			System.out.println();
		}
	}
	
	
	
	/**
	 * Swipe from xStart to xStop on the horizontal center of the screen.
	 * 
	 * @param xStart - start point in percents 
	 * @param xStop - end point in percents 
	 * @param speed - swipe speed
	 */
	public static void horizontalSwipe(double xStart, double xStop, int speed)
	{
		Dimension size = driver.manage().window().getSize();
		((AppiumDriver<?>) driver).swipe(

				// start point
				(int)xStart,
				size.height/2,
				
				// end point
				(int)xStop,
				size.height/2,

				speed);
		ThreadUtils.sleepQuiet(2000);
	}
	


	/**
	 * Swipe from yStart to yStop on the vertical center of the screen.
	 * 
	 * @param yStart - start point in percents  
	 * @param yStop - end point in percents 
	 * @param speed - swipe speed
	 */
	public static void verticalSwipe(double yStart, double yStop, int speed)
	{
		Dimension size = driver.manage().window().getSize();
		((AppiumDriver<?>) driver).swipe(

				// start point
				size.width/2,
				(int)(size.height*yStart),

				// end point
				size.width/2,
				(int)(size.height*yStop), 

				speed);
		ThreadUtils.sleepQuiet(2000);
	}
	
	
	
	public static void verticalSwipeUp()
	{
		logger.log("Swipe up");
		verticalSwipe(0.2, 0.8, 1000);
	}
	
	
	public static void verticalSwipeDown()
	{
		logger.log("Swipe down");
		verticalSwipe(0.8, 0.2, 1000);
	}


	
	public  static void verticalSwipeLeft()
	{
		logger.log("Swipe left");
		horizontalSwipe(0.8, 0.2, 1000);
	}
	
	
	
	public static void verticalSwipeRight()
	{
		logger.log("Swipe right");
		horizontalSwipe(0.2, 0.8, 1000);
	}
	
	
	
	/**
	 * Press Back (<) button on the mobile device.
	 */
	public static void goBack() {
		driver.navigate().back();
	}
}
