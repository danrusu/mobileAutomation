package base.tools;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import base.failures.TestCaseFailure;

public class ClassUtils {

	/**
	 * Get simple class name for current Class.
	 * 
	 * @return
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
	 * @param packageName - the package fo searching the classes
	 * @return - a list of all classes names that are in the specified package
	 */
	public static List<String> getClassesNamesInPackage(
			String jarName, 
			List<String> suitePackages){
		List<String> classes = new ArrayList<> ();

		for(String packageName : suitePackages){
			packageName = packageName.replaceAll("\\." , "/");

			/*System.out.println(
				"Jar " + jarName + " looking for " + packageName);*/

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
						/*System.out.println
					("Found " + jarEntry.getName().replaceAll("/", "\\."));*/
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


	public static Class<?> findClass(String testCaseName, List<String> suitePackages) {
		Class<?> testClass = null;
		for (String suitePackage : suitePackages){

			try {
				String className = suitePackage + "." + testCaseName;
				testClass = Class.forName( className );
				break;
			}
			catch(ClassNotFoundException cnfEx){
				/*Logger.getLogger().log("Class <" +  testCaseName 
						+ "> was not found in <" + suitePackage + "> package.");*/
			}

		}
		// if class not found
		if (testClass == null){
			throw new TestCaseFailure("Class <" + testCaseName +"> was not found"
					+ " in the suite packages: " + Arrays.asList(suitePackages) );
		}

		return testClass;
	}
	
	
	
	public static List<String> findPackageNamesEndingWith(String suffix) {
	    List<String> result = new ArrayList<>();
	    System.out.println(Package.getPackages());
	    for(Package p : Package.getPackages()) {
	        if (p.getName().endsWith(suffix)) {
	           result.add(p.getName());
	        }
	    }
	    return result;
	}
}
