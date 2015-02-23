// (C) 1998-2015 Information Desire Software GmbH
// www.infodesire.com

package com.infodesire.resthelper;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;
import com.infodesire.bsmcommons.io.PrintStringWriter;
import com.infodesire.bsmcommons.io.Writers;

import java.io.PrintWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;


/**
 * Prepare the info found in a http request for easy access
 *
 */
public class PreparedRequest {


  private URIBuilder uriBuilder;
  private HttpServletRequest request;
  private Route route;


  public PreparedRequest( HttpServletRequest request ) throws URISyntaxException {
    uriBuilder = parse( request );
    this.request = request;
    route = Route.parse( uriBuilder.getPath() );
  }
  
  
  public HttpServletRequest getRequest() {
    return request;
  }
  
  
  public URIBuilder getURIBuilder() {
    return uriBuilder;
  }
  
  
  public Route getRoute() {
    return route;
  }


  /**
   * Create some html debug info for a request
   * <p>
   * 
   * Try it with: http://localhost:8181/api/doc1/rest?p=1&c=2
   * 
   * @param writer Target writer
   * @return Html debug info for request
   * 
   */
  public void toHTML( PrintWriter writer ) {
    dump( writer, true );
  }


  private static void line( String name, Object value, PrintWriter writer,
    boolean html ) {
    if( value != null && value.getClass().isArray() ) {
      value = Arrays.asList( (Object[]) value );
    }
    if( html ) {
      writer.println( "<tr><td><i>" + name + "</i></td><td>"
        + ( value == null ? "" : value ) + "</td></tr>" );
    }
    else {
      writer.println( name + ": " + ( value == null ? "" : value ) );
    }
  }


  private static void line( String title, PrintWriter writer, boolean html ) {
    if( html ) {
      writer.println( "<tr><td colspan=2><b>" + ( title == null ? "" : title )
        + "</b></td></tr>" );
    }
    else {
      writer.println( title == null ? "" : title );
    }
  }


  private static URIBuilder parse( HttpServletRequest request ) throws URISyntaxException {

    String fullURL = request.getRequestURL().toString();
    String queryString = request.getQueryString();
    if( !Strings.isNullOrEmpty( queryString ) ) {
      fullURL += "?" + queryString;
    }
    
    return new URIBuilder( fullURL );

  }
  
  
  public String toString() {
    return request.getMethod() + " " + route;
  }


  /**
   * @param name Name of parameter. Query parameters are found in the URL behind the ?
   * @return Query parameter by name
   * 
   */
  public String getQueryParam( String name ) {
    for( NameValuePair param : uriBuilder.getQueryParams() ) {
      if( param.getName().equals( name ) ) {
        return param.getValue();
      }
    }
    return null;
  }
  
  
  public void dump( Writer writer, boolean html ) {
    
    PrintWriter print = Writers.printWriter( writer );

    if( html ) {
      print.println( "<table border=1>" );
    }

    line( "URL", print, html );
    line( "Method", request.getMethod(), print, html );
    line( "Scheme", uriBuilder.getScheme(), print, html );
    line( "Host", uriBuilder.getHost(), print, html );
    line( "Port", "" + uriBuilder.getPort(), print, html );
    line( "Path", uriBuilder.getPath(), print, html );
    line( "Fragment", uriBuilder.getFragment(), print, html );
    line( "UserInfo", uriBuilder.getUserInfo(), print, html );

    line( "Route", route, print, html );
    
    line( "Request", print, html );
    line( "AuthType", request.getAuthType(), print, html );
    line( "CharacterEncoding", request.getCharacterEncoding(), print, html );
    line( "ContentLength", request.getContentLength(), print, html );
    line( "ContentType", request.getContentType(), print, html );
    line( "ContextPath", request.getContextPath(), print, html );
    line( "LocalAddr", request.getLocalAddr(), print, html );
    line( "Locale", request.getLocale(), print, html );
    line( "LocaleName", request.getLocalName(), print, html );
    line( "LocalPort", request.getLocalPort(), print, html );
    line( "PathInfo", request.getPathInfo(), print, html );
    line( "PathTranslated", request.getPathTranslated(), print, html );
    line( "Protocol", request.getProtocol(), print, html );
    line( "RemoteAddr", request.getRemoteAddr(), print, html );
    line( "RemoteHost", request.getRemoteHost(), print, html );
    line( "RemotePort", request.getRemotePort(), print, html );
    line( "RemoteUser", request.getRemoteUser(), print, html );
    line( "RequestedSessionId", request.getRequestedSessionId(), print, html );
    line( "RequestURI", request.getRequestURI(), print, html );
    line( "RequestURL", request.getRequestURL(), print, html );
    line( "Scheme", request.getScheme(), print, html );
    line( "ServerName", request.getServerName(), print, html );
    line( "ServerPort", request.getServerPort(), print, html );
    line( "ServletPath", request.getServletPath(), print, html );
    line( "UserPrincipal", request.getUserPrincipal(), print, html );
    
    line( "QueryParameters", print, html );
    for( NameValuePair param : uriBuilder.getQueryParams() ) {
      line( param.getName(), param.getValue(), print, html );
    }

    line( "Parameters", print, html );
    for( Object object : request.getParameterMap().entrySet() ) {
      @SuppressWarnings("rawtypes")
      Map.Entry entry = (Map.Entry) object;
      line( "" + entry.getKey(), entry.getValue(), print, html );
    }
    
    line( "Headers", print, html );
    for( @SuppressWarnings("rawtypes")
    Enumeration e = request.getHeaderNames(); e.hasMoreElements(); ) {
      
      String name = (String) e.nextElement();
      @SuppressWarnings("unchecked")
      String value = Joiner.on( " " ).join(
        Iterators.forEnumeration( (Enumeration<String>) request
          .getHeaders( name ) ) );
      line( name, value, print, html );
      
    }

    line( "Cookies", print, html );
    for( Cookie cookie : request.getCookies() ) {
      line( cookie.getName(), cookie.getValue(), print, html );
    }
    
//    line( "Attributes", print );
//    for( @SuppressWarnings("rawtypes")
//    Enumeration e = request.getAttributeNames(); e.hasMoreElements(); ) {
//      
//      String name = (String) e.nextElement();
//      line( name, request.getAttribute( name ), print );
//      
//    }
    
    if( html ) {
      print.println( "</table>" );
    }

  }


  public String dump( boolean html ) {
    PrintStringWriter print = new PrintStringWriter();
    dump( print, false );
    return print.toString();
  }
  
  
  public String dump() {
    return dump( false );
  }
  
  
}


