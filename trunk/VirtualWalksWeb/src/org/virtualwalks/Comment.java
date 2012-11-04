package org.virtualwalks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class Comment
 */
@WebServlet("/Comment")
public class Comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String comment = request.getParameter("comment");
		//HttpSession mysession=request.getSession();
		//String uname=(String) mysession.getAttribute( "username" );
		String uid = (String) request.getParameter("uid");
		String wid = (String) request.getParameter("wid");
		uid=uid.replace('/',' ');
		wid=wid.replace('/',' ');
		
		String nextpage = "/displayWalk.jsp?wid="+wid+"&uid="+uid;
		String datecomment;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cdate = new Date();
        datecomment = dateFormat.format(cdate);
        
        String query = "Insert into comment (commentText,walk_idwalk,user_idUser,createdate) values ('"+comment+"','"+wid+"','"+uid+"','"+datecomment+"')";
		
        try{
			
			String url = Constants.CONNECTION_STRING;
			String userName = Constants.USERNAME;
			String password2 = Constants.PASSWORD;
        
			Class.forName ("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection (url, userName, password2);
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate(query);
		}
		catch (Exception e) {
			System.out.println(e.getMessage().toString());
		}
        ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request, response);
	}

}
