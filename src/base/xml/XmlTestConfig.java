package base.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import base.Logger;
import base.TestConfig;
import base.results.ResultInfo.ResultFileType;


/**
 * Read test configuration from XML file.
 * 
 * @author Dan Rusu
 *
 */
public class XmlTestConfig {
	int testIndex;
	Logger logger;
	
	private static boolean grid;

	private static String suiteName;
	private static String user;
	private static String emails;
	
	private static ResultFileType resultFileType;
	private static List<String> suitePackages = new ArrayList<>();
	
	// Map of tests from XML - Map< testName, TestConfig> >
	private static Map<Integer, TestConfig> testsMap;



	/**
	 * Accepted tags within config.xml
	 *
	 */
	private enum ConfigTags{
		suite,
		test;

		public static boolean contains(String s)
		{
			for( ConfigTags tag : values() ){
				if ( tag.name().equals(s) ) 
					return true;
			}
			return false;
		} 
	}



	/**
	 * Constructor.
	 */
	public XmlTestConfig(){
		logger = Logger.getLogger();
		testsMap = new TreeMap<Integer, TestConfig>();
		testIndex = 1;
		// default result file type 
		resultFileType = ResultFileType.html;
	}



	/**
	 * Initialize configuration from XML file.
	 * 
	 * @param testXml - the test XML file (path relative to user.dir)
	 * @return - true if no Exception
	 * @throws Exception - invalid test XML exception
	 */
	public boolean init(String testXml) throws Exception{

		File input = new File( testXml );					
		XmlDoc configDoc = new XmlDoc(input);

		// validate node tags and get root; this throws for invalid XML
		Element root = validateTestXml(configDoc);
		List<Element> testsList = XmlDoc.getChildren(root);
		

		Iterator<Element> iter = testsList.iterator();
		while ( iter.hasNext() ) {
			Element e = iter.next(); 
			switch ( e.getTagName() ){
			case "test":
				addTest(e);
				break;	

			default:
				break;
			}		
		}



		return true;
	}



	/**
	 * Validate the test XML and get the root element.
	 * 
	 * @param configDoc 
	 * @return  - the root element of the XML
	 * @throws Exception - in case of an invalid tag name; 
	 *                     accepted tag names are enumerated in ConfigTags 
	 */
	private Element validateTestXml(XmlDoc configDoc) throws Exception{
		// validate config.xml - only tags defined in ConfigTags are accepted 
		Element root = configDoc.getRoot();                                   

		// validate root                                                      
		if ( ! root.getTagName().equals(ConfigTags.suite.name()) ){
			throw new Exception("wrong xml root tag name; it should be 'suite' ");
		}                

		// TODO - change this from static
		setSuiteName(root.getAttribute("name"));
		setUser(root.getAttribute("user"));
		setEmail(root.getAttribute("email"));
		setGrid(root.getAttribute("grid"));
		setSuiteResultFileType(root.getAttribute("resultType"));
		
		
		// available testCases packages are set at start; this overwrites previous package settings
		String packages = TestConfig.nullToEmptyString(root.getAttribute("package"));
		if ( ! packages.isEmpty()){
			logger.log("Overwrite default package settings to: " + packages);
			setSuitePackages( packages );
		}
		
		
		List<Element> testsList = XmlDoc.getChildren(root);
		String testName;
		for (Element e : testsList) {
			testName = e.getTagName();
			// validate test tags         
			if ( ! ConfigTags.contains( testName ) ){                                                                            
				throw new Exception("wrong tag: " + e.getTagName());
			}
		}         

		return root;
	}



	/**
	 * Set suite's packages for finding test cases.
	 * 
	 * @param suitePackages - test cases packages
	 */
	private void setSuitePackages(String suitePackages) {
		String[] spkgs =  suitePackages.split(";");
		List<String> retSuitePackages = new ArrayList<>();
		for (int i=0; i<spkgs.length; i++){
			retSuitePackages.add(spkgs[i] + ".testCases");
		}
		
		XmlTestConfig.suitePackages = retSuitePackages;	
	}

	
	
	/**
	 * Get test cases packages.
	 * 
	 * @return - a list of test cases packages.
	 */
	public static List<String> getSuitePackages() {
		
		return XmlTestConfig.suitePackages;
	}



	/**
	 * Set if the test will use a Selenium grid.
	 * 
	 * @param useGrid - true if using grid, false otherwise
	 */
	private void setGrid(String useGrid) {
		XmlTestConfig.grid = TestConfig.stringToBoolean(useGrid);
	}



