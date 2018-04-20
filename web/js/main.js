$(document).ready(function(){
	$("#submit").click(function(){
		if($("#name").val()==""){
			if($("#password").val()==""){
				$("#mess").html("用户名与密码不能为空");
			}else{
				$("#mess").html("用户名不能为空");
			}
			return;
		}else if($("#password").val()==""){
			$("#mess").html("密码不能为空");
		}else{
			$("#mess").html("");
		}
		$.ajax({
			type:"POST",
			url:"binding.php",
			data:{
				"username":$('#name').val(),
				"password":hex_md5($('#password').val()),
				"weichatid":GetQueryString("weichatid")
			},
			dataType:"html",
			success:function(data){
				//此处需要重构，如果绑定成功需要将main部分重新覆盖
				$("#main").html("");
				$("#uph").html("");
				$("#mess").html(data);
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
