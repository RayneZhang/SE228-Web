<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/30
 * Time: 0:16
 */
include 'connect_user.php';
//获取输入的信息
$username = $_POST['username'];
$userpasswd = $_POST['userpasswd'];
$userphone = $_POST['userphone'];
$email = $_POST['email'];
$userflag=$_POST['userflag'];
$id=$_GET['id'];

$update = mysqli_query($link,"UPDATE `bookstore`.`user` SET `username`='$username',`userpasswd`='$userpasswd',`userphone`='$userphone',`email`='$email',`userflag`='$userflag'WHERE `id`='$id'");
$rs = mysqli_query($link,'select * from user');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);