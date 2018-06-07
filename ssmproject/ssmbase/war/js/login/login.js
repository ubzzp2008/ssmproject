var cookieName_remember = "LOGIN_REMEMBER",
cookieName_username="LOGIN_USER_NAME",
cookieName_password="LOGIN_PASSWORD";

$(document).ready(function() {
	getUserFormCookie();
	// 点击登录
	$('#but_login').click(function(e) {
		submit();
	});
	//update-begin--Author:zhangguoming  Date:20140226 for：添加验证码
	$('#randCodeImage').click(function(){
	    reloadRandCodeImage();
	});
	//回车登录
	
	$(document).keyup(function(e){
		var len = $(".layui-layer-shade").length;
		if(e.keyCode == 13&&len>0) {
			layer.close($(".layui-layer-shade").attr("times"));
			$("input[reg]").each(function() {
				if ($("#" + this.id).val() == "") {
					this.focus();
					return false;
				}
			});
		}
		if(e.keyCode == 13&&len==0) {
			submit();
		}
	});
});

/**
 * 刷新验证码
 */
function reloadRandCodeImage() {
    var date = new Date();
    var img = document.getElementById("randCodeImage");
    img.src='randCodeImage?a=' + date.getTime();
}

//表单提交
function submit(){
	var submit = true;
	$("input[reg]").each(function() {
		if ($("#" + this.id).val() == "") {
			var that=this;
			layer.alert(this.placeholder, {icon:0},function(index){
				that.focus();
				layer.close(index);
			});//0：弹框图片为叹号警告
			submit = false;
			return false;
		}
	});
	if (submit) {
		Login();
	}

}
//登录处理函数
function Login() {
	var actionurl=$('form').attr('action');//提交路径
	var checkurl=$('form').attr('check');//验证路径
	 var formData = new Object();
	var data=$(":input").each(function() {
		formData[this.name] =$("#"+this.name ).val();
	});
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		url : checkurl,// 请求的action路径
		data : formData,
		dataType : "json",
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			if (data.success) {
				setUserFormCookie();
//				setTimeout("window.location.href='"+actionurl+"'", 1000);
				window.location.href=actionurl;
			} else {
				if(data.msg == "a"){
					layer.confirm('数据库无数据,是否初始化数据？', {icon: 3, title:'提示'}, function(index){
						 layer.close(index);
						 window.location = "init.jsp";
					 });
				} else{
					layer.alert(data.msg, {icon:0});
//					reloadRandCodeImage();//刷新验证码
				}
			}
		}
	});
}
//设置cookie，默认记住90天
function setUserFormCookie(){
	delCookie(cookieName_username);//删除用户名Cookie
	delCookie(cookieName_password);//删除密码Cookie
	delCookie(cookieName_remember);//删除记住用户信息
	var $remember = $('#ck_remember');
	var _username = $.trim($("#userName").val()); 
	var _password = $.trim($("#password").val()); 
	if($remember[0].checked){
		SetCookie(cookieName_username,_username,90);//保存到Cookie中  保存90天
		SetCookie(cookieName_password,_password,90);
		SetCookie(cookieName_remember,$remember[0].checked,90);
	}
}
//读取cookie
function getUserFormCookie(){
	var _remember=getCookie(cookieName_remember),
	_username=getCookie(cookieName_username)||"",
	_password=getCookie(cookieName_password)||""
	if(_remember!=null&&!_remember.toString().isnullorempty()){
		$('#userName').val(_username);
		$('#password').val(_password);
		$('#ck_remember')[0].checked=true;
	}
}
