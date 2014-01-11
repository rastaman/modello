package org.codehaus.modello.plugin.velocity;

import java.io.PrintStream;
import java.util.List;

import org.codehaus.modello.model.Model;
import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.ModelField;

public class RooGenerator {

    private PrintStream outputStream = System.out;
    
    private PrintStream errorStream = System.err;

    public RooGenerator()
    {
        //
    }
    
    public void generate( Model model )
    {
        out( "project --topLevelPackage " + model.getDefaultPackageName( false, null) );
        List<ModelClass> allClasses = model.getClasses( model.getVersionRange().getToVersion() );
        for( ModelClass mc : allClasses )
        {
            RooClassWrapper rooClass = new RooClassWrapper( mc );
            out( rooClass.getEntityString() );
            for ( ModelField mf : mc.getAllFields() )
            {
                out( rooClass.getFieldString( mf ) );
            }
        }
    }
    
    public void out( String s )
    {
        outputStream.println( s );
    }

    public void err( String s )
    {
        errorStream.println( s );
    }

    /**
     * @return the outputStream
     */
    public PrintStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream the outputStream to set
     */
    public void setOutputStream(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * @return the errorStream
     */
    public PrintStream getErrorStream() {
        return errorStream;
    }

    /**
     * @param errorStream the errorStream to set
     */
    public void setErrorStream(PrintStream errorStream) {
        this.errorStream = errorStream;
    }
}
