package Sample.Servlet;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import Sample.Entity.Booklist;
import Sample.Entity.Cart;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/AddProduct")
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddProduct() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String bookname = (String) request.getParameter("name");
            String operator = (String) request.getParameter("operate");
            HttpSession cartsession = request.getSession();
    		Cart theCart = (Cart) cartsession.getAttribute("cart");
    		
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
    			cartsession.setAttribute("cart", theCart);
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
    				GetBooklist getbooklist=new GetBooklist();
    				List allbooks=getbooklist.processRequest();
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
    			cartsession.setAttribute("cart", theCart);
    		}
            
        } catch(Exception e){
        	e.printStackTrace();
        }
        finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
