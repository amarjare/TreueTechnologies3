package bookstore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Contact
 */
@WebServlet("/contact")
public class Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Contact() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PrintWriter pw=response.getWriter();
			ServletContext sc= getServletContext();
			Connection con=(Connection) sc.getAttribute("oracle");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String subject=request.getParameter("subject");
			String message=request.getParameter("message");
			String query="insert into feedback values(?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3,subject);
			pstmt.setString(4, message);
			pstmt.executeUpdate();
			pw.print("<html><head> <style> div{margin:200px 150px; background-color:pink; border:5px solid black; border-radius:10px; height:150px;width:1200px; text-align:center;}"
					+ "body{background-image: url(images/homepagebackground.jpg);background-size:cover;background-repeat: no-repeat;}</style></head><body><div>");
			pw.print("<h1>THANKS! YOUR RESPONSE HAS BEEN SAVED SUCCESSFULLY..</h1>");
			pw.print("<p>Our team will contact you shortly..</p>");
			pw.print("<a href='BookVerse.html'>HOMEPAGE</a>");
			pw.print("</div></body></html>");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

}
