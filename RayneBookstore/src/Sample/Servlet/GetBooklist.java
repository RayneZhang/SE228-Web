package Sample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Sample.Entity.Booklist;
import Sample.Util.HibernateUtil;

@WebServlet("/GetBooklist")
public class GetBooklist extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetBooklist() {
    	super();
    }
    

    public List processRequest()
            throws ServletException, IOException {
        try {           
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List booklists = session.createQuery("from Booklist").list();
            //List events = session.createSQLQuery("select * from Event;").list();
			session.getTransaction().commit();
			return booklists;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
    }
}
