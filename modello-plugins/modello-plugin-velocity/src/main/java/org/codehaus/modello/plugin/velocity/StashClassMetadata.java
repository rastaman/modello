/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.codehaus.modello.plugin.velocity;

import org.codehaus.modello.metadata.ClassMetadata;

import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id: StashClassMetadata.java 204 2005-01-08 19:23:18Z jvanzyl $
 */
public class StashClassMetadata
    implements ClassMetadata
{
    public static String ID = StashClassMetadata.class.getName();

    private boolean storable;

    public void setStorable( boolean storable )
    {
        this.storable = storable;
    }

    public boolean isStorable()
    {
        return storable;
    }
}
