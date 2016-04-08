package edu.ucla.library.libservices.rss.custom.web.services;

import com.sun.syndication.io.FeedException;

import javax.annotation.Resource;

import javax.servlet.ServletRequest;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

import edu.ucla.library.libservices.rss.custom.beans.generators.RssGenerator;
import edu.ucla.library.libservices.rss.custom.beans.utility.SqlConverter;

import javax.servlet.ServletContext;

@WebServiceProvider
@BindingType( value = HTTPBinding.HTTP_BINDING )
public class RssService
  implements Provider<Source>
{
  @Resource
  protected WebServiceContext wsContext;

  public RssService()
  {
  }

  public Source invoke( Source source )
  {
    try
    {
      ServletRequest request;
      ServletContext context;
      SqlConverter converter;
      
      request = 
          ( ServletRequest ) wsContext.getMessageContext().get( MessageContext.SERVLET_REQUEST );
      context = 
        ( ServletContext ) wsContext.getMessageContext().get( MessageContext.SERVLET_CONTEXT );
      converter = new SqlConverter();

      prepSqlConverter( converter, request );
      return getFeed( converter.getSql(), context.getInitParameter( "db.source" ) );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      throw new HTTPException( 500 );
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

  private Source getFeed( String sql, String source )
    throws FeedException
  {
    RssGenerator feedMaker;

    feedMaker = new RssGenerator();
    feedMaker.setQuery( sql );
    feedMaker.setDsName( source );
    return new DOMSource( null );
    //return new DOMSource( feedMaker.getFeed() );
  }
}
