package org.codehaus.modello.plugin.jpa.metadata;

/**
 * Copyright 2007 Rahul Thakur
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

import org.codehaus.modello.metadata.ModelMetadata;

/**
 * @author <a href='mailto:rahul.thakur.xdev@gmail.com'>Rahul Thakur</a>
 * @version $Id: JpaModelMetadata.java 793M 2009-07-18 10:59:28Z (local) $
 * @since 1.0.0
 */
public class JpaModelMetadata
    implements ModelMetadata
{

    public static final String ID = JpaModelMetadata.class.getName();
    
    private String version = JpaMetadataPlugin.JPA_VERSION_1;

    private String unit = JpaMetadataPlugin.JPA_DEFAULT_UNIT;
    
    public String getVersion()
    {
        return version;
    }

    public void setVersion( String jpaVersion )
    {
        this.version = jpaVersion;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit( String unit )
    {
        this.unit = unit;
    }

}
