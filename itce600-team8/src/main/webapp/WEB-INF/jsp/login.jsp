
<%@ include file="template/includes.jsp" %>

<html>
  <head>
    <title><s:text name="Login" /></title>
  </head>
                                                                                        
  <body>
    <div id="welcome" class="boxed2">
		<h1 class="title"><s:text name="Login"/></h1>
	
		<!--  -->
		<div class="content">
		    <form name="f" action="<c:url value='/login.do'/>" method="POST">
		      <table>
		        <tr><td><s:text name="Username" /></td><td><input type='text' name='user.userName'></td></tr>
		        <tr><td><s:text name="Password" /></td><td><input type='password' name='user.password'></td></tr>
		        		
		        <tr>
		        	<td colspan="2" align="center">
		        		<s:submit value="Submit" theme="simple"/>
						<s:reset value="Reset" theme="simple"/>	
					</td>
				</tr>
		      </table>
		    </form>
	    </div>
	</div>
	
  </body>
</html>