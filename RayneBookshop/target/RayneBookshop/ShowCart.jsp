<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ page language="java" import="bookshop.model.Cart" 
							import="com.opensymphony.xwork2.ActionContext" 
							import="java.util.Map"
							import="java.util.Vector"
							import="bookshop.model.Booklist"
							import="bookshop.model.SaleMessage"%>
	<%ActionContext actionContext = ActionContext.getContext();
      Map websession = actionContext.getSession(); 
      Cart theCart = (Cart) websession.get("cart");
      SaleMessage saleMessage = (SaleMessage) websession.get("salemessage");
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
			if(theCart.getTotal_price()>=saleMessage.getBasic()){
				out.println("享受满减优惠后总价为："+(theCart.getTotal_price()-saleMessage.getMinus()));
			}
			out.println("<button type=\"button\" onclick=\"window.location='gotopay.action'\">Pay</button>");
		}
    %>
	
</body>
</html>