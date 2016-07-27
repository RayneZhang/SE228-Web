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


public class GetOrdersForCategory extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private JSONArray jsonArray;
	private String result;
	
	public GetOrdersForCategory() {
		//初始化List对象
		jsonArray = new JSONArray();
	}
	
	@Override
    public String execute() throws Exception {
        try {
			
			//获取用户所有的订单
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List allorders = session.createQuery("from Orders order by order_date desc").list();
			List allbooks = session.createQuery("from Booklist order by category").list();
			session.getTransaction().commit();
        	
			for (int i = 0; i < allbooks.size(); i++) {
				Booklist theBook = (Booklist) allbooks.get(i);
				int bookid=theBook.getId();
				//Orders theOrder = new Orders();
				boolean newbook = true;
				HashMap map=new HashMap();
				for(int j = 0;j<allorders.size();j++){
					if(((Orders)allorders.get(j)).getBook_id()==bookid){
						Orders theOrder=(Orders)allorders.get(j);
						if(newbook){
							map.put("date", theOrder.getDate());
							map.put("img", theBook.getImage());
							map.put("bookname", theBook.getBookname());
							map.put("amount", theOrder.getAmount());
							map.put("price", theBook.getPrice());
							map.put("category", theBook.getCategory());
							newbook = false;
						}
						else{
							map.put("amount", (Integer) map.get("amount") + theOrder.getAmount());
						}
					}
					if(j == allorders.size()-1 && map.size()!=0){
						jsonArray.add(map);
					}
				}
				
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
