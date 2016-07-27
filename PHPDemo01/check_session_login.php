<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/28
 * Time: 17:59
 */
include 'connect_user.php';
//获取输入的信息
$username = $_POST['username'];
$passcode = $_POST['passcode'];

$query = mysqli_query($link,"select username,userpasswd,userflag from user where username='$username'and userpasswd='$passcode'")
or die("SQL error");

//判断用户以及密码
if($row = mysqli_fetch_array($query))
{   session_start();

    $_SESSION['username'] = $username;
    if(isset($_SESSION['username']))
    {
        setcookie("logged","1");
        if($row['userflag']=='user') {
            echo "欢迎用户" . $_SESSION['username'] . "登陆";
            $url = "newproduct.html";
            echo "<script language='javascript' type='text/javascript'>";
            echo "window.location.href='$url'";
            echo "</script>";
        }
        if($row['userflag']=='admin') {
            echo "欢迎管理员" . $_SESSION['username'] . "登陆";
            $url = "newpanel.html";
            echo "<script language='javascript' type='text/javascript'>";
            echo "window.location.href='$url'";
            echo "</script>";
        }
    }
    else
    {
        echo "您没有权限访问此页面";
    }

}else{
    echo "用户名或密码错误，请重新登陆";
    $url = "alogin.html";
    echo "<script language='javascript' type='text/javascript'>";
    echo "window.location.href='$url'";
    echo "</script>";
}