<%@ page contentType="text/html;charset=UTF-8" errorPage="/errors.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="left">
  <form action="">
    <table width="600">
      <tr>
        <td width="150">
          <input type="button" class="buttons" value="Cancel" onclick="javascript:parent.location='http://www2.library.ucla.edu/libraries/6453.cfm'"/>
        </td>
        <td width="150">
          <c:if test="${param.backLive}">
            <input type="button" class="buttons" value="<Back" onclick="javascript:document.location='${param.backScreen}'"/>
          </c:if>
          <c:if test="${not param.backLive}">
            <input type="button" class="buttons" value="<Back" disabled/>
          </c:if>
        </td>
        <td width="150">
          <c:if test="${param.nextLive}">
            <input type="button" class="buttons" value="Next>" onclick="javascript:document.location='${param.nextScreen}'"/>
          </c:if>
          <c:if test="${not param.nextLive}">
            <input type="button" class="buttons" value="Next>" disabled/>
          </c:if>
        </td>
        <td width="150">
          <c:choose>
            <c:when test="${param.finishType eq 'finish' and param.finishLive}">
              <input type="button" class="buttons" value="Finish" onclick="javascript:document.location='confirmation.jsp'"/>
            </c:when>
            <c:when test="${param.finishType eq 'finish' and not param.finishLive}">
              <input type="button" class="buttons" value="Finish" disabled/>
            </c:when>
            <c:when test="${param.finishType eq 'confirm'}">
              <input type="button" class="buttons" value="Confirm" onclick="javascript:document.location='view.jsp'"/>
            </c:when>
          </c:choose>
        </td>
      </tr>
    </table>
  </form>
</div>
