package base.results;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

import base.tools.TimeUtils;
import base.xml.XmlResult.Result;


public class ResultInfo {
	/**
	 * Available result file types.
	 * 
	 */
	public enum ResultFileType{
		txt,
		html;
	}



	private String id;
	private Map<String, String> attributes;



	private Result result;
	private String elapsedTime;
	private long elapsedTimeInMillis;

	

	public ResultInfo(String id, Map<String, String> attributes) {	
		this.id = id;
		this.attributes = attributes;
	}

	

	/**
	 * Get a header string for displaying test info.
	 * 
	 * @return - formatted test info header string
	 */
	public static String getHeader(Integer defaultColWidth){
		String colWidth = Integer.toString(defaultColWidth);
		String format = "%-"+ colWidth + "s";
		return String.format(format, "Test[/TestCase]" ) + " | " 
				+ String.format(format.replace(colWidth, "20"), "Elapsed" ) + " | "
				+ String.format(format.replace(colWidth, "40"), "Name" ) + " | "
				+ String.format(format.replace(colWidth, "15"), "Result" ) + " | "
				+ String.format(format, "Details" );
	}
	
	
	
	/**
	 * Get formatted test information.
	 * 
	 * @return - test information string
	 */
	public String getInfo(Integer defaultColWidth){
		String colWidth = Integer.toString(defaultColWidth);
		String format = "%-"+ colWidth + "s";
		// details: all attributes but name
		Map<String, String> details = new TreeMap<>();
		details.putAll(attributes);
		details.remove("name");

		return String.format(format, id ) + " | " 
			+ String.format(format.replace(colWidth, "20"), 
					elapsedTime ) + " | "
			+ String.format(format.replace(colWidth, "40"), 
					attributes.get("name") ) + " | "
			+ String.format(format.replace(colWidth, "15"),
					result ) + " | "
			+ String.format(format, details);
	}
	
	

	
	
	// Getters
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
	
	
	
	// Setters
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

	public Map<String, String> getAttributes() {
		return attributes;
	}

}
