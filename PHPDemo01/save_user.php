<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/28
 * Time: 10:40
 */
include 'connect_user.php';
//获取输入的信息
$username = $_POST['username'];
$userpasswd = $_POST['userpasswd'];
$userphone = $_POST['userphone'];
$email = $_POST['email'];
$userflag=$_POST['userflag'];

$insert = "INSERT INTO user(username,userpasswd,userphone,email,userflag)
           VALUES('$username','$userpasswd','$userphone','$email','$userflag')";
$result1 = mysqli_query($link,$insert);

$rs = mysqli_query($link,'select * from user');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);