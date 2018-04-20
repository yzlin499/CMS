<?php
require '../pdoLink.php';
$pdo=linkSQLS();
if($pdo==false){
	echo "数据库翻车了";
	exit;
}
$temp=$pdo->query("exec up_BindWehcatID N'".$_POST['loginID']."',N'".$_POST['password']."',N'".$_POST['wechatid']."'");
if($temp==false){
	echo "数据库翻车啦";
	exit;
}
$result = $temp->fetch()[0];
if($result=="1"){
	echo "绑定成功";
	exit;
}else{
	echo $result;
}
?>