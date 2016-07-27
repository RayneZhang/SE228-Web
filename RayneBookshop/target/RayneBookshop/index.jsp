<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Rayne's bookstore</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="themes/color.css">
    <link rel="stylesheet" type="text/css" href="demo/demo.css">
    <script type="text/javascript" src="demo/jquery.min.js"></script>
    <script type="text/javascript" src="demo/jquery.easyui.min.js"></script>
    <!-- 样式设计的style -->
    <style type="text/css">
        /*body{background-image: url(images/background.jpg);  background-repeat: repeat-y; background-size: cover;}*/
        body{background-color:rgb(255,255,255);}
        .products{
            list-style:none;
            margin-right:300px;
            padding:0px;
            height:100%;
        }
        .products li{
            display:inline;
            float:left;
            margin:10px;
        }
        .item{
            display:block;
            text-decoration:none;
        }
        .item img{
            border:1px solid #333;
        }
        .item p{
            margin:0;
            font-weight:bold;
            text-align:center;
            color:rgb(0,255,0);
        }
        .cart{
            position:fixed;
            right:0;
            top:0;
            width:300px;
            height:100%;
            background:#ccc;
            padding:0px 10px;
        }
        h1{
            /*background-image: url(images/background.jpg);  background-repeat: repeat-y; background-size: cover;*/
            text-align:center;
            color:rgb(0,0,255);
        }
        h2{
            position:absolute;
            font-size:16px;
            left:10px;
            bottom:20px;
            color:#555;
        }
        .total{
            margin:0;
            text-align:right;
            padding-right:20px;
        }
    </style>

    <!-- 购物车的拖动样式设计 -->
    <script>
        $(function(){
            $('#cartcontent').datagrid({
                singleSelect:true
            });
            $('.item').draggable({
                revert:true,
                proxy:'clone',
                onStartDrag:function(){
                    $(this).draggable('options').cursor = 'not-allowed';
                    $(this).draggable('proxy').css('z-index',10);
                },
                onStopDrag:function(){
                    $(this).draggable('options').cursor='move';
                }
            });
            $('.cart').droppable({
                onDragEnter:function(e,source){
                    $(source).draggable('options').cursor='auto';
                },
                onDragLeave:function(e,source){
                    $(source).draggable('options').cursor='not-allowed';
                },
                onDrop:function(e,source){
                    var name = $(source).find('p:eq(0)').html();
                    var price = $(source).find('p:eq(1)').html();
                    $.post('AddProduct?bookname='+name+'&operator=1');  //异步修改cart session
                    $('#cartcontent').datagrid('reload');    // reload the user data
                }
            });
        });     
	</script>
</head>

<body>
<%@ page language="java" import="bookshop.model.User" import="bookshop.model.SaleMessage" import="com.opensymphony.xwork2.ActionContext" import="java.util.Map"%>
<!-- 顶部链接 -->
<div style="margin:0px 5px 5px 5px;">
    <a href="index.jsp"   style="width:60px">首页</a> 
   
    <!-- 用java代码检查是否已登录 -->
    <%
   		ActionContext actionContext = ActionContext.getContext();
    	Map websession = actionContext.getSession();
    	User currentUser=(User) websession.get("user");
    	String imgurl = (String) websession.get("imgurl");
    	if(currentUser==null){
    		out.println("<a href=\"login.jsp\"   style=\"width:60px\">登录</a>");
    		out.println("<a href=\"register.jsp\"   style=\"width:60px\">注册</a>");
    	}
    	else{
    		out.println("欢迎用户"+currentUser.getUsername()+"<img alt=\"\" src=\""+imgurl+"\" width=\"20px\" height=\"20px\">登录");
    		out.println("<a href=\"gotocart.action\" style=\"width:60px\">购物车</a>");
    		out.println("<a href=\"GetOrders.jsp\"   style=\"width:60px\">我的订单</a>");
    		out.println("<a href=\"Information.action\"   style=\"width:60px\">个人信息</a>");
    		out.println("<a href=\"logout.action\" style=\"width:60px\">注销</a>");
    	}
    %>
</div>
<!-- 大图 -->
<div>
    <img src="resources/images/background.jpg" width="1050" height="280"/>
    <h1>Welcome to Rayne's Bookstore</h1>
    <%SaleMessage saleMessage = (SaleMessage) websession.get("salemessage");
    if(saleMessage!=null){
      out.println("<p>全场满"+saleMessage.getBasic()+"减"+saleMessage.getMinus()+"</p>");
    }
    %>
