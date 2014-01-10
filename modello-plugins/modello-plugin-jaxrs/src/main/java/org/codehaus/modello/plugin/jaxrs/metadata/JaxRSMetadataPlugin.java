package org.codehaus.modello.plugin.jaxrs.metadata;

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

import java.util.Map;

import org.codehaus.modello.ModelloException;
import org.codehaus.modello.metadata.AbstractMetadataPlugin;
import org.codehaus.modello.metadata.AssociationMetadata;
import org.codehaus.modello.metadata.ClassMetadata;
import org.codehaus.modello.metadata.FieldMetadata;
import org.codehaus.modello.metadata.InterfaceMetadata;
import org.codehaus.modello.metadata.MetadataPlugin;
import org.codehaus.modello.metadata.ModelMetadata;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelAssociation;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.ModelField;
import org.codehaus.modello.model.ModelInterface;
import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.context.Context;
import org.codehaus.plexus.context.ContextException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Contextualizable;

/**
 * A {@link MetadataPlugin} extension that processes JAXRS specific metadata.
 * 
 * @author <a href='mailto:ludovic.maitre@effervens.com'>Ludovic Maitre</a>
 * @version 1.0.0
 * @since 1.0.0
 * @plexus.component role="org.codehaus.modello.metadata.MetadataPlugin" role-hint="jaxrs"
 */
public class JaxRSMetadataPlugin
    extends AbstractMetadataPlugin
    implements Contextualizable
{

    /**
     * Prefix that identifies the attributes meant for consumption by the Modello JAXRS Plugin.
     */
    private static final String PLUGIN_PREFIX = "jaxrs.";

    public final static String JAXRS_VERSION = "version";

    public final static String JAXRS_IMPL = "impl";

    public final static String JAXRS_PATH = "path";

    public final static String JAXRS_URLS = "urls";

    public final static String JAXRS_VERSION_1 = "1.0";

    public final static String JAXRS_VERSION_1_1 = "1.1";
    
    public final static String JAXRS_VERSION_2 = "2.0";

    /**
     * For Metadata lookups.
     */
    private PlexusContainer container;

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.plexus.personality.plexus.lifecycle.phase.Contextualizable#contextualize(org.codehaus.plexus.context.Context)
     */
    public void contextualize( Context context )
        throws ContextException
    {
        this.container = (PlexusContainer) context.get( PlexusConstants.PLEXUS_KEY );
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.metadata.MetadataPlugin#getAssociationMetadata(org.codehaus.modello.model.ModelAssociation,
     *      java.util.Map)
     */
    public AssociationMetadata getAssociationMetadata( ModelAssociation modelAssociation, Map data )
        throws ModelloException
    {
        return new JaxRSAssociationLevelMetadata();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.metadata.MetadataPlugin#getClassMetadata(org.codehaus.modello.model.ModelClass,
     *      java.util.Map)
     */
    public ClassMetadata getClassMetadata( ModelClass modelClass, Map data )
        throws ModelloException
    {
        JaxRSClassLevelMetadata metadata = new JaxRSClassLevelMetadata();

        metadata.setPath( getString( data, PLUGIN_PREFIX + JAXRS_PATH ) );
        metadata.setUrls( getString( data, PLUGIN_PREFIX + JAXRS_URLS ) );

        return metadata;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.metadata.MetadataPlugin#getFieldMetadata(org.codehaus.modello.model.ModelField,
     *      java.util.Map)
     */
    public FieldMetadata getFieldMetadata( ModelField model, Map data )
        throws ModelloException
    {
        return new JaxRSFieldLevelMetadata();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.metadata.MetadataPlugin#getModelMetadata(org.codehaus.modello.model.Model,
     *      java.util.Map)
     */
    public ModelMetadata getModelMetadata( Model model, Map data )
        throws ModelloException
    {
        JaxRSModelMetadata metadata = new JaxRSModelMetadata();

        metadata.setVersion( getString( data, PLUGIN_PREFIX + JAXRS_VERSION ) );

        return metadata;
    }

	public InterfaceMetadata getInterfaceMetadata(ModelInterface iface,
			Map<String, String> data) throws ModelloException {
		return new JaxRSInterfaceMetadata( data );
	}

}
