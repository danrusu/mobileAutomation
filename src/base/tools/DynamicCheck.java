package base.tools;
import java.util.function.Function;
import java.util.function.Predicate;

import base.Logger;
import base.Page;
import base.Driver;

/**
 * Class for dynamic function evaluations.
 * 
 * @author dan.rusu
 *
 */
public class DynamicCheck {
	private Logger logger;

	
	public DynamicCheck(Logger logger){
		this.logger = logger;
	}


	public boolean waitUntilOneFunctionReturnsExpectedValue(
			long totalMilisTimeout,
			long stepTimeout,

			Function< Object, Object> function1,
			Object funtion1Argument,
			Object expectedObject1,

			Function< Object, Object> function2,
			Object funtion2Argument,
			Object expectedObject2){

		Object returnedObject1;
		Object returnedObject2;

		for(int i=0; i<totalMilisTimeout/stepTimeout; i++){
			returnedObject1 = function1.apply(funtion1Argument);
			returnedObject2 = function2.apply(funtion2Argument);
			if ( returnedObject1.equals(expectedObject1) 
					|| returnedObject2.equals(expectedObject2) ){
				logger.log("condition reached after " 
						+ i*stepTimeout + "ms");
				return true;
			}
			ThreadUtils.sleepQuiet(stepTimeout);
		}
		logger.log("condition not reached after " 
				+ totalMilisTimeout + "ms");
		return false;
	}


	public boolean waitUntilFunctionReturnsExpectedValue(
			long totalMilisTimeout,
			long stepTimeout,
			Function< Object, Object> function,
			Object funtionArgument,
			Object expectedObject
			){

		return waitUntilFunctionReturnsValueThatMachesCondition(
				totalMilisTimeout,
				stepTimeout,
				function,
				funtionArgument,
				x -> x.equals(expectedObject));
	}
	
	
	
	public boolean waitUntilFunctionReturnsDifferentValue(
			long totalMilisTimeout,
			long stepTimeout,
			Function< Object, Object> function,
			Object funtionArgument,
			Object expectedObject
			){

		return waitUntilFunctionReturnsValueThatMachesCondition(
			totalMilisTimeout,
			stepTimeout,
			function,
			funtionArgument,
			x -> ! x.equals(expectedObject));
	}
	
	
	public boolean waitUntilFunctionReturnsValueThatMachesCondition(
			long totalMilisTimeout,
			long stepTimeout,
			Function< Object, Object> function,
			Object funtionArgument,
			Predicate<Object> expectedCondition
			){

		for(int i=0; i<totalMilisTimeout/stepTimeout; i++){
			Object resultObject = function.apply(funtionArgument);
			logger.log("Dynamic wait - funtion result: " + resultObject.toString());
			if ( expectedCondition.test(resultObject) ){
				logger.log("condition reached after " 
						+ i*stepTimeout + "ms");
				return true;
			}
			ThreadUtils.sleepQuiet(stepTimeout);
		}
		logger.log("condition not reached after " 
				+ totalMilisTimeout + "ms");
		return false;
	}
	
	
	public boolean waitUntilWebPageActionMatchesCondition(
			long totalMilisTimeout,
			long stepTimeout,
			Function< Page, Object> function1,
			Page page,
			Predicate<Object> expectedCondition
			){

		Driver.resetImplicitWait();
		boolean success=false;
		
		for(int i=0; i<totalMilisTimeout/stepTimeout; i++){
			if ( expectedCondition.test(function1.apply(page)) ){
				logger.log("condition reached after " 
						+ i*stepTimeout + "ms");
				
				success = true;
				break;
			}
			ThreadUtils.sleepQuiet(stepTimeout);
		}

		Driver.setDefaultImplicitWait();
		if (! success){
			logger.log("condition not reached after " 
				+ totalMilisTimeout + "ms");
		}
		return success;
	}
	

}
