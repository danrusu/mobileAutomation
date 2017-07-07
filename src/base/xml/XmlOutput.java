package base.xml;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import base.Logger;

public class XmlOutput
{
	Logger logger = Logger.getLogger();
	private File Output;
	
	

	/**
	 * Save to outputFile from outputDoc object.
	 * 
	 * @param outputFile - file on disk
	 * @param outputDoc - XMLDoc document
	 */
	protected void saveOutput( File outputFile , XmlDoc outputDoc )
	{
		final Logger logger = Logger.getLogger( );
		final TransformerFactory transformerFactory = TransformerFactory.newInstance( );
		Transformer transformer = null;
		try
		{
			transformer = transformerFactory.newTransformer( );
			transformer.setOutputProperty( OutputKeys.INDENT , "yes" );

		}
		catch ( final TransformerConfigurationException e )
		{
			logger.log( "XMLOutput save" + e );
		}
		final StreamResult result = new StreamResult( outputFile );
		try
		{
			transformer.transform( outputDoc.getDomSrc( ) , result );
		}
		catch ( final Exception e )
		{
			logger.log( "XMLOutput save" + e );
		}
	}



	/**
	 * Save XmlDoc in an XML file.
	 * 
	 * @param outputDoc - the results XmlDoc
	 */
	public void saveOutput( XmlDoc outputDoc )
	{
		// TODO -- generate file name for output here
		final Logger logger = Logger.getLogger( );
		try
		{
			Output = new File( logger.getLogDirPath() + outputDoc.getRoot( ).getAttribute( "name" ) + "_result.xml" );
			logger.log( "XMlOutput output " + Output.getPath( ) );
			saveOutput( Output , outputDoc );

		}
		catch ( final Exception e )
		{
			logger.log( "XMlOutput caught exception" + e );
		}

	}



	/**
	 * Get output file.
	 * 
	 * @return - output file
	 */
	public File getOutput( )
	{
		return Output;
	}

}
