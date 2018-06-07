<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>协同平台 - 功能权限</title>
	<%@ include file="/common/publicCsJs.jsp" %> 
</head>
<body>
	<!-- 导航开始 -->
	<%@ include file="/common/menu.jsp" %>
	<!-- 导航结束-->
	<!-- 位置导航开始 -->
	<div class="dh_back">
		<div class="l_r_t_b20 font12">
			<img src="<c:url value='/img/icon_navigation.png'/>" width="16" height="16">&nbsp;&nbsp;当前位置：系统管理&nbsp;&nbsp;
			<img src="<c:url value='/img/arrow_red.gif'/>" width="7" height="5">
			&nbsp;&nbsp;功能权限管理
		</div>
	</div>
<!-- 位置导航结束 --> 	
<div class="roleMenuDiv">
	<div class="divLeft"></div>
	<div class="divRight"></div>
</div>
	<!-- 尾部开始 -->
	<jsp:include page="/common/footer.jsp"></jsp:include>
	<!-- 尾部结束 -->
</body>
</html>