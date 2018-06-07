<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%@include file="/common/mytags.jsp" %>
<jsp:include page='<%="/page/userInfo"%>' />
<div class="navback">
	<div class="row-fluid">
		<div class="span6">
			<div class="logo_cc"> <img src="<c:url value='/img/page_logo.png'/>"> </div>
		</div>
		<div class="span6">
			<div class="pull-right">
				<ul class="inline top10">
					<li><a href="<c:url value='/'/>">首 页</a></li>
					<li><a href="#">联系人</a></li>
					<li><a href="#">个人资料</a></li>
					<li><a href="javascript:updatePassWord();">修改密码</a></li>
					<li><a href="<c:url value='/logout'/>">退 出</a></li>
				</ul>
				<ul class="inline">
					<li class="fontred_black"><strong>技术支持：180-1151-4726  138-8026-4207</strong></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="menuback">
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container"> <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-responsive-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </a>
				<div class="nav-collapse collapse navbar-responsive-collapse">
					<ul id="menu_list" class="nav">
					</ul>
					<ul class="nav pull-right">
						<li><a href="javascript:;"><script>document.write(realName); </script></a></li>
						<li><a href="javascript:;"><script>document.write(userName); </script></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="updatePasswordModal" class="modal hide" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h4>用户信息</h4>
		</div>
		<div class="modal-body">
			<form id="modUserPassWord" name="modUserPassWord" class="form-horizontal"  method="post" action="modfiyPassWord">
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group1">
							<label class="control-label1">登录名：</label>
							<div class="controls1">
								<div class="span12">
									<input class="span10" type="text" id="loginUserName" name="loginUserName" value="" readonly="readonly">
									<input class="span10" type="hidden" id="userId" name="userId" value="" readonly="readonly">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group1">
							<label class="control-label1">真实姓名：</label>
							<div class="controls1">
								<div class="span12">
									<input class="span10" type="text" id="loginTruename" name="loginTruename" readonly="readonly">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group1">
							<label class="control-label1">新密码：</label>
							<div class="controls1">
								<div class="span12">
									<input class="span10" type="password" id="newPassword" name="newPassword" >
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="control-group1">
							<label class="control-label1">确认密码：</label>
							<div class="controls1">
								<div class="span12">
									<input class="span10" type="password" id="confirmPassword" name="confirmPassword" >
								</div>
							</div>
						</div>
					</div>
				</div>
				<div  align="right" class="row-fluid top10">
				  <a onclick="saveUserPassword();" class="btn btn-small btn-primary" >保&nbsp;存</a>
			      <a onclick="cancel();"  class="btn btn-small left10" >取&nbsp;消</a>
<!-- 			      <button onclick="saveUserPassword()" class="btn btn-small">保 存</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
<!--     					<button class=" btn btn-small" data-dismiss="modal" aria-hidden="true" onblur="close();">关 闭</button> -->
				</div>
			</form>
		</div>
		</div>
<script id="menu_list_tpl" type="text/template">
	{@each obj as menu,index}
	<li class="dropdown">
		{@if menu.subNodeList.length !== 0}
		<a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">${'${'}menu.name} <b class="caret"></b></a>
		<ul class="dropdown-menu text-left">
			{@each menu.subNodeList as submenu,index}
			<li><a href="${'${'}submenu.url}">${'${'}submenu.name}</a></li>
			{@/each}
		</ul>
		{@else}
		<a href="${'${'}menu.url}" class="dropdown-toggle" data-toggle="dropdown">${'${'}menu.name}</a>
		{@/if}
	</li>
	{@/each}
</script>
<script>

