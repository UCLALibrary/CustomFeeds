package edu.ucla.library.libservices.rss.custom.db.mappers;

import edu.ucla.library.libservices.rss.custom.beans.tables.RssResults;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RssMapper
  implements RowMapper
{
  public RssMapper()
  {
  }

  public Object mapRow( ResultSet rs, int i )
    throws SQLException
  {
    RssResults bean;

    bean = new RssResults();
    bean.setAdded_date( rs.getDate( "added_date" ) );
    rs.getString( "author" );
    if ( rs.wasNull() )
      bean.setAuthor( "" );
    else
      bean.setAuthor( rs.getString( "author" ) );

    rs.getString( "bib_id" );
    if ( rs.wasNull() )
      bean.setBib_id( "" );
    else
      bean.setBib_id( rs.getString( "bib_id" ) );

    rs.getString( "call_number" );
    if ( rs.wasNull() )
      bean.setCall_number( "" );
    else
      bean.setCall_number( rs.getString( "call_number" ) );

    rs.getString( "language_code" );
    if ( rs.wasNull() )
      bean.setLanguage_code( "" );
    else
      bean.setLanguage_code( rs.getString( "language_code" ) );

    rs.getString( "location" );
    if ( rs.wasNull() )
      bean.setLocation( "" );
    else
      bean.setLocation( rs.getString( "location" ) );

    rs.getString( "pub_year" );
    if ( rs.wasNull() )
      bean.setPub_year( "" );
    else
      bean.setPub_year( rs.getString( "pub_year" ) );

    rs.getString( "sort_call_number" );
    if ( rs.wasNull() )
      bean.setSort_call_number( "" );
    else
      bean.setSort_call_number( rs.getString( "sort_call_number" ) );

    rs.getString( "title" );
    if ( rs.wasNull() )
      bean.setTitle( "" );
    else
      bean.setTitle( rs.getString( "title" ) );

    rs.getString( "language" );
    if ( rs.wasNull() )
      bean.setLanguage( "" );
    else
      bean.setLanguage( rs.getString( "language" ) );

    rs.getString( "edition" );
    if ( rs.wasNull() )
      bean.setEdition( "" );
    else
      bean.setEdition( rs.getString( "edition" ) );

    rs.getString( "imprint" );
    if ( rs.wasNull() )
      bean.setImprint( "" );
    else
      bean.setImprint( rs.getString( "imprint" ) );

    rs.getString( "series" );
    if ( rs.wasNull() )
      bean.setSeries( "" );
    else
      bean.setSeries( rs.getString( "series" ) );

    rs.getString( "bib_subjects" );
    if ( rs.wasNull() )
      bean.setBib_subjects( "" );
    else
      bean.setBib_subjects( rs.getString( "bib_subjects" ) );

    rs.getString( "location_code" );
    if ( rs.wasNull() )
      bean.setLocation_code( "" );
    else
      bean.setLocation_code( rs.getString( "location_code" ) );

    return bean;
  }
}
