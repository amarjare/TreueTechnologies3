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


@WebServlet("/cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc=getServletContext();
			PrintWriter pw=response.getWriter();
			Connection con=(Connection) sc.getAttribute("oracle");
			UserDetails ud=(UserDetails) sc.getAttribute("userdetails");
			if(ud!=null) {
			String mobile=ud.getMobile();
			String query="insert into bookcart values(?,?,?,?)";
			PreparedStatement pstmt=con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, mobile);
			pstmt.setString(2, request.getParameter("bn"));
			pstmt.setString(3, request.getParameter("ba"));
			pstmt.setString(4, request.getParameter("pr"));
			pstmt.executeUpdate();
			query="select bookname,authorname,bookprice from bookcart where mobile=?";
			pstmt=con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, mobile);
			ResultSet rs=pstmt.executeQuery();
			pw.println("<html><head> <link rel=stylesheet href=cart.css> <head><body>");
			pw.println("<h1>WELCOME TO THE CART "+ud.getName()+"</h1>");
			pw.println("<div><table id='data-table'><tr><th>SR NO</th><th class='long'>BOOK NAME</th><th class='long'>AUTHOR NAME</th><th>PRICE</th><th>ACTION</th>");
			int count=1;
				while(rs.next()) {
					pw.println("<tr><td>"+ count +"</td><td class='long'>"+rs.getString(1)+"</td><td class='long'>"+rs.getString(2) +"</td><td>"+rs.getString(3)+"</td><td><a href='delete.html'  class='delete-link'>DELETE</a>"+ "<br> "+" <a href='checkout.html'>CHECKOUT</a></td>");
					count++;
				}
				pw.println("</div></table></body><html>");
			}
			else {
				pw.println("<html><body><p>PLEASE LOGIN TO ADD BOOK TO THE CART<p></body><html>");
				RequestDispatcher rd=request.getRequestDispatcher("/login.html");
				rd.include(request, response);
			}
		}catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}

