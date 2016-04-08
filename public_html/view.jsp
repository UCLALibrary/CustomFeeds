<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Custom RSS: Create/View Feed</title>
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
        <strong>Step Six - Create/View RSS Feed (<a href="JavaScript:newWindow('viewHelp.html','viewHelp');">More Help</a>)</strong>
        <br/><br/>
        Click on View/Subscribe in Browser to preview and subscribe to your customized 
        feed in your browser (some older versions of the browsers may only display the 
        raw XML format of your feed).<br/>
        You may also view and subscribe to your feed via Yahoo! or Google.
        <br/><br/>
        <table border="0" width="100%">
          <tr>
            <td align="left" width="30%">
              <a href="confirmationservlet?type=native" target="_blank">
                <img src="images/feed-icon-16x16.jpg" alt="Create RSS">
              </a>
            </td>
            <td align="left" width="45%">
              <a href="confirmationservlet?type=native" target="_blank">View/Subscribe in Browser</a>
            </td>
            <td align="center" valign="middle" rowspan="3" width="25%">
              <form name="confirmation" action="confirmationservlet" method="POST">
                <input type="submit" class="buttons" value="Start Over"/>
                <input type="hidden" name="filteraction" value="clear">
              </form>
            </td>
          </tr>
          <tr>
            <td align="left" width="30%">
              <a href="confirmationservlet?type=yahoo" target="_blank">
                <img src="http://us.i1.yimg.com/us.yimg.com/i/us/my/addtomyyahoo4.gif" width="91" height="17" alt="addtomyyahoo4">
              </a>
            </td>
            <td align="left" width="45%">
              <a href="confirmationservlet?type=yahoo" target="_blank">View/Subscribe in My Yahoo!</a>
            </td>
          </tr>
          <tr>
            <td align="left" width="30%">
              <a href="confirmationservlet?type=google" target="_blank">
                <img src="http://buttons.googlesyndication.com/fusion/add.gif" width="104" height="17" alt="Add to Google">
              </a>
            </td>
            <td align="left" width="45%">
              <a href="confirmationservlet?type=google" target="_blank">View/Subscribe in Google Reader</a>
            </td>
          </tr>
        </table>
      </div>
      <c:import url="buttons.jsp">
        <c:param name="backLive" value="true"/>
        <c:param name="backScreen" value="confirmation.jsp"/>
        <c:param name="nextLive" value="false"/>
        <c:param name="finishLive" value="false"/>
        <c:param name="finishType" value="finish"/>
      </c:import>
      <%--c:import url="footer.html"/--%>
    </div>
  </body>
</html>