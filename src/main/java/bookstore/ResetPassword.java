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


@WebServlet("/change")
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ResetPassword() {
        super();
      
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter();
		String pword=request.getParameter("confirmPassword");
		String otp =request.getParameter("otp");
		ServletContext sc=getServletContext();
		String mono = (String)  sc.getAttribute("mono");
		int otp1        = (int)        sc.getAttribute("otp");
		String otp2 =String.valueOf(otp1);
		Connection con=(Connection) sc.getAttribute("oracle");
		if(otp.equalsIgnoreCase(otp2)) {
			try {
				PreparedStatement pstmt=con.prepareStatement("select pword from bookuser where mobile=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				pstmt.setString( 1, mono);
				ResultSet rs=pstmt.executeQuery();
				if(rs.next()) {
					rs.updateString(1,pword);
					rs.updateRow();
					response.sendRedirect("successfull.html");
				}
				else {
					pw.println("PLEASE ENTER REGISTER MOBILE NUMBER ONLY.");
					RequestDispatcher rd=request.getRequestDispatcher("/reset.html");
					rd.include(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			pw.println("OTP NOT MATCHED FOR THE MOBILE NUMBER "+mono);
			RequestDispatcher rd=sc.getRequestDispatcher("/reset.html");
			rd.include(request, response);    
			}
		
		
	}

}
