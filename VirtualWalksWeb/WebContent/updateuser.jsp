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
	
	String query_str = "";
	Object q = request.getAttribute("q");
	if (q != null)
		query_str = q.toString();
%>
<%
	String uid = request.getParameter("uid");
%>
<body id="login">
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
				<li id="sign-in-link"><a>
					Welcome <%= namestring %>
				</a></li>
			</ul>

		</div>
	</div>
	<div class="clearfix" id="page-content">
		<div id="widgets">

			<form id="olta-widget" action="WalkList">
				<div class="field submit">
					<button class="cta-button" type="submit">
						My Profile<span class="icon"></span>
					</button>
				</div>
				<div class="field submit">
					<button class="cta-button" type="submit">
						My Walks <span class="icon"></span>
					</button>
				</div>
			</form>
		</div>
		
		
		<div id="widgets" style="float:left;margin-left:50px;width:400px;">

			<form method="post" action="Update" id="olta-widget">
							<div class="contentContainer form">
								<h2 class="avenir"
									style="border-bottom: 1px solid #999999; margin-bottom: 20px;margin-top:-17px;font-size: 16px;">
									My Profile</h2>
								<p>
<%
String user=session.getAttribute( "theName" ).toString();
String query="Select username,email,password from newuser where id='"+uid+"'";
try{
	   String url = "jdbc:mysql://titan.cmpe.boun.edu.tr/database8";
	   String userName = "project8";
	   String password2 = "1by37";
				
	   Class.forName ("com.mysql.jdbc.Driver").newInstance();
	   
	   Connection conn = DriverManager.getConnection(url, userName, password2);
				
	   Statement st = (Statement)conn.createStatement();
	   st.executeQuery(query);
	   ResultSet rs = st.getResultSet();
	   String username="";
	   String fullname="";
	   String email="";
	   String password="";
	   String confirmpassword="";
				
	   while (rs.next()) {
		   username = rs.getString("username");
		   fullname = rs.getString("fullname");
		   email = rs.getString("email");
		   password=rs.getString("password");
		   confirmpassword=password;
		  
	 }
%>
                                <input type="hidden" name="uid" value=<%=uid %>/>
                                
									<span class="formLabel avenir">Username </span> <input
										type="text" name="username" value=<%=username %> id="username">
								</p>
								<span class="formLabel avenir">Fullname </span> <input
										type="text" name="fullname" value=<%=fullname %> id="fullname">
								</p>
								<p>
									<span class="formLabel avenir">E-mail </span> <input
										type="text" name="email" value=<%=email %> id="email">
								</p>
								<p>
									<span class="formLabel avenir">Password </span> <input
										type="password" name="password" value=<%=password %> id="password">
								</p>
								<p>
									<span class="formLabel avenir">Confirm Password</span> <input
										type="password" name="confirmpassword" value=<%=password %> id="confirmpassword">
								</p>
								<p>
									<button type="submit" id="login-button" class="large-button">Update
										Account</button>
								</p>
								<p>
									<span style="color: red; display: inline; height: 20px;"
										id="spanLoginMessage"><%= error_message %></span>
								</p>
							</div>
						</form>
<%
				
   }
	catch (SQLException e){
	        	System.out.println(e.getMessage().toString());
    }

 %>
		</div>
		
	</div>
</body>

</html>
