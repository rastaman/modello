package org.codehaus.modello.generator.database.sql;

/*
 * Copyright (c) 2004, Codehaus.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.IOException;

import org.codehaus.modello.generator.database.model.Column;
import org.codehaus.modello.generator.database.model.Table;

/**
 * An SQL Builder for Oracle
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision$
 */
public class Db2Builder extends SqlBuilder
{

    public Db2Builder()
    {
        setPrimaryKeyEmbedded( false );
        setForeignKeyConstraintsNamed( true );
    }

    public void dropTable( Table table ) throws IOException
    {
        super.dropTable( table );
        print( "drop sequence if exists  " );
        print( table.getName() );
        print( ".SequenceName" );
        printEndOfStatement();
    }

    protected void printAutoIncrementColumn( Table table, Column column ) throws IOException
    {
        print( "GENERATED ALWAYS AS IDENTITY" );
    }
}