<%@ page import="java.sql.Connection" language="java" %>
<%@ page import="java.sql.DriverManager" language="java" %>
<%@ page import="java.sql.Statement" language="java" %>
<%@ page import="java.sql.ResultSet" language="java" %>
<%@ page import="java.sql.SQLException" language="java" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VIRTUAL WALKS</title>
</head>
<body bgcolor="e7e7e7">

<h1>

<%
String person = "?";
String user = request.getParameter("user");
if(user != null)
	person = user;
String kullanici = user;
session.setAttribute( "theName", kullanici );
%>
 
 </h1>
 <table cellspacing="4">
	<tr>
		<td style="width: 264px; ">Welcome  <%=session.getAttribute( "theName" )%>            </td>
		<td style="width: 100px; "></td>
		<td><a href="MainPage.jsp?user=<%=person%>"> HOME</a></td>
		<td style="width: 94px; "><a href="UpdateUserInfo.jsp"> MY PROFILE</a></td>
		<td><a href="Login.jsp"> LOGOUT</a></td>
	</tr>
	
</table><br><br><br>

<b>CURRENT WALKS</b>
<table border=3 style="width: 620px; ">
<tr bgcolor="3366FF">
<td><b>WalkName</b></td>
<td><b>Location</b></td>
<td><b>CreateDate</b></td>
<td><b>City</b></td>
<td><b>County</b></td>
<td><b>WalkerName</b></td>
</tr>
<%

String query="Select name,location,createdate,city_idcity,county_idcounty,createuser_idUser from walk where isActive=1";
try{
	   String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
	   String userName = "project8";
	   String password2 = "1by37";
				
	   Class.forName ("com.mysql.jdbc.Driver").newInstance();
	   
	   Connection conn = DriverManager.getConnection(url, userName, password2);
				
	   Statement st = (Statement)conn.createStatement();
	   st.executeQuery(query);
	   ResultSet rs = st.getResultSet();
	   String walkname="";
	   String location="";
	   String createdate="";
	   String city="";
	   String county="";
	   String id="";
	   String walker="";
				
	   while (rs.next()) {
		   walkname = rs.getString("name");
		   location = rs.getString("location");
		   createdate = rs.getString("createdate");
		   city=rs.getString("city_idcity");
		   county=rs.getString("county_idcounty");
		   id=rs.getString("createuser_idUser");
		   
		   query="Select username from user where idUser='"+id+"'";
		   try{	   
	   			Connection conn2 = DriverManager.getConnection(url, userName, password2);
				
	   			Statement st2 = (Statement)conn2.createStatement();
	   			st2.executeQuery(query);
	   			ResultSet rs2 = st2.getResultSet();
	   			while (rs2.next()) {
		   			walker = rs2.getString("username");
		   			System.out.println(walker);
		   		}
		   	}
		   	catch(SQLException e){
		   	
		   	}
		   
		   
%>
<tr bgcolor="Orange">
<td><%=walkname%></td>
<td><%=location%></td>
<td><%=createdate%></td>
<td><%=city%></td>
<td><%=county%></td>
<td><%=walker%></td>
</tr>
<%
       int control=0;
	   if(rs.next()){
		   walkname = rs.getString("name");
		   location = rs.getString("location");
		   createdate = rs.getString("createdate");
		   city=rs.getString("city_idcity");
		   county=rs.getString("county_idcounty");
		   id=rs.getString("createuser_idUser");
		
		   query="Select username from user where idUser='"+id+"'";
		   try{	   
	   			Connection conn2 = DriverManager.getConnection(url, userName, password2);
				
	   			Statement st2 = (Statement)conn2.createStatement();
	   			st2.executeQuery(query);
	   			ResultSet rs2 = st2.getResultSet();
	   			while (rs2.next()) {
		   			walker = rs2.getString("username");
		   			System.out.println(walker);
		   		}
		   	}
		   	catch(Exception e){
		   		System.out.println(e.getMessage().toString());
		   	}
%>
<tr bgcolor="BDBDBD">
<td><%=walkname%></td>
<td><%=location%></td>
<td><%=createdate%></td>
<td><%=city%></td>
<td><%=county%></td>
<td><%=walker%></td>
</tr>	   
<%
	   }
	 }

				
   }
	catch (SQLException e){
	        	
    }

 %>

 </table><br><br>  
 
 <b>OLD WALKS</b>
<table border=3 style="width: 620px; ">
<tr bgcolor="3366FF">
<td><b>WalkName</b></td>
<td><b>Location</b></td>
<td><b>CreateDate</b></td>
<td><b>City</b></td>
<td><b>County</b></td>
<td><b>WalkerName</b></td>
</tr>
<%

query="Select name,location,createdate,city_idcity,county_idcounty,createuser_idUser from walk where isActive=0";
try{
	   String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
	   String userName = "project8";
	   String password2 = "1by37";
				
	   Class.forName ("com.mysql.jdbc.Driver").newInstance();
	   
	   Connection conn = DriverManager.getConnection(url, userName, password2);
				
	   Statement st = (Statement)conn.createStatement();
	   st.executeQuery(query);
	   ResultSet rs = st.getResultSet();
	   String walkname="";
	   String location="";
	   String createdate="";
	   String city="";
	   String county="";
	   String id="";
	   String walker="";
				
	   while (rs.next()) {
		   walkname = rs.getString("name");
		   location = rs.getString("location");
		   createdate = rs.getString("createdate");
		   city=rs.getString("city_idcity");
		   county=rs.getString("county_idcounty");
		   id=rs.getString("createuser_idUser");
		   
		   query="Select username from user where idUser='"+id+"'";
		   try{	   
	   			Connection conn2 = DriverManager.getConnection(url, userName, password2);
				
	   			Statement st2 = (Statement)conn2.createStatement();
	   			st2.executeQuery(query);
	   			ResultSet rs2 = st2.getResultSet();
	   			while (rs2.next()) {
		   			walker = rs2.getString("username");
		   		}
		   	}
		   	catch(SQLException e){
		   	
		   	}
		   
		   
%>
<tr bgcolor="Orange">
<td><%=walkname%></td>
<td><%=location%></td>
<td><%=createdate%></td>
<td><%=city%></td>
<td><%=county%></td>
<td><%=walker%></td>
</tr>
<%
       int control=0;
	   if(rs.next()){
		   walkname = rs.getString("name");
		   location = rs.getString("location");
		   createdate = rs.getString("createdate");
		   city=rs.getString("city_idcity");
		   county=rs.getString("county_idcounty");
		   id=rs.getString("createuser_idUser");
		
		   query="Select username from user where idUser='"+id+"'";
		   try{	   
	   			Connection conn2 = DriverManager.getConnection(url, userName, password2);
				
	   			Statement st2 = (Statement)conn2.createStatement();
	   			st2.executeQuery(query);
	   			ResultSet rs2 = st2.getResultSet();
	   			while (rs2.next()) {
		   			walker = rs2.getString("username");
		   		}
		   	}
		   	catch(SQLException e){
		   	
		   	}
%>
<tr bgcolor="BDBDBD">
<td><%=walkname%></td>
<td><%=location%></td>
<td><%=createdate%></td>
<td><%=city%></td>
<td><%=county%></td>
<td><%=walker%></td>
</tr>	   
<%
	   }
	 }

				
   }
	catch (SQLException e){
	        	
    }

 %>

 </table>   
</body>
</html>