<?php
//加载各类头
require '../pdoLink.php';
$pdo=linkSQLS();
if($pdo==false){
	echo "数据库翻车了";
	exit;
}


//判断信息是否合法
if(!preg_match("/^[a-zA-Z0-9]{3,16}$/",$_POST['loginID'])){
	echo "0";
	exit;
}
if($_POST['loginID']=="NULL"){
	echo "0";
	exit;
}
if(!preg_match("/^1[34578]\\d{9}$/",$_POST['phone'])){
	echo "0";
	exit;
}


// 注册
$temp=$pdo->query("exec up_Regisiter '".$_POST['loginID']."','".$_POST['password']."',N'".$_POST['name']."','".$_POST['phone']."','".$_POST['theClass']."','".($_POST['sex']=="man"?"男":"女")."','".$_POST['mail']."'");

if($temp==false){
	echo "注册失败，数据库翻了车";
	exit;
}
$result = $temp->fetch()[0];
if($result=="1"){
	echo "1";
}else if($result=="0"){
	echo "2";
}

?>