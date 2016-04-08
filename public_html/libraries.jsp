<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="locations" class="edu.ucla.library.libservices.rss.custom.beans.filters.LocationFilter" scope="session"/>

<jsp:useBean id="locationOutput" class="edu.ucla.library.libservices.rss.custom.beans.display.LocationDisplay" scope="page"/>
<jsp:setProperty name="locationOutput" property="dsName" value="${initParam['db.source']}"/>
<jsp:setProperty name="locationOutput" property="codes" value="${locations.codes}"/>

<html>
  <head>
    <title>Custom RSS: Libraries</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/libraries.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <%--c:import url="banner.html"/--%>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        <strong>Step Three -  Set Library Filter (<a href="JavaScript:newWindow('localeHelp.html','localeHelp');">More Help</a>)</strong>
        <br/><br/>
        To include all libraries, click Next. <br/>
        To pick libraries for the filter, check their boxes. 
        <br/><br/>
      </div>
      <form  class="text" name="addlibs" action="locationservlet" method="POST"><!-- onsubmit="return validateAdd(addlibs)"-->
        <c:forEach items="${locationOutput.allLocations}" var="theLibrary">
          <c:choose>
            <c:when test="${locationOutput.testable}">
              <jsp:setProperty name="locationOutput" property="testSchool" value="${theLibrary.codes}"/>
              <c:choose>
                <c:when test="${locationOutput.schoolSelected}">
                  <input type="checkbox" name="library" value="${theLibrary.codes}" checked>
                </c:when>
                <c:otherwise>
                  <input type="checkbox" name="library" value="${theLibrary.codes}">
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${not empty theLibrary.pageLink}"><a href="${theLibrary.pageLink}" target="_blank">${theLibrary.location}</a><br/></c:when>
                <c:otherwise>${theLibrary.location}<br/></c:otherwise>
              </c:choose>
            </c:when>
            <c:otherwise>
              <input type="checkbox" name="library" value="${theLibrary.codes}">
              <c:choose>
                <c:when test="${not empty theLibrary.pageLink}"><a href="${theLibrary.pageLink}" target="_blank">${theLibrary.location}</a><br/></c:when>
                <c:otherwise>${theLibrary.location}<br/></c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:forEach>
        <br>
        <table border="0" width="98%">
          <tr align="center">
            <c:choose>
              <c:when test="${locations.include}">
                <td align="right"><input type="radio" name="includeExclude" value="true" checked onclick="verifyIncExc(addlibs.includeExclude, addlibs.origincexc, addlibs.firstShow)">Include Selected</td>
                <td align="left"><input type="radio" name="includeExclude" value="false" onclick="verifyIncExc(addlibs.includeExclude, addlibs.origincexc, addlibs.firstShow)">Exclude Selected</td>
              </c:when>
              <c:otherwise>
                <td align="right"><input type="radio" name="includeExclude" value="true" onclick="verifyIncExc(addlibs.includeExclude, addlibs.origincexc, addlibs.firstShow)">Include Selected</td>
                <td align="left"><input type="radio" name="includeExclude" value="false" checked onclick="verifyIncExc(addlibs.includeExclude, addlibs.origincexc, addlibs.firstShow)">Exclude Selected</td>
              </c:otherwise>
            </c:choose>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
        </table>
        <div align="left">
            <table width="600">
              <tr>
                <td width="150">
                  <input type="button" class="buttons" value="Cancel" onclick="javascript:parent.location='http://www2.library.ucla.edu/libraries/6453.cfm'"/>
                </td>
                <td width="150">
                  <input type="button" class="buttons" value="<Back" onclick="javascript:document.location='uclalangs.jsp'"/>
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
        <c:choose>
          <c:when test="${empty locations.codes}">
            <input type="hidden" name="firstShow" value="true">
          </c:when>
          <c:otherwise>
            <input type="hidden" name="firstShow" value="false">
          </c:otherwise>
        </c:choose>
        <input type="hidden" name="origincexc" value="${locations.include}">
        <input type="hidden" name="filteraction" value="add">
      </form>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>