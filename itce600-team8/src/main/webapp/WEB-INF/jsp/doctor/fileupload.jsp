
<%@ include file="../template/includes.jsp" %>

<html>
<head>
<s:head />
</head>

<body>
	<h1>Struts 2 &lt;s:file&gt; File upload example</h1>

	<s:form action="executeUpload.do" namespace="/" method="POST"
		enctype="multipart/form-data">

		<s:file name="fileUpload" label="Select a File to upload" size="40" />

		<s:submit value="submit" name="submit" />

	</s:form>

</body>
</html>