package org.codehaus.modello.plugin.velocity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RooTypes {

    private static Set<String> noJavaTypes = new HashSet<String>();
    
    private static Map<String,String> types = new HashMap<String,String>();
    
    private static Map<String,String> javaTypes = new HashMap<String,String>();
    
    /*
    field string --fieldName name --notNull --sizeMin 2 
    field string --fieldName address --sizeMax 30 
    field number --fieldName total --type java.lang.Float 
    field date --fieldName deliveryDate --type java.util.Date 
    field set --fieldName pizzas --element ~.domain.Pizza
             */
    static {
        types.put("String", "string");
        types.put("Boolean", "boolean");
        types.put("Date", "date");
        types.put("int", "number");
        noJavaTypes.add("String");
    }
    
    public static String getRooType( String modelloType )
    {
        return types.containsKey(modelloType) ? types.get(modelloType) : modelloType;
    }
    
    public static String getJavaType( String modelloType )
    {
        return javaTypes.containsKey(modelloType) ? javaTypes.get(modelloType) : modelloType;
    }
    
    public static boolean hasJavatype( String modelloType )
    {
        return !noJavaTypes.contains( modelloType );
    }

}
