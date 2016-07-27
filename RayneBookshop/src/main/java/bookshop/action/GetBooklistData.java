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
import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Orders;
import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class GetBooklistData extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private JSONArray array;
	private String result;
	
	public GetBooklistData() {
		//初始化List对象
		array = new JSONArray();
	}

	@Override
    public String execute() throws Exception {
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List booklists = session.createQuery("from Booklist").list();
			session.getTransaction().commit();
			for (int i = 0; i < booklists.size(); i++) {
				Map map=new HashMap();
				Booklist theBook = (Booklist) booklists.get(i);
				map.put("bookid", theBook.getId());
				map.put("bookname", theBook.getBookname());
				map.put("author", theBook.getAuthor());
				map.put("press", theBook.getPress());
				map.put("price", theBook.getPrice());
				map.put("img", theBook.getImage());
				map.put("category", theBook.getCategory());
				array.add(map);
				/*out.println("<tr> <td>" + theBook.getId() + "</td><td>"+ theBook.getBookname() 
				+ "</td><td>"+ theBook.getAuthor()+ "</td><td>"+ theBook.getPress()
				+ "</td><td>"+ theBook.getPrice()+ "</td><td>"+ theBook.getImage()+"</td> </tr>");*/
			}
			result = array.toString();
			return SUCCESS;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

}
