// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import com.google.common.base.Strings;

import java.io.File;


/**
 * Directory which contains sub directories for each app.
 * <p>
 * 
 * The default for the base dir is ~/.bsmapps 
 *
 */
public class BaseDir {

  
  /**
   * @param baseDirName Name of the base dir or null for defaul
   * @return Directory which contains sub directories for each app
   * 
   */
  public static File getBaseDir( String baseDirName ) {
    
    if( Strings.isNullOrEmpty( baseDirName ) ) {
      baseDirName = new File( System.getProperty( "user.home", "." ) )
        .getAbsolutePath() + "/.bsmapps";
    }
    return new File( baseDirName );
  }
  

}


