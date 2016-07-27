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
import java.util.Vector;

import net.sf.json.JSONArray;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.Orders;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class AddProductAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String bookname;
	private String operator;

	@Override
    public String execute() throws Exception {
        try {
            String bookname = this.bookname;
            String operator = this.operator;
            ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
    		Cart theCart = (Cart) websession.get("cart");
    		
    		Vector<Booklist> currentbooks=theCart.getBookincart();
    		Map<Integer,Integer> amountmap=theCart.getAmountmap();
    		int totalprice=theCart.getTotal_price();
    		//减少商品
    		if(operator.equals("-")){
    			for(int i=0;i<currentbooks.size();i++){
    				if(currentbooks.get(i).getBookname().equals(bookname)){
    					int id=currentbooks.get(i).getId();
    					int temamount = amountmap.get(id);
    					int oneprice=currentbooks.get(i).getPrice();
    					totalprice-=oneprice;
    					if(temamount!=1)
    						amountmap.put(id, temamount-1);
    					else{
    						amountmap.remove(id);
    						currentbooks.remove(i);
    					}
    					break;
    				}
    			}
    			//存session
    			theCart.setAmountmap(amountmap);
    			theCart.setBookincart(currentbooks);
    			theCart.setTotal_price(totalprice);
    			websession.put("cart", theCart);
    		}
    		
    		//增加商品
    		if(operator.equals("1")){
    			//搜索是否已经是cart里面的商品
    			Boolean already=false;
    			for(int i=0;i<currentbooks.size();i++){
    				if(currentbooks.get(i).getBookname().equals(bookname)){
    					int id=currentbooks.get(i).getId();
    					int temamount = amountmap.get(id);
    					int oneprice=currentbooks.get(i).getPrice();
    					totalprice+=oneprice;

    					amountmap.put(id, temamount+1);
    					already=true;
    					break;
    				}
    			}
    			if(already==false){
    				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    	            session.beginTransaction(); 
    	            List allbooks = session.createQuery("from Booklist").list();
    	            session.getTransaction().commit();
    				for(int j=0;j<allbooks.size();j++){
    					Booklist theBook=(Booklist) allbooks.get(j);
    					if(theBook.getBookname().equals(bookname)){
    						int id=theBook.getId();
        					int oneprice=theBook.getPrice();
        					totalprice+=oneprice;

        					currentbooks.add(theBook);
        					amountmap.put(id, 1);
        					break;
    					}
    				}
    			}
    			//存session
    			theCart.setAmountmap(amountmap);
    			theCart.setBookincart(currentbooks);
    			theCart.setTotal_price(totalprice);
    			websession.put("cart", theCart);
    		}
    		return SUCCESS;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public String getBookname() {      
        return bookname;
    }
     
    public void setBookname(String book) {
        bookname = book;       
    }
    
    public String getOperator() {
        return operator;        
    }
     
    public void setOperator(String operator) {
        this.operator = operator;   
    }

}
