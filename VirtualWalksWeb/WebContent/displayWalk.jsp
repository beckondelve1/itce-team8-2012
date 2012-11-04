<%@page import="org.apache.catalina.connector.Request"%>
<%@ page import="java.sql.Connection" language="java"%>
<%@ page import="java.sql.DriverManager" language="java"%>
<%@ page import="java.sql.Statement" language="java"%>
<%@ page import="java.sql.ResultSet" language="java"%>
<%@ page import="java.sql.SQLException" language="java"%>

<!DOCTYPE html>
<html lang="en-US">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<head>
<meta charset="UTF-8">
<title>VirtualWalk</title>
<link rel="stylesheet" href="static/css/packages/core-2.37.3-cmp.css">

<link href="static/css/packages/search-2.38.1214.2-cmp.css"
	media="screen" rel="stylesheet" type="text/css">
<link href="static/css/packages/mycss.css" media="screen"
	rel="stylesheet" type="text/css">
<script src="static/scripts/packages/core-2.37.3-cmp.js"></script>
<script type="text/javascript"
	src="static/scripts/packages/login-2.37.3-cmp.js"></script>
</head>
<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
%>
<%
	String uid = request.getParameter("uid");
	String walk=request.getParameter("wid");
%>
<body id="login" style="text-align:center;">
	<div id="new-header">
		<div id="header-wrapper">
			<h1 class="unselectable">
				<a title="" href="" class="pngfix"
					style="text-indent: 0; background: none; color: white; overflow: visible; font-weight: bold; font-size: 48px;">VirtualWalk</a>
			</h1>
<%
String findname="Select username from newuser where id='"+uid+"'";
String namestring="";
        try{
        	String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
			String userName = "project8";
			String password2 = "1by37";
			
			Class.forName ("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, userName, password2);
			
			Statement st = (Statement)conn.createStatement();
			st.executeQuery(findname);
			ResultSet rs = st.getResultSet();
			
			
			while (rs.next()) {
				namestring = rs.getString("username");
			}
			
        }
        catch(Exception e){
        	System.out.println(e.getMessage().toString());
        }

 %>			

			<ul class="unselectable" id="secondary-nav">
				<li id="sign-in-link">
				<a href="UpdateUserInfo">
					Welcome <%= namestring %>

				</a>
				</li>
			</ul>

		</div>
	</div>
	<div class="clearfix" id="page-content">
		<div id="widgets">

			<div id="olta-widget" style="width:680px;height:520px;margin-left:90px;padding-left:40px;">
				<!-- 
				<embed src="static/videos/tangramone.swf" height="200" width="200"/>
				-->
				<embed type="application/x-vlc-plugin" name="VLC" autoplay="yes" loop="no" volume="100" width="640" height="480" target="static/videos/brsbilgic.mp4">

<a href="javascript:;" onclick='document.VLC.play()'>Play</a>

<a href="javascript:;" onclick='document.VLC.pause()'>Pause</a>

<a href="javascript:;" onclick='document.VLC.stop()'>Stop</a>

<a href="javascript:;" onclick='document.VLC.fullscreen()'>Fullscreen</a>

			</div>
		</div>
	</div>
	
	<div style="width:500px;display:inline-block;">
		<form id="olta-widget" method="post" action="Rating">
		<input type="hidden" name="uid" value=<%=uid %>/>
		<input type="hidden" name="wid" value=<%=walk %>/>
			<div class="field query">
				<span class="formLabel" style="margin-bottom: 10px;">Rate Me
					</span> <select name="rating">
								<option value="5">5</option>
       							<option value="4">4</option>
     							<option value="3">3</option>
        						<option value="2">2</option>
        						<option value="1">1</option>
      						</select>
			</div>

			<div class="field submit">
					<button class="cta-button" type="submit" style="text-align:center;">
						Save <span class="icon"></span>
					</button><table><tr><td style="color: blue"><%= error_message %></td></tr></table>
				</div>
		</form>
	</div>
	
	<div style="width:500px;display:inline-block;">
			<form id="olta-widget" method="post" action="Comment">
				<input type="hidden" name="uid" value=<%=uid %>/>
				<input type="hidden" name="wid" value=<%=walk %>/>
				<div class="field query">

					<span class="formLabel" style="margin-bottom: 10px;">Comment
					</span> <input type="text" value="" name="comment" class="text search"
						autocomplete="off" style="width:350px;">
					<ul class="autocomplete-dropdown autocomplete-light"
						style="display: none;"></ul>
				</div>
				<div class="field submit">
					<button class="cta-button" type="submit" style="text-align:center;">
						Send <span class="icon"></span>
					</button>
				</div>
<%

String query="Select commentText,user_idUser,createdate from comment where walk_idwalk= '"+walk+"'";
try{
	   String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
	   String userName = "project8";
	   String password2 = "1by37";
				
	   Class.forName ("com.mysql.jdbc.Driver").newInstance();
	   
	   Connection conn = DriverManager.getConnection(url, userName, password2);
				
	   Statement st = (Statement)conn.createStatement();
	   st.executeQuery(query);
	   ResultSet rs = st.getResultSet();
	   String comment="";
	   String usid="";
	   String cdate="";
	 	System.out.println(comment+usid+cdate+"resultset");
	 	while (rs.next()) {
		   comment = rs.getString("commentText");
		   usid = rs.getString("user_idUser");
		   cdate = rs.getString("createdate");	
		   System.out.println(comment+usid+cdate+"resultset");   
		   String walker="";
		   query="Select username from newuser where id='"+usid+"'";
		   try{	   
	   			Connection conn2 = DriverManager.getConnection(url, userName, password2);
				
	   			Statement st2 = (Statement)conn2.createStatement();
	   			st2.executeQuery(query);
	   			ResultSet rs2 = st2.getResultSet();

	   			while (rs2.next()) {
		   			walker = rs2.getString("username");
		   			//System.out.println(walker);
		   		}
		   	}
		   	catch(SQLException e){
		   		System.out.println(e.getMessage().toString());
		   	}
		   
	 	%>
				
				<div style="width: 100%; border: 1px solid black;">
				<table style="width: 100%; height: 30px;">
					<tr>						
						<td style="width:300px;"><%=comment%></td>
						<td style="width:50px;"><%=walker%></td>
						<td style="width:50px;"><%=cdate%></td>				
					</tr>
				</table>
				</div>
				<% 
			}

				
   }
	catch (SQLException e){
	       System.out.println(e.getMessage().toString());
    }

 %>	
			
			
			</form>
		</div>
		
	</div>
</body>

</html>
