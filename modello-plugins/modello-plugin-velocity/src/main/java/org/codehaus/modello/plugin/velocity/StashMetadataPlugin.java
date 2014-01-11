package org.codehaus.modello.plugin.velocity;

import org.codehaus.modello.ModelloException;
import org.codehaus.modello.metadata.AbstractMetadataPlugin;
import org.codehaus.modello.metadata.AssociationMetadata;
import org.codehaus.modello.metadata.ClassMetadata;
import org.codehaus.modello.metadata.FieldMetadata;
import org.codehaus.modello.metadata.InterfaceMetadata;
import org.codehaus.modello.metadata.ModelMetadata;
import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelAssociation;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.ModelField;
import org.codehaus.modello.model.ModelInterface;

import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: StashMetadataPlugin.java 201 2005-01-08 18:54:25Z jvanzyl $
 */
public class StashMetadataPlugin
    extends AbstractMetadataPlugin
{
    // ----------------------------------------------------------------------
    // Map to Metadata
    // ----------------------------------------------------------------------

    public ModelMetadata getModelMetadata( Model model, Map data )
    {
        return new StashModelMetadata();
    }

    public ClassMetadata getClassMetadata( ModelClass clazz, Map data )
    {
        StashClassMetadata classMetadata = new StashClassMetadata();

        String storable = (String) data.get( "stash.storable" );

        if ( storable != null && storable.equals( "true" ) )
        {
            classMetadata.setStorable( true );
        }

        return classMetadata;
    }

    public FieldMetadata getFieldMetadata( ModelField field, Map data )
    {
        StashFieldMetadata metadata = new StashFieldMetadata();

        return metadata;
    }

    public AssociationMetadata getAssociationMetadata( ModelAssociation association, Map data )
    {
        return new StashAssociationMetadata();
    }

	public InterfaceMetadata getInterfaceMetadata(ModelInterface intf,
			Map<String, String> data) throws ModelloException {
		return new StashInterfaceMetadata();
	}

    // ----------------------------------------------------------------------
    // Metadata to Map
    // ----------------------------------------------------------------------

    public Map getFieldMap( ModelField field, FieldMetadata metadata )
    {
        return Collections.EMPTY_MAP;
    }

}
