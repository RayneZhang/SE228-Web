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
import java.util.Vector;

import org.hibernate.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bookshop.model.Booklist;
import bookshop.model.Cart;
import bookshop.model.User;
import bookshop.util.HibernateUtil;

public class GetCart extends ActionSupport {
    private static final long serialVersionUID = 18925377774889413L;

    @Override
    public String execute() throws Exception {
        try {
        	ActionContext actionContext = ActionContext.getContext();
		    Map websession = actionContext.getSession();
			User theUser = (User) websession.get("user");
			if(theUser==null){
				return ERROR;
				/*out.println("ÄúÉÐÎ´µÇÂ¼£¬ÇëÇ°ÍùµÇÂ¼");
				out.println("<button type=\"button\" onclick=\"window.location='index.jsp'\">ºÃµÄ</button>");*/
			}
            
			else{
				return SUCCESS;
			}
        }catch(Exception e){
        	e.printStackTrace();
        	return ERROR;
        }
    }
  
}

