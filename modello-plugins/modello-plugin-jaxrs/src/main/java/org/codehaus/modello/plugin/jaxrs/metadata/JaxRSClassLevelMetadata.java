package org.codehaus.modello.plugin.jaxrs.metadata;

import org.codehaus.modello.metadata.ClassMetadata;

/**
 * Wraps the <b>Class</b> level JAXRS metadata.
 * @author <a href='mailto:ludovic.maitre@effervens.com'>Ludovic Maitre</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class JaxRSClassLevelMetadata
    implements ClassMetadata
{

    public static final String ID = JaxRSClassLevelMetadata.class.getName();

    private String urls;

    private String path;

    public String getPath()
    {
        return path;
    }

    public void setPath( String path )
    {
        this.path = path;
    }

    public String getUrls()
    {
        return urls;
    }

    public void setUrls( String urls )
    {
        this.urls = urls;
    }

}
