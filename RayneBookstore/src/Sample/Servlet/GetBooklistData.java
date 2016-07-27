package Sample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.json.JSONArray;

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
@WebServlet("/GetBooklistData")
public class GetBooklistData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetBooklistData() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List booklists = session.createQuery("from Booklist").list();
			session.getTransaction().commit();
			Map map=new HashMap();
			JSONArray array = new JSONArray();
			for (int i = 0; i < booklists.size(); i++) {
				Booklist theBook = (Booklist) booklists.get(i);
				map.put("bookid", theBook.getId());
				map.put("bookname", theBook.getBookname());
				map.put("author", theBook.getAuthor());
				map.put("press", theBook.getPress());
				map.put("price", theBook.getPrice());
				map.put("img", theBook.getImage());
				map.put("category", theBook.getCategory());
				array.add(map);
				/*out.println("<tr> <td>" + theBook.getId() + "</td><td>"+ theBook.getBookname() 
				+ "</td><td>"+ theBook.getAuthor()+ "</td><td>"+ theBook.getPress()
				+ "</td><td>"+ theBook.getPrice()+ "</td><td>"+ theBook.getImage()+"</td> </tr>");*/
			}
			out.print(array);
			
            
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
