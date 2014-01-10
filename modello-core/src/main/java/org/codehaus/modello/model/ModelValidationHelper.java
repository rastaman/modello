package org.codehaus.modello.model;

public class ModelValidationHelper {

    public static boolean isPrimitiveType( String type)
    {
        // TODO: This should not happen
        if ( type == null )
        {
            return false;
        }

        for ( int i = 0; i < ModelField.PRIMITIVE_TYPES.length; i++ )
        {
            String validType = ModelField.PRIMITIVE_TYPES[i];

            if ( type.equals( validType ) || type.toLowerCase().equals( validType ) )
            {
                return true;
            }
        }

        return false;
    }

	/*!ModelDefault.isBaseType( type ) &&*/
    public static boolean isImported( ModelClass c, String t ) {
    	// cut the array part
    	if ( t.endsWith("[]") )
    		t = t.substring(0,t.length()-2);
    	if ( isPrimitiveType(t) )
    		return true;
        String[] imports = c.getModel().getDefault( ModelDefault.DEFAULT_IMPORTS ).getValue().split( "," );
        boolean valid = false;
        String pkg = null;
        if ( t.lastIndexOf( '.' ) > -1 )
        {
            pkg = t.substring( 0, t.lastIndexOf( '.' ) ) + ".*";
        }
        for ( int i = 0; i < imports.length; i++ )
        {
        	String importType = imports[i];
        	String pkgImport = null;
        	if ( importType.indexOf('.') > -1 ) {
        		pkgImport = importType.substring(0,importType.lastIndexOf('.'));
        	}
            if ( importType.equals( t ) || ( importType.equals( pkgImport + '.' + t )))
            {
                valid = true;
                break;
            }
            else if ( pkg != null )
            {
                if ( importType.equals( pkg ) )
                {
                    valid = true;
                    break;
                }
            }
        }
        if ( !valid )
        	return false;
        return true;
    }

}
