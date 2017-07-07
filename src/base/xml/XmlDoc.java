package base.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import base.Logger;
import base.failures.TestCaseFailure;



/**
 * Class for getting information from XML file.
 * 
 * @author Dan Rusu
 *
 */
public class XmlDoc {
	Logger logger = Logger.getLogger();
	private Document Doc;



	/**
	 * Opens and parses XML input file. Sets up Doc.
	 * Possible exception: ParserConfigurationException/SAXException/IOException/DOMException
	 * 
	 * @param xmlInputFile - an XML file
	 */
	public XmlDoc( File xmlInputFile )
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		dbf.setValidating(false);
		
		//TODO -- may consider additional validation
		//dbf.setIgnoringElementContentWhitespace(true);
		dbf.setIgnoringComments(true);
		dbf.setExpandEntityReferences(false);

		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			db.setEntityResolver(null);
			
			Doc = db.parse(xmlInputFile);
			
		} catch (Exception e) {
			logger.log(""+e);
			throw new TestCaseFailure(e.getMessage());
		}
	}



	/**
	 * Create a new XML doc with root element. 
	 *
	 * @param rootName - the tagName of the root element
	 */
	public XmlDoc( String rootName )
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
		DocumentBuilder db = null;
		
		try
		{
			db = dbf.newDocumentBuilder( );
		}
		catch ( ParserConfigurationException e )
		{
			logger.log(""+e);
			throw new TestCaseFailure(e.getMessage());
		}
		
		Doc = db.newDocument( );
		final Element root = Doc.createElement( rootName );
		Doc.appendChild( root );
	}



	/**
	 * Get XML root element.
	 * 
	 * @return - XML's root element
	 */
	public Element getRoot()
	{
		return Doc.getDocumentElement();
	}	



	/**
	 * Get all children nodes for an Element.
	 * 
	 * @param Elem - XML element
	 * @return - a set of children Elements for the specified element
	 */
	public static List<Element> getChildren(Element Elem)
	{
		List<Element> childrenList = new ArrayList<>();		
		NodeList list = Elem.getChildNodes();

		for ( int i = 0 ; i < list.getLength(); ++i )
		{
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE )
			{
				childrenList.add( (Element)list.item(i));
			}	
		}				
		return childrenList;
	}


	
	// Methods for creating an XML DOM - not user for the moment
	// Useful for an XML report

	/**
	 * Get document as a transform object ( For use in outputting to FILE or stream )
	 *
	 * @return - the transform object
	 * @throws Exception - custom exception
	 */
	public DOMSource getDomSrc( ) throws Exception
	{
		if ( Doc == null )
		{
			throw new Exception( "XMLDoc - getDomSrc() - NULL Doc! " );
		}
		return new DOMSource( Doc );
	}



	/**
	 * Create a new Element to use in the document.
	 *
	 * @param tagName - the tag name for the new element
	 * @return - the new created Element 
	 */
	public Element newChild( String tagName )
	{
		return Doc.createElement( tagName );
	}

}