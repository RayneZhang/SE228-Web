<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Ray's Bookstore - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="demo/demo.css">
    <script type="text/javascript" src="demo/jquery.min.js"></script>
    <script type="text/javascript" src="demo/jquery.easyui.min.js"></script>
</head>
<body>
<h2>Welcome to Rayne's Bookstore</h2>
<p>Please login first.</p>
<div style="margin:20px 0;"></div>
<div class="easyui-panel" title="Register" style="width:400px">
    <div style="padding:10px 60px 20px 60px">
        <form method="post" action="RegisterServlet">
            		<div class="fitem">
                    <label>User Name: </label>
                    <input name="username" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>User Passwd:</label>
                        <input name="userpasswd" class="easyui-textbox" type="text" required="true">
                    </div>
                    <div class="fitem">
                        <label>User Phone:</label>
                        <input name="userphone" class="easyui-textbox">
                    </div>
                    <div class="fitem">
                        <label>Email:</label>
                        <input name="email" class="easyui-textbox" validType="email">
                    </div>
        
        <P><INPUT TYPE=SUBMIT value="注册">
        <button type="button" onclick="window.location='index.jsp'">算了，返回主页</button>
        </form>
    </div>
</div>
</body>
</html>