	/**                                                       
	 * Add tests and tests' attributes to tests' map.
	 * 
	 * @param e - XML element (tag with attributes)
	 */
	private void addTest(Element e){
		NamedNodeMap attributes;
		Map <String, String> testAttributes = new TreeMap<>();	
		attributes = e.getAttributes();

		for (  int j=0; j<attributes.getLength(); j++ ){
			testAttributes.put(attributes.item(j).getNodeName(), 
					attributes.item(j).getNodeValue());
		}

		// get test cases info
		List<Element> testCasesList = XmlDoc.getChildren(e);
		Map<Integer, Map<String, String>> testCases = new TreeMap<>();
		Map<String, String> testCaseAttributes = new TreeMap<>();

		int k=0;
		for(Element tc:testCasesList){
			testCaseAttributes = getAttributes(tc.getAttributes());
			
			//dynamicEval(testCaseAttributes); - moved to WebPageTestCase
			
			// Maybe we will need this 
			if (! TestConfig.nullToEmptyString(testAttributes.get("browser")).isEmpty()){
				testCaseAttributes.put("browser", testAttributes.get("browser"));
			}
			testCases.put(new Integer(++k), testCaseAttributes);
		}


		TestConfig testConfig = new TestConfig(testAttributes, testCases);
		// add test with attributes to tests' map
		testsMap.put(testIndex++, testConfig);
		/*logger.log("Test added: " 
				+ "test_" + testIndex 
				+ " = " + attributesMap);*/
	}



	/**
	 * Get tests map from XML.
	 * 	
	 * @param testXML - the test XML file (path relative to user.dir)
	 * @return - the tests' map if succeeded or empty map if failed.
	 */
	public Map<Integer, TestConfig> readTestConfig(String testXML){
		Logger logger = Logger.getLogger();

		// read & validate configuration config.xml file
		logger.log("Read configuration: " 
				+ System.getProperty("user.dir")
				+ "/" +  testXML);							
		try {
			init(System.getProperty("user.dir") + "/" + testXML);

		} catch (Exception e) {
			logger.log("Wrong configuration!!!");
			e.printStackTrace();
			return new TreeMap<Integer, TestConfig>();
		}
		
		log(testsMap);
		
		return testsMap;
	}


	
	/**
	 * @return - tests map generated from XML
	 */
	public static Map<Integer, TestConfig> getTestsMap(){
		return testsMap;
	}
	
	

	/**
	 * Get XML element's attributes
	 * 
	 * @param attributes - XML node map
	 * @return - attributes map
	 */
	public Map<String, String> getAttributes(NamedNodeMap attributes){
		Map<String, String > attrMap = new TreeMap<>();
		for (  int j=0; j<attributes.getLength(); j++ ){
			attrMap.put(attributes.item(j).getNodeName(), 
					attributes.item(j).getNodeValue());
		}
		return attrMap;
	}



	public static String getSuiteName() {
		return suiteName;
	}

	
	
	public static ResultFileType getSuiteResultFileType() {
		return resultFileType;
	}
	


	public static void setSuiteName(String suiteName) {
		XmlTestConfig.suiteName = suiteName;
	}
	
	
	
	public static void setSuiteResultFileType(String resultFileType) {
		if ( ! resultFileType.isEmpty()){
			XmlTestConfig.resultFileType = ResultFileType.valueOf(resultFileType);
		}
	}
	
	
	
	public static String getUser() {
		return XmlTestConfig.user;
	}

	
	
	private static void setUser(String user) {
		XmlTestConfig.user = user;
	}
	
	
	
	private static void setEmail(String email) {
		XmlTestConfig.emails = email;
	}
	
	
	
	
	/**
	 * Log all tests.
	 * 
	 * @param tests - tests map
	 */
	private void log(Map<Integer, TestConfig> tests) {
		tests.keySet().forEach(
				k -> {
					logger.log("Test_" + k + ": " + tests.get(k).getTestAttributes());
					tests.get(k).getTestCases().entrySet().forEach(
							e -> logger.log("TestCase " + k + "_" + e.getKey() + ": " + e.getValue())
							);
				}
				);
		
	}



	/**
	 * @return - true if set for using grid
	 */
	public static boolean getGrid() {
		return XmlTestConfig.grid;
	}



	/**
	 * @return - an array of emails addresses 
	 */
	public static String[] getEmails() {
		return XmlTestConfig.emails.split(";");
	}



	public static void setSuitePackages(List<String> suitePackages) {
		XmlTestConfig.suitePackages.addAll(suitePackages);
		
	}
	
}

