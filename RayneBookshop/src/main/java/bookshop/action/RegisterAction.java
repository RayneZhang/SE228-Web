package bookshop.action;

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

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class RegisterAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

    private User personbean;

    @Override
    public String execute()throws Exception {
        try {

            /*String username = (String) request.getParameter("username");            
            String passwd = (String) request.getParameter("userpasswd");
            String userphone = (String) request.getParameter("userphone");
            String useremail = (String) request.getParameter("email");*/
            
            /*Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List users = session.createQuery("from User where username = ?").setString(0, username).list();
			session.getTransaction().commit();*/
			
			/*if(users.size()!=0) {
				//User theUser = (User) users.get(0);
				out.println("该用户名已被占用！请重新注册...");
				out.println("<button type=\"button\" onclick=\"window.location='register.jsp'\">好的</button>");
			}*/
			//else{
        		if(personbean.getPhone()==null){
        			System.out.print(personbean.getUsername());
        			return SUCCESS;
        		}
        		
				Session newsession = HibernateUtil.getSessionFactory().getCurrentSession();
	            newsession.beginTransaction();
	            User theUser=new User();
	            theUser.setUsername(personbean.getUsername());
	            theUser.setPasswd(personbean.getPasswd());
	            theUser.setPhone(personbean.getPhone());
	            theUser.setEmail(personbean.getEmail());
	            theUser.setFlag("user");
            	newsession.save(theUser);
            	newsession.getTransaction().commit();
            	
				return SUCCESS;
			//}
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }
    
    public User getPersonbean() {
        
        return personbean;
         
    }
     
    public void setPersonbean(User person) {
         
        personbean = person;
         
    }

}
