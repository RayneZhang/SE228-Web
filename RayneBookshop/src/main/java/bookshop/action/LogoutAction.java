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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class LogoutAction extends ActionSupport {
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
            int totalprice=theCart.getTotal_price();
            
            User theUser = (User) websession.get("user");
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
    		websession.clear();
    		return SUCCESS;
        } catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }
}

