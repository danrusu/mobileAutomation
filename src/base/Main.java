package base;


import java.util.Arrays;
import java.util.List;

import base.xml.XmlTestConfig;


/**
 * 
 * Main entry of application (milesAndMore.jar).
 * 
 * @author dan.rusu
 *
 */
public class Main {
	private static List<String> testPackages = Arrays.asList( 
			"milesAndMore.testCases"
			);
	

	public static void main(String[] args) {
		
		if ( args.length < 1 ){
			usage();
			return;
		}

		
		// print internal test cases documentation
		if (args[0].equals("docs")){
			TestCaseDocs tcDoc = new TestCaseDocs();
			if ( args.length == 1 ){
				tcDoc.run(testPackages);
			}
			else {
				String testcaseName = args[1];
				tcDoc.run(testPackages, testcaseName);
			}
		}
		// run test scenario from XML
		else{
			TestRunner testRunner;
			
			XmlTestConfig.setSuitePackages(testPackages);
			System.out.println(XmlTestConfig.getSuitePackages());
			
			if ( args.length == 1 ){
				testRunner = new TestRunner(args[0]);
			}
			
			// setup for running from Jenkins
			else if ( args.length == 3 ){
				testRunner = new TestRunner(args[0], args[1], args[2]);
			}
			
			// wrong arguments
			else {
				usage();
				return;
			}
			
			testRunner.runTests();
		}
	}
	
	
	
	/**
	 * Display milesAndMore.jar usage information.
	 */
	private static void usage(){
		System.out.println();
		System.out.println("Please add arguments.");
		System.out.println("Usage:");
		System.out.println("E.g.");
		System.out.println("java -jar milesAndMore.jar config.xml [jenkinsJobName jenkinsBuildNr]");
		System.out.println("java -jar milesAndMore.jar docs [testCaseName]");
	}

}
