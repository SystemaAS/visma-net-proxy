<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>
<!-- ================== special login header ==================-->
<jsp:include page="/WEB-INF/views/headerLoginVisma.jsp" />
<!-- =====================end header ==========================-->
<SCRIPT type="text/javascript" src="resources/js/login.js?ver=<%= new java.util.Date().getTime()/1000 %>"></SCRIPT>	
 
	<div style="height: 100px;">
	 	<h3 class="text18">&nbsp;</h3>
	 	<%-- <h4></h1><a href="?lang=en">en</a>|<a href="?lang=no">no</a></h4> --%>
	</div>	
	<table align="center" width="500px" border="0" cellpadding="0">
		<tr>
		<%-- <td class="loginFrame" width="100%"> --%>
		<td class="loginFrameE2" width="100%">
			
	 		<table align="center" border="0" cellpadding="1" cellspacing="1" >
	 			<form name="loginForm" id="loginForm" action="logonDashboard.do" method="POST" >
	 				
				<tr height="1"><td>&nbsp;</td></tr>
				<tr>
					<td align="center" colspan="2" class="text28Bold">eSpedsg</td>
				</tr>
				<tr height="3"><td>&nbsp;</td></tr>
				<tr>
					<td align="right" class="text18"><spring:message code="login.user.label.name"/>&nbsp;</td>
					<td ><input type="text" class="inputText16" name="user" id="user" size="18" /></td>
				</tr>
				<tr>
					<td align="right" class="text18"><spring:message code="login.user.label.password"/>&nbsp;</td>
					<td><input type="password"  class="inputText16" name="password" id="password" size="18"/></td>
				</tr>
				
				<tr>
					<td>&nbsp;</td>
					<td align="right">
					<input onClick="setBlockUI(this);" type="submit" name="submit" id="submit" class="inputFormLoginSubmitGreen" value='<spring:message code="login.user.submit"/>' ></td>
				</tr>
				</form>
				<tr height="1"><td>&nbsp;</td></tr>

			</table>
			
		</td>
		</tr>
		<%-- Validation Error section --%>
		<tr>
			<td colspan="5" id="backendError">
			<table>
				<spring:hasBindErrors name="user"> <%-- name must equal the command object name in the Controller --%>
					<tr>
					<td colspan="5" class="textError">					
			            <ul>
			            <c:forEach var="error" items="${errors.allErrors}">
			                <li >
			                	<spring:message code="${error.code}" text="${error.defaultMessage}"/>
			                </li>
			            </c:forEach>
			            </ul>
					</td>
					</tr>
				</spring:hasBindErrors>
				<c:if test="${not empty model.errorMessage}">
					<tr>
					<td colspan="5" class="textError">					
			            <ul>
			            	<li>${model.errorMessage}</li>
			            </ul>
			        </td>
			        </tr>
				</c:if>
			</table>
			</td>
		</tr>
	</table>
<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->
	