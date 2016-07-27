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


public class GetBooklist extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private List<HashMap<String,Object>> datas;
	
	public GetBooklist() {
		//初始化List对象
		datas = new ArrayList<HashMap<String,Object>>();
	}

	@Override
    public String execute() throws Exception {
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List booklists = session.createQuery("from Booklist").list();
			session.getTransaction().commit();
			for (int i = 0; i < booklists.size(); i++) {
				HashMap map=new HashMap();
				Booklist theBook = (Booklist) booklists.get(i);
				map.put("bookid", theBook.getId());
				map.put("bookbean.bookname", theBook.getBookname());
				map.put("bookbean.author", theBook.getAuthor());
				map.put("bookbean.press", theBook.getPress());
				map.put("bookbean.price", theBook.getPrice());
				map.put("bookbean.image", theBook.getImage());
				map.put("bookbean.category", theBook.getCategory());
				datas.add(map);
				/*out.println("<tr> <td>" + theBook.getId() + "</td><td>"+ theBook.getBookname() 
				+ "</td><td>"+ theBook.getAuthor()+ "</td><td>"+ theBook.getPress()
				+ "</td><td>"+ theBook.getPrice()+ "</td><td>"+ theBook.getImage()+"</td> </tr>");*/
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
