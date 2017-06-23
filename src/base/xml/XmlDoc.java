package base.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import base.Logger;



/**
 * Class for getting information from XML file.
 * 
 * @author Dan Rusu
 *
 */
public class XmlDoc {
	Logger logger = Logger.getLogger();
	private org.w3c.dom.Document Doc;



	/**
	 * Opens and parses XML input file.
	 * 
	 * @throws DOMException - for any 
	 * @param xmlInputFile - an XML file
	 * @throws Exception - ParserConfigurationException/SAXException/IOException
	 */
	public XmlDoc( File xmlInputFile ) throws Exception
	{
		Doc = null;
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
		} catch (ParserConfigurationException e) {
			logger.log(""+e);
			throw e;
		}

		try {			
			Doc = db.parse(xmlInputFile);
		} catch (SAXException e) {
			logger.log(""+e);
			throw e;
		} catch (IOException e) {
			logger.log(""+e);
			throw e;
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
		catch ( final ParserConfigurationException e )
		{
			e.printStackTrace( );
		}
		Doc = db.newDocument( );
		final Element root = Doc.createElement( rootName );
		Doc.appendChild( root );
	}



	/**
	 * Get xml root.
	 * 
	 * @return - XML's root element
	 */
	public Element getRoot()
	{
		return Doc.getDocumentElement();
	}	



	/**
	 *  Create a set of all children for some Element.
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



	/**
	 * Get document as a transform object ( For use in outputting to FILE or stream )
	 *
	 * @return - the transform object
	 * @throws Exception - when dom is null
	 */
	public DOMSource getDomSrc( ) throws Exception
	{
		if ( Doc == null )
		{
			throw new Exception( "XMLDoc - getDomSrc() - dom NULL! " );
		}
		return new DOMSource( Doc );
	}



	/**
	 * create a new Element for use in the document
	 *
	 * @param tagName - the tag name for the new element
	 * @return - the new created Element 
	 */
	public Element newChild( String tagName )
	{
		return Doc.createElement( tagName );
	}

}




