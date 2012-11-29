<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/includes.jsp"%>
	
<table width="100%">
	<tr>
		<td>
			<div class="login-as">
				<s:text name="label.logged-in-as" />
			</div>
		</td>
	
		<td align="right">
			<a href="<c:url value="/logout"/>"><s:text name="link.logout" /></a>
			
			<!--  -->
			<a href="<c:url value="/login.do"/>">
				<s:text name="Login" />
			</a>
		</td>
	</tr>
</table>
