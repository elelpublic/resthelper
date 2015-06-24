// (C) 1998-2016 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;


/**
 * Some commons
 *
 */
public interface Resthelper {
  
  
  /**
   * Name of the http header containing the application key
   * 
   */
  public static final String HTTP_HEADER_APPLICATION_KEY = "x-application-key"; 

  
  /**
   * Name of the http header which when found will cause resthelper to reload its configuration
   * 
   */
  public static final String HTTP_HEADER_RELOAD = "x-resthelper-reload"; 
  

}
