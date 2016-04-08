package edu.ucla.library.libservices.rss.custom.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import edu.ucla.library.libservices.rss.custom.beans.filters.LocationFilter;

public class LocationServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = 
    "text/html; charset=windows-1252";

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

    LocationFilter locations = 
      ( LocationFilter ) session.getAttribute( "locations" );

    if ( locations == null )
      locations = new LocationFilter();

    if ( request.getParameter( "filteraction" ) != null )
    {
      if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "add" ) )
        handleAdd( request, locations );
      else
        handleClear( request, locations );
    }

    session.setAttribute( "locations", locations );
    
    response.sendRedirect( "pubYears.jsp" );
  }

  private void handleAdd( HttpServletRequest request, LocationFilter libs )
  {
    if ( request.getParameterValues( "library" ) != null && 
         request.getParameterValues( "library" ).length != 0 )
      libs.addCodes( request.getParameterValues( "library" ) );

    if ( request.getParameter( "includeExclude" ) != null )
    {
      if ( request.getParameter( "includeExclude" ).equalsIgnoreCase( "true" ) )
        libs.setInclude( true );
      else
        libs.setInclude( false );
    }
  }

  private void handleClear( HttpServletRequest request, 
                            LocationFilter libs )
  {
    if ( request.getParameterValues( "remove" ) != null && 
         request.getParameterValues( "remove" ).length != 0 )
      libs.removeCalls( request.getParameterValues( "remove" ) );

    if ( libs.getCodes().isEmpty() )
      libs.setInclude( true );
  }
}
