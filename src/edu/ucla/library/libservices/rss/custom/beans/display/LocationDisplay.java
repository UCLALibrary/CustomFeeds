package edu.ucla.library.libservices.rss.custom.beans.display;

import edu.ucla.library.libservices.rss.custom.beans.tables.RssLocation;
import edu.ucla.library.libservices.rss.custom.db.mappers.RssLocationMapper;
import edu.ucla.library.libservices.rss.custom.db.utility.DataSourceFactory;

import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class LocationDisplay
{
  private static final String ALL_SQL = 
    "SELECT * FROM vger_support.rss_locations ORDER BY location";
  private Vector<String> codes;
  private List<RssLocation> locations;
  //private DriverManagerDataSource ds;
  private DataSource ds;
  private String dsName;
  private String testSchool;

  public LocationDisplay()
  {
  }

  public List<RssLocation> getAllLocations()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( ds );

    locations = sql.query( ALL_SQL, new RssLocationMapper() );
    return locations;
  }

  public void setCodes( Vector<String> codes )
  {
    this.codes = codes;
  }

  private StringBuffer getQuotedCodes()
  {
    StringBuffer quotedCodes;

    quotedCodes = new StringBuffer( "" );

    for ( String theCode: codes )
    {
      quotedCodes.append( "'" ).append( theCode ).append( "'," );
    }

    quotedCodes = quotedCodes.deleteCharAt( quotedCodes.length() - 1 );
    return quotedCodes;
  }

  public List<RssLocation> getLocationsByCode()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( ds );

    locations = 
        sql.query( "SELECT DISTINCT * FROM vger_support.rss_locations WHERE codes in (" + 
                   getQuotedCodes() + ") ORDER BY location", 
                   new RssLocationMapper() );
    return locations;
  }

  public boolean isTestable()
  {
    return !codes.isEmpty();
  }

  public void setTestSchool( String testSchool )
  {
    this.testSchool = testSchool;
  }

  private String getTestSchool()
  {
    return testSchool;
  }

  public boolean isSchoolSelected()
  {
    return codes.contains( getTestSchool() );
  }

  public void setDsName( String dsName )
  {
    this.dsName = dsName;
    ds = DataSourceFactory.createDataSource( getDsName() );
    //ds = DataSourceFactory.createDataSource(  );
  }

  public String getDsName()
  {
    return dsName;
  }
}
