<?php
require '../pdoLink.php';
$pdo=linkSQLS();
if($pdo==false){
	echo "数据库翻车了";
	exit;
}
$temp=$pdo->query("exec up_ResetPWD '".$_POST['loginID']."','".$_POST['validation']."','".$_POST['password']."'");
if($temp==false){
	echo "数据库翻车了";
	exit;
}
$result = $temp->fetch()[0];
echo $result;
?>