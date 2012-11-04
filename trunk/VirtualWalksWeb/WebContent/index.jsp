<!DOCTYPE html>
<html lang="en-US">
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<head>
<meta charset="UTF-8">
<title>VirtualWalk</title>
<link rel="stylesheet" href="static/css/packages/core-2.37.3-cmp.css">
<link href="static/css/packages/login-2.37.3-cmp.css" media="screen"
	rel="stylesheet" type="text/css">
<link href="static/css/packages/mycss.css" media="screen"
	rel="stylesheet" type="text/css">
<script src="static/scripts/packages/core-2.37.3-cmp.js"></script>

<script type="text/javascript"
	src="static/scripts/packages/login-2.37.3-cmp.js"></script>
</head>
<%
	String conf_message = "";
	Object conf = request.getAttribute("conf");
	if (conf != null)
		conf_message = conf.toString();
%>
<%
	String error_message = "";
	Object error = request.getAttribute("error");
	if (error != null)
		error_message = error.toString();
	if(error_message.equals(""))
	session.setAttribute("theName","user" );
	System.out.println("session"+session.getAttribute( "theName" ));

%>
<body id="login">
	<div id="new-header">
		<div id="header-wrapper">
			<h1 class="unselectable">
				<a title="" href="" class="pngfix"
					style="text-indent: 0; background: none; color: white; overflow: visible; font-weight: bold; font-size: 48px;">VirtualWalk</a>
			</h1>
		</div>
	</div>
	<div id="page-content" >
		<div id="modal">
			<div id="login-header" style="margin-top: 20px; margin-bottom: 20px;">
				<h1
					style="text-indent: 0px; color: white; background: none; margin-bottom: 20px;">Welcome
					to VirtualWalk</h1>
			</div>
			<div id="screens">
				<div id="sign-in" class="screen" style="height: 400px;">
					<div class="primary-pane">
						<form method="post" action="Login" id="formLogin">
							<div class="contentContainer form">
								<h2 class="avenir"
									style="border-bottom: 1px solid #999999; margin-bottom: 20px;">
									Login</h2>
								<p>
									<span class="formLabel avenir">Username </span> <input
										type="text" name="username" id="username">
								</p>
								<p>
									<span class="formLabel avenir">Password </span> <input
										type="password" name="password" id="password">
								</p>
								<p>
									<button type="submit" id="login-button" class="large-button">Enter</button>
								</p>
								<p>
									<span style="color: red; display: inline; height: 20px;"
										id="spanLoginMessage"> 
										<%= error_message %>
										</span>
								</p>
							</div>
						</form>
					</div>
					<div class="secondary-pane"
						style="text-align: left; height: 390px;">
						<form method="post" action="NewUser" id="formLogin">
							<div class="contentContainer form">
								<h2 class="avenir"
									style="border-bottom: 1px solid #999999; margin-bottom: 20px;">
									Register</h2>
								<p>
									<span class="formLabel avenir">Username </span> <input
										type="text" name="username" id="username">
								</p>
								<p>
									<span class="formLabel avenir">E-mail </span> <input
										type="text" name="email" id="email">
								</p>
								<p>
									<span class="formLabel avenir">Password </span> <input
										type="password" name="password" id="password">
								</p>
								<p>
									<span class="formLabel avenir">Confirm Password</span> <input
										type="password" name="confirmpassword" id="confirmpassword">
								</p>
								<p>
									<button type="submit" id="login-button" class="large-button">Create
										Account</button>
								</p>
								<p>
									<span style="color: red; display: inline; height: 20px;"
										id="spanLoginMessage"><%=conf_message%></span>
								</p>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="login-footer" class="clearfix">
			<div class="left-wrap"></div>
			<ul class="gilt-stack" id="login-footer-gilt">
			</ul>
		</div>
	</div>
</body>

</html>
