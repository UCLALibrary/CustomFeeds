<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="pubyears" class="edu.ucla.library.libservices.rss.custom.beans.filters.PubYearsFilter" scope="session"/>

<html>
  <head>
    <title>Custom RSS: Publication Years</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/years.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <%--c:import url="banner.html"/--%>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        <strong>Step Four -  Set Publication Year Filter (<a href="JavaScript:newWindow('yearsHelp.html','yearsHelp');">More Help</a>)</strong>
        <br/><br/>
        To include all years, click Next.<br/>
        If you wish to limit feed results to particular years, enter the year(s) 
        below and click Next.
        <br/><br/>
      </div>
      <form class="text" name="addyears" action="pubyearsservlet" method="POST" onsubmit="return validateAdd(addyears)">
        <table border="0" width="98%">
          <tr align="left">
            <td align="left"><b><label for="years">Year filter(s):</label></b></td>
            <td align="left"><input type="text" name="years" value="${pubyears.nakedYears}" size="25"></td>
          </tr>
        </table>
        <input type="hidden" name="filteraction" value="add">
        <div align="left">
            <table width="600">
              <tr>
                <td width="150">
                  <input type="button" class="buttons" value="Cancel" onclick="javascript:parent.location='http://www2.library.ucla.edu/libraries/6453.cfm'"/>
                </td>
                <td width="150">
                  <input type="button" class="buttons" value="<Back" onclick="javascript:document.location='libraries.jsp'"/>
                </td>
                <td width="150">
                  <input type="submit" class="buttons" value="Next>"/>
                </td>
                <td width="150">
                  <input type="button" class="buttons" value="Finish" onclick="javascript:document.location='confirmation.jsp'"/>
                </td>
              </tr>
            </table>
        </div>
      </form>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>