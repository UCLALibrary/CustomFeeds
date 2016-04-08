<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Custom RSS: Welcome</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" hreflang="en">
    <script language="JavaScript" src="js/openhelp.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="main">
      <c:import url="banner.html"/>
      <div class="text">
        <br/><br/>
        <strong>Custom RSS Feed Wizard (<a href="JavaScript:newWindow('genHelp.html','genHelp');">Help</a>)</strong>
        <br/><br/>
        The Customized Feed Wizard will help you to create your customized feeds. 
        In the following steps, you will set desired filters, call number(s), 
        language(s), library or libraries, and publication years, for your RSS 
        feed. You may skip any filter; the resulting feed will include all possible 
        values for that filter. For example: if you do not set a language filter, 
        then the resulting feed will include entries in all languages.
        <br/>
        You may also submit a feed with no filters set, but we discourage this, 
        since such a feed would include every item acquired/cataloged in the 
        last 5 days, and is likely to be inconveniently large or slow to build.
        <br/><br/>
        Click Next to start.
        <c:import url="buttons.jsp">
          <c:param name="backLive" value="false"/>
          <c:param name="nextLive" value="true"/>
          <c:param name="nextScreen" value="callNumbers.jsp"/>
          <c:param name="finishLive" value="false"/>
          <c:param name="finishType" value="finish"/>
        </c:import>
      </div>
      <hr class="section">
      <c:import url="footer.html"/>
    </div>
  </body>
</html>