package base;


import java.util.Arrays;
import java.util.List;

import base.xml.XmlTestConfig;


/**
 * 
 * Main entry of application (mat.jar).
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

		
		// list test cases names / documentation
		if (args[0].equals("docs")){
			TestCaseDocs tcDoc = new TestCaseDocs();
			
			// list all available test cases names
			if ( args.length == 1 ){
				tcDoc.run(testPackages);
			}
			
			// list test case's internal documentation (getTestScenario)
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
			
			// normal setup (running from IDE or command line)
			if ( args.length == 1 ){
				testRunner = new TestRunner(args[0]);
			}
			
			// Jenkins setup
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
	 * Display mat.jar usage information.
	 */
	private static void usage(){
		System.out.println();
		System.out.println("Wrong arguments.\n");
		System.out.println("Usage:");
		System.out.println("\nRun test:");
		System.out.println("java -jar mat.jar config.xml [jenkinsJobName jenkinsBuildNr]");
		System.out.println("\nList available test cases modules:");
		System.out.println("java -jar mat.jar docs");
		System.out.println("\nList test case's documentation:");
		System.out.println("java -jar mat.jar docs testCaseName");
	}

}


