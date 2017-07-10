package base;

import java.nio.file.Paths;
import java.util.List;

import base.tools.ClassUtils;

/**
 * Class for listing Test Cases names or documentation via TestScenario interface. 
 * @author Dan.Rusu
 *
 */
public class TestCaseDocs {

	public void run(List<String> suitePackages) {
		String jarPath = Paths.get(System.getProperty("user.dir") + "/milesAndMore.jar")
				.toString();

		List<String> availableTestCases = ClassUtils.getClassesNamesInPackage(
				jarPath, suitePackages);

		//TODO - sort test cases alphabetically 
		availableTestCases.forEach(
				c -> {
					final String[] items = c.split("\\.");
					if (items.length>1){
						System.out.println(items[items.length-1]);
					}
				});
	}


	public void run(List<String> suitePackages, String testcaseName) {
        System.out.println(TestCaseDocs.getTestScenario(suitePackages, testcaseName));
	}


	public static String getTestScenario(List<String> suitePackages, String testCaseName) {
		TestCase testCase;
		
		// find the testCaseName class in the packages listed in the suite project list 
		Class<?> testClass = ClassUtils.findClass(testCaseName, suitePackages);
			
		if ( TestCase.class.isAssignableFrom(testClass) ){				
				// instantiate and run current test
				try {
					testCase = (TestCase)testClass.newInstance();
					return testCase.getTestCaseScenario();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}

		return "error";
	}
}
