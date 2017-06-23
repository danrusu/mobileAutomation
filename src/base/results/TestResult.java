package base.results;

import java.util.Map;
import java.util.TreeMap;


public class TestResult extends ResultInfo{
	
	private Map<Integer, TestCaseResult> testCasesResults;

	

	public TestResult(String id, Map<String, String> testAttributes) {	
		super( id, testAttributes);
		testCasesResults = new TreeMap<>();
	}



	public Map<Integer, TestCaseResult> getTestCasesResults() {
		return testCasesResults;
	}



	public void addTestCaseInfo(String testCaseId, TestCaseResult testCaseResult) {
		this.testCasesResults.put(Integer.parseInt(testCaseId), testCaseResult);
	}


}
