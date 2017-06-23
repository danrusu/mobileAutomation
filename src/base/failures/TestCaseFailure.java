package base.failures;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Wrapper for any kind of test case failure: Exception, Error, RuntimeException.
 * 
 * TestCaseFailure extends Error so there is no need to add Throws 
 * to the method that will throw this.
 * 
 * @author Dan.Rusu
 *
 */
public class TestCaseFailure extends Error {

	private static final long serialVersionUID = 1L;

	public TestCaseFailure(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TestCaseFailure(String message) {
		super(message);
	}

	public static String stackToString(Throwable cause){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		cause.printStackTrace(pw);
		return sw.toString();
	}
	
}