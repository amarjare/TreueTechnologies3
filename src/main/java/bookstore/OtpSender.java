package bookstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/otp")
public class OtpSender extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int otp;
       
   
    public OtpSender() {
        super();
       
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Random random = new Random();
        PrintWriter pw=response.getWriter();
        otp = 100000 + random.nextInt(900000);
        String numbers = request.getParameter("mobile");
        ServletContext sc=getServletContext();
        Connection con=(Connection) sc.getAttribute("oracle");
        try {
        	PreparedStatement pstmt=con.prepareStatement("select pword from bookuser where mobile=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString( 1, numbers);
			ResultSet rs=pstmt.executeQuery();
            if(rs.next()) {
            String url ="https://www.fast2sms.com/dev/bulkV2?authorization=TXyspVMlC7EutdkD05mhRQZfSU21avrjPocnJWN8exKBLOIiAqtdo4NQJfgrmYeHnIh2GZLwvK5p0O6M&route=otp&variables_values="+otp+"&flash=0&numbers="+numbers;
            
           
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            
           
            connection.setRequestMethod("GET");
            
           
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response1 = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            in.close();
            
         
            System.out.println("Response: " + response1.toString());
            String number=request.getParameter("mobile");
            sc.setAttribute("mono", number);
            sc.setAttribute("otp", otp);
            response.sendRedirect("reset.html");
            }
            else {
            	pw.println("<html><body><h3>ENTER REGISTERED MOBILE NUMBER ONLY.</h3></body></html>");
				RequestDispatcher rd=request.getRequestDispatcher("/forgot.html");
				rd.include(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	}
