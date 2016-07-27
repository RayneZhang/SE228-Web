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
<h1> Rayne's Bookstore 管理平台</h1>
<div class="easyui-layout" style="width:100%;height:1200px;">
    <div region="west" split="true" title="Navigator" style="width:150px;">
        <p style="padding:5px;margin:0;">信息面板:</p>
        <ul>
            <li><a href="index.jsp" >返回首页</a></li>
            <li><a href="LogoutServlet" onclick="showcontent('注销登录')">注销登录</a></li>
            <li><a href="javascript:void(0)" onclick="showcontent('敬请期待')">敬请期待</a></li>
        </ul>
    </div>
    <div region="center" class="easyui-tabs" style="width:100%;height:1000px;">
        <div title="管理用户" style="padding:10px;">
            <table id="dg" title="My Users" class="easyui-datagrid" style="width:800px;height:500px"
                   url="GetUserData"
                   toolbar="#toolbar"
                   rownumbers="true" fitColumns="true" singleSelect="true">
                <thead>
                <tr>
                    <th field="id" width="300px">User ID</th>
                    <th field="username" width="300px">User Name</th>
                    <th field="userpasswd" width="300px">User Passwd</th>
                    <th field="userphone" width="300px">User phone</th>
                    <th field="email" width="300px">Email</th>
                    <th field="userflag" width="300px">User Flag</th>
                </tr>
                </thead>
                <tbody>
                
                </tbody>
            </table>
            <div id="toolbar">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加用户</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除用户</a>
            </div>
            <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons">
                <div class="ftitle">User Information</div>
                <form id="fm" method="post" novalidate>
                    <div class="fitem">
                        <label>User Name: </label>
                        <input name="username" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>User Passwd:</label>
                        <input name="userpasswd" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>User Phone:</label>
                        <input name="userphone" class="easyui-textbox">
                    </div>
                    <div class="fitem">
                        <label>Email:</label>
                        <input name="email" class="easyui-textbox" validType="email">
                    </div>
                    <div class="fitem">
                        <label>User Flag:</label>
                        <input name="userflag" class="easyui-textbox" data-options="prompt:'请输入admin/user'" required="true">
                    </div>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
            </div>
            <script type="text/javascript">
                var url;
                function newUser(){
                    $('#dlg').dialog('open').dialog('center').dialog('setTitle','New User');
                    $('#fm').form('clear');
                    url = 'SaveUserServlet?id=0';
                }
                function editUser(){
                    var row = $('#dg').datagrid('getSelected');
                    if (row){
                        $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
                        $('#fm').form('load',row);
                        url = 'SaveUserServlet?id='+row.bookid;
                    }
                }
                function saveUser(){
                    $('#fm').form('submit',{
                        url: url,
                        onSubmit: function(){
                            return $(this).form('validate');
                        },
                        success: function(){
                            $('#dg').datagrid('reload');    // reload the user data
                            $('#dlg').dialog('close');        // close the dialog
                        }
                    });
                }
                function destroyUser(){
                    var row = $('#dg').datagrid('getSelected');
                    if (row){
                        $.messager.confirm('Confirm','你确定要删除这本书吗?',function(){                      
                              $.post('DestroyUser?id='+row.id);
                              $('#dg').datagrid('reload');    // reload the user data
                        });
                    }
                }
            </script>
        </div>
        
        <div title="管理书库" style="padding:10px;">
            <table id="dg1" title="My Booklists" class="easyui-datagrid" style="width:800px;height:500px"
                   url="GetBooklistData"
                   toolbar="#toolbar1"
                   rownumbers="true" fitColumns="true" singleSelect="true">
                <thead>
                <tr>
                    <th field="bookid" width="300px">Book ID</th>
                    <th field="bookname" width="300px">Book Name</th>
                    <th field="author" width="300px">Author</th>
                    <th field="category" width="300px">Category</th>
                    <th field="press" width="150px">Press</th>
                    <th field="price" width="150px">Price</th>
                    <th field="img" width="300px">Image URL</th>
                </tr>
                </thead>
                <tbody>
                <%@ page language="java" import="java.util.List" import="Sample.Entity.Booklist"%>
                <!-- 本来用嵌入java代码的方式动态生成商品，为了刷新采用url -->
				<jsp:useBean id="books" scope="page" class="Sample.Servlet.GetBooklist" />
				<%
					/*List booklist = books.processRequest();
					for (int i = 0; i < booklist.size(); i++) {
						Booklist theBook = (Booklist) booklist.get(i);
						out.println("<tr> <td>" + theBook.getId() + "</td><td>"+ theBook.getBookname() 
						+ "</td><td>"+ theBook.getAuthor()+ "</td><td>"+ theBook.getPress()
						+ "</td><td>"+ theBook.getPrice()+ "</td><td>"+ theBook.getImage()+"</td> </tr>");
					}*/
				%>
                </tbody>
            </table>
            <div id="toolbar1">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBook()">添加图书</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBook()">编辑图书信息</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyBook()">删除图书</a>
            </div>
            <div id="dlg1" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons1">
                <div class="ftitle">Book Information</div>
                <form id="fm1" method="post" novalidate>
                    <div class="fitem">
                        <label>Book Name: </label>
                        <input name="bookname" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>Author:</label>
                        <input name="author" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>Press:</label>
                        <input name="press" class="easyui-textbox">
                    </div>
                    <div class="fitem">
                        <label>Price:</label>
                        <input name="price" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>Image URL:</label>
                        <input name="img" class="easyui-textbox">
                    </div>
                </form>
            </div>
            <div id="dlg-buttons1">
                <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBook()" style="width:90px">保存</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')" style="width:90px">Cancel</a>
            </div>
            <script type="text/javascript">
                var url;
                function newBook(){
                    $('#dlg1').dialog('open').dialog('center').dialog('setTitle','New Book');
                    $('#fm1').form('clear');
                    url = 'Savebook?id=0';
                }
                function editBook(){
                    var row = $('#dg1').datagrid('getSelected');
                    if (row){
                        $('#dlg1').dialog('open').dialog('center').dialog('setTitle','Edit Book');
                        $('#fm1').form('load',row);
                        url = 'Savebook?id='+row.bookid;
                    }
                }
                function saveBook(){
                	alert(1);
                    $('#fm1').form('submit',{
                        url: url,
                        onSubmit: function(){
                            return $(this).form('validate');
                        },
                        success: function(){
                            $('#dg1').datagrid('reload');    // reload the user data
                            $('#dlg1').dialog('close');        // close the dialog
                        }
                    });
                }
                function destroyBook(){
                    var row = $('#dg1').datagrid('getSelected');
                    if (row){
                        $.messager.confirm('Confirm','你确定要删除这本书吗?',function(){                      
                              $.post('DestroyBook?id='+row.bookid);
                              $('#dg1').datagrid('reload');    // reload the user data
                        });
                    }
                }
            </script>
        </div>
        
    	<div title="销售统计" style="padding:10px;">
            <table id="dg2" title="User's Orders" class="easyui-datagrid" style="width:800px;height:400px"
                   url="GetOrdersData"
                   
                   rownumbers="true" fitColumns="true" singleSelect="true">
                <thead>
                <tr>
                    <th data-options="field:'id',width:300" >Order ID</th>
                    <th field="user_id" width="300px">User id</th>
                    <th field="book_id" width="300px">Book id</th>
                    <th field="amount" width="300px">Amount</th>
                    <th field="order_date" width="300px">Date</th>
                </tr>
                </thead>
                <tbody>
                
                </tbody>
            </table>
            
            <button type="button" onclick="window.location='StatsByDay'">按日统计</button>
            <button type="button" onclick="window.location='StatsByMonth'">按月统计</button>
            <button type="button" onclick="window.location='StatsByYear'">按年统计</button>
            <button type="button" onclick="window.location='StatsByCategory'">按种类统计</button>
        </div>
    </div>
</div>
</body>
</html>