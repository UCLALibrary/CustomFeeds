package edu.ucla.library.libservices.rss.custom.beans.filters;

import java.util.Collections;
import java.util.Vector;

public class CallNumberFilter
{
  private Vector<String> callNumbers;
  private String callsUrl;

  public CallNumberFilter()
  {
    callNumbers = new Vector<String>();
    callsUrl = "";
  }

    public void setCallNumbers( Vector<String> callNumbers )
    {
      this.callNumbers = callNumbers;
      setCallsUrl();
    }

    public Vector<String> getCallNumbers()
    {
      return callNumbers;
    }

    private void setCallsUrl()
    {
      if ( callNumbers.isEmpty() )
        callsUrl = "";
      else
      {
        callsUrl = "";
        for ( String theCall: callNumbers )
        {
          if ( callsUrl.equals( "" ) )
            callsUrl = theCall;
          else
            callsUrl = callsUrl.concat( "," ).concat( theCall );
        }
      }
    }

    public String getCallsUrl()
    {
      return callsUrl;
    }

    public void addCalls( String[] calls )
    {
      for ( String theCall: calls )
        callNumbers.add( theCall );
      Collections.sort(callNumbers);
      setCallsUrl();
    }

    public void removeCalls( String[] calls )
    {
      for ( String theCall: calls )
        callNumbers.remove( theCall );
      setCallsUrl();
    }

    public void clearCalls()
    {
      callNumbers.clear();
      setCallsUrl();
    }

}