var data = {"success":true,"msg":"sssss",
		"obj":[
			{
				"name":"首页",
				"url":"#1",
				"subNodeList":[
					{
						"name":"待办工作",
						"url":"/mainPageController/page/mainPage",
						"subNodeList":[]
					}           
				  ]
			},{
				"name":"系统管理",
				"url":"",
				"subNodeList":[
					{
						"name":"用户管理",
						"url":"/ssmdemo/userInfoController/page/userInfoList",
						"subNodeList":[]
					},{
						"name":"菜单管理",
						"url":"/ssmdemo/menuController/page/menuList",
						"subNodeList":[]
					},{
						"name":"角色管理",
						"url":"/ssmdemo/roleController/page/roleList",
						"subNodeList":[]
					},{
						"name":"功能权限管理",
						"url":"/ssmdemo/roleMenuController/page/roleMenuList",
						"subNodeList":[]
					},{
						"name":"商品信息完善",
						"url":"/education/edu/commodity/page/perfectCommodity",
						"subNodeList":[]
					},{
						"name":"商品信息补充",
						"url":"/education/edu/masterdata/page/masterInfo",
						"subNodeList":[]
					},{
						"name":"商品信息管理",
						"url":"/education/article/page/toMasterDataManager",
						"subNodeList":[]
					},{
						"name":"商品信息录入",
						"url":"/education/article/page/toAddMasterData",
						"subNodeList":[]
					},{
                        "name":"门店征订处理",
                        "url":"/education/edu/demand/page/demandList",
                        "subNodeList":[]
                    },{
                        "name":"配供审核处理",
                        "url":"/education/edu/demand/page/demandCheck",
                        "subNodeList":[]
                    },{
                        "name":"采购统筹处理",
                        "url":"/education/edu/demand/page/demandPurchaseAudit",
                        "subNodeList":[]
                    },{
                        "name":"采购审核处理",
                        "url":"/education/edu/demand/page/demandMasterPurchaseAudit",
                        "subNodeList":[]
                    },{
                        "name":"订单创建",
                        "url":"/education/edu/purchaseOrder/page/purchaseOrderManager",
                        "subNodeList":[]
                    }
				]
			}
		]
	};

// In.ready("juicer",function() {
// 	var _tpl = $("#menu_list_tpl").html();
// 	var html = juicer(_tpl, data);
// 	$("#menu_list").html(html);
// 	WG.menuHover.init();
// });

In.ready("juicer",function() {
	$.ajax({
		url:"<c:url value='/menuController/json/findMenuByUserId'/>?userId="+userId,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.success){
// 				for ( var i = 0; i < data.objList.length; i++) {
//					var aa = data.objList[i].subNodeList;
//					for ( var j =  0; j < aa.length; j++) {
//						aa[j].url= aa[j].url.replace("qas.gd.cnpdx.cn","localhost:8080/gd");
//					}
//				}
			console.log(data);
			var _tpl = $("#menu_list_tpl").html();
			var html = juicer(_tpl, data);
			$("#menu_list").html(html);
			WG.menuHover.init();
			}else{
				layer.alert(data.msg, {icon: 0}, function(index){
					window.location.href="<c:url value='/logout'/>";
				 });
			}
		}
   });
});

function updatePassWord(){
	$("#modUserPassWord")[0].reset();
		/* $.ajax({ 
			type: "post",
			url: '<c:url value='/common/json/getUserInfo'/>', 
			dataType: "json", 
			success: function(json){
				if (json.success) {
					$('#userId').val(json.obj.soId);
					$('#loginUserName').val(json.obj.soName);
					$('#loginTruename').val(json.obj.soTrueName);
				}else{
					layer.alert(json.msg,{icon:0});
				}
			}
		}); */
	$("#updatePasswordModal").modal("show");
}
//用户密码修改保存
 function saveUserPassword(){
	 var userId = $("#userId").val();
	var loginName = $("#loginUserName").val();
	var newPassword = $("#newPassword").val();
	var confirmPassword = $("#confirmPassword").val();
	if(newPassword==null||newPassword=="" ||confirmPassword==null||confirmPassword==""){
		layer.alert("请完整填写密码！",{icon:0});
	}else if(newPassword==confirmPassword){
		$.ajax({ 
			type: "post",
			url: "<c:url value='/system/updatePassword'/>", 
			data: {"userId":userId,"newPassword":newPassword},
			dataType: "json", 
			success: function(json){
				if (json.success) {
					layer.alert(json.msg,{icon:1});
					$("#updatePasswordModal").modal("hide");
				}else{
					layer.alert(json.msg,{icon:0});
				}
			}
		});
	}else{
		layer.alert("两次输入的密码不相同，请重新输入",{icon:0});
		$('#newPassword').val("");
		$('#confirmPassword').val("");
	}
} 

 function cancel(){
		$('#updatePasswordModal').modal('hide');
	}
</script>
