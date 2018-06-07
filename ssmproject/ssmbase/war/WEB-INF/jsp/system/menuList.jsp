<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
  <head>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>协同平台 -管理</title>
	<%@ include file="/common/publicCsJs.jsp" %> 
	<script type="text/javascript" src="<c:url value='/js/system/menuList.js'/>"></script>
</head>
<body>
<!-- 导航开始 -->
<%@ include file="/common/menu.jsp" %>
<!-- 头文件结束 --> 

<!-- 中间内容结束开始 -->
<div class="dh_back">
  	<div class="l_r_t_b20 font12"> 
  		<img src="<c:url value='/img/icon_navigation.png'/>" width="16" height="16">&nbsp;&nbsp;当前位置：系统管理&nbsp;&nbsp; 
  		<img src="<c:url value='/img/arrow_red.gif'/>" width="7" height="5"> &nbsp;&nbsp;菜单管理
  	</div>
</div>

 <form class="form-horizontal">
		<div class="container_center">
			<div class="bs-docs-example">
				<div class="row-fluid">
					<div class="span3">
						<div class="control-group1 left-20">
							<label class="control-label3">菜单名称：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10" id="query_name"  type="text" value=""/>
									<button onclick="$(this).parent().find('input[type=text]').val('');" class="btn btn-small" type="button"> <i class="icon-trash"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="span3">
						<div class="control-group1">
							<label class="control-label3">菜单路径：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10"  id="query_url" type="text" value=""/>
									<button onclick="$(this).parent().find('input[type=text]').val('');" class="btn btn-small" type="button"> <i class="icon-trash"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="span3">
						<div class="control-group1">
							<label class="control-label3">父级菜单：</label>
							<div class="controls3">
								<div class="input-append span12">
									<input class="span10"  id="query_pname" type="text" value=""/>
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
			          <a href="javascript:;" id="btn_deleteMenuInfo" role="button" class="btn btn-small" data-toggle="modal">删 除</a>
				</div>
			</div>
			<div class="row-fluid top6">
				<table id="menuInfo_list"  class="table-block">
				</table>
				<div id="menuInfo_page"></div>
			</div>
		</div>
	</form>

<!-- 新增页面Modal弹出开始 --> 
<div id="addPageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a type="button" id="clearForm" class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>新增菜单</h4>
  </div>
  <form id="addMenuForm" method="post">
  <div class="modal-body form-horizontal">
  	<div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>菜单层级:</label>
          <div class="controls3">
            <div class="span12">
              <select class="span10" id="levele" name="levele"  reg="^.+$" tip="必填：菜单的层级">
					<option value="">--请选择--</option>
					<option value="1">一级菜单</option>
					<option value="2">二级菜单</option>
				</select>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid" id="pid_select" style="display: none">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>父级菜单:</label>
          <div class="controls3">
            <div class="span12">
              <select class="span10" id="pid" name="pid" >
					<option value="">--请选择--</option>
				</select>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>菜单名称:</label>
          <div class="controls3">
            <div class="span12">
             	<input class="span10" name="name" type="text"  reg="^.+$" tip="必填：菜单的显示名" />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid" id="url_div" style="display: none">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>菜单路径:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="url" name="url" type="text"  tip="必填：菜单的访问地址"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>显示顺序:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="seq" type="text"  reg="^.+$"  tip="必填：菜单的显示顺序"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">描述:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" name="description" type="text" />
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
  	<a class="btn btn-small btn-primary" href="javascript:;" id="btn_saveMenuInfo">保 存</a>
  	<a class="btn btn-small left10" data-dismiss="modal" aria-hidden="true">取消</a>
  </div>
</div>
<!-- 新增页面Modal弹出结束  -->

<!-- 修改页面Modal弹出开始  -->
<div id="updatePageModal" class="modal hide fade"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
  <div class="modal-header">
    <a  class="close" data-dismiss="modal" aria-hidden="true">×</a>
    <h4>编辑菜单</h4>
  </div>
  <form id="updateMenuForm" method="post">
  <div class="modal-body form-horizontal">
    <div class="row-fluid">
        <div class="control-group1">
        <input  id="update_id" name="id" type="hidden" value="" readonly="readonly" />
          <label class="control-label3"><font color="red">* </font>菜单层级:</label>
          <div class="controls3">
            <div class="span12">
              <select class="span10" id="update_levele"  >
					<option value="">--请选择--</option>
					<option value="1">一级菜单</option>
					<option value="2">二级菜单</option>
				</select>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid" id="update_pid_select" style="display: none">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>父级菜单:</label>
          <div class="controls3">
            <div class="span12">
              <select class="span10" id="update_pid" name="pid" >
					<option value="">--请选择--</option>
				</select>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>菜单名称:</label>
          <div class="controls3">
            <div class="span12">
             	<input class="span10" id="update_name" name="name" type="text"  reg="^.+$" tip="必填：菜单的显示名"  />
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid" id="update_url_div" style="display: none">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>菜单路径:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_url" name="url" type="text"  tip="必填：菜单的访问地址"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3"><font color="red">* </font>显示顺序:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_seq" name="seq" type="text"  reg="^.+$"  tip="必填：菜单的显示顺序"/>
            </div>
          </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="control-group1">
          <label class="control-label3">描述:</label>
          <div class="controls3">
            <div class="span12">
              <input class="span10" id="update_description" name="description" type="text" />
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
    <a class="btn btn-small btn-primary" href="javascript:;" id="btn_updateMenuInfo">保 存</a>
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
