package base.xml;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import base.Logger;
import base.tools.TimeUtils;

public class XmlDynamicData {
	private static Map<String, String> savedDataMap = new TreeMap<>();
	private static Logger logger = Logger.getLogger();
	
	public enum DynamicValue{
		$userDir,
		$logFolder,
		
		$HHmmss,
		$HH,
		$mm,
		$ss,
		
		$ddMMyyyy,
		$yyyy,
		$MM,
		$dd;

		public static boolean contains(String value)
		{
			for(DynamicValue d : values()){
				if (d.name().equals(value)){ 
					return true;
				}
			}
			return false;
		} 
	}

	

	public static String getDynamicValue(Map<String, String> savedDataMap, String dynamicValue){
		String value="";
		String key;
		int start;
		int foundEnd, foundNextStart;

		for (int i=0; i<dynamicValue.length(); i++){
			char c = dynamicValue.charAt(i);
			if ( c == '{' ){
				start = i;
				foundEnd = dynamicValue.indexOf('}', start+1);
				foundNextStart = dynamicValue.indexOf('{', start+1);

				// if '{' found before '}' do not evaluate value string
				if (foundNextStart !=-1 && foundNextStart < foundEnd){
					value += dynamicValue.substring(start, foundNextStart );
					i += foundNextStart - start - 1;
				}
				else if (foundEnd != -1){
					key = dynamicValue.substring(start + 1, foundEnd );
					if ( DynamicValue.contains(key)){
						value += dynamicEval(key);
					}
					else {
						// key found in map
						if (savedDataMap.get(key) != null){
							value += savedDataMap.get(key);
						}
						// key not found in map
						else {
							value +=  dynamicValue.substring(start, foundEnd+1 );
						}
					}
					i += foundEnd - start;
					
				}
				// if no '{' or '}' found
				else if (foundEnd == -1 && foundNextStart == -1){
					value += dynamicValue.substring(start);
					i = dynamicValue.length();
				}
				else{
					value += dynamicValue.substring(start);
					i = dynamicValue.length();
				}
			}


			else {
				value += c;
			}
		}

		if ( ! dynamicValue.equals(value)){
			logger.log("dynamycValue= " + value);
		}
		return value;
	}


	
	private static String dynamicEval(String value) {
		DynamicValue dv = DynamicValue.valueOf(value);
		String returnValue = value;
		
		switch (dv){
		case $userDir:
			returnValue = System.getProperty("user.dir");
			break;
		case $logFolder:
			returnValue = Logger.getLogger().getLogDirPath().toString();
			break;
		
		// current time
		case $HHmmss:
			returnValue = TimeUtils.getFormatedDate("HHmmss"); 
			break;
		case $HH:
			returnValue = TimeUtils.getFormatedDate("HH"); 
			break;
		case $mm:
			returnValue = TimeUtils.getFormatedDate("mm"); 
			break;
		case $ss:
			returnValue = TimeUtils.getFormatedDate("ss"); 
			break;
		
		// current date
		case $ddMMyyyy:
			returnValue = TimeUtils.getFormatedDate("ddMMyyyy"); 
			break;		
		case $yyyy:
			returnValue = TimeUtils.getFormatedDate("yyyy"); 
			break;
		case $MM:
			returnValue = Integer.toString(
					Integer.parseInt((TimeUtils.getFormatedDate("MM"))));
			break;
		case $dd:
			returnValue = Integer.toString(
					Integer.parseInt((TimeUtils.getFormatedDate("dd")))); 
			break;
			
			
		default:
			break;
		}	
		return returnValue;
	}


	
	public static Map<String, String> getSavedData() {
		//logger.log("Get current saved data: " + savedDataMap);
		return savedDataMap;
	}

	public static void saveData(String key, String value) {
		savedDataMap.put(key, value);
		logger.log("Saved data: " + key + "=" + savedDataMap.get(key));
	}


	public static Map<String, String> getMapFromSaveString(String saveString){
		Map<String, String> saveMap = new TreeMap<>();
		
		Arrays.asList(saveString.split(";")).forEach(
				pair -> {
					final String[] entry = pair.split("=");
					saveMap.put(entry[0], entry[1]);
				}
				);
		return saveMap;
	}
	
}
