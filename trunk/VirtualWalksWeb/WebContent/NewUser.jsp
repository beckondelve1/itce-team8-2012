<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>VIRTUAL WALKS</title>

<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
String error_message = "";
Object error = request.getAttribute("error");
if (error != null) 
	error_message = error.toString();
%>
<%
String conf_message = "";
Object conf = request.getAttribute("conf");
if (conf != null) 
	conf_message = conf.toString();
if(conf_message.equals("Your registration is completed!"))
	session.setAttribute("theName","user" );
%>
<div id="table">
<table BORDER="1" align="center">
<tr>
<td>
<h2>NEW USER</h2>
</td>
</tr>
</table>
</div>
<div id="login">

	<div id="content">
		<form action="NewUser">
			<table> 
				<tr>
					<td><label class="login-info">Name</label></td>
					<td><input class="newuser" name="name" type="text" /></td>
				</tr>
				<tr>
					<td><label class="login-info">Surname</label></td>
					<td><input class="newuser" name="surname" type="text" /></td>
				</tr>
				<tr>
					<td><label class="login-info">Username</label></td>
					<td><input class="newuser" name="user" type="text" /></td>
				</tr>
				<tr>
					<td><label class="login-info">Password</label></td>
					<td><input class="newuser" name="password" type="password" /></td>
				</tr>
				<tr>
					<td><label class="login-info">Confirm Password</label></td>
					<td><input class="newuser" name="confirmpassword" type="password" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="CREATE" style="width: 177px;height:40px; color: green "></td>
					<td style="color: blue"><%= conf_message %></td>
				</tr>
			</table>
			
		</form>
	</div>
</div>

</body>