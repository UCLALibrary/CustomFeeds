package edu.ucla.library.libservices.rss.custom.beans.utility;

import edu.ucla.library.libservices.rss.custom.db.utility.DataSourceFactory;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class FeedLoggingBean
{
  private int feedID;
  private String feedURL;
  private String clientIP;
  private String callsUrl;
  private String langUrl;
  private boolean includeLang;
  private String locUrl;
  private boolean includeLoc;
  private String yearsUrl;
  private String sourceName;
  private DataSource ds;
  //private DriverManagerDataSource ds;

  private static final String SEQUENCE_SQL = 
    "SELECT custom_feed_seq.nextval FROM dual";
  private static final String CREATE_FEED_SQL = 
    "INSERT INTO vger_support.custom_feed_log(feed_id,feed_date,client_ip," + 
    "call_number_filter,language_filter,include_exclude_language,location_filter" + 
    ",include_exclude_location,pub_year_filter) VALUES(?,sysdate,?,?,?,?,?,?,?)";
  private static final String COUNT_BY_IP_URL = 
    "SELECT COUNT(feed_id) FROM vger_support.custom_feed_log WHERE client_ip =" + 
    " ? AND call_number_filter = ? AND language_filter = ? AND " + 
    "include_exclude_language = ? AND location_filter = ? AND " + 
    "include_exclude_location = ? AND pub_year_filter = ?";
  private static final String UPDATE_BY_ID = 
    "UPDATE vger_support.custom_feed_log SET retrieval_date = sysdate, " 
    + "retrieval_hits = retrieval_hits + 1 WHERE feed_id = ?";
  private static final String UPDATE_BY_IP_URL = 
    "UPDATE vger_support.custom_feed_log SET retrieval_date = sysdate, " + 
    "retrieval_hits = retrieval_hits + 1  WHERE client_ip = ? AND " 
    + "call_number_filter = ? AND language_filter = ? AND " + 
    "include_exclude_language = ? AND location_filter = ? AND " + 
    "include_exclude_location = ? AND pub_year_filter = ?";
  private static final String UPDATE_BY_URL = 
    "UPDATE vger_support.custom_feed_log SET retrieval_date = sysdate, " + 
    "retrieval_hits = retrieval_hits + 1  WHERE " + 
    "call_number_filter = ? AND language_filter = ? AND " + 
    "include_exclude_language = ? AND location_filter = ? AND " + 
    "include_exclude_location = ? AND pub_year_filter = ?";

  public FeedLoggingBean()
  {
    setFeedID( 0 );
    setFeedURL( "" );
    setCallsUrl( "" );
    setLangUrl( "" );
    setIncludeLang( true );
    setLocUrl( "" );
    setIncludeLoc( true );
    setYearsUrl( "" );
  }

  public void setFeedID( int feedID )
  {
    this.feedID = feedID;
  }

  public int getFeedID()
  {
    return feedID;
  }

  public void setFeedURL( String feedURL )
  {
    this.feedURL = feedURL;
  }

  public String getFeedURL()
  {
    return feedURL;
  }

  public void setCallsUrl( String callsUrl )
  {
    this.callsUrl = callsUrl;
  }

  public String getCallsUrl()
  {
    return callsUrl;
  }

  public void setLangUrl( String langUrl )
  {
    this.langUrl = langUrl;
  }

  public String getLangUrl()
  {
    return langUrl;
  }

  public void setIncludeLang( boolean includeLang )
  {
    this.includeLang = includeLang;
  }

  public boolean isIncludeLang()
  {
    return includeLang;
  }

  public void setLocUrl( String locUrl )
  {
    this.locUrl = locUrl;
  }

  public String getLocUrl()
  {
    return locUrl;
  }

  public void setIncludeLoc( boolean includeLoc )
  {
    this.includeLoc = includeLoc;
  }

  public boolean isIncludeLoc()
  {
    return includeLoc;
  }

  public void setYearsUrl( String yearsUrl )
  {
    this.yearsUrl = yearsUrl;
  }

  public String getYearsUrl()
  {
    return yearsUrl;
  }

  private void openSource()
  {
    ds = DataSourceFactory.createDataSource( sourceName );
    //ds = DataSourceFactory.createDataSource();
  }

  public void setSourceName( String sourceName )
  {
    this.sourceName = sourceName;
  }

  public int doFeedCreation()
  {
    JdbcTemplate sql;

    openSource();

    if ( ds != null )
    {
      sql = new JdbcTemplate( ds );

      feedID = sql.queryForInt( SEQUENCE_SQL );

      sql.update( CREATE_FEED_SQL, getLogArray( true, true ) );
    }

    return feedID;
  }

  public void setClientIP( String clientIP )
  {
    this.clientIP = clientIP;
  }

  public String getClientIP()
  {
    return clientIP;
  }

  public void doFeedRetrieval()
  {
    JdbcTemplate sql;
    int feedCount;

    openSource();

    if ( ds != null )
    {
      sql = new JdbcTemplate( ds );
      if ( feedID != 0 )
      {
        sql.update( UPDATE_BY_ID, new Object[]
            { feedID } );
      }
      else
      {
        feedCount = 
            sql.queryForInt( COUNT_BY_IP_URL, getLogArray( false, true ) );

        if ( feedCount != 0 )
        {
          sql.update( UPDATE_BY_IP_URL, getLogArray( false, true ) );
        }
        else
        {
          sql.update( UPDATE_BY_URL, getLogArray( false, false ) );
        }
      }
    }
  }

  private Object[] getLogArray( boolean withFeedID, boolean withIP )
  {
    ArrayList<String> listForm;

    listForm = new ArrayList<String>();

    if ( withFeedID )
      listForm.add( String.valueOf( feedID ) );
    if ( withIP )
      listForm.add( clientIP );
    listForm.add( callsUrl );
    listForm.add( langUrl );
    listForm.add( includeLang? "Y" : "N" );
    listForm.add( locUrl );
    listForm.add( includeLoc? "Y" : "N" );
    listForm.add( yearsUrl );

    return listForm.toArray();
  }
}
/*
 private static final String COUNT_BY_ID = "SELECT count(retrieval_id) FROM vger_support.custom_feed_retrieval WHERE feed_id = ?";
 feedCount = sql.queryForInt(COUNT_BY_ID, new Object[]{feedID});
 
 if ( feedCount != 0 )
 {
 }
 else
 {
 sql.update(ADD_FEED_RETRIEVAL,new Object[]{feedURL,feedID});
 }
 
 private Object[] getLogArray()
 {
   Object[] theArray;

   theArray = new Object[ 8 ];
   theArray[ 0 ] = feedID;
   theArray[ 1 ] = clientIP;

   if ( !callsUrl.equals( "" ) )
     theArray[ 2 ] = callsUrl;
   else
     theArray[ 2 ] = "";
   if ( !langUrl.equals( "" ) )
   {
     theArray[ 3 ] = langUrl;
     theArray[ 4 ] = ( includeLang? "Y" : "N" );
   }
   else
   {
     theArray[ 3 ] = "";
     theArray[ 4 ] = "Y";
   }
   if ( !locUrl.equals( "" ) )
   {
     theArray[ 5 ] = locUrl;
     theArray[ 6 ] = ( includeLoc? "Y" : "N" );
   }
   else
   {
     theArray[ 5 ] = "";
     theArray[ 6 ] = "Y";
   }
   if ( !yearsUrl.equals( "" ) )
     theArray[ 7 ] = yearsUrl;
   else
     theArray[ 7 ] = "";

   return theArray;
 }

*/