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
if(error_message.equals(""))
	session.setAttribute("theName","user" );
%>
<div id="table">
<table BORDER="1" align="center">
<tr>
<td>
<h2>WELCOME TO VIRTUAL WALKS!</h2>
</td>
</tr>
</table>
</div>


<div id="login">
<label class="login-info"></label>
	<div id="content">
		<form method="post" action="Login">
			<label class="login-info">Username</label>
			<input class="input" name="user" type="text" />
			<label class="login-info">Password</label>
			<input class="input" name="password" type="password" />
			<div id="remember-forgot">
				<div class="checkbox">
					<input name="Checkbox1" type="checkbox" /></div>
				<table>
				<tr>
				<td><div class="rememberme">
					Remember Me</div></td>
				<td style="color: red"><%= error_message %></td>
				</tr>
				
					</table>
				
				<div id="forgot-password">
					<a href="#">Forgot Password ?</a> </div>
				<div id="login-buttton">
					<input name="Submit" src="images/login-button.jpg" type="image" value="Login" /> <a href="NewUser.jsp" color="blue">Sign Up</a></div>
				
			</div>
		</form>
	</div>
</div>

</body>

</html>