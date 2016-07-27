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
import java.util.Vector;

import net.sf.json.JSONArray;

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
@WebServlet("/GetCartData")
public class GetCartData extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetCartData() {
        super();
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
        	HttpSession cartsession = request.getSession();
			Cart theCart=(Cart) cartsession.getAttribute("cart");
        
			if(theCart!=null){ 
			Vector<Booklist> allbooks = theCart.getBookincart();
			Map<Integer,Integer> amountmap=theCart.getAmountmap();
			Map map=new HashMap();
			JSONArray array = new JSONArray();
			
			for (int i = 0; i < allbooks.size(); i++) {
				Booklist theBook = allbooks.get(i);
				map.put("name",theBook.getBookname());
				map.put("quantity", amountmap.get(theBook.getId()));
				map.put("price", theBook.getPrice());
				array.add(map);
			}
			out.print(array);
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