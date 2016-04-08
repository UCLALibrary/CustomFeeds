package edu.ucla.library.libservices.rss.custom.beans.filters;

import java.util.Vector;

public class PubYearsFilter
{
  private Vector<String> pubYears;
  private String nakedYears;
  private String yearsUrl;

  public PubYearsFilter()
  {
    pubYears = new Vector<String>();
    nakedYears = "";
    yearsUrl = "";
  }

  public void setPubYears( Vector<String> pubYears )
  {
    this.pubYears = pubYears;
    setYearsUrl();
  }

  public Vector<String> getPubYears()
  {
    return pubYears;
  }

  private void setYearsUrl()
  {
    if ( pubYears.isEmpty() )
      yearsUrl = "";
    else
    {
      yearsUrl = "";
      for ( String theYear: pubYears )
      {
        if ( yearsUrl.equals( "" ) )
          yearsUrl = theYear;
        else
          yearsUrl = yearsUrl.concat( "," ).concat( theYear );
      }
    }
  }

  public String getYearsUrl()
  {
    return yearsUrl;
  }

  public void addCalls( String[] years )
  {
    for ( String theYear: years )
      pubYears.add( theYear );
    //Collections.sort(pubYears);
    setYearsUrl();
  }

  public void addCalls( String years )
  {
    clearYears();
    setNakedYears( years );
    addCalls( years.split( "[;,\\/]" ) );
  }

  public void removeYears( String[] years )
  {
    for ( String theYear: years )
      pubYears.remove( theYear );
    setYearsUrl();
  }

  public void clearYears()
  {
    pubYears.clear();
    setYearsUrl();
  }

  private void setNakedYears( String nakedYears )
  {
    this.nakedYears = nakedYears;
  }

  public String getNakedYears()
  {
    return nakedYears;
  }
}
