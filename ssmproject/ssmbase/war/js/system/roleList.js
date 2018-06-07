In.ready('jqGrid','tip','serializeJson',function() {
	  	$("#roleInfo_list").jqGrid({ 
			url:appPath+'/console/query', 
			autowidth: true, //自适应宽度
	 		shrinkToFit: true, //列自适应
// 			forceFit:true,
			datatype: "json", //数据格式
			mtype:"POST",
			colNames:['ID','角色名称','角色描述','状态','创建人','创建时间','修改人','修改时间'], //列配置,必须和下面的colModel数量匹配
			colModel:[
				{name:'id',index:'id', width:60, hidden:true,sortable:false},
				{name:'roleName',index:'roleName', align:'center', width:140},
				{name:'roleDesc',index:'roleDesc',align:'center', width:140},
				{name:'status',index:'status', width:90, align:'center', resizable:false,sortable:false,
				 	formatter : function(value, options, rData){
						if (value==0) {
							return "<font color='gree'>启用</font>";
						}
						return "<font color='red'>停用</font>";
					}
				},
				{name:'addUser',index:'addUser',align:'center'},
				{name:'addDate',index:'addDate',align:'center'},
				{name:'updateUser',index:'updateUser',align:'center'},
				{name:'updateDate',index:'updateDate',align:'center'}
				],
		   	rowNum:20,  //默认分页行数
			rowList:[20,50,100],  //可选分页行数
			pager: '#roleInfo_page',  //分页按钮展示地址
			sortname: 'id',  //默认排序字段
			viewrecords: true,   //是否显示数据总条数
			multiselect:true,  //复选框
			rownumbers: true,    //显示左边排名列表
			sortorder: "desc",  //默认排序方式
			jsonReader: { 
				  root: "rows",
				  page: "page",
				  total: "total",
				  records: "records",
				  repeatitems : false
			}, //设置数据方式
			postData: {query_id:"getPageRoleList",query_type:"JQGRID",rdParseType:"dispersed",reqData:searchData()},
			height: '100%',
			/* 客户端排序--------------------------------------------------------开始 */
			loadBeforeSend:function(){
	             $("#roleInfo_list").jqGrid('clearGridData');
		    },
			onPaging : function(pgButton){
//				$("#menu_list").jqGrid('setGridParam',{datatype:'json',postData:{query_id:"getPageMenuList",query_type:"JQGRID",reqData:searchData()}});
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
	  	$("#roleInfo_list").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });  //jqgrid取消掉水平滚动条
	  	$("#roleInfo_list").jqGrid('navGrid','#roleInfo_page',{edit:false,add:false,del:false,search:false,refresh:true});//左下角的操作按钮
	  	
	  	//绑定按钮---start
	  	//查询按钮绑定点击事件
	  	$("#search").bind("click",function(){
	  		search();
	  	});
	  	//新增弹框页面显示前事件
	  	$("#addPageModal").on("show",function(){
	  		
	  	});
	  //新增弹框页面显示后事件
	  	$("#addPageModal").on("shown",function(){
	  		
	  	});
		//新增弹框页面关闭前事件
	  	$("#addPageModal").on("hide",function(){
	  		$("#addRoleForm")[0].reset();
	  		removeFormError("addRoleForm");//移除error class
	  	});
	  //新增弹框页面关闭后事件
	  	$("#addPageModal").on("hidden",function(){
  		
	  	});
	  	
		//修改弹框页面关闭前事件
	  	$("#updatePageModal").on("hide",function(){
	  		$("#updateRoleForm")[0].reset();
	  		removeFormError("updateRoleForm");//移除error class
	  	});
	  	//新增弹框页面，保存按钮绑定点击事件
	  	$("#btn_saveRoleInfo").bind("click",function(){
	  		saveRoleInfo();
	  	});
		//编辑按钮绑定点击事件
	  	$("#btn_updatePage").bind("click",function(){
	  		getRoleInfoById();
	  	});
	  //编辑页面保存按钮绑定点击事件
	  	$("#btn_updateRoleInfo").bind("click",function(){
	  		updateRoleInfo();
	  	});
	  //删除按钮绑定点击事件
	  	$("#btn_deleteRoleInfo").bind("click",function(){
	  		deleteRoleInfo();
	  	});
	  	//绑定按钮--end
	  	
		/**
	 	*查询操作
	 	*/
		function search(){
			jQuery("#roleInfo_list").jqGrid('setGridParam',{datatype:'json',page:1,postData:{query_id:"getPageRoleList",query_type:"JQGRID",reqData:searchData()}}).trigger("reloadGrid");
		}
		
	  	/**
	  	 * 保存菜单操作
	  	 */
		function saveRoleInfo(){
			var checkData=diyValidate("addRoleForm");
			if(checkData){
				var dataObj=$("#addRoleForm").serializeJson();
				layer.confirm('确定保存？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/roleController/json/saveRoleInfo",
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
						}
					});
				 });
			}
	 	}
		
		/**
		 * 根据选中的数据获取菜单信息
		 */
		function getRoleInfoById(){
	 		var selectedIds = $("#roleInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要编辑的数据！",{icon:0});
	  			return;
	  		}
	  		if(selectedIds.length > 1){
	  			layer.alert("只能选择一条数据进行编辑！",{icon:0});
	  			return;
	  		}
  			$.ajax({
  				url:appPath+"/roleController/json/getRoleInfoById?temp="+new Date(),
  				type:"get",
  				data: {id : selectedIds[0]},
  				dataType:"json",
  				// traditional: true,//传递数组参数
  				success:function(data){
  					if(data.success){
  						var obj=data.obj;
  						$("#update_id").val(obj.id);
  						$("#update_roleName").val(obj.roleName);
  						$("#update_roleDesc").val(obj.roleDesc);
  						//两种单选框根据值判断选中的方法
  						$("#updateRoleForm input[name=status][value='"+obj.status+"']").attr("checked",true);
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
		 * 修改保存菜单信息
		 */
		function updateRoleInfo(){
			var checkData=diyValidate("updateRoleForm");
			if(checkData){
				var dataObj=$("#updateRoleForm").serializeJson();
				layer.confirm('确定修改？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/roleController/json/updateRoleInfo",
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
		 * 删除菜单操作
		 */
		function deleteRoleInfo(){
	 		var selectedIds = $("#roleInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要删除的数据！",{icon:0});
	  			return;
	  		}
	  		layer.confirm('确定删除选中的菜单？', {icon: 3, title:'提示'}, function(index){
	  			 layer.close(index);
	  			$.ajax({
	  				url:appPath+"/roleController/json/deleteRoleInfo",
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
		var query_name = $("#query_name").val();
		var re = "{'groupOp':'AND','rules':[";
		 var str="";
		if(query_name&&query_name != ""){
			if(str==""){
				str += "{'field':'roleName','op':'eq','data':'"+ query_name +"'}";
			}else{
				str += ",{'field':'roleName','op':'eq','data':'"+ query_name +"'}";
			}
		}
		re = re.concat(str);
		var c= "]}";
		re = re.concat(c);
		return re;
	}
	
});