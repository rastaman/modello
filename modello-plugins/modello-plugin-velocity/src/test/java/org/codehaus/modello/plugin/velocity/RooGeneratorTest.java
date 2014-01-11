package org.codehaus.modello.plugin.velocity;

import java.util.Properties;

import org.codehaus.modello.AbstractModelloGeneratorTest;
import org.codehaus.modello.core.ModelloCore;
import org.codehaus.modello.model.Model;
import org.codehaus.plexus.util.ReaderFactory;

public class RooGeneratorTest extends AbstractModelloGeneratorTest
{
    public RooGeneratorTest()
    {
        super( "velocity" );
    }

    /*
    public void testVelocityGenerator()
        throws Exception
    {
        ModelloCore core = (ModelloCore) lookup( ModelloCore.ROLE );

        Properties p = new Properties();
        p.setProperty( "outputFile", "proambu.roo" );
        p.setProperty( "template", "/modello/templates/roo/roo.vm" );
        Model model = core.loadModel( ReaderFactory.newXmlReader( getTestFile( "src/test/resources/proambu.mdo" ) ) );
        core.generate( model, "velocity", p );
    }*/

    public void testStandaloneGenerator()
    throws Exception
    {
        ModelloCore core = (ModelloCore) lookup( ModelloCore.ROLE );
        Properties p = new Properties();
        p.setProperty( "outputFile", "proambu.roo" );
        p.setProperty( "template", "/modello/templates/roo/roo.vm" );
        Model model = core.loadModel( ReaderFactory.newXmlReader( getTestFile( "src/test/resources/proambu.mdo" ) ) );
        new RooGenerator().generate( model );
    }

}
