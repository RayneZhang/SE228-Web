<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Your Information</title>
    <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="themes/icon.css">
    <link rel="stylesheet" type="text/css" href="demo/demo.css">
    <script type="text/javascript" src="demo/jquery.min.js"></script>
    <script type="text/javascript" src="demo/jquery.easyui.min.js"></script>
    <title>添加书籍</title>  
        <script type="text/javascript">  
            //打开上传页面  
            function openUpload(){  
            	$('#dlg').dialog('open').dialog('center').dialog('setTitle','New Header');
            }
            //修改个人信息
            function editUser(){
                  $('#dlg1').dialog('open').dialog('center').dialog('setTitle','Edit User');
            }
            function saveUser(){
                $('#fm').form('submit',{
                    url: 'usersaveuser',
                    onSubmit: function(){
                        return $(this).form('validate');
                        $('#fm').form('load',{
                        	passwd: '<s:property value="username"/>',
                        	phone: '<s:property value="phone"/>',
                        	email: '<s:property value="email"/>'
                        });
                    },
                    success: function(){
                    	location.reload();
                        $('#dlg1').dialog('close');        // close the dialog
                    }
                });
            }
            
        </script>
</head>
<body>
<h1>您的个人信息</h1>  
<p>您的头像：  
	<label>  
          <!-- <input type="hidden" id="photo_id" name="photo" value="images/noimg.png"> -->
          <input type="button" onclick="openUpload()" value="上传头像"/><br/>  
          <img id="img_id" alt="" src="resources/images/header.png" width="200px" height="200px">  
    </label>
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons">
    	<form id="form1" action="fileUpload.action" method="post" enctype="multipart/form-data">  
            	<div>注:图片大小最大不能超过3M!</div>  
                	文件：<div><input type="file" name="image"/></div>  
            	<div><input type="submit" value="上传"/></div>  
    	</form>
    </div>
</p>
<p>您的用户名：<s:property value="username"/></p>
<p>您的密码：<s:property value="passwd"/></p>
<p>您的手机：<s:property value="phone"/></p>
<p>您的邮箱：<s:property value="email"/></p>
<button type="button" onclick="editUser()">修改个人信息</button>
			<div id="dlg1" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons">
                <div class="ftitle">User Information</div>
                <form id="fm" method="post" action="login" novalidate>
                    <div class="fitem">
                        <label>Passwd:</label>
                        <input name="passwd" class="easyui-textbox" required="true">
                    </div>
                    <div class="fitem">
                        <label>User Phone:</label>
                        <input name="phone" class="easyui-textbox">
                    </div>
                    <div class="fitem">
                        <label>Email:</label>
                        <input name="email" class="easyui-textbox" validType="email">
                    </div>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')" style="width:90px">Cancel</a>
            </div>
</body>
</html>