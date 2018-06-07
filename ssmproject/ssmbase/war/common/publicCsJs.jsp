<%@ page language="java"  pageEncoding="UTF-8"%>
<%@include file="/common/mytags.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/css/bootstrap.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/styles.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/docs.css'/>" rel="stylesheet" type="text/css">
<link rel="icon" href="<c:url value='/img/favicon.ico'/>" type="image/x-icon" />
<link rel="shortcut icon" href="<c:url value='/img/favicon.ico'/>" type="image/x-icon" />

<script type="text/javascript"  >
var appPath = "${pageContext.request.contextPath}";
</script>

<script type="text/javascript" src="<c:url value='/js/injs/in.js'/>" autoload="true" core="<c:url value='/js/jquery/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>

<script type="text/javascript"  >
var appPath = "${pageContext.request.contextPath}";
//处理日期序列后出现GMT+08:00时IE不能正确创建date对象的问题
function convertStringToDateForIE(dateStr) {
	var dateStr1 = dateStr;
	var gmtBegin = dateStr.indexOf("GMT");
	var cstBegin = dateStr.indexOf("CST");
	var fbegin = gmtBegin != -1 ? gmtBegin : (cstBegin != -1 ? cstBegin : -1);
	if (fbegin != -1) {
		var fEnd = dateStr.indexOf(" ", fbegin);
		var begin = dateStr.substr(0, fbegin);
		var end = dateStr.substr(fEnd+1, dateStr.length);
		dateStr1 = begin + end;
		//alert(dateStr1);
	}
	return new Date(dateStr1);
}
/**
 * 验证框架公用方法
 */
function diyValidate(formId) {
	var count = 0;
	$("#"+formId).find("[reg],[url]:not([reg])").each(function() {
		if ($(this).attr("reg") != undefined) {
			if (!validateDiy($(this))) {
				count++;
			}
		}
	});
	var errlen = $("#"+formId).find(".error").length;
	if (count > 0 || errlen>0) {
		layer.alert("请正确填写数据!", { icon : 0 });
		return false;
	}
	return true;
}
/**
 * 移除class中的error
 */
function removeFormError(formId){
	$("#"+formId).find(".error").removeClass("error");
}

/**
 * 字符串去掉空格
 */
function trim( text ){
  if (typeof(text) == "string"){
    return text.replace(/^\s*|\s*$/g, "");
  }else{
    return text;
  }
}
</script>

