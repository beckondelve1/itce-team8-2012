<%@ page import="java.sql.Connection" language="java" %>
<%@ page import="java.sql.DriverManager" language="java" %>
<%@ page import="java.sql.Statement" language="java" %>
<%@ page import="java.sql.ResultSet" language="java" %>
<%@ page import="java.sql.SQLException" language="java" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<h2>MY PROFILE</h2>
</td>
</tr>
</table>
</div>
<%
String user=session.getAttribute( "theName" ).toString();
String query="Select idUser,firstname,lastname,username,password from user where username='"+user+"'";
try{
	   String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
	   String userName = "project8";
	   String password2 = "1by37";
				
	   Class.forName ("com.mysql.jdbc.Driver").newInstance();
	   
	   Connection conn = DriverManager.getConnection(url, userName, password2);
				
	   Statement st = (Statement)conn.createStatement();
	   st.executeQuery(query);
	   ResultSet rs = st.getResultSet();
	   String firstname="";
	   String lastname="";
	   String username="";
	   String password="";
	   String confirmpassword="";
	   String userid="";
				
	   while (rs.next()) {
	   	   userid=rs.getString("idUser");	
		   firstname = rs.getString("firstname");
		   lastname = rs.getString("lastname");
		   username = rs.getString("username");
		   password=rs.getString("password");
		   confirmpassword=password;
		  
	 }
%>
<div id="login">

	<div id="content">
		<form method="post" action="UpdateUserInfo">
			<table> 
				<tr>
					<td><label class="login-info">Name</label></td>
					<td><input class="newuser" name="name" type="text" value=<%=firstname %> /></td>
				</tr>
				<tr>
					<td><label class="login-info">Surname</label></td>
					<td><input class="newuser" name="surname" type="text" value=<%=lastname %> /></td>
				</tr>
				<tr>
					<td><label class="login-info">Username</label></td>
					<td><input class="newuser" name="user" type="text" value=<%=username %> /></td>
				</tr>
				<tr>
					<td><label class="login-info">Password</label></td>
					<td><input class="newuser" name="password" type="password" value=<%=password %>/></td>
				</tr>
				<tr>
					<td><label class="login-info">Confirm Password</label></td>
					<td><input class="newuser" name="confirmpassword" type="password" value=<%=password %>/></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="SAVE" style="width: 177px;height:40px; color: green "></td>
					<td style="color: blue"><%= conf_message %></td>
				</tr>
			</table>
			
		</form>
	</div>
</div>

<%
				
   }
	catch (SQLException e){
	        	System.out.println(e.getMessage().toString());
    }

 %>


</body>
</html>