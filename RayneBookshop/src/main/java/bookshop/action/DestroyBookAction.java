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
import bookshop.util.HibernateUtil;

import org.hibernate.Query;

import org.hibernate.Query;
import org.hibernate.Session;


public class DestroyBookAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	
	@Override
	public String execute() throws Exception {
        try {
            String book_id=this.id;
            
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete from Booklist where id = ?").setString(0, book_id).executeUpdate();
            session.getTransaction().commit();
            return SUCCESS;
            
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

	public String getId() {
        
        return id;
         
    }
     
    public void setId(String id) {
         
        this.id = id;
         
    }

}
