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
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import Sample.Entity.Booklist;
import Sample.Entity.Cart;
import Sample.Entity.User;
import Sample.Util.HibernateUtil;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String username = (String) request.getParameter("username");            
            String passwd = (String) request.getParameter("userpasswd");
            String userphone = (String) request.getParameter("userphone");
            String useremail = (String) request.getParameter("email");
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List users = session.createQuery("from User where username = ?").setString(0, username).list();
			session.getTransaction().commit();
			
			if(users.size()!=0) {
				//User theUser = (User) users.get(0);
				out.println("该用户名已被占用！请重新注册...");
				out.println("<button type=\"button\" onclick=\"window.location='register.jsp'\">好的</button>");
			}
			else{
				Session newsession = HibernateUtil.getSessionFactory().getCurrentSession();
	            newsession.beginTransaction();
	            User theUser=new User();
	            theUser.setUsername(username);
	            theUser.setPasswd(passwd);
	            theUser.setPhone(userphone);
	            theUser.setEmail(useremail);
	            theUser.setFlag("user");
            	newsession.save(theUser);
            	newsession.getTransaction().commit();
				out.println("注册成功！请前往登录...");
				response.sendRedirect("login.jsp");
			}
			out.println("</body>");
			out.println("</html>");
            
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
