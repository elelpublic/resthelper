// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Properties;


/**
 * Persistent storage for some properties of the application. Will use a file named ~/.APPID/app.properties where APPID is the application id as specified in the parameter applicationId of the
 * ApplicationKeyFilter in web.xml.
 *
 */
public class AppProperties {


  private File file;
  private String applicationId;


  /**
   * Create instance
   * 
   * @param applicationId Application id with which it identifies at the server
   * 
   */
  public AppProperties( String applicationId ) {
    this.applicationId = applicationId;
    file = new File( new File( System.getProperty( "user.home", "." ) ), "."
      + applicationId.toLowerCase() + "/app.properties" );
  }


  private Properties properties = null;


  public String getApplicationKey() throws IOException {
    return getString( "applicationKey" );
  }
  
  
  public void setApplicationKey( String key ) throws IOException {
    getProperties().setProperty( "applicationKey", key );
    store();
  }
  
  
  private void store() throws IOException {
    Writer writer = new FileWriter( file );
    getProperties().store( writer,
      "Properties for app: " + applicationId + ", Last updated: " + new Date() );
    writer.close();
  }


  private String getString( String propertyName ) throws IOException {
    Object value = getProperties().get( propertyName );
    return value == null ? null : value.toString();
  }


  private Properties getProperties() throws IOException {
    if( properties == null ) {
      if( file.exists() && file.isFile() ) {
        Reader filereader = new FileReader( file );
        properties = new Properties();
        properties.load( filereader );
        filereader.close();
      }
    }
    return properties;
  }


  public void reload() {
    properties = null;
  }


}
