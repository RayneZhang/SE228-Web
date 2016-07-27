<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/30
 * Time: 9:26
 */
include 'connect_user.php';
//获取输入的信息
$id=$_POST['id'];

$remove=mysqli_query($link,"DELETE FROM `bookstore`.`user` WHERE `id`='$id'");

$rs = mysqli_query($link,'select * from user');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);