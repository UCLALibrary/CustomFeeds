package edu.ucla.library.libservices.rss.custom.beans.display;

import edu.ucla.library.libservices.rss.custom.beans.tables.MarcLanguageCode;
import edu.ucla.library.libservices.rss.custom.db.mappers.MarcLanguageCodeMapper;
import edu.ucla.library.libservices.rss.custom.db.utility.DataSourceFactory;

import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class LanguageDisplay
{
  private static final String SELECT_CLAUSE = 
    "SELECT code, language, ucla_display FROM vger_support.marc_language_codes";
  private static final String ALL_ORDER = " ORDER BY language";
  private static final String ALL_WHERE = " WHERE deprecated = 'N' ";
  private static final String UCLA_ORDER = " ORDER BY ucla_display";
  private static final String UCLA_WHERE = " WHERE ucla_lang = 'Y' ";
  private static final String SELECTED_WHERE = " WHERE code IN ( ";
  
  private Vector<String> codes;
  private List<MarcLanguageCode> languages;
  //private DriverManagerDataSource ds;
  private DataSource ds;
  private String dsName;

  public LanguageDisplay()
  {
    //ds = DataSourceFactory.createDataSource(getDsName());
  }
  
  public List<MarcLanguageCode> getAllLangs()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( ds );

    languages = 
        sql.query( getSql(true), new MarcLanguageCodeMapper() );
    return languages;
  }

  public List<MarcLanguageCode> getUclaLangs()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( ds );

    languages = 
        sql.query( getSql(false), new MarcLanguageCodeMapper() );
    return languages;
  }

  public List<MarcLanguageCode> getLangsByCodes()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( ds );

    languages = 
        sql.query( 
          "SELECT code, language, ucla_display FROM vger_support.marc_language_codes" 
          + " WHERE code IN (" + getQuotedCodes() + ") ORDER BY language", 
          new MarcLanguageCodeMapper() );
    return languages;
  }

  private StringBuffer getQuotedCodes()
  {
    StringBuffer quotedCodes;
    quotedCodes = new StringBuffer("");
    
    for ( String theCode: codes )
    {
      quotedCodes.append("'").append(theCode).append("',");
    }
    
    return quotedCodes.deleteCharAt(quotedCodes.length() - 1);
  }

  public void setCodes( Vector<String> codes )
  {
    this.codes = codes;
  }
  private String getSql(boolean listAll)
  {
    StringBuffer query;
    
    query = new StringBuffer(SELECT_CLAUSE);
    if (listAll)
      query.append(ALL_WHERE);
    else
      query.append(UCLA_WHERE);
    
    if ( codes != null && codes.size() != 0 )
    {
      query.append(" MINUS ").append(SELECT_CLAUSE).append(SELECTED_WHERE);
      query.append(getQuotedCodes()).append(" ) ");
    }
    if (listAll)
      query.append(ALL_ORDER);
    else
      query.append(UCLA_ORDER);
    
    return query.toString();
  }

  public void setLanguages( List<MarcLanguageCode> languages )
  {
    this.languages = languages;
  }

  public List<MarcLanguageCode> getLanguages()
  {
    return languages;
  }

  public void setDsName( String dsName )
  {
    this.dsName = dsName;
    ds = DataSourceFactory.createDataSource(getDsName());
    //ds = DataSourceFactory.createDataSource();
  }

  public String getDsName()
  {
    return dsName;
  }
}
