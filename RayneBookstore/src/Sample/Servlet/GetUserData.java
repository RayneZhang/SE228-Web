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
import Sample.Entity.User;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/GetUserData")
public class GetUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetUserData() {
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
			List users = session.createQuery("from User").list();
			session.getTransaction().commit();
			Map map=new HashMap();
			JSONArray array = new JSONArray();
			for (int i = 0; i < users.size(); i++) {
				User theUser = (User) users.get(i);
				map.put("id", theUser.getId());
				map.put("username", theUser.getUsername());
				map.put("userpasswd", theUser.getPasswd());
				map.put("userphone", theUser.getPhone());
				map.put("email", theUser.getEmail());
				map.put("userflag", theUser.getFlag());
				array.add(map);
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
