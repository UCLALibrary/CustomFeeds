package edu.ucla.library.libservices.rss.custom.beans.utility;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class SqlConverter
{
  private String calls;
  private String langCodes;
  private String libCodes;
  private String years;
  private boolean incLangs;
  private boolean incLibs;

  private static final String BARE_LETTERS = "[a-zA-Z]{1,3}";
  private static final String BASE_SQL = 
    "SELECT DISTINCT location,call_number,sort_call_number,added_date,bib_id," + 
    "language_code,language,edition,imprint,series,author,title,pub_year," + 
    "bib_subjects,location_code FROM vger_support.ucladb_rss_erdb_results ";
  private static final String BASE_WHERE = "WHERE ";
  private static final String LETTERS_AND_BASE = "[a-zA-Z]{1,3}[\\d]+";
  private static final String ORDER_BY = 
    " ORDER BY location, sort_call_number";
  private static final String WILDCARD_LETTERS = "[a-zA-Z]{1,3}[\\*]{1}";
  private final GregorianCalendar TODAY = new GregorianCalendar();
  private final int CURRENT_YEAR = TODAY.get( Calendar.YEAR );

  public SqlConverter()
  {
    setCalls( "" );
    setLangCodes( "" );
    setIncLangs( true );
    setLibCodes( "" );
    setIncLibs( true );
    setYears( "" );
  }

  public void setCalls( String calls )
  {
    this.calls = calls;
  }

  public void setLangCodes( String langCodes )
  {
    this.langCodes = langCodes;
  }

  public void setIncLangs( boolean incLangs )
  {
    this.incLangs = incLangs;
  }

  public void setLibCodes( String libCodes )
  {
    this.libCodes = libCodes;
  }

  public void setIncLibs( boolean incLibs )
  {
    this.incLibs = incLibs;
  }

  public void setYears( String years )
  {
    this.years = years;
  }

  public String getSql()
  {
    StringBuffer sql;
    StringBuffer whereClause;
    StringBuffer callClause;
    StringBuffer langClause;
    StringBuffer libClause;
    StringBuffer yearClause;

    sql = new StringBuffer( BASE_SQL );
    whereClause = new StringBuffer( BASE_WHERE );
    callClause = new StringBuffer( "" );
    langClause = new StringBuffer( "" );
    libClause = new StringBuffer( "" );
    yearClause = new StringBuffer( "" );

    if ( !calls.equals( "" ) )
      callClause = setCallSQL();
    if ( !langCodes.equals( "" ) )
      langClause = setlangSQL();
    if ( !libCodes.equals( "" ) )
      libClause = setLibSQL();
    if ( !years.equals( "" ) )
      yearClause = setYearSQL();

    if ( callClause.length() != 0 )
      whereClause.append( addClause( whereClause.length(), callClause ) );
    if ( langClause.length() != 0 )
      whereClause.append( addClause( whereClause.length(), langClause ) );
    if ( libClause.length() != 0 )
      whereClause.append( addClause( whereClause.length(), libClause ) );
    if ( yearClause.length() != 0 )
      whereClause.append( addClause( whereClause.length(), yearClause ) );

    if ( whereClause.length() > BASE_WHERE.length() )
      sql.append( whereClause );
    sql.append( ORDER_BY );

    return sql.toString();
  }

  private StringBuffer setCallSQL()
  {
    String[] splitCalls;
    StringBuffer callSQL;

    callSQL = new StringBuffer( " ( " );
    splitCalls = calls.split( "[,]" );

    callSQL.append( getCallSqlByType( splitCalls[ 0 ], 
                                      getPatternType( splitCalls[ 0 ] ) ) );
    for ( int index = 1; index < splitCalls.length; index++ )
      callSQL.append( " OR " ).append( getCallSqlByType( splitCalls[ index ], 
                                                         getPatternType( splitCalls[ index ] ) ) );

    callSQL.append( " ) " );
    return callSQL;
  }

  private StringBuffer setlangSQL()
  {
    String[] splitLangs;
    StringBuffer langSQL;

    splitLangs = langCodes.split( "[,]" );
    langSQL = new StringBuffer( " ( " );
    if ( incLangs )
    {
      langSQL.append( "language_code = '" + splitLangs[ 0 ] + "'" );
      for ( int index = 1; index < splitLangs.length; index++ )
        langSQL.append( " OR language_code = '" + splitLangs[ index ] + 
                        "'" );
    }
    else
    {
      langSQL.append( "language_code != '" + splitLangs[ 0 ] + "'" );
      for ( int index = 1; index < splitLangs.length; index++ )
        langSQL.append( " AND language_code != '" + splitLangs[ index ] + 
                        "'" );
    }

    langSQL.append( " ) " );

    return langSQL;
  }

  private StringBuffer setLibSQL()
  {
    String[] splitLibs;
    StringBuffer libsSQL;

    splitLibs = libCodes.split( "[,;]" );
    libsSQL = new StringBuffer( " ( " );

    if ( incLibs )
    {
      libsSQL.append( "location_code LIKE '" + splitLibs[ 0 ] + 
                      "' || '%'" );
      for ( int index = 1; index < splitLibs.length; index++ )
        libsSQL.append( " OR location_code LIKE '" + splitLibs[ index ] + 
                        "' || '%'" );
    }
    else
    {
      libsSQL.append( "location_code NOT LIKE '" + splitLibs[ 0 ] + 
                      "' || '%'" );
      for ( int index = 1; index < splitLibs.length; index++ )
        libsSQL.append( " AND location_code NOT LIKE '" + 
                        splitLibs[ index ] + "' || '%'" );
    }

    libsSQL.append( " ) " );

    return libsSQL;
  }

  private StringBuffer setYearSQL()
  {
    String[] splitYears;
    StringBuffer yearsSQL;

    splitYears = years.split( "[,]" );
    yearsSQL = new StringBuffer( " ( " );

    yearsSQL.append( getYearType( splitYears[ 0 ] ) );
    for ( int index = 1; index < splitYears.length; index++ )
    {
      yearsSQL.append( " OR " ).append( getYearType( splitYears[ index ] ) );
    }
    yearsSQL.append( " ) " );

    return yearsSQL;
  }

  private String getYearType( String theYear )
  {
    if ( theYear.startsWith( "+" ) )
      return " ( pub_year between " + 
        ( CURRENT_YEAR - Integer.parseInt( theYear.substring( 1 ) ) ) + 
        " and " + CURRENT_YEAR + " ) ";
    else if ( theYear.startsWith( "-" ) )
      return " ( pub_year <= " + theYear.substring( 1 ) + " ) ";
    else if ( theYear.endsWith( "-" ) )
      return " ( pub_year >= " + 
        theYear.substring( 0, theYear.length() - 1 ) + " ) ";
    else if ( theYear.indexOf( "-" ) != -1 )
      return " ( pub_year between " + theYear.split( "-" )[ 0 ] + " and " + 
        theYear.split( "-" )[ 1 ] + " ) ";
    else
      return " ( pub_year = " + theYear + " ) ";
  }

  private StringBuffer addClause( int length, StringBuffer clause )
  {
    StringBuffer temp;

    if ( length > BASE_WHERE.length() )
      temp = new StringBuffer( " AND " );
    else
      temp = new StringBuffer( "" );

    return temp.append( clause );
  }

  private StringBuffer getCallSqlByType( String call, int type )
  {
    StringBuffer sql;
    sql = new StringBuffer( "" );

    switch ( type )
    {
      case 1:
        sql.append( " ( sort_call_number BETWEEN vger_support.normalizecallnumber('" + 
                    call + "1') AND vger_support.normalizecallnumber('" + 
                    call + 
                    "9999') OR sort_call_number LIKE vger_support.normalizecallnumber('" + 
                    call + "9999') || '%' ) " );
        break;
      case 2:
        sql.append( " ( sort_call_number LIKE vger_support.normalizecallnumber('" + 
                    call.substring( 0, call.indexOf( "*" ) ) + 
                    "') || '%' ) " );
        break;
      case 3:
        sql.append( " ( sort_call_number LIKE vger_support.normalizecallnumber('" + 
                    call + "') || '%' ) " );
        break;
      case 4:
        sql.append( " ( sort_call_number BETWEEN vger_support.normalizecallnumber('" + 
                    call.split( "-" )[ 0 ] + 
                    "') AND vger_support.normalizecallnumber('" + 
                    call.split( "-" )[ 1 ] + 
                    "') OR sort_call_number LIKE " + 
                    "vger_support.normalizecallnumber('" + 
                    call.split( "-" )[ 1 ] + "') || '%' ) " );
        break;
    }

    return sql;
  }

  private int getPatternType( String toMatch )
  {
    int patterntype;

    patterntype = 0;

    if ( Pattern.matches( BARE_LETTERS, toMatch ) )
      patterntype = 1;
    else if ( Pattern.matches( WILDCARD_LETTERS, toMatch ) )
      patterntype = 2;
    else if ( Pattern.matches( LETTERS_AND_BASE, toMatch ) )
      patterntype = 3;
    else
      patterntype = 4;

    return patterntype;
  }
}
