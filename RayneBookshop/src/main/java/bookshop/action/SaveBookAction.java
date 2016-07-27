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
import java.util.Random;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class SaveBookAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private Booklist bookbean;
	private String id;

    @Override
	public String execute() throws Exception {
        try {
            String bookname = bookbean.getBookname();
            String author = bookbean.getAuthor();
            String press = bookbean.getPress();
            int price = bookbean.getPrice();
            String image = bookbean.getImage();
            String book_id= this.id;
            String category = bookbean.getCategory();
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            if(book_id.equals("0")){ 
            	Booklist theBooklist=new Booklist();
            	theBooklist.setBookname(bookname);
            	theBooklist.setAuthor(author);
            	theBooklist.setPress(press);
            	theBooklist.setPrice(price);
            	theBooklist.setImage(image);
            	theBooklist.setCategory(category);
            	session.save(theBooklist);
            }
            else{
            	Booklist theBooklist = (Booklist) session.createQuery("from Booklist where id = ?").setString(0, book_id).list().get(0);
            	theBooklist.setBookname(bookname);
            	theBooklist.setAuthor(author);
            	theBooklist.setPress(press);
            	theBooklist.setPrice(price);
            	theBooklist.setImage(image);
            	theBooklist.setCategory(category);
            	session.saveOrUpdate(theBooklist);
            }
            session.getTransaction().commit();
            return SUCCESS;
			
            /*session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List events = session.createQuery("from Event").list();
			session.getTransaction().commit();
			for (int i = 0; i < events.size(); i++) {
				Event theEvent = (Event) events.get(i);
				out.println("id: " + theEvent.getId() + "<br>" + "title: "
						+ theEvent.getTitle() + "<br>" + "date: " + theEvent.getDate() + "<br><br>");
			}*/
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

    public Booklist getBookbean() {
        
        return bookbean;
         
    }
     
    public void setBookbean(Booklist book) {
         
        bookbean = book;
         
    }
    
    public String getId() {
        
        return id;
         
    }
     
    public void setId(String id) {
         
        this.id = id;
         
    }

}
