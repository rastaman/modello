package org.codehaus.modello.generator.xml.xpp3;

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

import org.apache.maven.model.*;
import org.apache.maven.model.io.xpp3.*;
import org.codehaus.modello.generator.*;
import org.codehaus.modello.verifier.*;
import java.io.*;
import java.util.*;

/**
 * @author <a href="mailto:trygvis@inamo.no">Trygve Laugst&oslash;l</a>
 * @version $Id: Xpp3Verifier.java 201 2005-01-08 18:54:25Z jvanzyl $
 */
public class Xpp3Verifier
    extends Verifier
{
    private String expectedXml =
        "<model extender=\"/foo/bar\" modelVersion=\"4.0.0\">\n" +
        "  <type>jar</type>\n" +
        "  <name>Maven</name>\n" +
        "  <mailingLists>\n" +
        "    <mailingList>\n" +
        "      <name>Mailing list</name>\n" +
        "      <subscribe>Super Subscribe</subscribe>\n" +
        "      <unsubscribe>Duper Unsubscribe</unsubscribe>\n" +
        "      <archive>�ber Archive</archive>\n" +
        "    </mailingList>\n" +
        "  </mailingLists>\n" +
        "  <scm>\n" +
        "    <connection>connection</connection>\n" +
        "    <developerConnection>developerConnection</developerConnection>\n" +
        "    <url>url</url>\n" +
        "  </scm>\n" +
        "  <builder>\n" +
        "    <sourceDirectory>src/main/java</sourceDirectory>\n" +
        "    <unitTestSourceDirectory>src/test/java</unitTestSourceDirectory>\n" +
        "    <sourceModifications>\n" +
        "      <sourceModification>\n" +
        "        <className>excludeEclipsePlugin</className>\n" +
        "        <directory>foo</directory>\n" +
        "        <filtering>false</filtering>\n" +
        "        <excludes>\n" +
        "          <exclude>de/abstrakt/tools/codegeneration/eclipse/*.java</exclude>\n" +
        "        </excludes>\n" +
        "      </sourceModification>\n" +
        "    </sourceModifications>\n" +
        "    <unitTest />\n" +
        "  </builder>\n" +
//        "  <contributors />\n" +
//        "  <developers />\n" +
        "</model>";

    /**
     * TODO: Add a association thats not under the root element
     */ 
    public void verify()
        throws Exception
    {
        Model expected = new Model();

        expected.setExtend( "/foo/bar" );

        expected.setName( "Maven" );

        expected.setModelVersion( "4.0.0" );

        MailingList mailingList = new MailingList();

        mailingList.setName( "Mailing list" );

        mailingList.setSubscribe( "Super Subscribe" );

        mailingList.setUnsubscribe( "Duper Unsubscribe" );

        mailingList.setArchive( "�ber Archive" );

        expected.addMailingList( mailingList );

        Scm scm = new Scm();

        String connection = "connection";

        String developerConnection = "developerConnection";

        String url = "url";

        scm.setConnection( connection );

        scm.setDeveloperConnection( developerConnection );

        scm.setUrl( url );

        expected.setScm( scm );

        Build build = new Build();

        build.setSourceDirectory( "src/main/java" );

        build.setUnitTestSourceDirectory( "src/test/java" );

        SourceModification sourceModification = new SourceModification();

        sourceModification.setClassName( "excludeEclipsePlugin" );

        sourceModification.setDirectory( "foo" );

        sourceModification.addExclude( "de/abstrakt/tools/codegeneration/eclipse/*.java" );

        build.addSourceModification( sourceModification );

        expected.setBuild( build );

        MavenXpp3Writer writer = new MavenXpp3Writer();

        StringWriter buffer = new StringWriter();

        writer.write( buffer, expected );

        String actualXml = buffer.toString();
// /*
        System.out.println( expectedXml );
        System.err.println( actualXml );
/* */
        assertEquals( expectedXml, actualXml );

        MavenXpp3Reader reader = new MavenXpp3Reader();

        Model actual = reader.read( new StringReader( actualXml ) );

        assertNotNull( "Actual", actual );

        assertModel( expected, actual );
    }

    public void assertModel( Model expected, Model actual )
    {
        assertNotNull( "Actual model", actual );

        assertEquals( "/model/extend", expected.getExtend(), actual.getExtend() );

//        assertParent( expected.getParent(), actual.getParent() );

        assertEquals( "/model/modelVersion", expected.getModelVersion(), actual.getModelVersion() );

        assertEquals( "/model/groupId", expected.getGroupId(), actual.getGroupId() );

        assertEquals( "/model/artifactId", expected.getArtifactId(), actual.getArtifactId() );

        assertEquals( "/model/type", expected.getType(), actual.getType() );

        assertEquals( "/model/name", expected.getName(), actual.getName() );

        assertEquals( "/model/version", expected.getVersion(), actual.getVersion() );

        assertEquals( "/model/shortDescription", expected.getShortDescription(), actual.getShortDescription() );

        assertEquals( "/model/description", expected.getDescription(), actual.getDescription() );

        assertEquals( "/model/url", expected.getUrl(), actual.getUrl() );

        assertEquals( "/model/logo", expected.getLogo(), actual.getLogo() );

//        assertIssueManagement();

//        assertCiManagement();

        assertEquals( "/model/inceptionYear", expected.getInceptionYear(), actual.getInceptionYear() );

//        assertEquals( "/model/siteAddress", expected.getSiteAddress(), actual.getSiteAddress() );

//        assertEquals( "/model/siteDirectory", expected.getSiteDirectory(), actual.getSiteDirectory() );

//        assertEquals( "/model/distributionSite", expected.getDistributionSite(), actual.getDistributionSite() );

//        assertEquals( "/model/distributionDirectory", expected.getDistributionDirectory(), actual.getDistributionDirectory() );

        assertMailingLists( expected.getMailingLists(), actual.getMailingLists() );
/*
        assertDevelopers( );

        assertContributors( );

        assertDependencies( );

        assertLicenses( );

        assertPackageGroups( );

        assertReports( );
*/
        assertScm( expected.getScm(), actual.getScm() );
/*
        assertBuild( );

        assertOrganization( expected.getOrganization(), actual.getOrganization() );
*/
        assertBuild( expected.getBuild(), actual.getBuild() );
    }

    public void assertMailingLists( List expected, List actual )
    {
        assertNotNull( "/model/mailingLists", actual );

        assertEquals( "/model/mailingLists.size", expected.size(), actual.size() );

        for ( int i = 0; i < expected.size(); i++ )
        {
            assertMailingList( i, (MailingList) expected.get( i ), actual.get( i ) );
        }
    }

    public void assertMailingList( int i, MailingList expected, Object actualObject )
    {
        assertNotNull( "/model/mailingLists[" + i + "]", actualObject );

        assertInstanceOf( "/model/mailingLists", MailingList.class, actualObject.getClass() );

        MailingList actual = (MailingList) actualObject;

        assertEquals( "/model/mailingLists[" + i + "]/name", expected.getName(), actual.getName() );

        assertEquals( "/model/mailingLists[" + i + "]/subscribe", expected.getSubscribe(), actual.getSubscribe() );

        assertEquals( "/model/mailingLists[" + i + "]/unsubscribe", expected.getUnsubscribe(), actual.getUnsubscribe() );

        assertEquals( "/model/mailingLists[" + i + "]/archive", expected.getArchive(), actual.getArchive() );
    }

    public void assertScm( Scm expected, Object actualObject )
    {
        assertNotNull( "/model/scm", actualObject );

        assertInstanceOf( "/model/scm", Scm.class, actualObject.getClass() );

        Scm actual = (Scm) actualObject;

        assertEquals( "/model/scm/connection", expected.getConnection(), actual.getConnection() );

        assertEquals( "/model/scm/developerConnection", expected.getDeveloperConnection(), actual.getDeveloperConnection() );

        assertEquals( "/model/scm/url", expected.getUrl(), actual.getUrl() );
    }

    public void assertBuild( Build expected, Object actualObject )
    {
        assertNotNull( "/model/builder", actualObject );

        assertInstanceOf( "/model/builder", Build.class, actualObject.getClass() );

        Build actual = (Build) actualObject;

        assertEquals( "/model/builder/sourceDirectory", expected.getSourceDirectory(), actual.getSourceDirectory() );

        assertEquals( "/model/builder/unitTestSourceDirectory", expected.getUnitTestSourceDirectory(), actual.getUnitTestSourceDirectory() );
    }
}
