package bookshop.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Orders;
import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class MyInformation extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private String username;
	private String passwd;
	private String phone;
	private String email;
	
	@Override
    public String execute() throws Exception {
        try {
        	/*得到现有用户*/
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			User theUser = (User) websession.get("user");
			
			//获取用户所有的订单
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List allusers = session.createQuery("from User where id = ?").setString(0, theUser.getId()+"").list();
			session.getTransaction().commit();
        	
			User resultUser = (User) allusers.get(0);
			username = resultUser.getUsername();
			passwd = resultUser.getPasswd();
			email = resultUser.getEmail();
			phone = resultUser.getPhone();
			return SUCCESS;

        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
