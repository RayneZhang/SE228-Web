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

@WebServlet("/GetCart")
public class GetCart extends HttpServlet {
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
            
            
            HttpSession usersession = request.getSession();
			User theUser = (User) usersession.getAttribute("username");
			if(theUser==null){
				out.println("您尚未登录，请前往登录");
				out.println("<button type=\"button\" onclick=\"window.location='index.jsp'\">好的</button>");
			}
            
			else{
				HttpSession cartsession = request.getSession();
				Cart theCart = (Cart) cartsession.getAttribute("cart");
				if(theCart==null){
					out.println("您的购物车中尚未有商品，请返回购物");
					out.println("<button type=\"button\" onclick=\"window.location='index.jsp'\">返回</button>");
				}
				else{
					Vector<Booklist> booksincart=theCart.getBookincart();
					Map<Integer,Integer> amountmap= theCart.getAmountmap();
					out.println("这是您的购物车");
					out.println("<ul class=\"products\">");
					for (int i = 0; i < booksincart.size(); i++) {
						Booklist theBook = booksincart.get(i);
						out.print("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
								+ theBook.getBookname() + "</p> <p>数量:" + amountmap.get(theBook.getId()) + "</p> </div> </li>");
					}
					out.println("</ul>");
					out.println("总价为："+theCart.getTotal_price());
					out.println("<button type=\"button\" onclick=\"window.location='NewOrderServlet'\">Pay</button>");
				}
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

