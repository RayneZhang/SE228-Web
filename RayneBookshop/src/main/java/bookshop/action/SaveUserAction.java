package bookshop.action;

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

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class SaveUserAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private User personbean;
	private String id;

	@Override
	public String execute() throws Exception {
        try {
            String username = personbean.getUsername();
            String userpasswd = personbean.getPasswd();
            String userphone = personbean.getPhone();
            String email = personbean.getEmail();
            String userflag = personbean.getFlag();
            String id = this.id;
            
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
            return SUCCESS;
            
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
    
    public String getId() {
        
        return id;
         
    }
     
    public void setId(String id) {
         
        this.id = id;
         
    }

}
