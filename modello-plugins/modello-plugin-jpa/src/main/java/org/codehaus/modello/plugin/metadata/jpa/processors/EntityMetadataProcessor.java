/**
 * 
 */
package org.codehaus.modello.plugin.metadata.jpa.processors;

import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.plugin.jpa.JpaOrmMappingModelloGenerator;
import org.codehaus.modello.plugin.jpa.metadata.JpaMetadataPlugin;
import org.codehaus.modello.plugin.jpa.metadata.JpaModelMetadata;
import org.codehaus.modello.plugin.metadata.jpa.EntityProcessorMetadata;
import org.codehaus.modello.plugin.metadata.processor.ClassMetadataProcessorMetadata;
import org.codehaus.modello.plugin.metadata.processor.MetadataProcessor;
import org.codehaus.modello.plugin.metadata.processor.MetadataProcessorContext;
import org.codehaus.modello.plugin.metadata.processor.MetadataProcessorException;
import org.codehaus.modello.plugin.metadata.processor.ProcessorMetadata;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Processes any {@link EntityProcessorMetadata} instances encountered.
 * 
 * @author <a href='mailto:rahul.thakur.xdev@gmail.com'>Rahul Thakur</a>
 * @since 1.0.0
 * @version $Id: EntityMetadataProcessor.java 804M 2009-07-18 11:10:11Z (local) $
 * @plexus.component role="org.codehaus.modello.plugin.metadata.processor.MetadataProcessor"
 *                   role-hint="entity"
 */
public class EntityMetadataProcessor
    implements MetadataProcessor
{

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.plugin.metadata.processor.MetadataProcessor#process(MetadataProcessorContext,
     *      org.codehaus.modello.plugin.metadata.processor.ProcessorMetadata)
     */
    public void process( MetadataProcessorContext context, ProcessorMetadata metadata )
        throws MetadataProcessorException
    {
        ModelClass modelClass = ( (ClassMetadataProcessorMetadata) metadata ).getModelClass();
        String packageName = ( (ClassMetadataProcessorMetadata) metadata ).getpackageName();

        Document doc = context.getDocument();
        Element rootElement = doc.getRootElement();
        Model m = context.getModel();
        JpaModelMetadata md = (JpaModelMetadata) m.getMetadata( JpaModelMetadata.ID );
        if ( JpaMetadataPlugin.JPA_VERSION_2.equals( md.getVersion() ) )
        {
            Element pu = rootElement.element( "persistence-unit" );
            Element entity = JpaOrmMappingModelloGenerator.addElement( pu, "class" );
            entity.setText( packageName + "." + modelClass.getName() );
        } else {
            Element entity = JpaOrmMappingModelloGenerator.addElement( rootElement, "entity" );
            entity.addAttribute( "class", packageName + "." + modelClass.getName() );
            entity.addAttribute( "access", "property" );
            entity.addAttribute( "metadata-complete", "true" );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.codehaus.modello.plugin.metadata.processor.MetadataProcessor#validate(MetadataProcessorContext,
     *      org.codehaus.modello.plugin.metadata.processor.ProcessorMetadata)
     */
    public boolean validate( MetadataProcessorContext context, ProcessorMetadata metadata )
    {
        // TODO Auto-generated method stub
        return true;
    }

}
