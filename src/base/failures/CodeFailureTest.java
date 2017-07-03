package base.failures;

import java.util.function.Consumer;

/**
 * This is used to test (try) block code or method code, 
 * catch any kind of thrown error/exception,
 * add a custom message to it and throw an error 
 * with all info.
 * 
 * @author Dan.Rusu
 *
 */
public class CodeFailureTest {

	
	/**
	 * Test method with one Object parameter.
	 * 
	 * @param f - Consumer (or block code)
	 * @param functionParam
	 * @param failMessage
	 */
	public static void functionTest(
			Consumer< Object > f, 
			Object functionParam,
			String failMessage
			){
		
		
		try{
			f.accept(functionParam);
		}
		catch(Throwable th) {
			throw new TestCaseFailure(failMessage, th);
		}
	}
	
	
	/**
	 * Test block code or method no parameter.
	 * 
	 * @param f - code block or method with no parameter
	 * @param failMessage - message to add to final thrown Error
	 */
	public static void functionTest(
			Consumer<Object> f, 
			String failMessage
			){
		
		CodeFailureTest.functionTest(f, null, failMessage);
		
	}
}
