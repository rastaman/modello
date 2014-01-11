package org.codehaus.modello.plugin.velocity;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

public class AnnotationsHelperTest extends TestCase {

    private String complexAnnotation = "@Table(name=\"clients\",uniqueConstraints={@UniqueConstraint(columnNames=\"client_id\")})";
    
    public void testComplexAnnotationValue()
    {
      //uniqueConstraints={@UniqueConstraint(columnNames="client_id")})
        String complexValue = AnnotationsHelper.getValue(complexAnnotation);
        out(complexValue);
        
    }
    
    public Map getProperties( String annot )
    {
        String complexValue = AnnotationsHelper.getValue(complexAnnotation);
        Map<String,String> props = new LinkedHashMap<String,String>();
        
        return props;
    }
    
    public void out( String s )
    {
        System.out.println( s );
    }

    public void err( String s )
    {
        System.err.println( s );
    }
}
