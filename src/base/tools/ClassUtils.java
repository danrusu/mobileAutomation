package base.tools;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import base.failures.TestCaseFailure;

/**
 * Class actions.
 * 
 * @author dan.rusu
 *
 */
public class ClassUtils {

	/**
	 * Get simple class name for current Class.
	 * 
	 * @return - current class' name
	 */
	public static String getClassName() {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		int lastIndex = className.lastIndexOf('.');
		return className.substring(lastIndex + 1);
	}



	/**
	 * Get a list of classes within a jar package.
	 * This will be used for extracting test documentation from jars.
	 * 
	 * @param jarName - the jar for searching the package
	 * @param packageName - the package for searching the classes
	 * @return - a list of all classes names that are in the specified package
	 */
	public static List<String> getClassesNamesInPackage(
			String jarName, 
			List<String> suitePackages){
		List<String> classes = new ArrayList<> ();

		for(String packageName : suitePackages){
			packageName = packageName.replaceAll("\\." , "/");

			try(JarInputStream jarFile = new JarInputStream
					(new FileInputStream (jarName));)
			{

				JarEntry jarEntry;
				while(true) {
					jarEntry=jarFile.getNextJarEntry ();
					if(jarEntry == null){
						break;
					}
					if((jarEntry.getName ().startsWith (packageName)) &&
							(jarEntry.getName ().endsWith (".class")) ) {

						classes.add (jarEntry.getName()
								.replaceAll("/", "\\.")
								.replaceAll("\\.class", ""));
					}
				}
			}
			catch( Exception e){
				e.printStackTrace ();
			}
		}
		return classes;
	}


	
	/**
	 * Find class by name in a list of packages.
	 * 
	 * @param testCaseName - class name to search for
	 * @param suitePackages - a list of packages to search in
	 * @return - first class found in the packages list
	 */
	public static Class<?> findClass(String testCaseName, List<String> suitePackages) {
		Class<?> testClass = null;
		String failure = "";
		for (String suitePackage : suitePackages){

			try {
				String className = suitePackage + "." + testCaseName;
				testClass = Class.forName( className );
				break;
			}
			catch(ClassNotFoundException cnfEx){
				failure = cnfEx.getMessage();
			}

		}
		// if class not found
		if (testClass == null){
			throw new TestCaseFailure("Class <" + testCaseName +"> was not found"
					+ " in the suite packages: " + Arrays.asList(suitePackages)
					+ "failure: " + failure);
		}

		return testClass;
	}
}
