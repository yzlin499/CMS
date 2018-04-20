$(document).ready(function(){
	if((GetQueryString("validation")==null)||(!(/^[0-9]{8}$/.test(GetQueryString("validation"))))){
		$("#main").hide();
		$("#uph").html("");
		$("#mess").html("网页错误");
		return;
	}
	// 
	$("#submit").click(function(){
		//判断是否为空
		if($("#loginID").val()==""){
			$("#mess").html("用户名不能为空");
			return;
		}if($("#password").val()==""){
			$("#mess").html("新密码不能为空");
			return;
		}if($("#rePassword").val()==""){
			$("#mess").html("请再次输入新密码");
			return;
		}
		
		if(!(/^[a-zA-Z0-9]{6,16}$/.test($("#password").val()))){ 
			$("#mess").html("密码格式错误，请输入英文大小写与数字，长度为6~16位"); 
			return; 
		}
		if($("#password").val()!=$("#rePassword").val()){
			$("#mess").html("请确认两次密码相同");
			return;
		}
		//////////////////
		$.ajax({
			type:"POST",
			url:"resetPassWord.php",
			data:{
				"loginID":$('#loginID').val(),
				"password":hex_md5($('#password').val()),
				"validation":GetQueryString("validation")
			},
			dataType:"html",
			success:function(data){
				if(data=="2"){
					$("#main").hide();
					$("#uph").html("");
					$("#mess").html("该链接过期，请重新申请找回密码");
				}else if(data=="1"){
					$("#mess").html("用户名错误或链接已失效");
				}else if(data=="0"){
					$("#main").hide();
					$("#uph").html("");
					$("#mess").html("重置成功<br>请重新绑定微信账号");
				}else{
					$("#main").hide();
					$("#uph").html("");
					$("#mess").html(data);
					
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
