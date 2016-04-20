package edu.ucla.library.libservices.rss.custom.db.utility;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceFactory
{
  public DataSourceFactory()
  {
  }

  public static DriverManagerDataSource createDataSource()
  {
    DriverManagerDataSource ds;
    
    ds  = new DriverManagerDataSource();
    ds.setDriverClassName( "oracle.jdbc.OracleDriver" );
    ds.setUrl( "URL" );
    ds.setUsername( "USER" );
    ds.setPassword( "PWD" );
    
    return ds;
  }
  
  public static DataSource createDataSource(String name)
  {
    InitialContext context;
    DataSource connection;
    
    try
    {
      context = new InitialContext();
      connection = (DataSource)context.lookup(name);
    }
    catch ( NamingException e )
    {
      e.printStackTrace();
      connection = null;
    }
    
    return connection;
  }
}
