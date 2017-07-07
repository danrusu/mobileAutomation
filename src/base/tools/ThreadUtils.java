package base.tools;

import base.Logger;

/**
 * Class for hard coded waits using Thread.sleep.
 * 
 * @author dan.rusu
 *
 */
public class ThreadUtils {
	
	/**
	 * Wait and log log the wait duration. 
	 * 
	 * @param millis - duration of the wait in milliseconds 
	 */
	public static void sleep(long millis){
		try {
			Logger.getLogger().log("Wait " + millis + "ms");
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logger.getLogger().log(
					Thread.currentThread().getId() 
					+ "was interrupted");
		}
	}

	
	
	/**
	 * Do not log the wait :)
	 * 
	 * @param millis - duration of the wait in milliseconds 
	 */
	public static void sleepQuiet(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Logger.getLogger().log(
					Thread.currentThread().getId() 
					+ "was interrupted");
		}
		
	}
}
