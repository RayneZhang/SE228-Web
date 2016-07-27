<?php
/**
 * Created by PhpStorm.
 * User: dell
 * Date: 2016/3/26
 * Time: 9:38
 */
$link = mysqli_connect('localhost','root','fish617');
if(!$link){
    die('连接失败!');
}

mysqli_select_db($link,'bookstore') or die("选择数据库失败!");
