package org.virtualwalks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

//import com.mysql.jdbc.Statement;
import java.sql.Statement;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nextpage = "/walklist.jsp";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String query = "Select username,password from newuser where username='" + username + "' and password='" + password + "'";
		
		if (username == null || username.length() == 0) {
			nextpage = "/index.jsp";
			request.setAttribute("error", "User name must not be empty.");
		}

		else {
			try {
				String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection(url, userName,
						password2);

				Statement st = (Statement) conn.createStatement();
				st.executeQuery(query);
				ResultSet rs = st.getResultSet();
				PrintWriter out = response.getWriter();
				int entry_num = 0;
				while (rs.next()) {
					entry_num++;
				}
				if (entry_num == 0) {
					out.print(0);
					nextpage = "/index.jsp";
					request.setAttribute("error", "Wrong username or password.");
				} else {
					out.print(1);
					//HttpSession sess = request.getSession(true);
					
					//String uname = rs.getString("username");
					
					//sess.setAttribute("username", uname);
					
	
					String findid="Select id from newuser where username='"+username+"'";
					String idstring="";
			        try{
						Class.forName ("com.mysql.jdbc.Driver").newInstance();
						Connection conn2 = DriverManager.getConnection(url, userName, password2);
						
						Statement st2 = (Statement)conn2.createStatement();
						st2.executeQuery(findid);
						ResultSet rs2 = st2.getResultSet();
						
						while (rs2.next()) {
							idstring = rs2.getString("id");
						}
						
			        }
			        catch(Exception e){
			        	
			        }
					nextpage = "/walklist.jsp?id="+idstring;
				}

			} catch (Exception e) {

			}
		}
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(nextpage);
		dispatcher.forward(request, response);
	}

}
