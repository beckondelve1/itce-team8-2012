package org.virtualwalks;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class android
 */
@WebServlet("/android")
public class android extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public android() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();  
	       
		 int createuser=1;
		 int updateuser=1;
	       
	     String action = request.getParameter("action");
	        
	     if(action.equals("create")){
	    	 
	        String username = request.getParameter("username");
	        
	        String findname="Select idUser from user where username='"+username+"'";
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
				createuser = Integer.parseInt(idstring);
				updateuser=createuser;
				
	        }
	        catch(Exception e){
	        	
	        }
	        
	        String updatewalks="Update walk set isactive=0 where createuser_idUser='"+createuser+"'";
	        try{
	        	String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;
				
				Class.forName ("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection(url, userName, password2);
				PreparedStatement pstmt = conn.prepareStatement(updatewalks);
				pstmt.executeUpdate(updatewalks);
				
	        }
	        catch(Exception e){
	        	
	        }
	        
	        String name = request.getParameter("name");
	        String location = request.getParameter("location");
	        String createdate;
			String updatedate;
			String isactive="1";
			String city=request.getParameter("city");
			String county=request.getParameter("county");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date cdate = new Date();
	        createdate = dateFormat.format(cdate);
	        updatedate=createdate;
	        
	        String query="INSERT INTO walk (idwalk,name,location,createdate,updatedate,isactive,city_idcity,county_idcounty,createuser_idUser,updateuser_idUser1) VALUES (null, '"+name+"','"+location+"','"+createdate+"','"+updatedate+"','"+isactive+"','"+city+"','"+county+"','"+createuser+"','"+updateuser+"');";
	        try{
	        	String url = Constants.CONNECTION_STRING;
	        	String userName = Constants.USERNAME;
	        	String password2 = Constants.PASSWORD;
			
	        	Class.forName ("com.mysql.jdbc.Driver").newInstance();
	        	Connection conn = DriverManager.getConnection(url, userName, password2);
  
	        	PreparedStatement pstmt = conn.prepareStatement(query);
	        	pstmt.executeUpdate(query);
	        	out.print(1);
			
	    
	        }
	        catch (Exception e) {
	        	System.out.println(e.getMessage().toString());	
	        	out.print(0);
			}
	       
	     }   
		 
	     else if(action.equals("login")){
	    	 String user,password; 
		     user=request.getParameter("user"); 
		     password=request.getParameter("password"); 
		     
		     if(user.equals("") || password.equals("")){
		    	 out.print(0);
		     }
		     
		     else{
		      
			     String query="Select username,password from user where username='"+user+"' and password='"+password+"'";
			       
			     try{
					String url = Constants.CONNECTION_STRING;
					String userName = Constants.USERNAME;
					String password2 = Constants.PASSWORD;
						
					Class.forName ("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(url, userName, password2);
		        
					Statement st = (Statement)conn.createStatement();
					st.executeQuery(query);
					ResultSet rs = st.getResultSet();
					int entry_num = 0;
					while (rs.next())
					{
						entry_num++; 
					}
					if(entry_num == 0){
						out.print(0);
					}
					else{
						out.print(1);
						System.out.println("1234");
					}
		    	    
				}
				catch (Exception e) {
					System.out.println(e.getMessage().toString());
				}
		     }
	    	 
	     }
	
		 
	    
	       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	 @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) 
 	    throws ServletException, IOException { 
		 PrintWriter out = response.getWriter();  
	       
		 int createuser=1;
		 int updateuser=1;
	       
	     String action = request.getParameter("action");
	        
	     if(action.equals("create")){
	    	 
	        String username = request.getParameter("username");
	        
	        String findname="Select id from newuser where username='"+username+"'";
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
					idstring = rs.getString("id");
				}
				createuser = Integer.parseInt(idstring);
				updateuser=createuser;
				
	        }
	        catch(Exception e){
	        	
	        }
	        
	        String updatewalks="Update walk set isActive=0 where createuser_idUser='"+createuser+"'";
	        try{
	        	String url = Constants.CONNECTION_STRING;
				String userName = Constants.USERNAME;
				String password2 = Constants.PASSWORD;
				
				Class.forName ("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection(url, userName, password2);
				PreparedStatement pstmt = conn.prepareStatement(updatewalks);
				pstmt.executeUpdate(updatewalks);
				//Statement st = (Statement)conn.createStatement();
				//st.executeQuery(updatewalks);
				
	        }
	        catch(Exception e){
	        	
	        }
	        
	        String name = request.getParameter("name");
		    String location = request.getParameter("location");
			String createdate;
			String updatedate;
			String isactive="1";
			String city=request.getParameter("city");
			String county=request.getParameter("county");
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date cdate = new Date();
	        createdate = dateFormat.format(cdate);
	        updatedate=createdate;
	        
	        //String query = "Insert into walk (name,location,createdate,updatedate) values ('"+name+"','"+location+"','"+createdate+"','"+updatedate+"')";
	        String query="INSERT INTO walk (idwalk,name,location,createdate,updatedate,isActive,city_idcity,county_idcounty,createuser_idUser,updateuser_idUser1) VALUES (null, '"+name+"','"+location+"','"+createdate+"','"+updatedate+"','"+isactive+"','"+city+"','"+county+"','"+createuser+"','"+updateuser+"');";
	        try{
	        	String url = Constants.CONNECTION_STRING;
	        	String userName = Constants.USERNAME;
	        	String password2 = Constants.PASSWORD;
			
	        	Class.forName ("com.mysql.jdbc.Driver").newInstance();
	        	Connection conn = DriverManager.getConnection(url, userName, password2);
   
	        	PreparedStatement pstmt = conn.prepareStatement(query);
	        	pstmt.executeUpdate(query);
	        	out.print(1);
			
	    
	        }
	        catch (Exception e) {
	        	System.out.println(e.getMessage().toString());	
	        	out.print(0);
			}
	       
	     }   
		 
	     else if(action.equals("login")){
	    	 String user,password; 
		     user=request.getParameter("user"); 
		     password=request.getParameter("password"); 
		     
		     if(user.equals("") || password.equals("")){
		    	 out.print(0);
		     }
		     
		     else{
		      
			     String query="Select username,password from newuser where username='"+user+"' and password='"+password+"'";
			       
			     try{
					String url = Constants.CONNECTION_STRING;
					String userName = Constants.USERNAME;
					String password2 = Constants.PASSWORD;
						
					Class.forName ("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection(url, userName, password2);
		        
					Statement st = (Statement)conn.createStatement();
					st.executeQuery(query);
					ResultSet rs = st.getResultSet();
					int entry_num = 0;
					while (rs.next())
					{
						entry_num++; 
					}
					if(entry_num == 0){
						out.print(0);
					}
					else{
						out.print(1);
						System.out.println("1234");
					}
		    	    
				}
				catch (Exception e) {
					System.out.println(e.getMessage().toString());
				}
		     }
	    	 
	     }
	
		 
		 
 	    
 	    }
	

}
