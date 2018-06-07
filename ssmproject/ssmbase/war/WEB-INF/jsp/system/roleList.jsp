<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>协同平台 -管理</title>
	<%@ include file="/common/publicCsJs.jsp" %> 
	<script type="text/javascript" src="<c:url value='/js/system/roleList.js'/>"></script>
</head>
<body>
<!-- 导航开始 -->
<%@ include file="/common/menu.jsp" %>
<!-- 头文件结束 --> 

<!-- 中间内容结束开始 -->
<div class="dh_back">
  	<div class="l_r_t_b20 font12"> 
  		<img src="<c:url value='/img/icon_navigation.png'/>" width="16" height="16">&nbsp;&nbsp;当前位置：系统管理&nbsp;&nbsp; 
  		<img src="<c:url value='/img/arrow_red.gif'/>" width="7" height="5"> &nbsp;&nbsp;角色管理
  	</div>
</div>

 <form class="form-horizontal">
		<div class="container_center">
			<div class="bs-docs-example">
				<div class="row-fluid">
					<div class="span3">
						<div class="control-group1 left-20">
							<label class="control-label3">角色名称：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10" id="query_name"  type="text" value=""/>
									<button onclick="$(this).parent().find('input[type=text]').val('');" class="btn btn-small" type="button"> <i class="icon-trash"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<!-- <div class="span3">
						<div class="control-group1">
							<label class="control-label3">状态：</label>
							<div class="controls3">
								<input type="checkbox" name="status" checked="checked" value="0"/> 启用
          	  					<input type="checkbox" name="status"  value="1"/>禁用
							</div>
						</div>
					</div> -->
				</div>
			</div>
			<div class="row-fluid top6">
				<div class="pull-right">
					  <a href="javascript:;" id="search"  role="button" class="btn btn-small" data-toggle="modal">查 询</a>
<!-- 			          <a href="javascript:;" id="changeState_stop" class="btn btn-small">停用</a> -->
<!-- 			          <a href="javascript:;" id="changeState_enable" class="btn btn-small">启用</a> -->
			          <a href="#addPageModal" role="button" class="btn btn-small" data-toggle="modal">新 增</a>
			          <a href="javascript:;" id="btn_updatePage" role="button" class="btn btn-small" data-toggle="modal">编 辑</a>
			          <a href="javascript:;" id="btn_deleteRoleInfo" role="button" class="btn btn-small" data-toggle="modal">删 除</a>
				</div>
			</div>
			<div class="row-fluid top6">
				<table id="roleInfo_list"  class="table-block">
				</table>
				<div id="roleInfo_page"></div>
			</div>
		</div>
	</form>

<!-- 新增页面Modal弹出开始 --> 
<div id="addPageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a type="button" id="clearForm" class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>新增角色</h4>
  </div>
  <form id="addRoleForm" method="post">
  <div class="modal-body form-horizontal">
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>角色名称:</label>
          <div class="controls3">
            <div class="span12">
             	<input class="span10" name="roleName" type="text"  reg="^.+$" tip="必填：角色名称" />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">角色描述:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="roleDesc" type="text" />
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
  	<a class="btn btn-small btn-primary" href="javascript:;" id="btn_saveRoleInfo">保 存</a>
  	<a class="btn btn-small left10" data-dismiss="modal" aria-hidden="true">取消</a>
  </div>
</div>
<!-- 新增页面Modal弹出结束  -->

<!-- 修改页面Modal弹出开始  -->
<div id="updatePageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a  class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>编辑角色</h4>
  </div>
  <form id="updateRoleForm" method="post">
  <div class="modal-body form-horizontal">
  	<input  id="update_id" name="id" type="hidden" value="" readonly="readonly" />
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>角色名称:</label>
          <div class="controls3">
            <div class="span12">
             	<input class="span10" id="update_roleName" name="roleName" type="text"  reg="^.+$" tip="必填：角色名称"  />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">角色描述:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_roleDesc" name="roleDesc" type="text" />
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
    <a class="btn btn-small btn-primary" href="javascript:;" id="btn_updateRoleInfo">保 存</a>
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
