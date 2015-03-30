// (C) 1998-2016 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import static org.junit.Assert.*;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class AppPropertiesTest {


  private File tmpDir;


  @Before
  public void setUp() throws Exception {
    tmpDir = Files.createTempDir();
  }


  @After
  public void tearDown() throws Exception {
    FileUtils.deleteQuietly( tmpDir );
  }


  @Test
  public void test() throws IOException {
    
    assertFalse( new File( tmpDir, "my-app-id/app.properties" ).exists() );
    
    AppProperties p = new AppProperties( tmpDir, "MY-APP-ID" );
    
    assertEquals( AppProperties.SAMPLE_APPLICATION_KEY, p.getApplicationKey() );
    assertEquals( AppProperties.SAMPLE_REST_URL, p.getRestURL() );
    
    assertTrue( new File( tmpDir, "my-app-id/app.properties" ).exists() );
    
    p.setApplicationKey( "K2" );
    assertEquals( "K2", p.getApplicationKey() );
    p.setRestURL( "U2" );
    assertEquals( "U2", p.getRestURL() );
    
    p = new AppProperties( tmpDir, "MY-APP-ID" );
    assertEquals( "K2", p.getApplicationKey() );
    assertEquals( "U2", p.getRestURL() );
    
  }

}
