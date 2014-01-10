package org.codehaus.modello.plugin.jaxrs;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.codehaus.modello.AbstractModelloGeneratorTest;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.core.ModelloCore;
import org.codehaus.modello.model.Model;
import org.codehaus.plexus.util.ReaderFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * @author <a href='mailto:ludovic.maitre@effervens.com'>Ludovic Ma”tre</a>
 * @since 1.0.0
 */
public class JaxRSMappingModelloGeneratorTest
    extends AbstractModelloGeneratorTest
{

    private static final String WEB_XML = "web.xml";

    private static final String TEST_FILE = "src/test/resources/users.mdo";// "src/test/resources/continuum-jaxrs.xml";

    /**
     * @param arg0
     */
    public JaxRSMappingModelloGeneratorTest()
    {
        super( "jaxrs-mapping" );
    }

    public void testJaxRSMappingGeneration()
        throws Exception
    {

        ModelloCore core = (ModelloCore) lookup( ModelloCore.ROLE );

        Model model = core.loadModel( ReaderFactory.newXmlReader( getTestFile( getBasedir(), TEST_FILE ) ) );

        Properties parameters = new Properties();

        parameters.setProperty( ModelloParameterConstants.OUTPUT_DIRECTORY, this.getOutputDirectory().getAbsolutePath() );

        parameters.setProperty( ModelloParameterConstants.VERSION, "1.0" );

        parameters.setProperty( ModelloParameterConstants.PACKAGE_WITH_VERSION, Boolean.FALSE.toString() );

        core.generate( model, "jaxrs-mapping", parameters );

        assertTrue( new File( this.getOutputDirectory(), WEB_XML ).exists() );

        // verify structure of generated web.xml
        SAXReader reader = new SAXReader();
        Document webDoc = reader.read( new File( this.getOutputDirectory(), WEB_XML ) );
        assertNotNull( webDoc );

        Element rootElement = webDoc.getRootElement();
        assertNotNull( rootElement );
        assertEquals( "web-app", rootElement.getName() );

        rootElement.addNamespace( "j2ee", "http://java.sun.com/xml/ns/j2ee" );
        XPath expression = DocumentHelper.createXPath( "/j2ee:web-app/j2ee:servlet-mapping" );
        List nodes = expression.selectNodes( webDoc );
        assertNotNull( nodes );
        assertEquals( 4, nodes.size() );
    }
}
