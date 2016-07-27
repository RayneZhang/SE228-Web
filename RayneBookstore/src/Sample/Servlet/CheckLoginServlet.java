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

@WebServlet("/CheckLoginServlet")
public class CheckLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CheckLoginServlet() {
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
            //out.println("<meta http-equiv=\"refresh\" content=\"5; url=index.jsp; charset=utf-8\">");
            out.println("<title>Servlet UserServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String username = (String) request.getParameter("username");            
            String passwd = (String) request.getParameter("passcode");
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List users = session.createQuery("from User where username = ? and passwd = ?").setString(0, username).setString(1, passwd).list();
			session.getTransaction().commit();
			
			if(users.size()==0) {
				out.println("用户名或密码错误！请重新登录...");
				out.println("<button type=\"button\" onclick=\"window.location='login.jsp'\">好的</button>");
				
			}
			else{
				User theUser = (User) users.get(0);
				HttpSession loginsession = request.getSession();
				loginsession.setAttribute("username", theUser);
				loginsession.setMaxInactiveInterval(3600);
				
				session = HibernateUtil.getSessionFactory().getCurrentSession();
	            session.beginTransaction(); 
				List CartsDB = session.createQuery("from Cart where user_id = ?").setString(0, theUser.getId()+"").list();
				session.getTransaction().commit();
				
				Vector<Booklist> bookincart=new Vector<Booklist>();
				Map<Integer,Integer> idmap =new HashMap<Integer,Integer>();
				Map<Integer,Integer> amountmap =new HashMap<Integer,Integer>();
				Cart theCart = new Cart();
				int totalprice=0;
				for (int i = 0; i < CartsDB.size(); i++) {
					Cart temCart = (Cart) CartsDB.get(i);
					idmap.put(temCart.getUser_id(), temCart.getBook_id());
					amountmap.put(temCart.getBook_id(), temCart.getTotal_amount());
					
					session = HibernateUtil.getSessionFactory().getCurrentSession();
		            session.beginTransaction(); 
					List Price = session.createQuery("from Booklist where id = ?").setString(0, temCart.getBook_id()+"").list();
					session.getTransaction().commit();
					bookincart.add((Booklist) Price.get(0));
					totalprice += ((Booklist) Price.get(0)).getPrice() * temCart.getTotal_amount();
				}
				theCart.setTotal_price(totalprice);
				theCart.setBookincart(bookincart);
				theCart.setIdmap(idmap);
				theCart.setAmountmap(amountmap);
				HttpSession cartsession = request.getSession();
				cartsession.setAttribute("cart", theCart);
				/*注意为什么用equals而不是==*/
				if(theUser.getFlag().equals("user")){
					out.println("欢迎用户"+username+"登录！");
					response.sendRedirect("index.jsp");
				}
				if(theUser.getFlag().equals("admin")){
					out.println("欢迎管理员"+username+"登录！");
					response.sendRedirect("manage.jsp");
				}
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
