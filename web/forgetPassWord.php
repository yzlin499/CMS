<?php
require '../pdoLink.php';
require '../tools/mail.php';
$pdo=linkSQLS();
if($pdo==false){
	echo "-1";
	exit;
}
$temp=$pdo->query("exec up_GetCodeToResetPWD '".$_POST['loginID']."','".$_POST['mail']."'");
if($temp==false){
	echo "-1";
	exit;
}
$result = $temp->fetch()[0];
if($result=="0"){
	echo "0";
	exit;
}else{
	$url="http://".$_SERVER['HTTP_HOST']."/regisiter/resetPassWord.html?validation=".$result;
	
	$text='<blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><font size="4">Hi</font></blockquote><div><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4">亲爱的'.$_POST['loginID'].'，你好！</font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4">忘记了助理系统的密码么？别紧张。</font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4">请点击以下链接，该链接有效期为两个小时。</font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4"><a href="'.$url.'">'.$url.'</AAA></font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4"><br></font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4">如果这不是您的邮件请忽略，很抱歉打扰您，请原谅。</font></includetail></blockquote><blockquote formatblock="1" style="margin: 0.8em 0px 0.8em 2em; padding: 0px 0px 0px 0.7em; border-left: 2px solid rgb(221, 221, 221);"><includetail><font size="4">请勿回复</font></includetail></blockquote></div>';
	
	sendMail($_POST['mail'],"北理实验室助理系统密码重置",$text,$_POST['loginID']);
	$result=preg_split("/@/",$_POST['mail']);
	$result=$result[1];
	$result="mail.".$result;
	echo $result;
}
?>