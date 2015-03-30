// (C) 1998-2016 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class BaseDirTest {


  @Before
  public void setUp() throws Exception {
  }


  @After
  public void tearDown() throws Exception {
  }


  @Test
  public void test() {
    
    System.getProperties().remove( BaseDir.DEFAULT_DIR_PROPERTY );

    // test default values
    
    assertEquals( new File( System.getProperty( "user.home" ) + "/.bsmapps" ),
      BaseDir.getBaseDir( null ) );
    
    System.setProperty( BaseDir.DEFAULT_DIR_PROPERTY, "/opt/sampleapp" );

    assertEquals( new File( "/opt/sampleapp" ),
      BaseDir.getBaseDir( null ) );
    
    System.setProperty( BaseDir.DEFAULT_DIR_PROPERTY, "${user.home}/.sampleapp" );
  
    assertEquals( new File( System.getProperty( "user.home" ) + "/.sampleapp" ),
      BaseDir.getBaseDir( null ) );
    
    System.getProperties().remove( BaseDir.DEFAULT_DIR_PROPERTY );

    assertEquals( new File( System.getProperty( "user.home" ) + "/.bsmapps" ),
      BaseDir.getBaseDir( null ) );

    assertEquals( new File( System.getProperty( "user.home" ) + "/.sample2" ),
      BaseDir.getBaseDir( "${user.home}/.sample2" ) );

  }

}
