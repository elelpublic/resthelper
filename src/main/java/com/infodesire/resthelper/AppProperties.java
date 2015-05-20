// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;


/**
 * Persistent storage for some properties of the application. Will use a file named ~/.bsmapps/APPID/app.properties where APPID is the application id as specified in the parameter applicationId of the
 * ApplicationKeyFilter in web.xml.
 *
 */
public class AppProperties {


  public static final String SAMPLE_APPLICATION_KEY = "MY-APPLICATION-KEY";
  public static final String SAMPLE_REST_URL = "http://RESTSERVER/REST-URI";
  private File file;
  private String applicationId;
  public static boolean quietMode = false;


  /**
   * Create instance
   * 
   * @param baseDir Directory which contains a subdirectory for each application
   * @param applicationId Application id with which it identifies at the server
   * 
   */
  public AppProperties( File baseDir, String applicationId ) {
    this.applicationId = applicationId;
    file = new File( baseDir, applicationId.toLowerCase() + "/app.properties" );
  }


  private Properties properties = null;


  public String getApplicationKey() throws IOException {
    return getString( "applicationKey" );
  }
  
  
  public void setApplicationKey( String key ) throws IOException {
    getProperties().setProperty( "applicationKey", key );
    store();
  }
  
  
  public String getRestURL() throws IOException {
    return getString( "restURL" );
  }
  
  
  public void setRestURL( String key ) throws IOException {
    getProperties().setProperty( "restURL", key );
    store();
  }
  
  
  private void store() throws IOException {
    file.getParentFile().mkdirs();
    Writer writer = new FileWriter( file );
    getProperties().store( writer, "Properties for app: " + applicationId );
    writer.close();
  }


  private String getString( String propertyName ) throws IOException {
    Object value = getProperties().get( propertyName );
    return value == null ? null : value.toString();
  }


  private Properties getProperties() throws IOException {
    if( properties == null ) {
      Properties loading = new Properties();
      if( !file.exists() ) {
        installSampleConfig();
        if( !quietMode ) {
          new IOException(
            "Config file not found. An example file was created at "
              + file.getAbsolutePath()
              + ". Please edit it to fit your installation." ).printStackTrace();
        }
      }
      if( file.exists() && file.isFile() ) {
        Reader filereader = new FileReader( file );
        loading.load( filereader );
        filereader.close();
      }
      properties = loading;
    }
    return properties;
  }


  private void installSampleConfig() throws IOException {
    InputStream from = AppProperties.class.getResourceAsStream( "/app.sample.properties" );
    file.getParentFile().mkdirs();
    OutputStream to = new FileOutputStream( file );
    ByteStreams.copy( from, to );
    from.close();
    to.close();
  }


  public void reload() {
    properties = null;
  }


  public String describeFile() {
    return file.getAbsolutePath();
  }


}
