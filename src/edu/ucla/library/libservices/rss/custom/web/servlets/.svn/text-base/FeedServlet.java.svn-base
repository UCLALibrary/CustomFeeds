package edu.ucla.library.libservices.rss.custom.web.servlets;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.WireFeedOutput;

import edu.ucla.library.libservices.rss.custom.beans.generators.RssGenerator;
import edu.ucla.library.libservices.rss.custom.beans.utility.FeedLoggingBean;
import edu.ucla.library.libservices.rss.custom.beans.utility.SqlConverter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.IllegalDataException;

public class FeedServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";
//  private static final String DOC_TYPE = null;

  public void init( ServletConfig config )
    throws ServletException
  {
    super.init(config);
  }

  /**Process the HTTP doGet request.
   */
  public void doGet( HttpServletRequest request, 
                     HttpServletResponse response )
    throws ServletException, IOException
  {
    doPost(request, response);
  }

  /**Process the HTTP doPost request.
   */
  public void doPost( HttpServletRequest request, 
                      HttpServletResponse response )
    throws ServletException, IOException
  {
    PrintWriter out;
    WireFeedOutput output;
    SqlConverter converter;
    RssGenerator feedMaker;

    response.setContentType(CONTENT_TYPE);

    out = response.getWriter();
    converter = new SqlConverter();
    feedMaker = new RssGenerator();
    output = new WireFeedOutput();

    prepSqlConverter( converter, request );
    logRequest( request );

    feedMaker.setQuery( converter.getSql() );
    feedMaker.setDsName( this.getServletContext().getInitParameter("db.source") );

    try
    {
      output.output(feedMaker.getFeed(), out);
    }
    catch ( FeedException fe )
    {
      out.println(
        "<error><reason>Feed generation failed because of output-generation " 
        + "issue</reason><message>" + fe.getMessage() + "</message><contact>" 
        + "Please contact webadmin@library.ucla.edu with the abve message and" 
        + " the feed URL</contact></error>");
      //fe.printStackTrace(out);
    }
    catch ( IllegalDataException ide )
    {
      out.println(
        "<error><reason>Feed generation failed because of bad data</reason><message>" 
        + ide.getMessage() + "</message><contact>Please contact webadmin@library.ucla.edu" 
        + " with the abve message and the feed URL</contact></error>");
      //ide.printStackTrace();
    }
  }

  private void prepSqlConverter( SqlConverter bean, ServletRequest req )
  {
    if ( req.getParameter( "langs" ) != null )
    {
      bean.setLangCodes( req.getParameter( "langs" ) );
      bean.setIncLangs( ( req.getParameter( "inclangs" ).equals( "true" )? 
                          true : false ) );
    }
    if ( req.getParameter( "locs" ) != null )
    {
      bean.setLibCodes( req.getParameter( "locs" ) );
      bean.setIncLibs( ( req.getParameter( "inclocs" ).equals( "true" )? 
                         true : false ) );
    }
    if ( req.getParameter( "calls" ) != null )
      bean.setCalls( req.getParameter( "calls" ) );
    if ( req.getParameter( "years" ) != null )
      bean.setYears( req.getParameter( "years" ) );
  }

  private void logRequest(HttpServletRequest request)
  {
    FeedLoggingBean feedLog;
    
    feedLog = new FeedLoggingBean();
    feedLog.setSourceName(getServletContext().getInitParameter("db.source"));
    //feedLog.setFeedURL( request.getQueryString() );
     feedLog.setClientIP(request.getRemoteAddr());
     //feedLog.setClientIP("127.0.0.1");
     if ( request.getParameter( "langs" ) != null )
     {
       feedLog.setLangUrl( request.getParameter( "langs" ) );
       feedLog.setIncludeLang( ( request.getParameter( "inclangs" ).equals( "true" )? 
                           true : false ) );
     }
     if ( request.getParameter( "locs" ) != null )
     {
       feedLog.setLocUrl( request.getParameter( "locs" ) );
       feedLog.setIncludeLoc( ( request.getParameter( "inclocs" ).equals( "true" )? 
                          true : false ) );
     }
     if ( request.getParameter( "calls" ) != null )
       feedLog.setCallsUrl( request.getParameter( "calls" ) );
     if ( request.getParameter( "years" ) != null )
       feedLog.setYearsUrl( request.getParameter( "years" ) );
    if ( request.getParameter("feedID") != null && !request.getParameter("feedID").equals("") )
      feedLog.setFeedID( Integer.parseInt(request.getParameter("feedID")) );
    feedLog.doFeedRetrieval();
  }
}
