package base;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.tools.TimeUtils;


/**
 * Singleton logger class.
 * 
 * @author Dan Rusu
 *
 */
public class Logger {

	private Path logDirPath;
	private Path logFilePath;
	private Path tempDirPath;
	
	private static String timeStampFormat = "yyyy/MM/dd HH:mm:ss";
	private static String fileTimeStampFormat = "yyyy_MM_dd_HH_mm_ss";

	private static Logger loggerInstance=null;

	private String separator = new StringBuilder()
			.append("##############################")
			.append("##############################")
			.append("##############################")
			.append("##############################")
			.toString();



	/**
	 * Private constructor for singleton.
	 */
	private Logger(){	
		logDirPath = Paths.get(
				System.getProperty("user.dir") 
				+ "/logs/log__"
				+ getTimeStamp(fileTimeStampFormat)
				);

		logFilePath = Paths.get(
				logDirPath.toString() 
				+ "/log.txt"
				); 
		
		tempDirPath = Paths.get(
				System.getProperty("user.dir") 
				+ "/temp");

		try {
			Files.createDirectory(Paths.get(
					System.getProperty("user.dir") 
					+ "/logs"));
			
			
		} catch (FileAlreadyExistsException e){
			System.out.println("Log folder already exists: "+e);
		}catch(Exception e){
			System.out.println("Error: Failed to create folder!\n" + e);
		}
		
		
		// temp folder
		try {
			Files.createDirectory(tempDirPath);
		} catch (FileAlreadyExistsException e){
			System.out.println("Temp folder already exists: "+e);
		}catch(Exception e){
			System.out.println("Error: Failed to create 'temp' folder!\n" + e);
		}
		
		
		// TODO
		// implement logic for error
		try {			
			Files.createDirectory( logDirPath );			
			Files.createFile(logFilePath);
		}
		catch(Exception e){
			//System.out.println("Error: Coud not create log !\n" + e);
		}
		//log("Log created: " + logFilePath);
	}



	/**
	 * Method to write formatted log messages to the log file.
	 * 	
	 * Log format:
	 * 
	 * timeStamp              | thread id | message
	 * 2016/07/18 09:03:02    |    1      | Instantiate driver 
	 * 
	 * @param message - String to be logged 
	 */
	public void log(String message){	
		String formattedMessage = new StringBuilder()
				.append(getTimeStamp(timeStampFormat))
				.append(" | t=" + Thread.currentThread().getId() + " | ")
				.append(message) 
				.toString();

		System.out.println(formattedMessage);

		try {
			Files.write(logFilePath, 
					("\n" + formattedMessage).getBytes(), 
					StandardOpenOption.APPEND
					);

		} catch (Exception e) {
			System.out.println("Could not write to log! \n" + e);
		}
	}



	/**
	 * Method to get formatted current date.
	 * 
	 * @return formatted current date
	 */
	private String getTimeStamp(String format){
		return TimeUtils.getFormatedDate(format);	
	}

	
	
	public static String getTimeStamp(){
		return TimeUtils.getFormatedDate(timeStampFormat);	
	}
	
	
	
	public static String getFileTimeStamp(){
		return TimeUtils.getFormatedDate(fileTimeStampFormat);	
	}

	

	/**
	 * Return current Logger instance if exists or instantiate logger if not.
	 * 
	 * @return logger instance
	 */
	public static Logger getLogger(){
		if (loggerInstance==null){
			loggerInstance = new Logger();
		}
		return loggerInstance;
	}



	/**
	 * Logs the message in a header style
	 * between two lines of #
	 * 
	 * @param message - message to be logged
	 */
	public void logHeader(String message){
		log(separator);
		log(message);
		log(separator);		
	}



	/**
	 * Returns a line of #
	 * 
	 * @return - the log.separator string
	 */
	public String getSeparator(){
		return separator;
	}



	/**
	 * Get the log directory.
	 * 
	 * @return - log directory path
	 */
	public Path getLogDirPath() {
		return logDirPath;
	}

	
	
	/**
	 * Get the log file path.
	 * 
	 * @return - log file path
	 */
	public Path getLogFilePath() {
		return logFilePath;
	}

	

	/**
	 * Log multiple line string, one line at a time.
	 * 
	 * @param lines - multiple lines string
	 */
	public void logLines(String lines) {
		Arrays.asList(lines.split("\n")).stream().forEach( 
				line -> log(line));
	}
	
	
	
	/**
	 * Log First line from string.
	 * 
	 * @param lines - multiple lines string
	 */
	public void logFirstLine(String lines) {
		List<String> allLines = new ArrayList<>(Arrays.asList(lines.split("\n")));
		if (allLines.size()>1){
			log(allLines.get(0));
		}
	}
	
	
	// this could be useful for getting failure details
	public List<String> getLatestLogLines(int nrOfLines){
		List<String> logLines = new ArrayList<>();
		
		//TODO - implementation
		
		return logLines;
	}
	
	
	
	/**
	 * @return - the temporary folder path
	 */
	public Path getTempDirPath(){
		return tempDirPath;
	}
}





