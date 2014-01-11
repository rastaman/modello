package org.codehaus.modello.plugin.velocity;

/*
 * Copyright (c) 2004, Codehaus.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.File;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.codehaus.modello.ModelloException;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.Version;
import org.codehaus.modello.plugin.AbstractModelloGenerator;
import org.codehaus.plexus.util.WriterFactory;
import org.codehaus.plexus.velocity.VelocityComponent;

/**
 * @author <a href="mailto:jason@modello.org">Jason van Zyl</a>
 * @author <a href="mailto:evenisse@codehaus.org">Emmanuel Venisse</a>
 * @version $Id: VelocityGenerator.java 848 2007-07-24 20:57:35Z hboutemy $
 */
public class VelocityGenerator
    extends AbstractModelloGenerator
{
    public final static String DEFAULT_VELOCITY_TEMPLATE = "/modello/templates/prevayler/prevayler.vm";
    
    public final static String DEFAULT_VELOCITY_OUTPUT = "foo.txt";
    
    private VelocityComponent velocity;

    private String template;

    private String destinationFile;
    
    public void generate( Model model, Properties parameters )
        throws ModelloException
    {
        //initialize( model, parameters );

        // Take the template from the properties
        template = parameters.getProperty("template", DEFAULT_VELOCITY_TEMPLATE );
        // Put the model in the context
        destinationFile = parameters.getProperty("outputFile", DEFAULT_VELOCITY_OUTPUT );
        // Generate

        ModelClass c;

        Version version = new Version( "1.0.0 " );

        try
        {
            Context context = new VelocityContext();

            context.put( "version", version );

            context.put( "package", model.getDefaultPackageName( false, version ) );

            context.put( "id", StashClassMetadata.ID );

            context.put( "model", model );

            Writer writer = WriterFactory.newPlatformWriter( new File( destinationFile ) );

            velocity.getEngine().mergeTemplate( template, context, writer );

            writer.flush();

            writer.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
