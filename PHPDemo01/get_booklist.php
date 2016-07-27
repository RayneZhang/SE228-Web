<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/28
 * Time: 9:58
 */
include 'connect_user.php';
$rs = mysqli_query($link,'select * from booklist');
$result = array();
while($row = mysqli_fetch_object($rs)){
    array_push($result, $row);
}

echo json_encode($result);