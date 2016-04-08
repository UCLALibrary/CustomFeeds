package edu.ucla.library.libservices.rss.custom.db.mappers;

import edu.ucla.library.libservices.rss.custom.beans.tables.RssLocation;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RssLocationMapper
  implements RowMapper
{
  public RssLocationMapper()
  {
  }
  public Object mapRow( ResultSet rs, int i )
    throws SQLException
  {
    RssLocation bean;
    
    bean = new RssLocation();
    bean.setCodes(rs.getString("codes"));
    bean.setLocation(rs.getString("location"));
    bean.setPageLink(rs.getString("page_link"));
    
    return bean;
  }
}
