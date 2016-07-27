/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package Sample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import Sample.Entity.Orders;
import Sample.Entity.User;
import Sample.Util.HibernateUtil;

@WebServlet("/StatsByDay")
public class StatsByDay extends HttpServlet {
    private static final long serialVersionUID = 18925377774889413L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     *  methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("deprecation")
	protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            
            /*//获取用户
            HttpSession loginsession = request.getSession();
			User theUser = (User) loginsession.getAttribute("username");*/
            
			//获取用户所有的订单
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction(); 
			List allorders = session.createQuery("from Orders order by order_date desc").list();
			List allbooks = session.createQuery("from Booklist").list();
			session.getTransaction().commit();
			
			if(allorders.get(0)==null){
				out.println("尚无相关订单");
			}
			else{
				int amount=0;
				int total_price=0;
				
				Date temDate =((Orders) allorders.get(0)).getDate();
				//DateFormat temDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				out.println("<p>*************"+temDate+"*************</p>");
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
					if(theOrder.getDate().equals(temDate)){
						amount+=theOrder.getAmount();
						total_price+=theBook.getPrice();
						/*out.println("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
			    				+ theBook.getBookname() + "</p> <p>Amount:" + theOrder.getAmount() + "</p> </div> </li>");*/
					}
					else{
						out.println("Amount:"+amount);
						out.println("Total price:"+total_price);
						amount=0;
						total_price=0;
						temDate= theOrder.getDate();
						out.println("<p>*************"+temDate+"*************</p>");
						amount+=theOrder.getAmount();
						total_price+=theBook.getPrice();
						/*out.println("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
			    				+ theBook.getBookname() + "</p> <p>Amount:" + theOrder.getAmount() + "</p> </div> </li>");*/
					}
				}
				out.println("Amount:"+amount);
				out.println("Total price:"+total_price);
			}
			
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

