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
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import Sample.Entity.User;
import Sample.Util.HibernateUtil;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 18925377774889413L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     *  methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Servlet MoodServlet</title>");
            out.println("</head>");
            out.println("<body>");
            
            //获取购物车信息
            HttpSession cartsession = request.getSession();
			Cart theCart = (Cart) cartsession.getAttribute("cart");
			Vector<Booklist> booksincart=theCart.getBookincart();
            Map<Integer,Integer> amountmap= theCart.getAmountmap();
            int totalprice=theCart.getTotal_price();
            
            User theUser = (User) cartsession.getAttribute("username");
            int user_id=0;
            if(theUser!=null){
            	user_id=theUser.getId();
            }
            //将购物车信息写入数据库
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
			session.createQuery("delete from Cart where user_id = ?").setString(0, user_id+"").executeUpdate();         
			session.getTransaction().commit();

			Cart temCart = new Cart();
			temCart.setUser_id(user_id);
			Set<Integer> keys=amountmap.keySet();
    		for (int i = 0; i < booksincart.size(); i++) {
    			temCart.setBook_id(booksincart.get(i).getId());
    			temCart.setTotal_amount(amountmap.get(temCart.getBook_id()));
    			Session insertsession = HibernateUtil.getSessionFactory().getCurrentSession();
                insertsession.beginTransaction(); 
    		    insertsession.save(temCart);         
    			insertsession.getTransaction().commit();
    		}
    		
    		//删除用户session
    		cartsession.removeAttribute("username");
    		cartsession.removeAttribute("cart");
			
    		response.sendRedirect("index.jsp");
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

