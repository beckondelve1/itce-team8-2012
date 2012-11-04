package org.virtualwalks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateUserInfo
 */
@WebServlet("/UpdateUserInfo")
public class UpdateUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uid=request.getParameter("uid");
		System.out.println(uid+"son gelen userid");
		String nextpage="/updateuser.jsp?uid='"+uid+"'";
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
String nextpage="/updateuser.jsp";
		/*
		String name = request.getParameter("name");
	    String surname = request.getParameter("surname");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		password=password.replace('/',' ');
		confirmpassword=confirmpassword.replace('/',' ');
		
		int id=0;
		
		HttpSession mysession=request.getSession();
		String my=mysession.getAttribute( "theName" ).toString();
		String findname="Select idUser from user where username='"+my+"'";
        try{
        	String url = Constants.CONNECTION_STRING;
			String userName = Constants.USERNAME;
			String password2 = Constants.PASSWORD;
			
			Class.forName ("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, userName, password2);
			
			Statement st = (Statement)conn.createStatement();
			st.executeQuery(findname);
			ResultSet rs = st.getResultSet();
			String idstring="";
			
			while (rs.next()) {
				idstring = rs.getString("idUser");
			}
			id = Integer.parseInt(idstring);
			
        }
        catch(Exception e){
        	System.out.println(e.getMessage().toString());
        }
		
		//int userid=Integer.parseInt(id);
		String tarih;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cdate = new Date();
        tarih = dateFormat.format(cdate);
		String query = "Update user set username='"+user+"',firstname='"+name+"',lastname='"+surname+"',password='"+password+"',updatedate='"+tarih+"' where idUser='"+id+"'";
		if (!password.equals(confirmpassword)) {
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
				request.setAttribute("conf", "Your registration is completed!");
				nextpage="/MainPage.jsp";
         
			}
			catch (Exception e) {
				System.out.println(e.getMessage().toString());
			}
			
		}
		*/
		
		String uid=request.getParameter("uid");
		System.out.println(uid+"son gelen userid");
		nextpage="/updateuser.jsp?uid='"+uid+"'";
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);

		context = getServletContext();
		dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request,response);
	}

}
