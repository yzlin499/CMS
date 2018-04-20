$(document).ready(function(){
    if (GetQueryString("wechatid") == null) {
		$("#main").html("");
        $("#uph").html("");
        $("#mess").html('请使用微信打开此网页');
	} else {
		//$.ajax({
		//	type:"POST",
		//	url:"CheckWechatBind.php",
		//	data:{
		//		"wechatid":'14546'
		//	},
		//	dataType:"html",
		//	success:function(data){
		//		if(data=="0"){
		//			return false;
		//		}else{
		//			$("#main").html("");
		//			$("#uph").html("");
  //                  $("#mess").html(data);
                
		//		}
		//	}
		//});
        //alert("dsnajkd");
        $.post("CheckWechatBind.php",
			{"wechatid": GetQueryString("wechatid") },
			function (data) {
				if (data == "0") {
				   return false;
				} else {
				   $("#main").html("");
				   $("#uph").html("");
				   $("#mess").html(data);
				}
			}
		);
	}
	
	if (GetQueryString("lid")!= null) {
		$("#loginID").attr("value",GetQueryString("lid"));
	}
	
	$("#fgp").click(function(){
		window.open("./forgetPassWord.html");
	});
	
	$("#submit").click(function(){
		if($("#loginID").val()==""){
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
				"loginID":$('#loginID').val(),
				"password":hex_md5($('#password').val()),
				"wechatid":GetQueryString("wechatid")
			},
			dataType:"html",
			success:function(data){
				if(data=="0"){
					$("#mess").html("用户名或密码不正确");
				}else{
					$("#main").html("");
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
