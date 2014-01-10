/**
 * 
 */
package org.codehaus.modello.plugin.metadata.processor;

/**
 * TODO:
 * 
 * @author <a href='mailto:rahul.thakur.xdev@gmail.com'>Rahul Thakur</a>
 * @version $Id: ProcessorMetadata.java 794 2007-02-03 22:03:26Z rahul $
 * @since 1.0.0
 */
public interface ProcessorMetadata
{

    /**
     * Returns a key that can be used to look up {@link MetadataProcessor} for
     * this metadata.
     * 
     * @return
     */
    public String getKey();

}
