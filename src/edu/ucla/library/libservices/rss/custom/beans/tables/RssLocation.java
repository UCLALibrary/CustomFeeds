package edu.ucla.library.libservices.rss.custom.beans.tables;

public class RssLocation
{
  private String location;
  private String codes;
  private String pageLink;

  public RssLocation()
  {
    setCodes( "" );
    setLocation( "" );
    setPageLink( "" );
  }

  public void setLocation( String location )
  {
    this.location = location;
  }

  public String getLocation()
  {
    return location;
  }

  public void setCodes( String codes )
  {
    this.codes = codes;
  }

  public String getCodes()
  {
    return codes;
  }

  public void setPageLink( String pageLink )
  {
    this.pageLink = pageLink;
  }

  public String getPageLink()
  {
    return pageLink;
  }
}
