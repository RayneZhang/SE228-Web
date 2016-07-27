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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Orders;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */

public class GetOrdersData extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private List<HashMap<String,Object>> result;
	
	public GetOrdersData() {
		//初始化List对象
		result = new ArrayList<HashMap<String,Object>>();
	}
	
	@Override
    public String execute() throws Exception {
        try {
        	/*得到现有用户*/
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			User theUser = (User) websession.get("user");
        	
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List orders = session.createQuery("from Orders").list();
			session.getTransaction().commit();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//String startTime = sdf.format(startTime);
			for (int i = 0; i < orders.size(); i++) {
				HashMap map=new HashMap();
				Orders theOrder = (Orders) orders.get(i);
				map.put("id", theOrder.getId());
				map.put("user_id", theOrder.getUser_id());
				map.put("book_id", theOrder.getBook_id());
				map.put("amount", theOrder.getAmount());
				map.put("order_date", sdf.format(theOrder.getDate()));
				result.add(map);
			}
			return SUCCESS;		
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public List<HashMap<String,Object>> getResult() {
		return result;
	}
}
