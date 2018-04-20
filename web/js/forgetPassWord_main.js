$(document).ready(function(){
	$("#submit").click(function(){
		$("#submit").attr("value","发送中···");
		//判断是否为空
		if($("#loginID").val()==""){
			$("#mess").html("用户名不能为空");
			return;
		}
		//////////////////
		$.ajax({
			type:"POST",
			url:"forgetPassWord.php",
			data:{
				"loginID":$('#loginID').val(),
				"mail":$('#mail').val()
			},
			dataType:"html",
			success:function(data){
				if(data=="-1"){
					$("#main").hide();
					$("#uph").html("");
					$("#mess").html("后台翻车，请等待工作人员修复");
				}else if(data=="0"){
					$("#mess").html("用户名与邮箱不正确");
					$("#submit").attr("value","找回密码");
				}else{
					$("#main").hide();
					$("#uph").html("");
					$("#mess").html("3秒后自动跳转到邮箱");
					setTimeout(function(){
						$("#mess").html("2秒后自动跳转到邮箱");
						setTimeout(function(){
							$("#mess").html("1秒后自动跳转到邮箱");
							setTimeout(function(){
								$("#mess").html("自动跳转到邮箱");
								window.open("http://"+data);
							},1000);
						},1000);
					},1000);
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
