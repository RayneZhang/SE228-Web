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

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class GetUserData extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private List<HashMap<String,Object>> datas;

	public GetUserData() {
		//初始化List对象
		datas = new ArrayList<HashMap<String,Object>>();
	}

	@Override
    public String execute() throws Exception {
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List users = session.createQuery("from User").list();
			session.getTransaction().commit();
			for (int i = 0; i < users.size(); i++) {
				User theUser = (User) users.get(i);
				HashMap map=new HashMap();
				map.put("id", theUser.getId());
				map.put("personbean.username", theUser.getUsername());
				map.put("personbean.passwd", theUser.getPasswd());
				map.put("personbean.phone", theUser.getPhone());
				map.put("personbean.email", theUser.getEmail());
				map.put("personbean.flag", theUser.getFlag());
				datas.add(map);
			}
			return SUCCESS;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public List<HashMap<String,Object>> getDatas() {
		return datas;
	}
}
