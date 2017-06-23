package base.xml;

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
	      UnknownError, ;
	   }

	   
	   
	   
}
