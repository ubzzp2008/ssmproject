<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>协同平台 - 组织机构管理</title>
	<%@ include file="/common/publicCsJs.jsp" %> 
	<script type="text/javascript" src="<c:url value='/js/system/userInfoList.js'/>"></script>
</head>
<body>
<!-- 导航开始 -->
<%@ include file="/common/menu.jsp" %>
<!-- 头文件结束 --> 

<!-- 中间内容结束开始 -->
<div class="dh_back">
  	<div class="l_r_t_b20 font12"> 
  		<img src="<c:url value='/img/icon_navigation.png'/>" width="16" height="16">&nbsp;&nbsp;当前位置：系统管理&nbsp;&nbsp; 
  		<img src="<c:url value='/img/arrow_red.gif'/>" width="7" height="5"> &nbsp;&nbsp;用户管理
  	</div>
</div>

 <form class="form-horizontal">
		<div class="container_center">
			<div class="bs-docs-example">
				<div class="row-fluid">
					<div class="span3">
						<div class="control-group1 left-20">
							<label class="control-label3">用户名：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10" id="query_userName"  type="text" value=""/>
									<button onclick="$(this).parent().find('input[type=text]').val('');" class="btn btn-small" type="button"> <i class="icon-trash"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="span3">
						<div class="control-group1">
							<label class="control-label3">真实名：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10"  id="query_realName" type="text" value=""/>
									<button onclick="$(this).parent().find('input[type=text]').val('');" class="btn btn-small" type="button"> <i class="icon-trash"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid top6">
				<div class="pull-right">
					  <a href="javascript:;" id="search"  role="button" class="btn btn-small" data-toggle="modal">查 询</a>
<!-- 			          <a href="javascript:;" id="changeState_stop" class="btn btn-small">停用</a> -->
<!-- 			          <a href="javascript:;" id="changeState_enable" class="btn btn-small">启用</a> -->
			          <a href="#addPageModal" role="button" class="btn btn-small" data-toggle="modal">新 增</a>
			          <a href="javascript:;" id="btn_updatePage" role="button" class="btn btn-small" data-toggle="modal">编 辑</a>
			          <a href="javascript:;" id="btn_deleteUserInfo" role="button" class="btn btn-small" data-toggle="modal">删 除</a>
				</div>
			</div>
			<div class="row-fluid top6">
				<table id="userInfo_list"  class="table-block">
				</table>
				<div id="userInfo_page"></div>
			</div>
		</div>
	</form>

<!-- 新增页面Modal弹出开始 --> 
<div id="addPageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a type="button" id="clearForm" class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>新增用户</h4>
  </div>
  <form id="addUserForm" method="post">
  <div class="modal-body form-horizontal">
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>用户名:</label>
          <div class="controls3">
            <div class="span12">
             	<input class="span10" name="userName" type="text"  reg="^.+$" tip="必填：用户登录名" />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>真实名:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="realName" type="text"  reg="^.+$"  tip="必填：用户真实姓名"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>密&nbsp;码:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="password" type="password"  reg="^.+$"  tip="必填：用户登录密码">
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">联系电话:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="phone"  type="text"  tip="可选：用户联系方式"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">是否启用:</label>
          <div class="controls3 top3">
          	  <input type="radio" name="status" checked="checked" value="0"/> 启用
          	  <input type="radio" name="status"  value="1"/>禁用
          </div>
        </div>
    </div>
<!--     选中的角色值 -->
    <input type="hidden" id="roleIds" name="roleIds" value="">
    <div class="row-fluid">
        <div class="span5">
          <div align="center">
            <h5 class="fontblue">备选角色</h5>
          </div>
        </div>
        <div class="span2"></div>
        <div class="span5">
          <div align="center">
            <h5 class="fontblue">已有角色</h5>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span5">
          <div align="center">
            <select class="pageComGroup"  id="roleList" multiple="multiple" style="height:150px;">
            </select>
          </div>
        </div>
        <div class="span2">
          <div align="center">
            <p>&nbsp;</p>
            <input type="button" title="追加备选角色"  id="addRoleBtn" class="btn btn-mini" value="&nbsp;>>&nbsp;">
            <p>&nbsp;</p>
            <input type="button" title="移除已有角色" id="removeRoleBtn" class="btn btn-mini" value="&nbsp;<<&nbsp;">
          </div>
        </div>
        <div class="span5">
          <div align="center">
            <select class="pageComGroup" id="roleSelect" multiple="multiple" style="height:150px;">
            
            </select>
          </div>
        </div>
    </div>
    
  </div>
  </form>
  <div class="modal-footer">
  	<a class="btn btn-small btn-primary" href="javascript:;" id="btn_saveUserInfo">保 存</a>
  	<a class="btn btn-small left10" data-dismiss="modal" aria-hidden="true">取消</a>
  </div>
</div>
<!-- 新增页面Modal弹出结束  -->

<!-- 修改页面Modal弹出开始  -->
<div id="updatePageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a  id="clearForm_update" class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>编辑用户</h4>
  </div>
  <form id="updateUserForm" method="post">
  <div class="modal-body form-horizontal">
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>用户名:</label>
          <div class="controls3">
            <div class="span12">
            	<input  id="update_id" name="id" type="hidden" value="" readonly="readonly" />
             	<input class="span10" id="update_userName" name="userName" type="text" value="" readonly="readonly" />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>真实名:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_realName" name="realName" type="text"  reg="^.+$"  tip="必填：用户真实姓名"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">联系电话:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_phone" name="phone"  type="text"  tip="可选：用户联系方式"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">是否启用:</label>
          <div class="controls3 top3">
          	  <input type="radio" name="status" checked="checked" value="0"/> 启用
          	  <input type="radio" name="status"  value="1"/>禁用
          </div>
        </div>
    </div>
  </div>
  </form>
  <div class="modal-footer">
    <a class="btn btn-small btn-primary" href="javascript:;" id="btn_updateUserInfo">保 存</a>
  	<a class="btn btn-small left10" data-dismiss="modal" aria-hidden="true">取消</a>
  </div>
</div>
<!-- 修改页面Modal弹出结束 --> 

<!-- 中间内容结束 --> 

<!-- 尾文件开始 -->
<%@ include file="/common/footer.jsp"%>
<!-- 尾文件结束 -->
</body>
</html>
