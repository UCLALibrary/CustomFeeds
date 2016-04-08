package edu.ucla.library.libservices.rss.custom.web.servlets;

import edu.ucla.library.libservices.rss.custom.beans.filters.PubYearsFilter;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PubYearsServlet
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
    PubYearsFilter pubyears = 
      ( PubYearsFilter ) session.getAttribute( "pubyears" );

    if ( pubyears == null )
      pubyears = new PubYearsFilter();

    //if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "add" ) )
      handleAdd( request, pubyears );
    //else
      //handleClear( request, pubyears );

    session.setAttribute( "pubyears", pubyears );

    response.sendRedirect( "confirmation.jsp" );
  }

  private void handleAdd( HttpServletRequest request, 
                          PubYearsFilter years )
  {
    if ( request.getParameter( "years" ) != null && 
         request.getParameter( "years" ).length() != 0 )
      years.addCalls( request.getParameter( "years" ) );
  }

  private void handleClear( HttpServletRequest request, 
                            PubYearsFilter years )
  {
    if ( request.getParameterValues( "remove" ) != null && 
         request.getParameterValues( "remove" ).length != 0 )
      years.removeYears( request.getParameterValues( "remove" ) );
  }
}
