package edu.ucla.library.libservices.rss.custom.beans.display;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PubYearsDisplay
{
  private final GregorianCalendar TODAY = new GregorianCalendar();
  private final int CURRENT_YEAR = TODAY.get( Calendar.YEAR );
  private String year;

  public PubYearsDisplay()
  {
    setYear( "" );
  }

  public void setYear( String year )
  {
    this.year = year;
  }

  public String getFormattedYear()
  {
    String formattedYear = "";
    if ( year.startsWith( "+" ) )
      formattedYear = 
          String.valueOf( CURRENT_YEAR - Integer.parseInt( year.substring( 1 ) ) ) + 
          "-" + CURRENT_YEAR;
    else if ( year.startsWith( "-" ) )
      formattedYear = year.substring( 1 ).concat( " or earlier" );
    else if ( year.endsWith( "-" ) )
      formattedYear = 
          year.substring( 0, year.length() - 1 ).concat( " or later" );
    else
      formattedYear = year;

    return formattedYear;
  }

  public int getCurrentYear()
  {
    return CURRENT_YEAR;
  }
}
