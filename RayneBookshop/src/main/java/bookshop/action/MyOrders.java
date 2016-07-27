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


public class MyOrders extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private JSONArray jsonArray;
	private String result;
	
	public MyOrders() {
		//初始化List对象
		jsonArray = new JSONArray();
	}
	
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
			List allorders = session.createQuery("from Orders where user_id = ? order by order_date desc").setString(0, theUser.getId()+"").list();
			List allbooks = session.createQuery("from Booklist").list();
			session.getTransaction().commit();
        	
			//out.println("*************"+temDate+"*************");
			for (int i = 0; i < allorders.size(); i++) {
				Orders theOrder = (Orders) allorders.get(i);
				int bookid=theOrder.getBook_id();
				Booklist theBook = new Booklist();
				for(int j = 0;j<allbooks.size();j++){
					if(((Booklist)allbooks.get(j)).getId()==bookid){
						theBook=(Booklist)allbooks.get(j);
						break;
					}
				}
				HashMap map=new HashMap();
				map.put("date", theOrder.getDate());
				map.put("img", theBook.getImage());
				map.put("bookname", theBook.getBookname());
				map.put("amount", theOrder.getAmount());
				map.put("price", theBook.getPrice());
				map.put("category", theBook.getCategory());
				jsonArray.add(map);
				//System.out.print(map.get("date"));
				/*out.println("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
		    			+ theBook.getBookname() + "</p> <p>Amount:" + theOrder.getAmount() + "</p> </div> </li>");*/
				
				/*out.println("*************"+temDate+"*************");
				out.println("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
		    			+ theBook.getBookname() + "</p> <p>Amount:" + theOrder.getAmount() + "</p> </div> </li>");*/
			}
			result = jsonArray.toString();
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
