package bookstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SettingConnection implements ServletContextListener {

   
    public SettingConnection() {
        // TODO Auto-generated constructor stub
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

	
    public void contextInitialized(ServletContextEvent sce)  { 
        try {
			ServletContext sc=sce.getServletContext();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","system");
			UserDetails ud=new UserDetails();
			sc.setAttribute("user", ud);
			sc.setAttribute("oracle", con);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
