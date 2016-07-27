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

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/Savebook")
public class Savebook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Savebook() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String bookname = (String) request.getParameter("bookname");
            String author = (String) request.getParameter("author");
            String press = (String) request.getParameter("press");
            int price = Integer.parseInt(request.getParameter("price"));
            String image = (String) request.getParameter("img");
            String book_id=request.getParameter("id");
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            if(book_id.equals("0")){ 
            	Booklist theBooklist=new Booklist();
            	theBooklist.setBookname(bookname);
            	theBooklist.setAuthor(author);
            	theBooklist.setPress(press);
            	theBooklist.setPrice(price);
            	theBooklist.setImage(image);
            	session.save(theBooklist);
            }
            else{
            	Booklist theBooklist = (Booklist) session.createQuery("from Booklist where id = ?").setString(0, book_id).list().get(0);
            	theBooklist.setBookname(bookname);
            	theBooklist.setAuthor(author);
            	theBooklist.setPress(press);
            	theBooklist.setPrice(price);
            	theBooklist.setImage(image);
            	session.saveOrUpdate(theBooklist);
            }
            session.getTransaction().commit();
            
			
            /*session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List events = session.createQuery("from Event").list();
			session.getTransaction().commit();
			for (int i = 0; i < events.size(); i++) {
				Event theEvent = (Event) events.get(i);
				out.println("id: " + theEvent.getId() + "<br>" + "title: "
						+ theEvent.getTitle() + "<br>" + "date: " + theEvent.getDate() + "<br><br>");
			}*/
            
        } catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
