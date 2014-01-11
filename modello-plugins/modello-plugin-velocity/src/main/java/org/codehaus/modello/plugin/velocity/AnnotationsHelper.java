package org.codehaus.modello.plugin.velocity;

import org.codehaus.modello.model.BaseElement;

public class AnnotationsHelper {

    public static String getAnnotationValue( Object target, String annotationName, String annotationProperty )
    {
        if ( target instanceof BaseElement && annotationName != null && annotationProperty != null )
        {
            for ( String s : ((BaseElement)target).getAnnotations() )
            {
                if ( annotationName.equals( getSource( s ) ) )
                {
                    String v = getValue( s );
                    String[] keysAndValues = v.split(",");
                    for ( int i = 0; i < keysAndValues.length; i++ )
                    {
                        String[] kv = keysAndValues[ i ].split("=");
                        if ( annotationProperty.equals( kv[0] ) )
                        {
                            return kv[1];
                        }
                    }
                }
            }
        }
        return null;
    }

    public static boolean hasAnnotationProperty( Object target, String annotationName, String annotationProperty )
    {
        if ( target instanceof BaseElement && annotationName != null && annotationProperty != null )
        {
            for ( String s : ((BaseElement)target).getAnnotations() )
            {
                if ( annotationName.equals( getSource( s ) ) )
                {
                    String v = getValue( s );
                    String[] keysAndValues = v.split(",");
                    for ( int i = 0; i < keysAndValues.length; i++ )
                    {
                        String[] kv = keysAndValues[ i ].split("=");
                        if ( annotationProperty.equals( kv[0] ) )
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean hasAnnotation( Object target, String annotationName )
    {
        if ( target instanceof BaseElement && annotationName != null )
        {
            for ( String s : ((BaseElement)target).getAnnotations() )
            {
                if ( annotationName.equals( getSource( s ) ) )
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static String getSource( String annot )
    {
        String source = null;
        if ( annot != null && annot.startsWith( "@" ) )
        {
            if ( annot.indexOf( "(" ) > -1 )
            {
                source = annot.substring( 1, annot.indexOf( "(" ) );
            }
            else
            {
                source = annot.substring( 1 );
            }
        }
        return source;
    }
    
    public static String getValue( String annot )
    {
        String value = null;
        if ( annot != null && annot.startsWith( "@" ) )
        {
            if ( annot.indexOf( "(" ) > -1 )
            {
                value = annot.substring( annot.indexOf( "(" ) + 1, annot.lastIndexOf( ")" ) );
            }
            else
            {
                value = "";
            }
        }
        return value;
    }

}
