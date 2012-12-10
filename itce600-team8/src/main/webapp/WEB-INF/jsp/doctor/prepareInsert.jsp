
<%@ include file="../template/includes.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Doctor Registration</title>
</head>
<body>
	Doctor
	<br>
	<s:form action="insert">
		Full Name: <s:textfield key="doctor.fullName" />
		User Name: <s:textfield key="doctor.userName" />
		Password: <s:password key="doctor.password" />
		<s:submit />
	</s:form>
	<br>
	<br>
</body>
</html>