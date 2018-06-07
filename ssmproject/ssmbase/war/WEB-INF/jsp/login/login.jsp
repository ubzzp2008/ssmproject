<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
<head>
<!-- <meta content="IE=11.0000" http-equiv="X-UA-Compatible"> -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>登录页面</title>
<link href="<c:url value='/img/favicon.ico'/>" rel="icon" type="image/x-icon" />
<link href="<c:url value='/css/login.css'/>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/jquery.cookie.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/cookiestool.js'/>"></script>
<script type="text/javascript"  >
var appPath = "${pageContext.request.contextPath}";
</script>
 <script type="text/javascript" src="<c:url value='/js/login/login.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/layer/layer.js'/>" ></script>

<script type="text/javascript">
	$(function() {
		//得到焦点
		$("#password").focus(function() {
			$("#left_hand").animate({
				left : "150",
				top : " -38"
			}, {
				step : function() {
					if (parseInt($("#left_hand").css("left")) > 140) {
						$("#left_hand").attr("class", "left_hand");
					}
				}
			}, 2000);
			$("#right_hand").animate({
				right : "-64",
				top : "-38px"
			}, {
				step : function() {
					if (parseInt($("#right_hand").css("right")) > -70) {
						$("#right_hand").attr("class", "right_hand");
					}
				}
			}, 2000);
		});
		//失去焦点
		$("#password").blur(function() {
			$("#left_hand").attr("class", "initial_left_hand");
			$("#left_hand").attr("style", "left:100px;top:-12px;");
			$("#right_hand").attr("class", "initial_right_hand");
			$("#right_hand").attr("style", "right:-112px;top:-12px");
		});
	});
</script>
</head>
<%-- <body>
	<div class="top_div"></div>
	<form name="formLogin" id="formLogin" action="<c:url value='/loginController/login'/>" check="<c:url value='/loginController/checkuser'/>" method="post">
	<div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 260px; text-align: center;">
		<div style="width: 165px; height: 90px; position: absolute;">
			<div class="tou"></div>
			<div class="initial_left_hand" id="left_hand"></div>
			<div class="initial_right_hand" id="right_hand"></div>
		</div>
		<p style="padding: 30px 0px 10px; position: relative;">
			<span class="u_logo"></span> 
			<input class="ipt" type="text" id="userName"  name="userName" 	placeholder="请输入用户名" value="" reg=^.+$>
		</p>
		<p style="position: relative;">
			<span class="p_logo"></span> 
			<input class="ipt" id="password" name="password" type="password" placeholder="请输入密码" value="" reg=^.+$>
		</p>
		<p style="padding: 10px 0px 10px; position: relative;">
			<input class="ipt1"  id="randCode" name="randCode" type="text" placeholder="请输入验证码" value="" reg=^.+$>
			<span style="padding: 10px;"><img style="vertical-align: middle;cursor:pointer;" id="randCodeImage" src="<c:url value='/randCodeImage'/>"  /></span>
		</p>
		<div style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
			<p style="margin: 0px 35px 20px 45px;">
				<span style="float: left;">
					<input type="checkbox" id="ck_remember" >&nbsp;记住密码
				</span> 
				<span style="float: right;"><a style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 8px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
					href="#" id="but_login">登录</a> </span>
			</p>
		</div>
	</div>
	</form>
</body> --%>

<body>
	<div class="top_div"></div>
	<form name="formLogin" id="formLogin" action="<c:url value='/loginController/login'/>" check="<c:url value='/loginController/checkuser'/>" method="post">
	<div style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; text-align: center;">
	<div style="width: 165px; height: 96px; position: absolute;">
		<div class="tou"></div>
		<div class="initial_left_hand" id="left_hand"></div>
		<div class="initial_right_hand" id="right_hand"></div>
	</div>

	<div style="padding: 30px 0px 10px; position: relative;">
		<span class="u_logo"></span>
		<input class="ipt" type="text" id="userName"  name="userName" 	placeholder="请输入用户名" value="" reg=^.+$>
	</div>

	<div style="position: relative;">
		<span class="p_logo"></span>
		<input class="ipt" id="password" name="password" type="password" placeholder="请输入密码" value="" reg=^.+$>
  	</div>

	<div style="position: relative; overflow: hidden;margin-top: 10px">
		<span class="c_logo"></span>
		<input class="ipt"  id="randCode" name="randCode" type="text" placeholder="请输入验证码" value="" reg=^.+$  style="float: left ;width: 200px;margin-left:32px">
		<div style="float: right; height: 37px; line-height: 37px;margin-right: 32px;max-width: 150px;overflow: hidden">
			<img style="vertical-align: middle;cursor:pointer;" id="randCodeImage" src="<c:url value='/randCodeImage'/>"  />
		</div>
	</div>

	<div style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
	<div style="margin: 0px 35px 20px 34px;">
		<span style="float: left; overflow: hidden;">
			<div style="display: inline-block; float:left; overflow: hidden;margin-right: 20px">
				<input type="checkbox" id="ck_remember" style="float: left; margin-right: 5px; height:50px;line-height: 50px">
				<label for="ck_remember" style="float: left;line-height: 49px;color:rgb(204, 204, 204);cursor: pointer">记住密码</label>
			</div>
			<a style="color: rgb(204, 204, 204); float: left;line-height:49px " href="javascript:;">忘记密码?</a>
		</span>
	   	<span style="float: right;">
<!-- 	   		<a style="color: rgb(204, 204, 204); margin-right: 10px;" href="#">注册</a> -->
		  	<a style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;" 
		  		href="javascript:;"  id="but_login">登录</a>
	   	</span>
	</div>
</div>
</div>
</form>
</body>
</html>



