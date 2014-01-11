package org.codehaus.modello.plugin.velocity;

import org.codehaus.modello.model.ModelClass;
import org.codehaus.modello.model.ModelField;

public class RooClassWrapper {

    private ModelClass modelClass;
    
    public RooClassWrapper( ModelClass mc )
    {
        this.modelClass = mc;
    }

    /**
     * @return the modelClass
     */
    public ModelClass getModelClass() {
        return modelClass;
    }

    /**
     * @param modelClass the modelClass to set
     */
    public void setModelClass(ModelClass modelClass) {
        this.modelClass = modelClass;
    }
    
    public boolean isEntity() {
        return AnnotationsHelper.hasAnnotation( modelClass, "Entity");
    }
    
    /*
    <class jpa.entity="true" sc.generators="model,view,controller">
      <name>Client</name>
      <packageName>com.effervens.proambu.model</packageName>
      <annotations>
        <annotation>@Entity</annotation>
        <annotation>@XmlRootElement</annotation>
        <annotation>@Table(name="clients",uniqueConstraints={@UniqueConstraint(columnNames="client_id")})</annotation>
        <annotation>@XmlAccessorType(XmlAccessType.FIELD)</annotation>
        <annotation>@FormClass(name="Client")</annotation>
      </annotations>
      <version>1.0.0</version>
      <superClass>BaseObject</superClass>
      <fields>
        <field>
          <name>nomClient</name>
          <type>String</type>
          <annotations>
            <annotation>@FormField(name="Nom",order=1)</annotation>
          </annotations>
          <version>1.0.0</version>
        </field>
        <field>
          <name>prenomClient</name>
          <type>String</type>
          <annotations>
            <annotation>@FormField(name="Prénom",order=2)</annotation>
          </annotations>
          <version>1.0.0</version>
        </field>
 */
    public String getEntityString()
    {
        String base = this.isEntity() ? "entity" : "class";
        StringBuilder sb = new StringBuilder(base + " --class "  + getFullClassName() );
        /*
--class The name of the class to create; no default value (mandatory)
--rooAnnotations Whether the generated class should have common Roo annotations; default if option present: 'true'; default if option not present: 'false'
--path Source directory to create the class in; default: 'SRC_MAIN_JAVA'
--extends The superclass (defaults to java.lang.Object); default if option not present: 'java.lang.Object'
--abstract Whether the generated class should be marked as abstract; default if option present: 'true'; default if option not present: 'false'
--permitReservedWords Indicates whether reserved words are ignored by Roo; default if option present: 'true'; default if option not present: 'false'        
         */
        if ( modelClass.hasSuperClass() )
        {
            sb.append(" --extends "+ modelClass.getSuperClass() );
        }
        //if ( modelClass.getModifier().contains("abstract") )
        //{
        //}
        sb.append( " --testAutomatically " );
        if ( this.hasTable() )
        {
            sb.append( " --table " ).append( this.getTableName() );
        }
        if ( this.getIdentifierField() != null )
        {
            sb.append( " --identifierField " ).append( this.getIdentifierField() );
        }
        if ( this.getIdentifierColumn() != null )
        {
            sb.append( " --identifierColumn " ).append( this.getIdentifierColumn() );
        }
        return sb.toString();
    }
    
    public String getIdentifierField()
    {
        //uniqueConstraints={@UniqueConstraint(columnNames="client_id")})
        for ( ModelField mf : modelClass.getAllFields() )
        {
            if ( AnnotationsHelper.hasAnnotation( mf , "Id" ) )
                return mf.getName();
        }
        return null;
    }
    
    public String getIdentifierColumn()
    {
        for ( ModelField mf : modelClass.getAllFields() )
        {
            if ( AnnotationsHelper.hasAnnotation( mf , "Column" ) && AnnotationsHelper.hasAnnotation( mf , "Id" ) )
                return AnnotationsHelper.getAnnotationValue(mf, "Column", "name").replaceAll("\"", "");
        }
        return null;
    }

    public String getTableName()
    {
        return AnnotationsHelper.getAnnotationValue( modelClass, "Table", "name" ).replaceAll("\"", "");
    }
    
    public boolean hasTable()
    {
        return AnnotationsHelper.hasAnnotation( modelClass , "Table" );
    }

    public String getFullClassName()
    {
        return modelClass.getPackageName() + "." + modelClass.getName();
    }
    /**
     * 
     * @param mf
     * @return
     */
    public String getFieldString( ModelField mf )
    {
        /*
field string --fieldName name --notNull --sizeMin 2 
field string --fieldName address --sizeMax 30 
field number --fieldName total --type java.lang.Float 
field date --fieldName deliveryDate --type java.util.Date 
field set --fieldName pizzas --element ~.domain.Pizza
         */
        StringBuilder sb = new StringBuilder();
        if ( RooTypes.getRooType( mf.getType() ) != null )
        {
            sb = sb.append( "field " + RooTypes.getRooType( mf.getType() ) + " --class " + getFullClassName() );
            sb.append( " --fieldName " + mf.getName() );
            if ( RooTypes.hasJavatype( mf.getType() ) )
            {
                sb.append( " --type " ).append( mf.getType() );
            }
        }
        else
        {
            //sb = sb.append( "field " + RooTypes.getRooType( mf.getType() ) + " --class " + getFullClassName() );
        }
        return sb.toString();
    }
    
    
}
