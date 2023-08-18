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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    PrintWriter out;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id=request.getParameter("username");
			String pass=request.getParameter("password");
			ServletContext sc=getServletContext();
			Connection con= (Connection) sc.getAttribute("oracle");
			PreparedStatement pstmt=con.prepareStatement("select * from bookuser where mobile=? and pword=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			ResultSet rs=pstmt.executeQuery();
			out = response.getWriter();
			if(rs.next()) {
				UserDetails ud=new UserDetails();
				ud.setName(rs.getString(1));
				ud.setDob(rs.getString(2));
				ud.setGender(rs.getString(3));
				ud.setEmailid(rs.getString(5));
				ud.setMobile(rs.getString(4));
				sc.setAttribute("userdetails", ud);
				response.setContentType("text/html");
		        out.println("<!DOCTYPE html>");
		        out.println("<html>");
		        out.println("<head>");
		        out.println("<title>Customer Homepage</title>");
		        out.println("</head>");
		        out.println("<body >");
		        out.println("<h1>Welcome, " + ud.getName() +" ! "+"</h1>");
		        RequestDispatcher rd=request.getRequestDispatcher("/lhome.html");
		        rd.include(request, response);
		        out.println("</body></html>");
			}
			else {
				out.println("<html><body ><h3 text=red><center>INVALID CREDENTIALS..</center></h3></body></html>");
				RequestDispatcher rd=sc.getRequestDispatcher("/login.html");
				rd.include(request, response);    
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
