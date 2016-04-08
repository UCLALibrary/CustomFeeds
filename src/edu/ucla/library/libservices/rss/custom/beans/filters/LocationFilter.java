package edu.ucla.library.libservices.rss.custom.beans.filters;

import java.util.Collections;
import java.util.Vector;

public class LocationFilter
{
  private Vector<String> codes;
  private String locUrl;
  private boolean include;

  public LocationFilter()
  {
    codes = new Vector<String>();
    locUrl = "";
    include = true;
  }

  public void setCodes( Vector<String> codes )
  {
    this.codes = codes;
    setLocUrl();
  }

  public Vector<String> getCodes()
  {
    return codes;
  }

  public void setInclude( boolean include )
  {
    this.include = include;
  }

  public boolean isInclude()
  {
    return include;
  }

  private void setLocUrl()
  {
    if ( codes.isEmpty() )
      locUrl = "";
    else
    {
      locUrl = "";
      for ( String theCode: codes )
      {
        if ( locUrl.equals( "" ) )
          locUrl = theCode;
        else
          locUrl = locUrl.concat( ";" ).concat( theCode );
      }
    }
  }

  public String getLocUrl()
  {
    return locUrl;
  }

  public void addCodes( String[] _codes )
  {
    clearCalls();
    if ( _codes != null && _codes.length != 0 )
    {
      for ( String theCode: _codes )
        codes.add( theCode );
      Collections.sort( codes );
      setLocUrl();
    }
  }

  public void removeCalls( String[] _codes )
  {
    for ( String theCode: _codes )
      codes.remove( theCode );
    setLocUrl();
  }

  public void clearCalls()
  {
    codes.clear();
    setLocUrl();
  }

}