</div>
<input align="center" class="easyui-searchbox" data-options="prompt:'输入书名/作者/出版社',menu:'#mm',searcher:doSearch" style="width:400px;height:30px">
<!-- 搜索选项 -->
<div id="mm">
    <div data-options="name:'all',iconCls:'icon-ok'">All</div>
    <div data-options="name:'literature'">Literature</div>
    <div data-options="name:'poem'">Poems</div>
    <div data-options="name:'journal'">Journals</div>
</div>
<!-- 用js接受Servlet发送的json字符串实现搜索书单 -->
<script>
//用ajax异步申请书单
var dataObj;
$.ajax({
    type: "GET",//请求方式
    url: "GetBooklistData.action",//地址，就是action请求路径
    data: "json",//数据类型text xml json  script  jsonp
    success: function(msg) {//返回的参数就是 action里面所有的有get和set方法的参数
        dataObj = eval("(" + msg + ")");
        var show = '';
        for (var i = 0; i < dataObj.length; i++) {
                show+='<li class="item"> <img src=\"'+dataObj[i].img +'\"width="100" height="100"/> <div> <p>'+
                        dataObj[i].bookname+'</p> <p>Price:$'+dataObj[i].price+'</p> </div> </li>';
        }

        document.getElementById("sth").innerHTML = show;
        
        $('.item').draggable({
            revert: true,
            proxy: 'clone',
            onStartDrag: function () {
                $(this).draggable('options').cursor = 'not-allowed';
                $(this).draggable('proxy').css('z-index', 10);
            },
            onStopDrag: function () {
                $(this).draggable('options').cursor = 'move';
            }
        });
    }
});
	//搜索书单，但在draggble方面与上面存在冗余
	function doSearch(value,name){
        var show = '';
        for (var i = 0; i < dataObj.length; i++) {
            if((dataObj[i].bookname.indexOf(value)>=0)||(dataObj[i].author.indexOf(value)>=0)||(dataObj[i].press.indexOf(value)>=0)) {
                show+='<li class="item"> <img src=\"'+dataObj[i].img +'\"width="100" height="100"/> <div> <p>'+
                       dataObj[i].bookname+'</p> <p>Price:$'+dataObj[i].price+'</p> </div> </li>';
            }
        }

        document.getElementById("sth").innerHTML = show;
            $('.item').draggable({
                 revert: true,
                 proxy: 'clone',
                 onStartDrag: function () {
                        $(this).draggable('options').cursor = 'not-allowed';
                        $(this).draggable('proxy').css('z-index', 10);
                 },
                 onStopDrag: function () {
                    $(this).draggable('options').cursor = 'move';
                 }
            });
    }
</script>
<!-- 用unlist来排列书单 -->
<ul class="products" id="sth">
	<%-- <%@ page language="java" import="java.util.List" import="Sample.Entity.Booklist"%>
	<!-- 用嵌入java代码的方式动态生成商品 -->
	<jsp:useBean id="books" scope="page" class="Sample.Servlet.GetBooklist" />
	<%
		List booklist = books.processRequest();
		for (int i = 0; i < booklist.size(); i++) {
			Booklist theBook = (Booklist) booklist.get(i);
			out.println("<li class=\"item\"> <img src=\"" + theBook.getImage() + "\"width=\"100\" height=\"100\"/> <div> <p>"
				+ theBook.getBookname() + "</p> <p>Price:$" + theBook.getPrice() + "</p> </div> </li>");
		}
	%> --%>
</ul>
<!-- 购物车界面 -->
<div class="cart">
    <h1>Shopping Cart</h1>
    <table id="cartcontent" style="width:300px;height:auto;" url="getcartdata">
        <thead>
        <tr>
            <th field="name" width=140>Name</th>
            <th field="quantity" width=60 align="right">Quantity</th>
            <th field="price" width=60 align="right">Price</th>
        </tr>
        </thead>
    </table>
    <script>
        $("#cartcontent").datagrid({
            onClickRow : function(index, row){
                $.post('AddProduct?bookname='+row.name+'&operator=-');
                $('#cartcontent').datagrid('reload');    // reload the user data
            }
        });
    </script>
    
    <div style="margin:20px 0">
        <a href="gotocart.action" class="easyui-linkbutton c5" style="width:120px">GoPay</a>
    </div>
</div>

</body>
</html>