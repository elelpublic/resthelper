// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import com.google.common.base.Strings;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.PropertyConfigurator;


/**
 * Filter to add the authentication key to a proxied request from browser to REST.
 *
 */
public class ApplicationKeyFilter implements Filter {

  private AppProperties appProperties;
  private String applicationId;
  private String configBaseDir;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter( ServletRequest request, ServletResponse response,
    FilterChain chain ) throws IOException, ServletException {
    
    String applicationKey = getAppProperties().getApplicationKey();

    if( !Strings.isNullOrEmpty( applicationKey ) ) {
      if( request instanceof HttpServletRequest ) {
        
        RequestWithAddedHeaders newRequest = new RequestWithAddedHeaders(
          (HttpServletRequest) request );
        newRequest.addHeader( "x-application-key", applicationKey );
        request = newRequest;
        
      }
    }
    
    chain.doFilter( request, response );
    
  }

  
  /**
   * Lazy evaluation is important, because some apps might want to set a system property
   */
  private AppProperties getAppProperties() {
    if( appProperties == null ) {
      File baseDir = BaseDir.getBaseDir( configBaseDir );
      appProperties = new AppProperties( baseDir, applicationId );
    }
    return appProperties;
  }
  

  @Override
  public void init( FilterConfig config ) throws ServletException {
    
    applicationId = config.getInitParameter( "applicationId" );
    configBaseDir = config.getInitParameter( "configBaseDir" );
    
    Properties properties = new Properties();
    properties.put( "log4j.logger.org.apache", "WARN" );
    properties.put( "log4j.logger.org.restlet", "WARN" );
    PropertyConfigurator.configure( properties );
    
  }

}
