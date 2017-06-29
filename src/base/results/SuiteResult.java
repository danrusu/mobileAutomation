package base.results;

public class SuiteResult {
	
	private int totalTests;
	private int succeededTests;
	private int failedTests;
	private int crashedTests;
	private String elapsedSuiteTime;
	
	
	public SuiteResult() {
		this.totalTests = 0;
		this.succeededTests = 0;
		this.failedTests = 0;
		this.crashedTests = 0;
		this.elapsedSuiteTime = "";
	}

	
	
	public int getSucceededTests() {
		return succeededTests;
	}
	
	public void setSucceededTests(int succeededTests) {
		this.succeededTests = succeededTests;
	}
	
	public void incrementSucceededTests() {
		succeededTests++;
	}
	
	
	
	public int getFailedTests() {
		return failedTests;
	}
	
	public void setFailedTests(int failedTests) {
		this.failedTests = failedTests;
	}
	
	public void incrementFailedTests() {
		failedTests++;
	}
	
	
	
	public int getCrashedTests() {
		return crashedTests;
	}
	
	public void setCrashedTests(int crashedTests) {
		this.crashedTests = crashedTests;
	}
	
	public void incrementCrashTests() {
		crashedTests++;
	}
	
	
	
	public int getTotalTests() {
		return totalTests;
	}
	
	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}
	
	
	
	public String getElapsedSuiteTime() {
		return elapsedSuiteTime;
	}

	public void setElapsedSuiteTime(String elapsedSuiteTime) {
		this.elapsedSuiteTime = elapsedSuiteTime;
	}



	public boolean isSucceeded() {
		return totalTests == succeededTests;
	}

}
