In.ready('jqGrid','tip','serializeJson',function() {
	  	$("#userInfo_list").jqGrid({ 
			url:appPath+'/console/query', 
			autowidth: true, //自适应宽度
	 		shrinkToFit: true, //列自适应
// 			forceFit:true,
			datatype: "json", //数据格式
			mtype:"POST",
			colNames:['ID','用户名','真实名','联系电话','启用状态','','创建人','创建时间','修改人','修改时间'], //列配置,必须和下面的colModel数量匹配
			colModel:[
				{name:'id',index:'id', width:60, hidden:true,sortable:false},
				{name:'userName',index:'userName', align:'center', width:140},
				{name:'realName',index:'realName',align:'center'},
				{name:'phone',index:'phone', width:255,align:'center', sortable:false},
				{name:'status',index:'status', width:90, align:'center', resizable:false,sortable:false,
				 	formatter : function(value, options, rData){
						if (value==0) {
							return "<font color='gree'>启用</font>";
						}
						return "<font color='red'>停用</font>";
					}
				},
				{name:'status',index:'status', hidden:true},
				{name:'addUser',index:'addUser',align:'center'},
				{name:'addDate',index:'addDate',align:'center'},
				{name:'updateUser',index:'updateUser',align:'center'},
				{name:'updateDate',index:'updateDate',align:'center'}
				],
		   	rowNum:20,  //默认分页行数
			rowList:[20,50,100],  //可选分页行数
			pager: '#userInfo_page',  //分页按钮展示地址
			sortname: 'id',  //默认排序字段
			rownumbers: true,    //显示左边排名列表
			viewrecords: true,   //是否显示数据总条数
			multiselect:true,  //复选框
			sortorder: "desc",  //默认排序方式
			jsonReader: { 
				  root: "rows",
				  page: "page",
				  total: "total",
				  records: "records",
				  repeatitems : false
			}, //设置数据方式
			postData: {query_id:"getPageUserInfoList",query_type:"JQGRID",rdParseType:"dispersed",reqData:searchData()},
			height: '100%',
			/* 客户端排序--------------------------------------------------------开始 */
			loadBeforeSend:function(){
	             $("#userInfo_list").jqGrid('clearGridData');
		    },
			onPaging : function(pgButton){
				$("#userInfo_list").jqGrid('setGridParam',{datatype:'json',postData:{query_id:"getPageUserInfoList",query_type:"JQGRID",reqData:searchData()}});
			}, 
			loadComplete : function(data) {
	        	/*var $this = $(this);
				if ($this.jqGrid('getGridParam', 'datatype') === 'json') {
			        $this.jqGrid('setGridParam', {
			            datatype: 'local',
			            data: data.rows,
			            pageServer: data.page,
			            recordsServer: data.records,
			            lastpageServer: data.total
			    	});
	        		this.refreshIndex();
			        if ($this.jqGrid('getGridParam', 'sortname') !== '') {
			            $this.triggerHandler('reloadGrid');
			        }
	    		} else {
			        $this.jqGrid('setGridParam', {
			            page: $this.jqGrid('getGridParam', 'pageServer'),
			            records: $this.jqGrid('getGridParam', 'recordsServer'),
			            lastpage: $this.jqGrid('getGridParam', 'lastpageServer')
			        });
	        		this.updatepager(false, true);
	    		} */
			}
			/* 客户端排序 --------------------------------------------------------结束*/
		});
	  	$("#userInfo_list").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });  //jqgrid取消掉水平滚动条
	  	$("#userInfo_list").jqGrid('navGrid','#userInfo_page',{edit:false,add:false,del:false,search:false,refresh:true});//左下角的操作按钮
	  	
	  	//绑定按钮---start
	  	//查询按钮绑定点击事件
	  	$("#search").bind("click",function(){
	  		search();
	  	});
	  	//新增弹框页面显示前事件
	  	$("#addPageModal").on("show",function(){
	  		getAllRole();
	  	});
	  //新增弹框页面显示后事件
	  	$("#addPageModal").on("shown",function(){
	  		
	  	});
		//新增弹框页面关闭前事件
	  	$("#addPageModal").on("hide",function(){
	  		$("#addUserForm")[0].reset();
	  		removeFormError("addUserForm");//移除error class
	  	});
	  //新增弹框页面关闭后事件
	  	$("#addPageModal").on("hidden",function(){
  		
	  	});
	  	
		//修改弹框页面关闭前事件
	  	$("#updatePageModal").on("hide",function(){
	  		$("#updateUserForm")[0].reset();
	  		removeFormError("updateUserForm");//移除error class
	  	});
	  	
	  	//新增弹框页面，保存按钮绑定点击事件
	  	$("#btn_saveUserInfo").bind("click",function(){
	  		saveUserInfo();
	  	});
		//编辑按钮绑定点击事件
	  	$("#btn_updatePage").bind("click",function(){
	  		getUserInfoById();
	  	});
	  //编辑页面保存按钮绑定点击事件
	  	$("#btn_updateUserInfo").bind("click",function(){
	  		updateUserInfo();
	  	});
	  //删除按钮绑定点击事件
	  	$("#btn_deleteUserInfo").bind("click",function(){
	  		deleteUserInfo();
	  	});
	  	
	 	//添加角色
	  	$("#addRoleBtn").bind("click",function(){
	  		addRole();
	  	});
	  	//删除角色
	  	$("#removeRoleBtn").bind("click",function(){
	  		removeRole();
	  	});
	  	
	  	//绑定按钮--end
	  	
		/**
	 	*查询操作
	 	*/
		function search(){
			jQuery("#userInfo_list").jqGrid('setGridParam',{datatype:'json',page:1,postData:{query_id:"getPageUserInfoList",query_type:"JQGRID",reqData:searchData()}}).trigger("reloadGrid");
		}
	  	/**
	  	 * 保存用户操作
	  	 */
		function saveUserInfo(){
			var checkData=diyValidate("addUserForm");
			if(checkData){
				var roleIds="";
				//获取选中的角色Id
				$("#roleSelect option").each(function(){
					if(roleIds==""){
						roleIds=$(this).val();
					}else{
						roleIds+=","+$(this).val();
					}
				});
				if(roleIds==""){
					layer.alert("请选择角色", {icon:0});// 2：弹框图片为叉叉
					return;
				}
				$("#roleIds").val(roleIds);
				
				var dataObj=$("#addUserForm").serializeJson();
				layer.confirm('确定保存用户？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/userInfoController/json/saveUserInfo",
						type:"post",
						data: {dataObj :dataObj},
						dataType:"json",
						success:function(data){
							if(data.success){
								layer.alert("操作成功！", {icon:1});// 1：弹框图片为勾
								$("#addPageModal").modal("hide");
								search();
							}else{
								layer.alert(data.msg, {icon:2});// 2：弹框图片为叉叉
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							layer.alert("请求服务器异常，请稍后重试", {icon:2});// 2：弹框图片为叉叉
//							 alert(XMLHttpRequest.status);
//							 alert(XMLHttpRequest.readyState);
//							 alert(textStatus);
						}
					});
				 });
			}
	 	}
		
		/**
		 * 根据选中的数据获取用户信息
		 */
		function getUserInfoById(){
	 		var selectedIds = $("#userInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要编辑的数据！",{icon:0});
	  			return;
	  		}
	  		if(selectedIds.length > 1){
	  			layer.alert("只能选择一条数据进行编辑！",{icon:0});
	  			return;
	  		}
  			$.ajax({
  				url:appPath+"/userInfoController/json/getUserInfoById?temp="+new Date(),
  				type:"get",
  				data: {id : selectedIds[0]},
  				dataType:"json",
  				// traditional: true,//传递数组参数
  				success:function(data){
  					if(data.success){
  						var obj=data.obj;
  						$("#update_id").val(obj.id);
  						$("#update_userName").val(obj.userName);
  						$("#update_realName").val(obj.realName);
  						$("#update_phone").val(obj.phone);
  						//两种单选框根据值判断选中的方法
  						$("#updateUserForm input[name=status][value='"+obj.status+"']").attr("checked",true);
//  						$("#updateUserForm").find("input[name=status][value='"+obj.status+"']").attr("checked",true);
  						$("#updatePageModal").modal("show");
  					}else{
  						layer.alert(data.msg, {icon:2});// 2：弹框图片为叉叉
  					}
  				},
  				error: function(XMLHttpRequest, textStatus, errorThrown) {
					layer.alert("请求服务器异常，请稍后重试", {icon:2});// 2：弹框图片为叉叉
				}
  		   });
	 	}
		/**
		 * 修改保存用户信息
		 */
		function updateUserInfo(){
			var checkData=diyValidate("updateUserForm");
			if(checkData){
				var dataObj=$("#updateUserForm").serializeJson();
				layer.confirm('确定修改？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/userInfoController/json/updateUserInfo",
						type:"post",
						data: {dataObj :dataObj},
						dataType:"json",
						success:function(data){
							if(data.success){
								layer.alert("修改成功！", {icon:1});// 1：弹框图片为勾
								$("#updatePageModal").modal("hide");
								search();
							}else{
								layer.alert(data.msg, {icon:2});// 2：弹框图片为叉叉
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
							layer.alert("请求服务器异常，请稍后重试", {icon:2});// 2：弹框图片为叉叉
						}
					});
				 });
			}
	 	}
	  	
		
		/**
		 * 删除用户操作
		 */
		function deleteUserInfo(){
	 		var selectedIds = $("#userInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要删除的数据！",{icon:0});
	  			return;
	  		}
	  		layer.confirm('确定删除选中的数据？', {icon: 3, title:'提示'}, function(index){
	  			 layer.close(index);
	  			$.ajax({
	  				url:appPath+"/userInfoController/json/deleteUserInfo",
	  				type:"post",
	  				data: {ids : JSON.stringify(selectedIds)},
	  				dataType:"json",
	  				// traditional: true,//传递数组参数
	  				success:function(data){
	  					if(data.success){
	  						layer.alert("操作成功！", {icon:1});// 1：弹框图片为勾
	  						search();
	  					}else{
	  						layer.alert(data.msg, {icon:2});// 2：弹框图片为叉叉
	  					}
	  				},
	  				error: function(XMLHttpRequest, textStatus, errorThrown) {
						layer.alert("请求服务器异常，请稍后重试", {icon:2});// 2：弹框图片为叉叉
					}
	  		   });
	  		});
	 	}
	  	
	 /**
	  * 封装查询条件
	  */
	function searchData(){
		var query_userName = $("#query_userName").val();
		var query_realName = $("#query_realName").val();
		var re = "{'groupOp':'AND','rules':[";
		 var str="";
		if(query_userName&&query_userName != ""){
			if(str==""){
				str += "{'field':'userName','op':'eq','data':'"+ query_userName.toUpperCase() +"'}";
			}else{
				str += ",{'field':'userName','op':'eq','data':'"+ query_userName.toUpperCase() +"'}";
			}
		}
		if(query_realName&&query_realName != ""){
			if(str==""){
				str += "{'field':'realName','op':'eq','data':'"+ query_realName+"'}";
			}else{
				str += ",{'field':'realName','op':'eq','data':'"+ query_realName+"'}";
			}
		}
		re = re.concat(str);
		var c= "]}";
		re = re.concat(c);
		return re;
	}
	
	function getAllRole(){
		$("#roleList option").remove();
		$.ajax({
				url:appPath+"/roleController/json/getRoleInfoList?temp="+new Date(),
				type:"get",
				dataType:"json",
				// traditional: true,//传递数组参数
				success:function(data){
					if(data.success){
						$.each(data.obj,function(i,obj){
							$("#roleList").append("<option value='"+obj.id+"'>"+obj.roleName+"</option>");
						});
					}else{
						layer.alert(data.msg, {icon:2});// 2：弹框图片为叉叉
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
				layer.alert("请求服务器异常，请稍后重试", {icon:2});// 2：弹框图片为叉叉
			}
		   });
	}
	
	
	/***添加角色***/
	function addRole(){
		$("#roleList option:selected").each(function(){
			$("#roleSelect").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
			$("#roleList option[value='"+$(this).val()+"']").remove();
		});
	}
	/***移除已选角色***/
	function removeRole(){
		var roleId = $("#roleSelect option:selected").val();
		var roleName = $("#roleSelect option:selected").text();
		$("#roleSelect option:selected").each(function(){
			$("#roleList").append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
			$("#roleSelect option[value='"+$(this).val()+"']").remove();
		});
	}
	
});