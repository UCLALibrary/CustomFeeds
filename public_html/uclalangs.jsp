<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="langs" class="edu.ucla.library.libservices.rss.custom.beans.filters.LanguageFilter" scope="session"/>

<jsp:useBean id="langDisplay" class="edu.ucla.library.libservices.rss.custom.beans.display.LanguageDisplay" scope="page"/>
<jsp:setProperty name="langDisplay" property="dsName" value="${initParam['db.source']}"/>
<jsp:setProperty name="langDisplay" property="codes" value="${langs.codes}"/>

<html>
  <head>
    <title>Custom RSS: Languages</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/yahoo.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/event.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/connection.js" type="text/javascript"></script>
    <!--script language="JavaScript" src="js/selectbox.js" type="text/javascript"></script-->
    <script language="JavaScript" src="js/langs.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <%--c:import url="banner.html"/--%>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        <strong>Step Two - Set Language Filter (<a href="JavaScript:newWindow('langHelp.html','langHelp');">More Help</a>)</strong>
        <br/><br/>
        To include all languages, click Next.<br/>
        To pick language(s) for the filter, select them from &quot;Available Languages&quot; 
        and click &quot;Add --&gt;&quot; to move them to the &quot;Selected Languages&quot; list.<br/>
        Click &quot;&lt;-- Remove&quot; to remove selected language(s) from &quot;Selected Languages&quot;, 
        or &quot;&lt;-- Remove All&quot; to clear the list.
        <br/><br/>
      </div>
      <form class="text" name="addlangs" action="" method="POST" onsubmit="return validateAdd(addlangs)">
        <table border="0" width="98%">
          <tr>
            <td align="left">
                <b><label for="source">Available Languages taught at UCLA:</label></b><br/>
                <a href="genlangs.jsp">View all languages available in the catalog</a>.
            </td>
            <td align="left">&nbsp;</td>
            <td align="left">
                <b><label for="langs">Selected Languages</label></b>
            </td>
          </tr>
          <tr>
            <td width="45%" align="left" valign="middle" style="border:0;">
              <select name="source" size="15" multiple="yes" style="width: 250px">
                <c:forEach var="theLang" items="${langDisplay.uclaLangs}">
                  <option value="${theLang.code}">${theLang.uclaDisplay}</option>
                </c:forEach>
              </select>
            </td>
            <td width="10%" valign="middle" style="border:0;">
              <center>
                <input type="button" class="bigbuttons" value="Add -->" onClick="addSelectedOptions(addlangs.source,addlangs.langs,addlangs.includeExclude);"></input>
                <br/>
                <input type="button" class="bigbuttons" value="<-- Remove" onClick="removeSelectedOptions(addlangs.langs,addlangs.source,addlangs.includeExclude)"></input>
                <br/>
                <input type="button" class="bigbuttons" value="<-- Remove All" onClick="removeAllOptions(addlangs.langs,addlangs.source)"></input>
              </center>
            </td>
            <td width="45%" valign="middle" style="border:0;">
              <select name="langs" size="15" multiple="yes" style="width: 300px">
                <c:if test="${not empty langs.codes}">
                  <c:forEach items="${langDisplay.langsByCodes}" var="theLang">
                    <option value="${theLang.code}">${theLang.uclaDisplay}</option>
                  </c:forEach>
                </c:if>
              </select>
            </td>
          </tr>
        </table>
        <table align="center" border="0" width="98%">
          <tr align="center">
            <c:choose>
              <c:when test="${langs.include}">
                <td align="right"><input type="radio" name="includeExclude" value="true" checked onclick="verifyIncExc(addlangs.includeExclude, addlangs.origincexc, addlangs.firstShow)">Include Selected</td>
                <td align="left"><input type="radio" name="includeExclude" value="false" onclick="verifyIncExc(addlangs.includeExclude, addlangs.origincexc, addlangs.firstShow)">Exclude Selected</td>
              </c:when>
              <c:otherwise>
                <td align="right"><input type="radio" name="includeExclude" value="true" onclick="verifyIncExc(addlangs.includeExclude, addlangs.origincexc, addlangs.firstShow)">Include Selected</td>
                <td align="left"><input type="radio" name="includeExclude" value="false" checked onclick="verifyIncExc(addlangs.includeExclude, addlangs.origincexc, addlangs.firstShow)">Exclude Selected</td>
              </c:otherwise>
            </c:choose>
          </tr>
        </table>
        <c:choose>
          <c:when test="${empty langs.codes}">
            <input type="hidden" name="firstShow" value="true">
          </c:when>
          <c:otherwise>
            <input type="hidden" name="firstShow" value="false">
          </c:otherwise>
        </c:choose>
        <input type="hidden" name="origincexc" value="${langs.include}">
      </form>
      <c:import url="buttons.jsp">
        <c:param name="backLive" value="true"/>
        <c:param name="backScreen" value="callNumbers.jsp"/>
        <c:param name="nextLive" value="true"/>
        <c:param name="nextScreen" value="libraries.jsp"/>
        <c:param name="finishLive" value="true"/>
        <c:param name="finishType" value="finish"/>
      </c:import>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>