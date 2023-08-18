package bookstore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter pw;
	ServletContext sc;
       
   
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			sc=getServletContext();
			pw=response.getWriter();
			Connection con=(Connection) sc.getAttribute("oracle");
			UserDetails ud=(UserDetails) sc.getAttribute("ud");
			String  name=request.getParameter("name");
			String  dob=request.getParameter("dob");
			String  gender=request.getParameter("gender");
			String  mobile=request.getParameter("mobile");
			String  email=request.getParameter("email");
			String pword=request.getParameter("password");
			String query="insert into bookuser values(?,?,?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1,name);
			pstmt.setString(2,dob);
			pstmt.setString(3,gender);
			pstmt.setString(4,mobile);
			pstmt.setString(5,email);
			pstmt.setString(6,pword);
			pstmt.executeUpdate();
			response.sendRedirect("create_account.html");
		} catch (IOException | SQLException e) {
			pw.println("<html><body ><h3><center>MOBILE NUMBER ALREADY  EXIST.USE ANOTHER MOBILE NO.</center></h3></body></html>");
			RequestDispatcher rd=sc.getRequestDispatcher("/register.html");
			rd.include(request, response);    			
		}
	}

}
