<?php
//加载各类头
require '../pdoLink.php';

$wechatID=isset($_POST['wechatid'])?$_POST['wechatid']:'';
if ($wechatID==''){
   echo "该网页只能通过微信打开";
   exit;
} else {
	$pdo=linkSQLS();
	if($pdo==false){
	   echo "数据库翻车了";
	   exit;
	}
	$temp=$pdo->query("EXEC up_CheckWechatBind '".$wechatID."'");
	if($temp==false){
	   echo "数据库翻了车，耐心等待技术人员维修";
	   exit;
	}
	$temp=$temp->fetch()[0];
	if($temp==NULL){
	   echo "0";
	}else{
	   echo "此微信账号已经被用户[".$temp."]绑定<br>如果需要解除绑定请在公众号回复2";
	}
}
?>