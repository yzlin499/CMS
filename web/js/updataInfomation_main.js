$(document).ready(function(){
	
	
	
	$("#submit").click(function(){
		//判断是否为空
		if($("#loginID").val()==""){
			$("#mess").html("用户名不能为空");
			return;
		}if($("#password").val()==""){
			$("#mess").html("密码不能为空");
			return;
		}if($("#mail").val()==""){
			$("#mess").html("邮箱不能为空");
			return;
		}if($("#rePassword").val()==""){
			$("#mess").html("请再次输入密码");
			return;
		}if($("#name").val()==""){
			$("#mess").html("姓名不能为空");
			return;
		}if($("#sex").val()==""){
			$("#mess").html("性别不能为空");
			return;
		}if($("#phone").val()==""){
			$("#mess").html("手机不能为空");
			return;
		}if($("#theClass").val()==""){
			$("#mess").html("年级专业班级不能为空");
			return;
		}
		//判断是否符合要求
		if(!(/^[a-zA-Z0-9]{3,16}$/.test($("#loginID").val()))){ 
			$("#mess").html("账号格式错误，请输入英文大小写与数字，长度为3~16位"); 
			return; 
		}
		if(!(/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/.test($("#mail").val()))){ 
			$("#mess").html("邮箱格式错误"); 
			return; 
		}
		if(!(/^[a-zA-Z0-9]{6,16}$/.test($("#password").val()))){ 
			$("#mess").html("密码格式错误，请输入英文大小写与数字，长度为6~16位"); 
			return; 
		}
		if($("#password").val()!=$("#rePassword").val()){
			$("#mess").html("两个密码不相同"); 
			return;
		}
		if(!(/^[\u4E00-\u9FA5]{2,5}$/.test($("#name").val()))){
			$("#mess").html("姓名格式错误，请输入中文，长度为2~5位"); 
			return;
		}
		if(!(/^(man|woman)$/.test($("#sex").val()))){
			$("#mess").html("开控制台好玩么"); 
			return;
		}
		if(!(/^1[34578]\d{9}$/.test($("#phone").val()))){ 
			$("#mess").html("手机格式错误"); 
			return; 
		}
		
		//////////////////
		$.ajax({
			type:"POST",
			url:"regisiter.php",
			data:{
				"loginID":$('#loginID').val(),
				"mail":$('#mail').val(),
				"password":hex_md5($('#password').val()),
				"name":$('#name').val(),
				"sex":$('#sex').val(),
				"phone":$('#phone').val(),
				"theClass":$('#theClass').val()
			},
			dataType:"html",
			success:function(data){
				//此处需要重构，如果绑定成功需要将main部分重新覆盖
				if(data=="1"){
					$("#main").hide();
					$("#uph1").html("");
					$("#uph2").html("");
					$('#submit').hide();
					$("#mess").html("注册成功");
					
				}else if(data=="0"){
					alert("注册失败.请正常注册");
				}else if(data=="2"){
					$("#mess").html("注册失败,用户名已经存在");
				}else{
					alert("注册失败."+data);
				}
			}
		});
		return false;
	});
});

function GetQueryString(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}

/**
	* 从url中获取参数
	* @return array 参数数组
	*/
function urlGet(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)", "i");
	arr = window.location.search.substr(1).match(reg);
	if(arr) return unescape(arr[2]);
};

function toBind(){
	var parm=urlGet('wechatid');
	if ("undefined" != typeof parm){
		if($('#loginID').val()==""){
			window.open("./binding.html?wechatid="+parm);
		}else{
			window.open("./binding.html?wechatid="+parm+"&lid="+$('#loginID').val());
		}
	}
}

if ("undefined" == typeof urlGet('wechatid')){
    $('#ToBind').hide();	
}