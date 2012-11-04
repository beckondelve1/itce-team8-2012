package org.virtualwalks;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;

import javax.sql.*;
import javax.swing.JOptionPane;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nextpage="/index.jsp";
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		String tarih;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date cdate = new Date();
		tarih = dateFormat.format(cdate);
		String query = "Insert into newuser (username,password,email,createdate,updatedate) values ('"+username+"','"+password+"','"+email+"','"+tarih+"','"+tarih+"')";
		
		
		if (username.isEmpty()) {
			request.setAttribute("conf", "Username cannot be blank!");
		}else if (email.isEmpty()) {
			request.setAttribute("conf", "Email cannot be blank!");
		}else if (password.isEmpty()) {
			request.setAttribute("conf", "Password cannot be blank!");
		}else if (!password.equals(confirmpassword)) {
			request.setAttribute("conf", "Confirm your password!");
		}
		
		else{
			try{  	
				String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;
            
				Class.forName ("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection (url, userName, password2);
				
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.executeUpdate(query);
				nextpage="/walklist.jsp";
			}
			catch (Exception e) {
			
			}
			
		}
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);
	}

}
