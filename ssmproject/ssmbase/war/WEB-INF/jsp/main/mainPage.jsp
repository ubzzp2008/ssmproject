<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<%@ include file="/common/publicCsJs.jsp"%>
<title>云汉供应链平台</title>
</head> 
<body>
<!-- 头文件开始 --> 
<%@ include file="/common/menu.jsp"%> 
<!-- 头文件结束 --> 

<!-- 中间内容结束开始 -->
<div class="dh_back"> 
		<div class="l_r_t_b20 font12">
			<img src="<c:url value='/img/icon_navigation.png'/>" width="16" height="16">&nbsp;&nbsp;当前位置：首 页&nbsp;&nbsp; 
			<img src="<c:url value='/img/arrow_red.gif'/>" width="7" height="5">&nbsp;&nbsp; 待办工作 &nbsp;&nbsp; 
		</div>
	</div>
<form class="form-horizontal">
  <div class="container_center">
    <div class="row-fluid">
      <div class="span7">
        <div class="row-fluid taskBack">
          <div class="left20">
            <div class="span6">
              <br>
              <img src="<c:url value='/img/task.gif'/>"> &nbsp;&nbsp; 待办工作
              <br>
            </div>
          </div>
	    </div>
        <div class="row-fluid left40">
          <div class="top10"><span class="fontB">您有：</span></div>
          <div class="left40 top6"><a class="line" href="GDXH02-05.html">商品主数据修改 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH03.html">待新品信息补充处理 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-01.html">待报订处理新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-04.html">待处理外采统筹（先征后采）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-04.html">待处理外采统筹（先采后征）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-04.html">待处理外采统筹（审核退回）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH06.html">待创建订单需求数 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-02.html">待征订处理新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-03.html">待收订处理（先征后采）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-03.html">待收订处理（先采后征）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-03.html">待收订处理（审核退回）新品 <span class="fontRed">X</span> 条！</a></div>
          <div class="left40 top6"><a class="line" href="GDXH04-05.html">待处理外采审核新品 <span class="fontRed">X</span> 条！</a></div>
        </div>
      </div>
    </div>
  </div>
</form>
<!-- 中间内容结束 --> 

<!-- 尾文件开始 -->
<%@ include file="/common/footer.jsp"%>
<!-- 尾文件结束 --> 
</body>
</html>
