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

@WebServlet("/search")
public class SearchBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ServletContext sc=getServletContext();
			PreparedStatement pstmt=null;
			String query;int booklength=0;int autlength=0;String bname=null;String authname=null;
			PrintWriter pw=response.getWriter();
			Connection con=(Connection) sc.getAttribute("oracle");
			UserDetails ud=(UserDetails) sc.getAttribute("userdetails");
			String bookname=request.getParameter("bookname");
			String authorname=request.getParameter("author");
			try {
				if(bookname!=null) {
				booklength=bookname.length();
				bname="%"+ bookname +"%";
				}
				if(authorname!=null) {
				autlength=authorname.length();
				authname="%"+ authorname +"%";
				}
				
				
			 if(booklength >= 1 &&  autlength==0) { 
			query="select bookname,author_name,price,category from books where bookname like ?";
			pstmt=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1,bname);
		}
			else if(booklength == 0 &&  autlength>=1) {
				query="select * from books where author_name like ?";
				pstmt=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, authname);
				}
			else if(booklength >= 0 &&  autlength>=1) {
				query="select * from books where bookname like ? and  author_name like ?";
				pstmt=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, bname);
				pstmt.setString(2,authname );
				}
			 
			 	ResultSet rs=pstmt.executeQuery();
			 	if(rs.next()) {
					pw.println("<html><head> <link rel=stylesheet href=cart.css> </head><body>");
					pw.println("<h1>BOOKS THAT MATCH WITH YOUR SERACH </h1>");
					pw.println("<div><table id='data-table'><tr><th>SR NO</th><th class='long'>BOOK NAME</th><th class='long'>AUTHOR NAME</th><th>PRICE</th><th>CATEGORY</th><th>ACTION</th>");
					int count=1;
						rs.beforeFirst();
						 if(rs.next()) {
							pw.println("<tr><td>"+ count +"</td><td class='long'>"+rs.getString(1)+"</td><td class='long'>"+rs.getString(2) +"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td><a href='cart'>ADD TO CART</a></td>");
							count++;
						}
						pw.println("</div></table></body></html>");
			 	}
			 	else {
					pw.println("<html><head> <link rel=stylesheet href=cart.css> </head><body><div>");
					pw.println("<h1>NO BOOKS FOUND..</h1>");
					pw.println("<p>please enter the correct Bookname or Authorname</p>");
					pw.println("</div></body></html>");

			 		
			 	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
