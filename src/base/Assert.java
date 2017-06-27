package base;

public class Assert{

	
	
	/**
	 * Assertion check - log message before it throws.
	 * 
	 * @param isSuccessful - expression to be asserted
	 * @param message - message
	 */
	public static void assertTrue(boolean isSuccessful, String errorMessage){
		if( isSuccessful ){
			Logger.getLogger().logLines("Assertion: " + errorMessage + " - SUCCEEDED!");
		}
		else {
			Logger.getLogger().logLines("Assertion: " + errorMessage + " - FAILED!");
			throw new AssertionError(errorMessage);
		}
	}
	
	
	
	public static void assertTrue(boolean isSuccessful, 
			String errorMessage,
			String expected,
			String current){
		if( isSuccessful ){
			Logger.getLogger().logLines(errorMessage 
					+ "\nexpected: \"" + expected + "\""
					+ "\nSUCCEEDED!");
		}
		else {
			String message = errorMessage 
					+ "\nexpected: \"" + expected + "\""
					+ "\ncurrent: \"" + current + "\""
					+ "\nFAILED!";
			Logger.getLogger().logLines(message);
			
			throw new AssertionError(message);
		}
	}


	
	public static void fail(String message){
		Logger.getLogger().logLines("Assertion: " + message + " - FAILED!");
		throw new AssertionError(message);
	}

	
	
	public static void fail(){
		throw new AssertionError();
	}

}
