<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user create</title>
</head>
<body>
	user
	<br>
	<s:form action="create.action">
   name:<s:textfield key="user.name" />
		<s:submit />
	</s:form>
	<br>
	<br>
	<s:if test="userList != null">
		<table border="1">
			<tr>
				<td>id</td>
				<td>value</td>
			</tr>
			<s:iterator value="userList">
				<tr>
					<td><s:property value="id" /></td>
					<td><s:property value="name" /></td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
</body>
</html>