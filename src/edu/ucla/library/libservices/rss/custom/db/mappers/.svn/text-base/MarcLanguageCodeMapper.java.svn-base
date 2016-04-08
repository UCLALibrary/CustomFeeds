package edu.ucla.library.libservices.rss.custom.db.mappers;

import edu.ucla.library.libservices.rss.custom.beans.tables.MarcLanguageCode;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MarcLanguageCodeMapper
  implements RowMapper
{
  public MarcLanguageCodeMapper()
  {
  }
  public Object mapRow( ResultSet rs, int i )
    throws SQLException
  {
    MarcLanguageCode bean;
    bean = new MarcLanguageCode();
    
    bean.setCode(rs.getString("code"));
    bean.setLanguage(rs.getString("language"));
    bean.setUclaDisplay(rs.getString("ucla_display"));
    
    return bean;
  }
}
