package edu.ucla.library.libservices.rss.custom.web.servlets;

import edu.ucla.library.libservices.rss.custom.beans.filters.CallNumberFilter;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CallNumberServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

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

    CallNumberFilter callNumbers = 
      ( CallNumberFilter ) session.getAttribute( "callnumbers" );

    if ( callNumbers == null )
      callNumbers = new CallNumberFilter();

    if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "add" ) )
      handleAdd( request, callNumbers );
    else if ( request.getParameter( "filteraction" ).equalsIgnoreCase( "delSome" ) )
      handleClearSome( request, callNumbers );
    else
      handleClearAll( callNumbers );

    session.setAttribute( "callnumbers", callNumbers );

    //response.setContentType("text/plain; charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    response.getOutputStream().print( callNumbers.getCallsUrl() );
    response.flushBuffer();
  }

    private void handleAdd( HttpServletRequest request, 
                            CallNumberFilter numbers )
    {
      if ( request.getParameterValues( "rangelist" ) != null && 
           request.getParameterValues( "rangelist" ).length != 0 )
        numbers.addCalls( request.getParameterValues( "rangelist" ) );
    }

    private void handleClearSome( HttpServletRequest request, 
                              CallNumberFilter numbers )
    {
      if ( request.getParameterValues( "remove" ) != null && 
           request.getParameterValues( "remove" ).length != 0 )
        numbers.removeCalls( request.getParameterValues( "remove" ) );
    }

      private void handleClearAll( CallNumberFilter numbers )
      {
          numbers.clearCalls();
      }
}
