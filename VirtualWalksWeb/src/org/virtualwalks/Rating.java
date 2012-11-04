package org.virtualwalks;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

//import com.mysql.jdbc.Statement;
import java.sql.Statement;

/**
 * Servlet implementation class Rating
 */
@WebServlet("/Rating")
public class Rating extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rating() {
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
		String url = Constants.CONNECTION_STRING;
		String userName = Constants.USERNAME;
		String password2 = Constants.PASSWORD;
		
		
		String rating = request.getParameter("rating");
		request.setAttribute("error", "You rated walker as: "+rating);
		int rate=Integer.parseInt(rating);
		HttpSession mysession=request.getSession();
		//String uname=(String) mysession.getAttribute( "username" );
		String uid = (String) request.getParameter("uid");
		String wid = (String) request.getParameter("wid");
		uid=uid.replace('/',' ');
		wid=wid.replace('/',' ');
		
		String nextpage = "/displayWalk.jsp?wid="+wid+"&uid="+uid;
		String daterate;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cdate = new Date();
        daterate = dateFormat.format(cdate);
        
        int rateCount = 0;
        
        String query = "SELECT COUNT(*) AS N1 FROM rate WHERE user_idUser="+uid+" and walk_idwalk="+wid ;
        try{

    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		Connection conn = DriverManager.getConnection (url, userName, password2);
        	Statement st = (Statement) conn.createStatement();
        	ResultSet rs = st.executeQuery(query);
        	rateCount = rs.getInt("N1");
        	
        	if(rateCount == 0)
            	query = "Insert into rate (rate,walk_idwalk,user_idUser,createdate) values ('"+rate+"','"+wid+"','"+uid+"','"+daterate+"')";
            else
            	query = "UPDATE rate SET rate="+ rate +",createdate="+daterate+" WHERE user_idUser="+uid+" and walk_idwalk="+wid ;
			
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
