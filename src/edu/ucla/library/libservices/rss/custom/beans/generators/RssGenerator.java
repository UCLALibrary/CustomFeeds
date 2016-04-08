package edu.ucla.library.libservices.rss.custom.beans.generators;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Guid;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.FeedException;
//import com.sun.syndication.io.WireFeedOutput;

import edu.ucla.library.libservices.rss.custom.beans.tables.RssResults;
import edu.ucla.library.libservices.rss.custom.db.mappers.RssMapper;
import edu.ucla.library.libservices.rss.custom.db.utility.DataSourceFactory;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class RssGenerator
{
  private String query;
  private String dsName;
  //private DriverManagerDataSource ds;
  private DataSource ds;
  private static final char BAD_CHAR = 0x1f;
  private static final char GOOD_CHAR = 0x20;

  public RssGenerator()
  {
    setQuery( "" );
  }

  public void setQuery( String query )
  {
    this.query = query;
  }

  private void getConnection()
  {
    ds = DataSourceFactory.createDataSource(getDsName());
    //ds = DataSourceFactory.createDataSource();
  }

  //public Document getFeed()
  public Channel getFeed()
    throws FeedException
  {
    //WireFeedOutput output;
    Channel feed;
    List entries;
    JdbcTemplate details;
    List<RssResults> results;

    feed = new Channel();
    entries = new ArrayList();

    feed.setTitle( "UCLA - Custom Feed - Newly added titles" );
    feed.setLink( "http://www2.library.ucla.edu/libraries/6453.cfm" );
    feed.setDescription( "Titles added between two and five days ago" );
    feed.setEncoding( "UTF-8" );

    getConnection();

    if ( ds != null )
    {
      details = new JdbcTemplate( ds );

      results = details.query( query, new RssMapper() );
      for ( RssResults theBook: results )
      {
        Item entry;
        Description description;
        Guid guid;

        entry = new Item();
        description = new Description();
        guid = new Guid();

        entry.setTitle( theBook.getTitle().replace(BAD_CHAR, GOOD_CHAR) );
        entry.setLink(
          "http://catalog.library.ucla.edu/cgi-bin/Pwebrecon.cgi?DB=local&BBID="
          + theBook.getBib_id() );
        entry.setPubDate( theBook.getAdded_date() );

        description.setType( "text/html" );

        description.setValue(
          "<table><tr><td align=\"right\">Language:</td><td align=\"left\">" +
          theBook.getLanguage() + "</td></tr>" +
          ( !theBook.isEmpty( theBook.getEdition() )?
            "<tr><td align=\"right\">Edition:</td><td align=\"left\">" +
            theBook.getEdition() + "</td></tr>" : "" ) +
          ( !theBook.isEmpty( theBook.getImprint() )?
            "<tr><td align=\"right\">Published/Distributed:</td><td align=\"left\">" +
            theBook.getImprint() + "</td></tr>" : "" ) +
          ( !theBook.isEmpty( theBook.getBib_subjects() )?
            buildSubjects( theBook.getBib_subjects() ) : "" ) +
          ( !theBook.isEmpty( theBook.getSeries() )?
            "<tr><td align=\"right\">Series:</td><td align=\"left\">" +
            theBook.getSeries() + "</td></tr>" : "" ) +
          "<tr><td align=\"right\">Location:</td><td align=\"left\">" +
          theBook.getLocation() + ": " + theBook.getCall_number() + "</td></tr>" +
          "</table>" );

        guid.setPermaLink( true );
        guid.setValue(
          "http://catalog.library.ucla.edu/cgi-bin/Pwebrecon.cgi?DB=local&BBID=" +
          theBook.getBib_id() );
        entry.setDescription( description );
        entry.setGuid( guid );
        entries.add( entry );
      }

    }

    feed.setItems( entries );

    feed.setFeedType( "rss_2.0" );
    return feed;
  }

  private String buildSubjects( String theSubjects )
  {
    StringBuffer formatted;
    String[] subjects;

    formatted =
        new StringBuffer(
          "<tr><td align=\"right\">Subject(s):" + "</td><td align=\"left\">" );
    subjects = theSubjects.split( "[|]" );

    formatted.append( subjects[ 0 ].replaceAll( "\\$[a-zA-Z]",
                                                "-" ).replaceFirst( "-",
                                                                    "" ).trim() +
                      "</td></tr>" );

    for ( int i = 1; i < subjects.length; i++ )
    {
      formatted.append( "<tr><td align=\"right\">&nbsp;</td><td align=\"left\">" +
                        subjects[ i ].replaceAll( "\\$[a-zA-Z]",
                                                  "-" ).replaceFirst( "-",
                                                                      "" ).trim() +
                        "</td></tr>" );
    }

    return formatted.toString();
  }

  public void setDsName( String dsName )
  {
    this.dsName = dsName;
  }

  private String getDsName()
  {
    return dsName;
  }
}
    //output = new WireFeedOutput();
    /*try
    {
      output.output(feed, new File("E:\\jboss-4.2.2.GA\\server\\default\\log\\rss.xml"));
    }
    catch ( IOException ioe )
    {
      ioe.printStackTrace();
    }*/
    //return output.outputW3CDom( feed );
     //import org.w3c.dom.Document;

       //import java.io.File;
       //import java.io.IOException;
       //import java.util.GregorianCalendar;
       //import org.springframework.jdbc.datasource.DriverManagerDataSource;

