package Sample.Servlet;

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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Sample.Entity.Booklist;
import Sample.Entity.Orders;
import Sample.Util.HibernateUtil;

/**
 * Servlet implementation class EventServlet
 */
@WebServlet("/GetOrdersData")
public class GetOrdersData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetOrdersData() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List orders = session.createQuery("from Orders").list();
			session.getTransaction().commit();
			
			Map map=new HashMap();
			JSONArray array = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//String startTime = sdf.format(startTime);
			for (int i = 0; i < orders.size(); i++) {
				Orders theOrder = (Orders) orders.get(i);
				map.put("id", theOrder.getId());
				map.put("user_id", theOrder.getUser_id());
				map.put("book_id", theOrder.getBook_id());
				map.put("amount", theOrder.getAmount());
				map.put("order_date", sdf.format(theOrder.getDate()));
				array.add(map);
			}
			out.print(array);
			
            
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
