package bookshop.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.Vector;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.SaleMessage;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class LoginAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private User personbean;
	private String imgurl;
	private SaleMessage saleMessage;
	
	public LoginAction(){
		imgurl = "images/background.jpg";
	}
	
	@Override
    public String execute() throws Exception {
        try {
        	ApplicationContext context = new ClassPathXmlApplicationContext(
    				new String[] { "DiscountMessage.xml" });
        	saleMessage = context.getBean(SaleMessage.class);
        	
            String username = personbean.getUsername();            
            String passwd = personbean.getPasswd();
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List users = session.createQuery("from User where username = ? and passwd = ?").setString(0, username).setString(1, passwd).list();
			session.getTransaction().commit();
			
			if(users.size()==0) {
				/*out.println("用户名或密码错误！请重新登录...");
				out.println("<button type=\"button\" onclick=\"window.location='login.jsp'\">好的</button>");*/				
				return INPUT;
			}
			else{
				User theUser = (User) users.get(0);
				ActionContext actionContext = ActionContext.getContext();
			    Map websession = actionContext.getSession();
			    websession.clear();
				websession.put("user", theUser);
				websession.put("salemessage", saleMessage);
				//websession.setMaxInactiveInterval(3600);如何设置定时呢？
				
				session = HibernateUtil.getSessionFactory().getCurrentSession();
	            session.beginTransaction(); 
				List CartsDB = session.createQuery("from Cart where user_id = ?").setString(0, theUser.getId()+"").list();
				session.getTransaction().commit();
				
				Vector<Booklist> bookincart=new Vector<Booklist>();
				Map<Integer,Integer> idmap =new HashMap<Integer,Integer>();
				Map<Integer,Integer> amountmap =new HashMap<Integer,Integer>();
				Cart theCart = new Cart();
				int totalprice=0;
				for (int i = 0; i < CartsDB.size(); i++) {
					Cart temCart = (Cart) CartsDB.get(i);
					idmap.put(temCart.getUser_id(), temCart.getBook_id());
					amountmap.put(temCart.getBook_id(), temCart.getTotal_amount());
					
					session = HibernateUtil.getSessionFactory().getCurrentSession();
		            session.beginTransaction(); 
					List Price = session.createQuery("from Booklist where id = ?").setString(0, temCart.getBook_id()+"").list();
					session.getTransaction().commit();
					bookincart.add((Booklist) Price.get(0));
					totalprice += ((Booklist) Price.get(0)).getPrice() * temCart.getTotal_amount();
				}
				theCart.setTotal_price(totalprice);
				theCart.setBookincart(bookincart);
				theCart.setIdmap(idmap);
				theCart.setAmountmap(amountmap);
				//HttpSession cartsession = request.getSession();直接用websession
				websession.put("cart", theCart);
				
				//接下来要链接mongodb确定用户头像——默认或已有
				MongoClient mongoClient = new MongoClient();
		        DB db = mongoClient.getDB("test");
		        DBCollection allheaders = db.getCollection("person");
		        
		        //寻找ID为userid的元素
		        BasicDBObject query = new BasicDBObject();  
		        query.put("ID", theUser.getId());  
		        DBCursor cursor = allheaders.find(query);
		        if(cursor.hasNext()){//已经有头像
		        	imgurl = "resources/images/header.png";
		        	websession.put("imgurl", imgurl);
		        	DBObject obj = cursor.next();
		        	byte[] buffer = (byte[]) obj.get("img");
		        	
		        	String realpath="F:\\Apache Software Foundation\\Tomcat 8.0\\webapps\\RayneBookshop\\resources\\images";
		        	String filename="header.png";
		        	//将buffer转化为文件
		        	 BufferedOutputStream bos = null;  
		             FileOutputStream fos = null;  
		             File file = null; 
		             File dir = new File(realpath);
		             if (!dir.exists() && dir.isDirectory())  
		             {  
		                 dir.mkdirs();  
		             }
		             file = new File(realpath + File.separator + filename);  
		             fos = new FileOutputStream(file);  
		             bos = new BufferedOutputStream(fos);  
		             bos.write(buffer);
		             //成功将buffer转化为文件
		        }
				
				
				/*注意为什么用equals而不是==*/
				if(theUser.getFlag().equals("user")){
					return LOGIN;
				}
				if(theUser.getFlag().equals("admin")){
					return NONE;
				}
			}
			return ERROR;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public User getPersonbean() {
        
        return personbean;
         
    }
     
    public void setPersonbean(User person) {
         
        personbean = person;
         
    }

}
