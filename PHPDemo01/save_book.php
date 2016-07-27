<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/30
 * Time: 20:39
 */
include 'connect_user.php';
//获取输入的信息
$bookname = $_POST['bookname'];
$author = $_POST['author'];
$press = $_POST['press'];
$price = $_POST['price'];
$img=$_POST['img'];

$insert = "INSERT INTO booklist(bookname,author,press,price,img)
           VALUES('$bookname','$author','$press','$price','$img')";
$result1 = mysqli_query($link,$insert);

$rs = mysqli_query($link,'select * from booklist');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);