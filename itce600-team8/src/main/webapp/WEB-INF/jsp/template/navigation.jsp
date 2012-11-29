<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../template/includes.jsp"%>
	
<table width="100%">
	<tr>
		<td>
			<div class="login-as">
				<s:text name="label.logged-in-as" />
				</b>
			</div>
		</td>
	
		<td align="right">
			<a href="<c:url value="/changeLang.task?request_locale=en_US"/>">
				<img src="<c:url value="/images/lang/en.jpg.gif"/> " alt="English (United States)" 
				title="English (United States)" class="icon">
			</a>
			<a href="<c:url value="/changeLang.task?request_locale=vi"/>">
				<img src="<c:url value="/images/lang/vi.jpg.gif"/> " alt="Tiếng Việt (Việt Nam)" 
				title="Tiếng Việt (Việt Nam)" class="icon">
			</a>
			&nbsp;&nbsp;&nbsp;
			
			<!--  -->
			<a href="<c:url value="/changePasswordForm.task"/>">
				<s:text name="link.change-password" />
			</a>
			&nbsp;&nbsp;
			<a href="<c:url value="/logout"/>"><s:text name="link.logout" /></a>
			
			<!--  -->
			<a href="<c:url value="/login.task"/>">
				<s:text name="link.login" />
			</a>
		</td>
	</tr>
</table>
