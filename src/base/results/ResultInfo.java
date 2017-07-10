package base.results;

import java.time.Instant;
import java.util.Map;

import base.tools.TimeUtils;
import base.xml.XmlResult.Result;


/**
 * Class for holding test results information.
 * 
 * @author dan.rusu
 *
 */
public class ResultInfo {
	
	/**
	 * Available result file types.
	 * 
	 */
	public enum ResultFileType{
		txt,
		html, // default
		json, // not available yet
		xml;  // not available yet
	}


	private Map<String, String> attributes;
	
	private String id;
	private Result result;
	private String elapsedTime;
	private long elapsedTimeInMillis;

	

	public ResultInfo(String id, Map<String, String> attributes) {	
		this.id = id;
		this.attributes = attributes;
	}

	
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	
	
	public String getElapsedTime() {
		return elapsedTime;
	}

	

	public long getElapsedTimeInMillis() {
		return elapsedTimeInMillis;
	}	
	
		

	public Result getResult() {
		return result;
	}

	
	public String getId() {
		return id;
	}
	
	
	
	public void setResult(Result result) {
		this.result = result;
	}

	
	
	/**
	 * Set elapsedTimeInMillis and elapsedTime.
	 * 
	 * @param startTime - test start time Instant
	 */
	public void setElapsedTestTime( Instant startTime ){
		this.elapsedTimeInMillis = TimeUtils.getElapsedTimeInMillis(startTime);
		this.elapsedTime = TimeUtils.getElapsedTime(startTime);
	}
	

	
	public void setTestId(String id) {
		this.id = id;
	}

}
