package base.tools;

import base.Logger;

public class ThreadUtils {
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
