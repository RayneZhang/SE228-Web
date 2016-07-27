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
import java.util.Map;
import java.util.Random;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class UserSaveUser extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String passwd;
	private String phone;
	private String email;

	@Override
	public String execute() throws Exception {
        try {
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			User theUser = (User) websession.get("user");
			
            String userpasswd = passwd;
            String userphone = phone;
            String email = this.email;

            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            theUser.setPasswd(userpasswd);
            theUser.setPhone(userphone);
            theUser.setEmail(email);
            session.saveOrUpdate(theUser);
            session.getTransaction().commit();
            
            websession.put("user", theUser);
            return SUCCESS;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }
    
    public String getPhone() { 
        return phone;      
    }
     
    public void setPhone(String phone) {     
        this.phone = phone;      
    }
    
    public String getPasswd() { 
        return passwd;      
    }
     
    public void setPasswd(String passwd) {     
        this.passwd = passwd;      
    }
    
    public String getEmail() { 
        return email;      
    }
     
    public void setEmail(String email) {     
        this.email = email;      
    }

}
