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

import Sample.Entity.User;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/SaveUserServlet")
public class SaveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SaveUserServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String username = (String) request.getParameter("username");
            String userpasswd = (String) request.getParameter("userpasswd");
            String userphone = (String) request.getParameter("userphone");
            String email = (String) request.getParameter("email");
            String userflag = (String) request.getParameter("userflag");
            String id=request.getParameter("id");
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            if(id.equals("0")){ 
            	User theUser=new User();
            	theUser.setUsername(username);
            	theUser.setPasswd(userpasswd);
            	theUser.setPhone(userphone);
            	theUser.setEmail(email);
            	theUser.setFlag(userflag);
            	session.save(theUser);
            }
            else{
            	User theUser = (User) session.createQuery("from User where id = ?").setString(0, id).list().get(0);
            	theUser.setUsername(username);
            	theUser.setPasswd(userpasswd);
            	theUser.setPhone(userphone);
            	theUser.setEmail(email);
            	theUser.setFlag(userflag);
            	session.saveOrUpdate(theUser);
            }
            session.getTransaction().commit();
            
            
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
