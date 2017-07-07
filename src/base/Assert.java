package base;

public class Assert{

	
	
	/**
	 * True expression assertion check - log message before it throws.
	 * 
	 * @param expression - expression to be asserted
	 * @param message - message to throw
	 */
	public static void assertTrue(boolean expression, String errorMessage){
		if( expression ){
			Logger.getLogger().logLines("Assertion: " + errorMessage + " - SUCCEEDED!");
		}
		else {
			Logger.getLogger().logLines("Assertion: " + errorMessage + " - FAILED!");
			throw new AssertionError(errorMessage);
		}
	}
	
	
	
	/**
	 * True expression assertion check - log message before it throws.
	 * 
	 * @param expression - expression to be asserted
	 * @param errorMessage - message to throw
	 * @param expected - expected value
	 * @param current - current value
	 */
	public static void assertTrue(boolean expression, 
			String errorMessage,
			String expected,
			String current){
		if( expression ){
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


	
	/**
	 * Throw failure assertion message.
	 * 
	 * @param message - message to throw
	 */
	public static void fail(String message){
		Logger.getLogger().logLines("Assertion: " + message + " - FAILED!");
		throw new AssertionError(message);
	}

	
	
	public static void fail(){
		throw new AssertionError();
	}

}
