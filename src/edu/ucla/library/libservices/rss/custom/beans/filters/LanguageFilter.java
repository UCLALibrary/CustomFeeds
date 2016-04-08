package edu.ucla.library.libservices.rss.custom.beans.filters;

import java.util.Collections;
import java.util.Vector;

public class LanguageFilter
{
  private Vector<String> codes;
  private String langUrl;
  private boolean include;

  public LanguageFilter()
  {
    codes = new Vector<String>();
    langUrl = "";
    include = true;
  }

    public void setCodes( Vector<String> codes )
    {
      this.codes = codes;
      setLangUrl();
    }

    public Vector<String> getCodes()
    {
      return codes;
    }

    private void setLangUrl()
    {
      if ( codes.isEmpty() )
        langUrl = "";
      else
      {
        langUrl = "";
        for ( String theCode: codes )
        {
          if ( langUrl.equals( "" ) )
            langUrl = theCode;
          else
            langUrl = langUrl.concat( "," ).concat( theCode );
        }
      }
    }

    public String getLangUrl()
    {
      return langUrl;
    }

    public void addLangs( String[] _codes )
    {
      for ( String theCode: _codes )
        codes.add( theCode );
      Collections.sort(codes);
      setLangUrl();
    }

    public void removeCalls( String[] _codes )
    {
      for ( String theCode: _codes )
        codes.remove( theCode );
      setLangUrl();
    }

    public void clearCalls()
    {
      codes.clear();
      setLangUrl();
    }

    public void setInclude( boolean include )
    {
      this.include = include;
    }

    public boolean isInclude()
    {
      return include;
    }
}
