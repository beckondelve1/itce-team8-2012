package org.virtualwalks;

import java.io.IOException;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uid = request.getParameter("uid");
		uid=uid.replace('/',' ');
		String nextpage="/updateuser.jsp?uid='"+uid+"'";
		
		
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");
	    String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		username=username.replace('/',' ');
		fullname=fullname.replace('/',' ');
		email=email.replace('/',' ');
		password=password.replace('/',' ');
		confirmpassword=confirmpassword.replace('/',' ');
		
		String udate;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cdate = new Date();
        udate = dateFormat.format(cdate);
		
		String query = "Update newuser set username='"+username+"',fullname='"+fullname+"',email='"+email+"',password='"+password+"',confirmpassword='"+confirmpassword+"',updatedate='"+udate+"' where id='"+uid+"'";
		if (!password.equals(confirmpassword)) {
			request.setAttribute("conf", "Confirm your password!");
		}
		
		else{
			try{
				
				String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;
            
				Class.forName ("com.mysql.jdbc.Driver").newInstance();
				Connection conn = (Connection) DriverManager.getConnection (url, userName, password2);
				
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.executeUpdate(query);
				request.setAttribute("conf", "Profile is updated!");
				nextpage = "/walklist.jsp?id="+uid;
         
			}
			catch (Exception e) {
				System.out.println(e.getMessage().toString());
			}
			
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uid = request.getParameter("uid");
		uid=uid.replace('/',' ');
		String nextpage="/updateuser.jsp?uid='"+uid+"'";
		
		
		String username = request.getParameter("username");
		String fullname = request.getParameter("fullname");
	    String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		username=username.replace('/',' ');
		fullname=fullname.replace('/',' ');
		email=email.replace('/',' ');
		password=password.replace('/',' ');
		confirmpassword=confirmpassword.replace('/',' ');
		
		String udate;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cdate = new Date();
        udate = dateFormat.format(cdate);
		
		String query = "Update newuser set username='"+username+"',fullname='"+fullname+"',email='"+email+"',password='"+password+"',confirmpassword='"+confirmpassword+"',updatedate='"+udate+"' where id='"+uid+"'";
		if (!password.equals(confirmpassword)) {
			request.setAttribute("conf", "Confirm your password!");
		}
		
		else{
			try{
				
				String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;
            
				Class.forName ("com.mysql.jdbc.Driver").newInstance();
				Connection conn = (Connection) DriverManager.getConnection (url, userName, password2);
				
				PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(query);
				pstmt.executeUpdate(query);
				request.setAttribute("conf", "Profile is updated!");
				nextpage = "/walklist.jsp?id="+uid;
         
			}
			catch (Exception e) {
				System.out.println(e.getMessage().toString());
			}
			
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);
	}

}
