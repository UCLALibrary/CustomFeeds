package edu.ucla.library.libservices.rss.custom.web.servlets;

import edu.ucla.library.libservices.rss.custom.beans.filters.CallNumberFilter;
import edu.ucla.library.libservices.rss.custom.beans.filters.LanguageFilter;
import edu.ucla.library.libservices.rss.custom.beans.filters.LocationFilter;
import edu.ucla.library.libservices.rss.custom.beans.filters.PubYearsFilter;
//import edu.ucla.library.libservices.rss.custom.db.utility.DataSourceFactory;
import edu.ucla.library.libservices.rss.custom.beans.utility.FeedLoggingBean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

public class ConfirmationServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
  private static final String BASE_URL = "feed";
  //private static final String BASE_URL = "rssoutputservlet";
  /*private static final String INSERT_SQL = 
    "INSERT INTO vger_support.custom_feed_log(feed_id,feed_date,client_ip," + 
    "call_number_filter,language_filter,include_exclude_language,location_filter" + 
    ",include_exclude_location,pub_year_filter) VALUES(custom_feed_seq.nextval," + 
    "sysdate,?,?,?,?,?,?,?)";*/

  public void init( ServletConfig config )
    throws ServletException
  {
    super.init( config );
  }

  /**Process the HTTP doGet request.
   */
  public void doGet( HttpServletRequest request, 
                     HttpServletResponse response )
    throws ServletException, IOException
  {
    response.setContentType( CONTENT_TYPE );
    //PrintWriter out = response.getWriter();
    HttpSession session;
    String query;
    StringBuffer homeUrl;
    int feedID;
    
    session = request.getSession();
    feedID = logFeed( session, request );
    query = getUrlQuery( session, feedID );
    homeUrl = 
        new StringBuffer( request.getRequestURL().substring( 
          0, request.getRequestURL().lastIndexOf( "/" ) + 1 ) );
    homeUrl.append( BASE_URL.concat( query ) );

    if ( request.getParameter( "type" ).equalsIgnoreCase( "google" ) )
      response.sendRedirect( "http://fusion.google.com/add?feedurl=".concat( 
        URLEncoder.encode( homeUrl.toString(), "UTF-8" ) ) );
    else if ( request.getParameter( "type" ).equalsIgnoreCase( "yahoo" ) )
      response.sendRedirect( "http://add.my.yahoo.com/rss?url=".concat( 
        URLEncoder.encode( homeUrl.toString(), "UTF-8" ) ) );
    else
      response.sendRedirect( BASE_URL.concat( query ) );
  }

  /**Process the HTTP doPost request.
   */
  public void doPost( HttpServletRequest request, 
                      HttpServletResponse response )
    throws ServletException, IOException
  {
    response.setContentType( CONTENT_TYPE );
    //PrintWriter out = response.getWriter();
    HttpSession session;

    session = request.getSession();

    //logFeed( session, request );

    /*if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "add" ) )
      response.sendRedirect( BASE_URL.concat( getUrlQuery( session ) ) );
    else*/
    {
      handleClear( session );
      response.sendRedirect( "callNumbers.jsp" );
    }
  }

  private String getUrlQuery( HttpSession session, int id )
    throws UnsupportedEncodingException
  {
    StringBuffer urlQuery;
    CallNumberFilter callnumbers;
    LanguageFilter langs;
    LocationFilter locations;
    PubYearsFilter pubyears;

    urlQuery = new StringBuffer( "" );
    callnumbers = 
        ( CallNumberFilter ) session.getAttribute( "callnumbers" );
    langs = ( LanguageFilter ) session.getAttribute( "langs" );
    locations = ( LocationFilter ) session.getAttribute( "locations" );
    pubyears = ( PubYearsFilter ) session.getAttribute( "pubyears" );

    if ( callnumbers != null && !callnumbers.getCallsUrl().equals( "" ) )
      addQueryElement( urlQuery, "calls", 
                       URLEncoder.encode( callnumbers.getCallsUrl(), 
                                          "UTF-8" ) );
    if ( langs != null && !langs.getLangUrl().equals( "" ) )
    {
      addQueryElement( urlQuery, "langs", 
                       URLEncoder.encode( langs.getLangUrl(), "UTF-8" ) );
      addQueryElement( urlQuery, "inclangs", 
                       ( langs.isInclude()? "true" : "false" ) );
    }
    if ( locations != null && !locations.getLocUrl().equals( "" ) )
    {
      addQueryElement( urlQuery, "locs", 
                       URLEncoder.encode( locations.getLocUrl(), 
                                          "UTF-8" ) );
      addQueryElement( urlQuery, "inclocs", 
                       ( locations.isInclude()? "true" : "false" ) );
    }
    if ( pubyears != null && !pubyears.getYearsUrl().equals( "" ) )
      addQueryElement( urlQuery, "years", 
                       URLEncoder.encode( pubyears.getYearsUrl(), 
                                          "UTF-8" ) );
    addQueryElement(urlQuery,"feedID",String.valueOf(id));
    return urlQuery.toString();
  }

  private void addQueryElement( StringBuffer query, String varName, 
                                String varValue )
  {
    if ( query.length() == 0 || query.equals( "" ) )
      query.append( "?" ).append( varName ).append( "=" ).append( varValue );
    else
      query.append( "&" ).append( varName ).append( "=" ).append( varValue );
  }

  private int logFeed( HttpSession session, HttpServletRequest request )
  {
    CallNumberFilter callnumbers;
    LanguageFilter langs;
    LocationFilter locations;
    PubYearsFilter pubyears;
    FeedLoggingBean feedLogger;

    callnumbers = 
        ( CallNumberFilter ) session.getAttribute( "callnumbers" );
    langs = ( LanguageFilter ) session.getAttribute( "langs" );
    locations = ( LocationFilter ) session.getAttribute( "locations" );
    pubyears = ( PubYearsFilter ) session.getAttribute( "pubyears" );
    feedLogger = new FeedLoggingBean();
    
    feedLogger.setClientIP(request.getRemoteAddr());
    //feedLogger.setClientIP("127.0.0.1");
    feedLogger.setSourceName(getServletContext().getInitParameter( "db.source" ));

    if ( callnumbers != null && !callnumbers.getCallsUrl().equals( "" ) )
      feedLogger.setCallsUrl( callnumbers.getCallsUrl() );
    else
      feedLogger.setCallsUrl( "" );
    if ( langs != null && !langs.getLangUrl().equals( "" ) )
    {
      feedLogger.setLangUrl( langs.getLangUrl() );
      feedLogger.setIncludeLang( langs.isInclude() );
    }
    else
    {
      feedLogger.setLangUrl( "" );
      feedLogger.setIncludeLang( true );
    }
    if ( locations != null && !locations.getLocUrl().equals( "" ) )
    {
      feedLogger.setLocUrl( locations.getLocUrl() );
      feedLogger.setIncludeLoc( locations.isInclude() );
    }
    else
    {
      feedLogger.setLocUrl( "" );
      feedLogger.setIncludeLoc( true );
    }
    if ( pubyears != null && !pubyears.getYearsUrl().equals( "" ) )
      feedLogger.setYearsUrl( pubyears.getYearsUrl() );
    else
      feedLogger.setYearsUrl( "" );
    
    return feedLogger.doFeedCreation();
  }

  private void handleClear( HttpSession session )
  {
    session.removeAttribute( "callnumbers" );
    session.removeAttribute( "langs" );
    session.removeAttribute( "locations" );
    session.removeAttribute( "pubyears" );
  }

}

