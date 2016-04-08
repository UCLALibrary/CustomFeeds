<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="callnumbers" class="edu.ucla.library.libservices.rss.custom.beans.filters.CallNumberFilter" scope="session"/>

<html>
  <head>
    <title>Custom RSS: Call Numbers</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/yahoo.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/event.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/connection.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/calls.js" type="text/javascript"></script>
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <%--c:import url="banner.html"/--%>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        <strong>Step One -  Set Call Number Filter (<a href="JavaScript:newWindow('callHelp.html','callHelp');">More Help</a>)</strong>
        <br/><br/>
        To include all call numbers, click Next.<br/>
        Select a call letter from the drop down menu and enter optional range 
        numbers or use wildcard, and click &quot;Add --&gt;&quot; to add the call number to the 
        &quot;Selected Call Numbers&quot; list. Repeat this step as needed.<br/>
        Click &quot;&lt;-- Remove&quot; to remove selected entries from &quot;Selected Call Numbers&quot;, 
        or &quot;&lt;-- Remove All&quot; to clear the list.
        <br/><br/>
      </div>
      <form  class="text" name="addcalls" action="" method="POST" onsubmit="return validate(addcalls)">
        <table border=0 width="98%">
          <tr valign="center">
            <td width="60%" align="left" valign="top" style="border:0;">
              <table width="100%" border="0">
                <tr>
                  <td align="left" colspan="2">
                    <strong>
                      1.&nbsp;<input type="radio" name="locOrNlm" value="loc" checked/>
                      &nbsp;Select <a href="http://www.loc.gov/catdir/cpso/lcco/" target="_blank">LC</a> Call Letter(s):
                    </strong>
                  </td>
                </tr>
                <tr>
                  <td align="left" colspan="2">
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <select class="calls" name="locLetters" onchange="switchLocNlm(addcalls,0)">
                      <c:import url="locLetters.html"/>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="left" colspan="2">
                    <strong>
                      &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="locOrNlm" value="loc"/>
                      &nbsp;Select <a href="http://www.nlm.nih.gov/pubs/factsheets/nlmclassif.html" target="_blank">NLM</a> Call Letter(s):
                    </strong>
                  </td>
                </tr>
                <tr>
                  <td align="left" colspan="2">
                  	&nbsp;&nbsp;&nbsp;&nbsp;
                    <select class="calls" name="nlmLetters" onchange="switchLocNlm(addcalls,1)">
                      <c:import url="nlmLetters.html"/>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="left" colspan="2">&nbsp;</td>
                </tr>
                <tr>
                  <td align="left" colspan="2">
                    <b>
                      2.&nbsp;<input type="radio" name="rangeOrWild" value="range" checked onclick="switchRangeWild(addcalls)"/>Enter Optional Range:
                    </b>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <label for="rlow">Start Range/Base Number</label>
                  </td>
                  <td align="left">
                    <input type="text" name="rlow" size=8/>
                  </td>
                </tr>
                <tr>
                  <td align="right">
                    <label for="rhigh">End Range</label>
                  </td>
                  <td align="left">
                    <input type="text" name="rhigh" size=8/>
                  </td>
                </tr>
                <tr>
                  <td align="left" colspan="2">&nbsp;</td>
                </tr>
                <tr>
                  <td align="left" colspan="2">
                    <b>
                      &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="rangeOrWild" value="wild" onclick="switchRangeWild(addcalls)"/>
                      Use a wildcard with the call letter
                    </b>
                  </td>
                </tr>
              </table>
            </td>
            <td width="10%" valign="middle" style="border:0;">
              <center>
                <input type="button" class="bigbuttons" value="Add -->" onClick="addRange(addcalls.locLetters,addcalls.nlmLetters,addcalls.rlow,addcalls.rhigh);"></input>
                <br/>
                <input type="button" class="bigbuttons" value="<-- Remove" onClick="removeRange(addcalls.rangelist)"></input>
                <br/>
                <input type="button" class="bigbuttons" value="<-- Remove All" onClick="removeRangeAll(addcalls.rangelist)"></input>
              </center>
            </td>
            <td width="30%" valign="top" style="border:0;">
              <center>
                <b><label for="rangelist">Selected Call Numbers</label></b>
                <br/>
                <select name="rangelist" size=10 multiple="yes" style="width: 150px">
                  <c:forEach var="theCall" items="${callnumbers.callNumbers}">
                    <option value="${theCall}">${theCall}</option>
                  </c:forEach>
                </select>
              </center>
            </td>
          </tr>
        </table>
      </form>
      <c:import url="buttons.jsp">
          <c:param name="backLive" value="false"/>
        <c:param name="nextLive" value="true"/>
        <c:param name="nextScreen" value="uclalangs.jsp"/>
        <c:param name="finishLive" value="true"/>
        <c:param name="finishType" value="finish"/>
      </c:import>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>