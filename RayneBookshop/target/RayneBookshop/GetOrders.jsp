<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Your Orders</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="themes/color.css">
    <link rel="stylesheet" type="text/css" href="demo/demo.css">
    <script type="text/javascript" src="demo/jquery.min.js"></script>
    <script type="text/javascript" src="demo/jquery.easyui.min.js"></script>
</head>
<body>
<script>
        $.ajax({
            type: "GET",//请求方式
            url: "getorders.action",//地址，就是action请求路径
            data: "json",//数据类型text xml json script jsonp
            success: function(msg) {//返回的参数就是 action里面所有的有get和set方法的参数
                var dataObj = eval("(" + msg + ")");
                var show = '';
                if(dataObj.length == 0){
                	show += "您尚无相关订单"; 
                }
                else{
                	var temDay = dataObj[0].date.date;
                	var temMonth = dataObj[0].date.month;
                	var temYear = dataObj[0].date.year;
                	show += "<h1>这是您的历史订单</h1>"
                	show += "<p>*************"+(temYear+1900)+"年"+(temMonth+1)+"月"+temDay+"日*************";
                	for (var i = 0; i < dataObj.length; i++) {
                		if(temDay==dataObj[i].date.date && temMonth == dataObj[i].date.month && temYear == dataObj[i].date.year)
                        {
                			show+='<li class="item"> <img src=\"'+dataObj[i].img 
                        	+'\"width="100" height="100"/> <div> <p>'+dataObj[i].bookname
                        	+'</p> <p>Price:$'+dataObj[i].price
                        	+'</p> <p>Amount:'+dataObj[i].amount
                        	+'</p> </div> </li>';
                        }
                		else{
                			temDay = dataObj[i].date.date;
                        	temMonth = dataObj[i].date.month;
                        	temYear = dataObj[i].date.year;
                        	show += "<p>*************"+(temYear+1900)+"年"+(temMonth+1)+"月"+temDay+"日*************";
                        	show+='<li class="item"> <img src=\"'+dataObj[i].img 
                        		+'\"width="100" height="100"/> <div> <p>'+dataObj[i].bookname
                        		+'</p> <p>Price:$'+dataObj[i].price
                        		+'</p> <p>Amount:'+dataObj[i].amount
                        		+'</p> </div> </li>';
                		}
                	}
                }
	
                document.getElementById("sth").innerHTML = show;
            },
            error: function(){alert("fail");}
        });
</script>
<ul class="products" id="sth">
</ul>
</body>
</html>