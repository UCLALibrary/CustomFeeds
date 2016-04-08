<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="callnumbers" class="edu.ucla.library.libservices.rss.custom.beans.filters.CallNumberFilter" scope="session"/>
<jsp:useBean id="langs" class="edu.ucla.library.libservices.rss.custom.beans.filters.LanguageFilter" scope="session"/>
<jsp:useBean id="locations" class="edu.ucla.library.libservices.rss.custom.beans.filters.LocationFilter" scope="session"/>
<jsp:useBean id="pubyears" class="edu.ucla.library.libservices.rss.custom.beans.filters.PubYearsFilter" scope="session"/>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Custom RSS: Confirm Filters</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <%--c:import url="banner.html"/--%>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        <strong>Step Five -  Confirm Filter(s) (<a href="JavaScript:newWindow('confHelp.html','confHelp');">More Help</a>)</strong>
        <br/><br/>
        The following are the values you set for your filter(s). If you want to 
        make any changes, click Back as needed and edit your selections.  When 
        you are ready, click Next.
        <br/><br/>
        <table border="0" width="100%">
          <tr>
            <td align="left" valign="top" width="30%"><strong>Selected Call Number(s):</strong></td>
            <td align="left" width="45%">
              <c:if test="${not empty callnumbers.callNumbers}">
                <c:forEach var="theCall" items="${callnumbers.callNumbers}">
                  ${theCall};
                </c:forEach>
              </c:if>
              <c:if test="${empty callnumbers.callNumbers}">
                <strong>All</strong>
              </c:if>
            </td>
            <td align="center" valign="middle" rowspan="4" width="25%">
              <form name="confirmation" action="confirmationservlet" method="POST">
                <input type="submit" class="buttons" value="Start Over"/>
                <input type="hidden" name="filteraction" value="clear">
              </form>
            </td>
          </tr>
          <tr>
            <td align="left" valign="top" width="30%"><strong>Selected Language(s):</strong></td>
            <td align="left" width="45%">
              <c:if test="${not empty langs.codes}">
                <jsp:useBean id="langDisplay" class="edu.ucla.library.libservices.rss.custom.beans.display.LanguageDisplay" scope="page">
                  <jsp:setProperty name="langDisplay" property="dsName" value="${initParam['db.source']}"/>
                  <jsp:setProperty name="langDisplay" property="codes" value="${langs.codes}"/>
                </jsp:useBean>
                <c:if test="${langs.include}">Limited to&nbsp;</c:if>
                <c:if test="${not langs.include}">Excluding&nbsp;</c:if>
                <c:forEach items="${langDisplay.langsByCodes}" var="theLang">
                  <c:choose>
                    <c:when test="${not empty theLang.uclaDisplay}">${theLang.uclaDisplay};</c:when>
                    <c:otherwise>${theLang.language};</c:otherwise>
                  </c:choose>
                </c:forEach>
              </c:if>
              <c:if test="${empty langs.codes}">
                <strong>All</strong>
              </c:if>
            </td>
          </tr>
          <tr>
            <td align="left" valign="top" width="30%"><strong>Selected Library or Libraries:</strong></td>
            <td align="left" width="45%">
              <c:if test="${not empty locations.codes}">
                <jsp:useBean id="locationOutput" class="edu.ucla.library.libservices.rss.custom.beans.display.LocationDisplay" scope="page">
                  <jsp:setProperty name="locationOutput" property="dsName" value="${initParam['db.source']}"/>
                  <jsp:setProperty name="locationOutput" property="codes" value="${locations.codes}"/>
                </jsp:useBean>
                <c:if test="${locations.include}">Limited to&nbsp;</c:if>
                <c:if test="${not locations.include}">Excluding&nbsp;</c:if>
                <c:forEach items="${locationOutput.locationsByCode}" var="theLibrary">
                  ${theLibrary.location};
                </c:forEach>
              </c:if>
              <c:if test="${empty locations.codes}">
                <strong>All</strong>
              </c:if>
            </td>
          </tr>
          <tr>
            <td align="left" valign="top" width="30%"><strong>Selected Publication Year(s):</strong></td>
            <td align="left" width="45%">
              <c:if test="${not empty pubyears.pubYears}">
                <jsp:useBean id="displayyears" class="edu.ucla.library.libservices.rss.custom.beans.display.PubYearsDisplay" scope="page"/>
                <c:forEach var="theYear" items="${pubyears.pubYears}">
                  <jsp:setProperty name="displayyears" property="year" value="${theYear}"/>
                  ${displayyears.formattedYear};
                </c:forEach>
              </c:if>
              <c:if test="${empty pubyears.pubYears}">
                <strong>All</strong>
              </c:if>
            </td>
          </tr>
        </table>
      </div>
      <c:import url="buttons.jsp">
        <c:param name="backLive" value="true"/>
        <c:param name="backScreen" value="pubYears.jsp"/>
        <c:param name="nextLive" value="true"/>
        <c:param name="nextScreen" value="view.jsp"/>
        <c:param name="finishLive" value="true"/>
        <c:param name="finishType" value="confirm"/>
      </c:import>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>