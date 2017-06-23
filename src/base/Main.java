package base;


import java.util.Arrays;
import java.util.List;

import base.xml.XmlTestConfig;

public class Main {
	private static List<String> testPackages = Arrays.asList( 
			// web test validation 
			//"dr.testCases",
			
			"milesAndMore.testCases"
			
			// common modules
			// "common.testCases", 
			);
	
	// TODO
	//List<String> suitePackages = ClassUtils.findPackageNamesContaining("testCases");

	public static void main(String[] args) {
		TestRunner testRunner;
		
		
		if ( args.length < 1 ){
			usage();
			return;
		}

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
		else{
			XmlTestConfig.setSuitePackages(testPackages);
			System.out.println(XmlTestConfig.getSuitePackages());
			if ( args.length == 1 ){
				testRunner = new TestRunner(args[0]);
			}
			// run from Jenkins
			else if ( args.length == 3 ){
				testRunner = new TestRunner(args[0], args[1], args[2]);
			}
			else {
				usage();
				return;
			}
			
			testRunner.runTests();
		}
	}
	
	
	
	private static void usage(){
		System.out.println();
		System.out.println("Please add arguments.");
		System.out.println("Usage:");
		System.out.println("E.g.");
		System.out.println("java -jar watt.jar config.xml [jenkinsJobName jenkinsBuildNr]");
		System.out.println("java -jar watt.jar docs [testCaseName]");
	}

}
