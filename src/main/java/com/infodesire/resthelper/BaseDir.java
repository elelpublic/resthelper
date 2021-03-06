// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import com.google.common.base.Strings;

import java.io.File;

import org.apache.commons.lang3.text.StrSubstitutor;


/**
 * Directory which contains sub directories for each app.
 * <p>
 * 
 * The default for the base dir is ~/.bsmapps
 * <p> 
 * 
 * The default base dir can be overridden with the com.infodesire.resthelper.configBaseDir
 * system property. 
 *
 */
public class BaseDir {

  
  public static final String DEFAULT_DIR_PROPERTY = "com.infodesire.resthelper.configBaseDir";
  

  /**
   * @param baseDirName Name of the base dir or null for default
   * @return Directory which contains sub directories for each app
   * 
   */
  public static File getBaseDir( String baseDirName ) {
    
    if( !Strings.isNullOrEmpty( baseDirName ) ) {
      baseDirName = replaceSystemProperties( baseDirName );
      return new File( baseDirName );
    }
    
    String property = System.getProperty( DEFAULT_DIR_PROPERTY );
    if( !Strings.isNullOrEmpty( property ) ) {
      property = replaceSystemProperties( property );
      return new File( property );
    }
    
    return new File( new File( System.getProperty( "user.home", "." ) ), ".bsmapps" );
  }


  /**
   * Replace system properties in the syntax ${propertyname}
   * 
   * @param original Original text
   * @return Original text with system property variables replaced
   * 
   */
  public static String replaceSystemProperties( String original ) {
    return StrSubstitutor.replaceSystemProperties( original );
  }
  

}


