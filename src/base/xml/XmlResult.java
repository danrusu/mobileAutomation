package base.xml;

/**
 * XML result states.
 * 
 * @author dan.rusu
 *
 */
public class XmlResult {

	   public enum Result
	   {
		  TestRunning,
	      TestPass, 
	      TestFail,
	      TestCrash, /** indicates a problem with the code */
	      TestCasePass,
	      TestCaseFail,
	      TestCaseCrash,
	      UnknownError;
	   }
   
}
