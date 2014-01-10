package org.codehaus.modello.plugin.jaxrs;

/**
 * Copyright 2009 Ludovic Maitre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.codehaus.modello.ModelloException;
import org.codehaus.modello.ModelloParameterConstants;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.plugin.AbstractModelloGenerator;
import org.codehaus.modello.plugin.jaxrs.metadata.JaxRSClassLevelMetadata;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * Generates the web descriptor for Jersey from the Modello model source.
 * 
 * @author <a href='mailto:ludovic.maitre@effervens.com'>Ludovic Maitre</a>
 * @version 1.0.0
 * @since 1.0.0
 * @plexus.component role="org.codehaus.modello.plugin.ModelloGenerator" role-hint="jaxrs-mapping"
 */
public class JaxRSMappingModelloGenerator
    extends AbstractModelloGenerator
{

    private final static String JERSEY_SERVLET_NAME = "JWA";

    private Namespace namespace;

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.plugin.ModelloGenerator#generate(org.codehaus.modello.model.Model,
     *      java.util.Properties)
     */
    public void generate( Model model, Properties properties )
        throws ModelloException
    {
        initialize( model, properties );

        String fileName = properties.getProperty( ModelloParameterConstants.FILENAME, "web.xml" );

        String directoryName =
            properties.getProperty( ModelloParameterConstants.OUTPUT_DIRECTORY, getOutputDirectory().getAbsolutePath() );

        File directory = new File( directoryName );
        File webDescriptor = new File( directory, fileName );
        File parent = webDescriptor.getParentFile();

        if ( !parent.exists() && !parent.mkdirs() )
            throw new ModelloException( "Error while creating parent directories for the file " + "'"
                + webDescriptor.getAbsolutePath() + "'." );
        else
        {
            getLogger().info( "Output:" + webDescriptor.getAbsolutePath() );
        }
        // all good, continue with JAX-RS servlets mapping generation
        try
        {
            generateJaxRS( webDescriptor, model );
        }
        catch ( IOException e )
        {
            if ( getLogger().isErrorEnabled() )
                getLogger().error( "Error generating web mapping " + webDescriptor.getAbsolutePath() );
        }

    }

    private void generateJaxRS( File webXml, Model model )
        throws IOException, ModelloException
    {
        OutputStreamWriter fileWriter = new OutputStreamWriter( new FileOutputStream( webXml ), "UTF-8" );

        org.dom4j.io.XMLWriter writer = null;

        namespace = DocumentHelper.createNamespace( "", "http://java.sun.com/xml/ns/j2ee" );
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "web-app" );
        root.addNamespace( "", "http://java.sun.com/xml/ns/j2ee" );
        root.addAttribute( "version", "2.4" );
        root.addAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );
        root.addAttribute( "xsi:schemaLocation",
                           "http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" );
        Element displayName = addElement( root, "display-name", model.getName() );
        // add the declaration for the Jersey servlet
        Element servlet = addElement( root, "servlet" );
        addElement( servlet, "servlet-name", JERSEY_SERVLET_NAME );
        addElement( servlet, "servlet-class", "com.sun.jersey.spi.container.servlet.ServletContainer" );
        Element pkgsParam = addElement( servlet, "init-param" );
        addElement( pkgsParam, "param-name", "com.sun.jersey.config.property.packages" );
        addElement( servlet, "load-on-startup", "1" );

        Set<String> pkgs = new HashSet<String>();

        // Processed classes to be mapped here
        for ( Iterator it = model.getClasses( getGeneratedVersion() ).iterator(); it.hasNext(); )
        {
            ModelClass modelClass = (ModelClass) it.next();

            JaxRSClassLevelMetadata metadata =
                (JaxRSClassLevelMetadata) modelClass.getMetadata( JaxRSClassLevelMetadata.ID );

            // only generate servlet-mappings for classes which have a jaxrs.urls parameter
            if ( !isEmpty( metadata.getUrls() ) )
            {
                String packageName = modelClass.getPackageName( isPackageWithVersion(), getGeneratedVersion() );
                if ( !pkgs.contains( packageName ) )
                {
                    pkgs.add( packageName );
                }

                String[] urls = metadata.getUrls().split( "," );
                for ( String url : urls )
                {
                    Element mapping = addElement( root, "servlet-mapping" );
                    addElement( mapping, "servlet-name", JERSEY_SERVLET_NAME );
                    addElement( mapping, "url-pattern", url );
                }
            }

        }

        String pkgsString = "";
        for ( String s : pkgs )
        {
            if ( !"".equals( pkgsString ) )
                pkgsString += ",";
            pkgsString += s;
        }
        addElement( pkgsParam, "param-value", pkgsString );

        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter( fileWriter, format );
        writer.write( document );
        writer.close();
    }

    private Element addElement( Element base, String elementName )
    {
        return addElement( base, elementName, null );
    }

    private Element addElement( Element base, String elementName, String elementText )
    {
        QName elem = QName.get( elementName, namespace );
        Element newElement = base.addElement( elem );
        if ( elementText != null )
            newElement.setText( elementText );
        return newElement;
    }
}
