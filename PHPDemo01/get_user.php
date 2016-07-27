<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/22
 * Time: 19:46
 */
 include 'connect_user.php';
$rs = mysqli_query($link,'select * from user');
$result = array();
while($row = mysqli_fetch_object($rs)){
   array_push($result, $row);
}

echo json_encode($result);