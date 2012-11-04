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
	
	String query_res_status = "";
	Object s = request.getAttribute("status");
	if (s != null)
		query_res_status = s.toString();
%>
<%
	String username = request.getParameter("username");
	String uid = request.getParameter("id");
%>
<body id="login">
	<div id="new-header">
		<div id="header-wrapper">
			<h1 class="unselectable">
				<a title="" href="" class="pngfix"
					style="text-indent: 0; background: none; color: white; overflow: visible; font-weight: bold; font-size: 48px;">VirtualWalk</a>
			</h1>
			<ul class="unselectable" id="secondary-nav">
				<li id="sign-in-link">
				<a href="UpdateUserInfo">
					Welcome <%= username %>
				</a>
				</li>
			</ul>

		</div>
	</div>
	<div class="clearfix" id="page-content">
		<div id="widgets">

			<form id="olta-widget" action="WalkList">
				<input type="hidden" name="uid" value=<%=uid %>/>
				<div class="field query">

					<span class="formLabel" style="margin-bottom: 10px;">Keyword
					</span> <input type="text" value="" name="q" class="text search"
						autocomplete="off">
					<ul class="autocomplete-dropdown autocomplete-light"
						style="display: none;"></ul>
				</div>
				
				<div class="field query">

					<span class="formLabel" style="margin-bottom: 10px;">Status
					</span> 
					<br>
					<select name="status" style="width:200px;height:30px;text-align:center;padding-top:5px;">
								<option value="2">All</option>
								<option value="1">Active</option>
       							<option value="0">Passive</option>
      						</select>
				</div>
				
				
				<div class="field submit">
					<button class="cta-button" type="submit">
						Search <span class="icon"></span>
					</button>
				</div>
			</form>
		</div>
			<%
			String query="Select idwalk,name,location,createdate,city_idcity,county_idcounty,createuser_idUser,isactive from walk where name LIKE '%"+query_str+"%'";
			if(query_res_status.equals("0") || query_res_status.equals("1")){
				 query = query + (" and isactive="+query_res_status);
			}
			System.out.println(query);
			
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
	   String stat="";
	   String wid="";
	   
	 	int status = 0;
	 	int recCount = 0; 
	 	while(rs.next()) recCount++; 
	 	
	 	st.executeQuery(query);
		rs = st.getResultSet();
	 	%>


		<div id="results">
			<div data-page="1" class="page">
				<p class="matches"><%= recCount %> matches found</p>
			</div>
<%			
	   while (rs.next()) {
		   wid = rs.getString("idwalk");
		   walkname = rs.getString("name");
		   location = rs.getString("location");
		   createdate = rs.getString("createdate");
		   city=rs.getString("city_idcity");
		   county=rs.getString("county_idcounty");
		   id=rs.getString("createuser_idUser");
		   status=rs.getInt("isactive");
		   if(status == 1){
			   stat = "Active";
		   }else{
			   stat = "Passive";
		   }
				   
		   
		   query="Select username from newuser where id='"+id+"'";
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
						<td style="width:200px;"><a href="DisplayWalk?wid=<%=wid %>&uid=<%=uid%>"> <%=walkname%> </a></td>
						<td style="width:200px;"><%=location%></td>
						<td style="width:100px;"><%=createdate%></td>
						<td style="width:100px;"><%=city%></td>
						<td style="width:100px;"><%=county%></td>
						<td style="width:100px;"><%=walker%></td>
						<td style="width:100px;"><%=stat%></td>
					</tr>
				</table>
			</div>
			
			<% 
			}

				
   }
	catch (SQLException e){
	        	
    }

 %>
		</div>
	</div>
</body>

</html>