/*
 * when/if we incorporate fb:
 *  1) retrieve beans from session & build URL; include server ala google/yahoo forwards
 *  2) log params to db & retrieve ID (separate sequence.nextval & insert calls; SELECT custom_feed_seq.nextval FROM DUAL)
 *  3) make call to FB to add feed, setting feed name to uclalib/customFeed${ID};
 *  4) forward user to feeds.library.ucla.edu/uclalib/customFeed${ID}
 * */

 //              caller first sets callsUrl,langUrl,includeLang,locUrl,includeLoc,yearsUrl,
     
     /*
     //DriverManagerDataSource ds;
     DataSource ds;
     JdbcTemplate sql;

     ds = DataSourceFactory.createDataSource( getServletContext().getInitParameter( "db.source" ) );
     //ds = DataSourceFactory.createDataSource();
     
     if ( ds != null )
     {
       sql = new JdbcTemplate( ds );
       sql.update( INSERT_SQL, getLogArray( session, request ) );
     }
     */
/*
 private Object[] getLogArray( HttpSession session, 
                               HttpServletRequest request )
 {
   Object[] theArray;
   CallNumberFilter callnumbers;
   LanguageFilter langs;
   LocationFilter locations;
   PubYearsFilter pubyears;

   theArray = new Object[ 7 ];
   theArray[ 0 ] = request.getRemoteAddr();
   callnumbers = 
       ( CallNumberFilter ) session.getAttribute( "callnumbers" );
   langs = ( LanguageFilter ) session.getAttribute( "langs" );
   locations = ( LocationFilter ) session.getAttribute( "locations" );
   pubyears = ( PubYearsFilter ) session.getAttribute( "pubyears" );

   if ( callnumbers != null && !callnumbers.getCallsUrl().equals( "" ) )
     theArray[ 1 ] = callnumbers.getCallsUrl();
   else
     theArray[ 1 ] = "";
   if ( langs != null && !langs.getLangUrl().equals( "" ) )
   {
     theArray[ 2 ] = langs.getLangUrl();
     theArray[ 3 ] = ( langs.isInclude()? "Y" : "N" );
   }
   else
   {
     theArray[ 2 ] = "";
     theArray[ 3 ] = "Y";
   }
   if ( locations != null && !locations.getLocUrl().equals( "" ) )
   {
     theArray[ 4 ] = locations.getLocUrl();
     theArray[ 5 ] = ( locations.isInclude()? "Y" : "N" );
   }
   else
   {
     theArray[ 4 ] = "";
     theArray[ 5 ] = "Y";
   }
   if ( pubyears != null && !pubyears.getYearsUrl().equals( "" ) )
     theArray[ 6 ] = pubyears.getYearsUrl();
   else
     theArray[ 6 ] = "";

   return theArray;
 }

 */