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
import java.util.Vector;

//import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.util.HibernateUtil;


public class GetCartData extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private List<HashMap<String,Object>> datas;
	//private List datas = new ArrayList<HashMap<String,Object>>();
	
	public GetCartData() {
		//初始化List对象
		datas = new ArrayList<HashMap<String,Object>>();
	}
	
	@Override
    public String execute() throws Exception {
    	/*response.setCharacterEncoding("UTF-8");*/
		//怎么保证json可以带中文呢
        try {
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			Cart theCart=(Cart) websession.get("cart");
        
			if(theCart!=null){ 
			Vector<Booklist> allbooks = theCart.getBookincart();
			Map<Integer,Integer> amountmap=theCart.getAmountmap();
			
			datas.clear();
			//JSONArray array = new JSONArray();
			
			for (int i = 0; i < allbooks.size(); i++) {
				Booklist theBook = allbooks.get(i);
				HashMap map=new HashMap();
				map.put("name",theBook.getBookname());
				map.put("quantity", amountmap.get(theBook.getId()));
				map.put("price", theBook.getPrice());
				datas.add(map);
			}
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