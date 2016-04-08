package edu.ucla.library.libservices.rss.custom.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import edu.ucla.library.libservices.rss.custom.beans.filters.LanguageFilter;

public class LanguageServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

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
    doPost( request, response );
  }

  /**Process the HTTP doPost request.
   */
  public void doPost( HttpServletRequest request, 
                      HttpServletResponse response )
    throws ServletException, IOException
  {
    response.setContentType( CONTENT_TYPE );
    //PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();

    LanguageFilter langs = 
      ( LanguageFilter ) session.getAttribute( "langs" );

    if ( langs == null )
      langs = new LanguageFilter();

    if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "add" ) )
      handleAdd( request, langs );
    else if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "delSome" ) )
      handleClearSome( request, langs );
    else
      handleClearAll( langs );

    session.setAttribute( "langs", langs );

    response.setHeader( "Cache-Control", "no-cache" );
    response.getOutputStream().print( langs.getLangUrl() + "\t" + langs.isInclude() );
    response.flushBuffer();
  }

  private void handleAdd( HttpServletRequest request, 
                          LanguageFilter langs )
  {
    if ( request.getParameterValues( "langs" ) != null && 
         request.getParameterValues( "langs" ).length != 0 )
      langs.addLangs( request.getParameterValues( "langs" ) );

    if ( request.getParameter( "includeExclude" ) != null )
    {
      if ( request.getParameter( "includeExclude" ).equalsIgnoreCase( "true" ) )
        langs.setInclude( true );
      else
        langs.setInclude( false );
    }
  }

  private void handleClearSome( HttpServletRequest request, 
                                LanguageFilter langs )
  {
    if ( request.getParameterValues( "remove" ) != null && 
         request.getParameterValues( "remove" ).length != 0 )
      langs.removeCalls( request.getParameterValues( "remove" ) );

    if ( langs.getCodes().isEmpty() )
      langs.setInclude( true );
  }

  private void handleClearAll( LanguageFilter langs )
  {
    langs.clearCalls();
    langs.setInclude( true );
  }
}
