
<%@ include file="template/includes.jsp" %>

<html>
  <head>
    <title><s:text name="title.login" /></title>
  </head>
                                                                                        
  <body onload="document.f.j_username.focus();">
    <div id="welcome" class="boxed2">
		<h1 class="title"><s:text name="title.login"/></h1>
	
		<!--  -->
		<div class="content">
		    <form name="f" action="<c:url value='/login'/>" method="POST">
		      <table>
		        <tr><td><s:text name="login.username" /></td><td><input type='text' name='j_username'></td></tr>
		        <tr><td><s:text name="login.password" /></td><td><input type='password' name='j_password'></td></tr>
		        <tr>
		        	<td colspan="2">
		        	<input type="checkbox" name="_spring_security_remember_me">
		        	<s:text name="login.remember-me" />
		        	</td>
		        </tr>
		
		        <tr>
		        	<td colspan="2" align="center">
		        		<s:submit value="%{getText('button.submit')}" theme="simple"/>
						<s:reset value="%{getText('button.reset')}" theme="simple"/>	
					</td>
				</tr>
		      </table>
		    </form>
	    </div>
	</div>
	
  </body>
</html>