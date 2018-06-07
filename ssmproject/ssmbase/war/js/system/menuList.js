In.ready('jqGrid','tip','serializeJson',function() {
	  	$("#menuInfo_list").jqGrid({ 
			url:appPath+'/console/query', 
			autowidth: true, //自适应宽度
	 		shrinkToFit: true, //列自适应
// 			forceFit:true,
			datatype: "json", //数据格式
			mtype:"POST",
			colNames:['ID','菜单名称','菜单路径','描述','层级','显示顺序','父级ID','父级菜单','状态','创建人','创建时间','修改人','修改时间'], //列配置,必须和下面的colModel数量匹配
			colModel:[
				{name:'id',index:'id', width:60, hidden:true,sortable:false},
				{name:'name',index:'name', align:'center', width:140},
				{name:'url',index:'url', width:200},
				{name:'description',index:'description',align:'center', width:140},
				{name:'levele',index:'levele',align:'center', width:140},
				{name:'seq',index:'seq',align:'center', width:140},
				{name:'pid',index:'pid',align:'center', hidden:true},
				{name:'pname',index:'pname',align:'center', width:140},
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
			pager: '#menuInfo_page',  //分页按钮展示地址
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
			postData: {query_id:"getPageMenuList",query_type:"JQGRID",rdParseType:"dispersed",reqData:searchData()},
			height: '100%',
			/* 客户端排序--------------------------------------------------------开始 */
			loadBeforeSend:function(){
	             $("#menuInfo_list").jqGrid('clearGridData');
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
	  	$("#menuInfo_list").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });  //jqgrid取消掉水平滚动条
	  	$("#menuInfo_list").jqGrid('navGrid','#menuInfo_page',{edit:false,add:false,del:false,search:false,refresh:true});//左下角的操作按钮
	  	
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
	  		$("#addMenuForm")[0].reset();
	  		removeFormError("addMenuForm");//移除error class
	  	});
	  //新增弹框页面关闭后事件
	  	$("#addPageModal").on("hidden",function(){
  		
	  	});
	  	
		//修改弹框页面关闭前事件
	  	$("#updatePageModal").on("hide",function(){
	  		$("#updateMenuForm")[0].reset();
	  		removeFormError("updateMenuForm");//移除error class
	  	});
	  	//绑定菜单层级的onchange事件
	  	$("#levele").bind("change",function(){
	  		var va=$(this).val();
	  		//如果选择的是二级菜单，则需要获取所有的一级菜单出来
	  		if(va==2){
	  			$("#url").attr("reg","^.+$");//新增字段控制属性
	  			$("#pid").attr("reg","^.+$");//新增字段控制属性
	  			$("#url_div").css("display","block");//显示菜单链接输入框
	  			$("#pid_select").css("display","block");//显示父级菜单下拉框
	  			getMenuByLevele("1","pid","");//获取第一级菜单
	  		}else{
	  			$("#pid option[value!='']").remove(); //清楚父级下拉框的所有值
	  			$("#url").removeAttr("reg");//移除菜单链接地址输入控制属性
	  			$("#pid").removeAttr("reg");//移除父级菜单下拉框的输入控制属性
	  			removeFormError("addMenuForm");//去掉error样式
	  			$("#url_div").css("display","none");//隐藏菜单链接地址输入框
	  			$("#pid_select").css("display","none");//隐藏父级菜单下拉框
	  		}
	  	});
	  //绑定菜单层级的onchange事件
	  	$("#update_levele").bind("change",function(){
	  		var va=$(this).val();
	  		//如果选择的是二级菜单，则需要获取所有的一级菜单出来
	  		if(va==2){
	  			$("#update_url").attr("reg","^.+$");
	  			$("#update_pid").attr("reg","^.+$");
	  			$("#update_url_div").css("display","block");
	  			$("#update_pid_select").css("display","block");
	  			getMenuByLevele("1","update_pid","");
	  		}else{
	  			$("#update_pid option[value!='']").remove(); 
	  			$("#update_url").removeAttr("reg");
	  			$("#update_pid").removeAttr("reg");
	  			removeFormError("updateMenuForm");
	  			$("#update_url_div").css("display","none");
	  			$("#update_pid_select").css("display","none");
	  		}
	  	});
	  	//新增弹框页面，保存按钮绑定点击事件
	  	$("#btn_saveMenuInfo").bind("click",function(){
	  		saveMenuInfo();
	  	});
		//编辑按钮绑定点击事件
	  	$("#btn_updatePage").bind("click",function(){
	  		getMenuInfoById();
	  	});
	  //编辑页面保存按钮绑定点击事件
	  	$("#btn_updateMenuInfo").bind("click",function(){
	  		updateMenuInfo();
	  	});
	  //删除按钮绑定点击事件
	  	$("#btn_deleteMenuInfo").bind("click",function(){
	  		deleteMenuInfo();
	  	});
	  	//绑定按钮--end
	  	
		/**
	 	*查询操作
	 	*/
		function search(){
			jQuery("#menuInfo_list").jqGrid('setGridParam',{datatype:'json',page:1,postData:{query_id:"getPageMenuList",query_type:"JQGRID",reqData:searchData()}}).trigger("reloadGrid");
		}
		
		/**
		 * 获取一级菜单
		 * d：层级值
		 * id：下拉框ID
		 * va : 下拉框值
		 */
		function getMenuByLevele(d,id,va){
			$("#"+id+" option[value!='']").remove(); 
			$.ajax({
				url:appPath+"/menuController/json/getMenuByLevele",
				type:"get",
				data: {levele : d},
				dataType:"json",
				success:function(data){
					if(data.success){
						$.each(data.obj,function(i,obj){
							if(obj.id==va){
								$("#"+id).append("<option value='"+obj.id+"' selected>"+obj.name+"</option>"); 
							}else{
								$("#"+id).append("<option value='"+obj.id+"'>"+obj.name+"</option>"); 
							}
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
		
	  	/**
	  	 * 保存菜单操作
	  	 */
		function saveMenuInfo(){
			var checkData=diyValidate("addMenuForm");
			if(checkData){
				var dataObj=$("#addMenuForm").serializeJson();
				layer.confirm('确定保存用户？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/menuController/json/saveMenuInfo",
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
		function getMenuInfoById(){
	 		var selectedIds = $("#menuInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要编辑的数据！",{icon:0});
	  			return;
	  		}
	  		if(selectedIds.length > 1){
	  			layer.alert("只能选择一条数据进行编辑！",{icon:0});
	  			return;
	  		}
  			$.ajax({
  				url:appPath+"/menuController/json/getMenuInfoById?temp="+new Date(),
  				type:"get",
  				data: {id : selectedIds[0]},
  				dataType:"json",
  				// traditional: true,//传递数组参数
  				success:function(data){
  					if(data.success){
  						var obj=data.obj;
  						$("#update_id").val(obj.id);
  						$("#update_name").val(obj.name);
  						$("#update_levele").val(obj.levele);
  						$("#update_url").val(obj.url);
  						$("#update_seq").val(obj.seq);
  						$("#update_description").val(obj.description);
  						//两种单选框根据值判断选中的方法
  						$("#updateMenuForm input[name=status][value='"+obj.status+"']").attr("checked",true);
//  						$("#updateUserForm").find("input[name=status][value='"+obj.status+"']").attr("checked",true);
  						//如果是二级菜单，则显示父级菜单和菜单路径
  						if(obj.levele==2){
  							$("#update_url").attr("reg","^.+$");
  				  			$("#update_pid").attr("reg","^.+$");
  				  			$("#update_url_div").css("display","block");
  				  			$("#update_pid_select").css("display","block");
  							getMenuByLevele("1","update_pid",obj.pid);
  						}else{
  							$("#update_pid option[value!='']").remove(); 
  				  			$("#update_url").removeAttr("reg");
  				  			$("#update_pid").removeAttr("reg");
  				  			removeFormError("updateMenuForm");
  				  			$("#update_url_div").css("display","none");
  				  			$("#update_pid_select").css("display","none");
  						}
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
		function updateMenuInfo(){
			var checkData=diyValidate("updateMenuForm");
			if(checkData){
				var dataObj=$("#updateMenuForm").serializeJson();
				layer.confirm('确定修改？', {icon: 3, title:'提示'}, function(index){
					 layer.close(index);
					$.ajax({
						url:appPath+"/menuController/json/updateMenuInfo",
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
		function deleteMenuInfo(){
	 		var selectedIds = $("#menuInfo_list").jqGrid("getGridParam", "selarrrow");
	  		if(selectedIds.length < 1){
	  			layer.alert("请选择要删除的数据！",{icon:0});
	  			return;
	  		}
	  		layer.confirm('确定删除选中的菜单？', {icon: 3, title:'提示'}, function(index){
	  			 layer.close(index);
	  			$.ajax({
	  				url:appPath+"/menuController/json/deleteMenuInfo",
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
		var query_url = $("#query_url").val();
		var query_pname = $("#query_pname").val();
		var re = "{'groupOp':'AND','rules':[";
		 var str="";
		if(query_name&&query_name != ""){
			if(str==""){
				str += "{'field':'name','op':'eq','data':'"+ query_name +"'}";
			}else{
				str += ",{'field':'name','op':'eq','data':'"+ query_name +"'}";
			}
		}
		if(query_url&&query_url != ""){
			if(str==""){
				str += "{'field':'url','op':'eq','data':'"+ query_url+"'}";
			}else{
				str += ",{'field':'url','op':'eq','data':'"+ query_url+"'}";
			}
		}
		if(query_pname&&query_pname != ""){
			if(str==""){
				str += "{'field':'pname','op':'eq','data':'"+ query_pname+"'}";
			}else{
				str += ",{'field':'pname','op':'eq','data':'"+ query_pname+"'}";
			}
		}
		re = re.concat(str);
		var c= "]}";
		re = re.concat(c);
		return re;
	}
	
});