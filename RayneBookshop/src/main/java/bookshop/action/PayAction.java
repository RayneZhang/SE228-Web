/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package bookshop.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.Orders;
import bookshop.model.User;
import bookshop.util.HibernateUtil;


public class PayAction extends ActionSupport {
    private static final long serialVersionUID = 18925377774889413L;

    @Override
	public String execute() throws Exception {
        try {
            //获取购物车信息
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			Cart theCart = (Cart) websession.get("cart");
			Vector<Booklist> booksincart=theCart.getBookincart();
            Map<Integer,Integer> amountmap= theCart.getAmountmap();
            
            User theUser = (User) websession.get("user");
            int user_id=0;
            if(theUser!=null){
            	user_id=theUser.getId();
            }
            //将购物车写入数据库中的订单，表示成功购买
			Orders temOrder = new Orders();
			temOrder.setUser_id(user_id);
    		for (int i = 0; i < booksincart.size(); i++) {
    			temOrder.setBook_id(booksincart.get(i).getId());
    			temOrder.setAmount(amountmap.get(temOrder.getBook_id()));
    			temOrder.setDate(new Date());
    			Session insertsession = HibernateUtil.getSessionFactory().getCurrentSession();
                insertsession.beginTransaction(); 
    		    insertsession.save(temOrder);         
    			insertsession.getTransaction().commit();
    		}
    		
    		return SUCCESS;
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }

}

