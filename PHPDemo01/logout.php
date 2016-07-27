<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/30
 * Time: 1:40
 */
session_start();
unset($_SESSION['username']);
unset($_SESSION['passcode']);
//unset($_SESSION['userflag']);
setcookie("logged");
echo "注销成功...";
$url = "newproduct.html";
echo "<script language='javascript' type='text/javascript'>";
echo "window.location.href='$url'";
echo "</script>";
?>