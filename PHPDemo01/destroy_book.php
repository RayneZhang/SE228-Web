<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/30
 * Time: 20:53
 */
include 'connect_user.php';
//获取输入的信息
$bookid=$_POST['bookid'];

$remove=mysqli_query($link,"DELETE FROM `bookstore`.`booklist` WHERE `bookid`='$bookid'");

$rs = mysqli_query($link,'select * from booklist');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);