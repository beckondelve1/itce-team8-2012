<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<s:head />
</head>

<body>
	<h1>Struts 2 &lt;s:file&gt; file upload example</h1>

	<s:form action="executeUploadAction" namespace="/" method="POST"
		enctype="multipart/form-data">

		<s:file name="fileUpload" label="Select a File to upload" size="40" />

		<s:submit value="submit" name="submit" />

	</s:form>

</body>
</html>