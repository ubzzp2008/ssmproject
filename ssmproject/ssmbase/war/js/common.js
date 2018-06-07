var baseUrl = appPath+'/';
In.add('juicer',{path:baseUrl+'js/juicer-min.js',type:'js',charset:'utf-8'});
In.add('jqueryUi',{path:baseUrl+'js/jquery-ui-1.8.min.js',type:'js',charset:'utf-8'});
In.add('shade',{path:baseUrl+'js/shade.js',type:'js',charset:'utf-8'});
In.add('bootstrap',{path:baseUrl+'js/bootstrap.js',type:'js',charset:'utf-8'});
In.add('localeCn',{path:baseUrl+'js/jqGrid/js/i18n/grid.locale-cn.js',type:'js',charset:'utf-8'});
In.add('jqGrid',{path:baseUrl+'js/jqGrid/js/jquery.jqGrid-4.4.3.min.js',type:'js',charset:'utf-8',rely:['jqueryUi','bootstrap','shade','localeCn','jqGrid-css','jqueryUi-css']});
In.add('layer',{path:baseUrl+'js/layer/layer.js',type:'js',charset:'utf-8'});
In.add('modalmanager',{path:baseUrl+'js/bootstrap-modalmanager.js',type:'js',charset:'utf-8'});
In.add('modal',{path:baseUrl+'js/bootstrap-modal.js',type:'js',charset:'utf-8',rely:['modal-css','modalmanager']});
In.add('serializeJson',{path:baseUrl+'js/serializeJson.js',type:'js',charset:'utf-8'});
In.add('tip',{path:baseUrl+'js/diyValidate/js/easy_validator.pack.js',type:'js',charset:'utf-8',rely:['bgiframe','tip-css']});
In.add('bgiframe',{path:baseUrl+'js/diyValidate/js/jquery.bgiframe.min.js',type:'js',charset:'utf-8'});
//In.add('WdatePicker',{path:baseUrl+'js/My97DatePicker/WdatePicker.js',type:'js',charset:'utf-8'});
//In.add('queryDataBox',{path:baseUrl+'js/queryDataBox.js',type:'js',charset:'utf-8',rely:['juicer','jqueryUi','queryDataBox-css','autocomplete','json']});
//In.add('listDataBox',{path:baseUrl+'js/listDataBox.js',type:'js',charset:'utf-8',rely:['juicer','jqueryUi','queryDataBox-css']});
//In.add('scDataBox',{path:baseUrl+'js/scDataBox.js',type:'js',charset:'utf-8',rely:['juicer','jqueryUi','json']});
//In.add('sc2DataBox',{path:baseUrl+'js/sc2DataBox.js',type:'js',charset:'utf-8',rely:['juicer','jqueryUi','json']});
//In.add('multipleDataBox',{path:baseUrl+'js/multipleDataBox.js',type:'js',charset:'utf-8',rely:['jqueryUi']});
//In.add('json',{path:baseUrl+'js/jquery.json-2.4.js',type:'js',charset:'utf-8'});
//In.add('multipleselect',{path:baseUrl+'js/multipleselect/multiple-select.js',type:'js',charset:'utf-8',rely:['multipleselect-css']});
//In.add('autocomplete',{path:baseUrl+'js/autocomplete/jquery.autocomplete.js',type:'js',charset:'utf-8',rely:['autocomplete-css']});
//In.add('dateFormat',{path:baseUrl+'js/dateformat.js',type:'js',charset:'utf-8'});
//In.add('validform',{path:baseUrl+'js/validform/Validform_v5.3.2_min.js',type:'js',charset:'utf-8'});
//In.add('easyUi',{path:baseUrl+'js/easyui/js/jquery.easyui.min.js',type:'js',charset:'utf-8',rely:['easyui-icon-css','easyui-default-css','easyui-themes-default-css','easyUiLoader']});
//In.add('easyUiLoader',{path:baseUrl+'js/easyui/js/easyloader.js',type:'js',charset:'utf-8'});
//In.add('dtree',{path:baseUrl+'js/dtree.js',type:'js',charset:'utf-8',rely:['dtree-css']});
//In.add('ztree',{path:baseUrl+'js/ztree/jquery.ztree-3.5.min.js',type:'js',charset:'utf-8',rely:['ztree-css']});
//In.add('iframe',{path:baseUrl+'js/iframe.js',type:'js',charset:'utf-8'});

//In.add('select',{path:baseUrl+'js/select.js',type:'js',charset:'utf-8'});
//In.add('highcharts',{path:baseUrl+'js/highcharts/highcharts.js',type:'js',charset:'utf-8'});
//In.add('highcharts-3d',{path:baseUrl+'js/highcharts/highcharts-3d.js',type:'js',charset:'utf-8',rely:['highcharts']});
//In.add('highcharts-data',{path:baseUrl+'js/highcharts/modules/data.js',type:'js',charset:'utf-8',rely:['highcharts']});
//In.add('orderStatus',{path:baseUrl+'js/constant/orderStatusConstant.js',type:'js',charset:'utf-8'});
//In.add('uploadBox',{path:baseUrl+'js/uploadBox.js',type:'js',charset:'utf-8',rely:['artDialog','webuploader']});
//In.add('artDialog',{path:baseUrl+'js/artDialog/dialog-min.js',type:'js',charset:'utf-8',rely:['artDialog-css']});
//In.add('webuploader',{path:baseUrl+'js/webuploader/webuploader.js',type:'js',charset:'utf-8',rely:['webuploader-css']});
//In.add('orderType',{path:baseUrl+'js/constant/orderTypeConstant.js',type:'js',charset:'utf-8'});
//In.add('eduPscType',{path:baseUrl+'js/constant/eduProcessConstant.js',type:'js',charset:'utf-8'});

//In.add('cookie',{path:'http://qas.web.cnpdx.cn/js/cookie/jquery.cookie.js',charset:'utf-8'});
//In.add('checkDomain',{path:'http://qas.web.cnpdx.cn/js/cookie/checkDomain.js',type:'js',charset:'utf-8',rely:['cookie']});

In.add('wg-css',{path:baseUrl+'css/wg.css',type:'css'});
In.add('jqueryUi-css',{path:baseUrl+'js/jqGrid/css/ui-lightness/jquery-ui-1.8.14.custom.css',type:'css'});
In.add('jqGrid-css',{path:baseUrl+'js/jqGrid/css/ui.jqgrid.css',type:'css'});
In.add('modal-css',{path:baseUrl+'css/bootstrap-modal.css',type:'css'});
In.add('tip-css',{path:baseUrl+'js/diyValidate/css/validate.css',type:'css'});
//In.add('queryDataBox-css',{path:baseUrl+'css/queryDataBox.css',type:'css'});
//In.add('multipleselect-css',{path:baseUrl+'js/multipleselect/multiple-select.css',type:'css'});
//In.add('autocomplete-css',{path:baseUrl+'js/autocomplete/jquery.autocomplete.css',type:'css'});
//In.add('easyui-default-css',{path:baseUrl+'js/easyui/css/default.css',type:'css'});
//In.add('easyui-themes-default-css',{path:baseUrl+'js/easyui/js/themes/default/easyui.css',type:'css'});
//In.add('easyui-icon-css',{path:baseUrl+'js/easyui/js/themes/icon.css',type:'css'});
//In.add('dtree-css',{path:baseUrl+'css/dtree.css',type:'css'});
//In.add('ztree-css',{path:baseUrl+'js/ztree/zTreeStyle/zTreeStyle.css',type:'css'});
//In.add('checkboxAll',{path:baseUrl+'js/checkboxAll.js',type:'js',charset:'utf-8'});
//In.add('artDialog-css',{path:baseUrl+'js/artDialog/css/art-dialog.css',type:'css'});
//In.add('webuploader-css',{path:baseUrl+'js/webuploader/css/webuploader.css',type:'css'});

//In.ready('bootstrap','jqueryUi','layer','modal','wg-css','checkDomain');
In.ready('bootstrap','jqueryUi','layer','modal','wg-css');

/*

 */
window.onload = function() {
	WGinit()
};

function WGinit(){
	if(!$){
		WGinit()
	}else{
		WG.init();
	}
};

window.WG = {
	init: function(T) {
		T = T || this;
		for (var p in T) {
			T[p] && T[p].init && T[p].init();
		};
	},
	stopBubble: function(e) {
		// 如果传入了事件对象，那么就是非ie浏览器  
		if (e && e.stopPropagation) {
			//因此它支持W3C的stopPropagation()方法  
			e.stopPropagation();
		} else {
			//否则我们使用ie的方法来取消事件冒泡  
			window.event.cancelBubble = true;
		}
	}
};


WG.tabSetUrl = {
	init: function(){
		this.load()
	},
	load: function(callback){
		//tab判断锚点id，根据id显示不同的标签页
		var thisId = window.location.hash;
		In.ready('bootstrap',function() {
			$('a[data-toggle="tab"]').on('show', function() {
				var url = window.location.href;
				var m = url.split("#");
				url = m[0] + $(this).attr("href") + "_Area";
				window.location.href = url;
				WG.tabSetUrl.tabId = $(this).attr("href");
			});
			thisId = thisId.replace("_Area","")
			if (thisId && thisId != "") {
				$('a[href= "'+ thisId +'" ]').tab('show');
				WG.resizeGrid.show();
				WG.tabSetUrl.tabId = thisId;
			}
		})
	}
};

// WG.load = function(callback){
// 	return WG.assets.load(callback)
// }


/**
 * [Grid resize的时候重置宽度]
 * @type {Object}
 */
WG.resizeGrid = {
	init: function() {
		this.show()
	},
	show: function() {
		In.ready('jqGrid',function() {
			$(".table-block").setGridWidth($(".container_center").width());
			$(window).resize(function() {
				$(".table-block").setGridWidth($(".container_center").width());
			});
		})
	}
};

/*
Menu滑动
 */
WG.menuHover = {
	init: function(){
		var $dropdownLi = $('li.dropdown');
		$dropdownLi.hover(function(){
			$(this).children('ul').stop(true,true).show('slow');
		},function(){
			$(this).children('ul').stop(true,true).hide(0,'slow');
		});
	}
}


/**
 * [gotoNextCell 表格里面输入框，按回车跳到下一个输入框]
 * @param  {[type]} obj [当前table Jquery对象，如$("#table")]
 */
WG.gotoNextCell = function(obj){
	var $tablegrid = obj || null;
	if(!$tablegrid) return;
	$tablegrid.find("input[type='text']").live("keyup",function(e){
		var $this = $(this);
		var rows = $tablegrid.jqGrid("getRowData");
		if (event.keyCode==13) {
			var _id = $this.attr("id");
			var _idarr = _id.split("_");
			var _idname = _idarr[1];
			var _idnum = Number(_idarr[0]);
			var _nextid = null;

			for (var i = 0; i < rows.length; i++) {
				if(rows[i].id == _idnum ){
					if(i != rows.length-1){
						_nextid = rows[i+1].id
					}
				}
			};
			$("#"+_nextid+"_"+_idname).click().focus();
		}
	})
}


WG.checkitem = {
	init: function(){
		this.run()
	},
	run: function(){
		$(".all-check").bind("click",this.checkall);
		$(".single-check").bind("click",this.checksingle);
	},
	checkall: function(){
		var $this = $(this);
		var $singlecheck = $this.parent().parent().find(".single-check");
		if($this.prop("checked")){
			$singlecheck.prop("checked", true)
		}else{
			$singlecheck.prop("checked", false)
		}
	},
	checksingle: function(){
		$this = $(this);
		var $singlecheck = $this.parent().parent().find(".single-check");
		var $allcheck =  $this.parent().parent().find(".all-check");
		var _checkall = true;
		if(!$this.prop("checked")){
			$allcheck.prop("checked",false)
		}else{
			$singlecheck.each(function(){
				if(!$(this).prop("checked")){
					$allcheck.prop("checked",false);
					_checkall = false;
				}
			})
			if(_checkall){$allcheck.prop("checked",true)}
		}
	}
};
/*
WG.checkRequest = {
	init: function(){
		//this.run()
	},
	run: function(){
		var _this = this;
		var _loadinghtml = [
			'<div class="loadingbox" style="display:none">',
			'加载中',
			'</div>'].join(",");
		if($(".loadingbox").length == 0){
			$("body").append(_loadinghtml);

		};
		console.log("listen start")
		$(".loadingbox").ajaxStart(function(){
			$(this).show();
			$("body").css("overflow","hidden")
			console.info(1)
		}).ajaxStop(function(){
			_this.hide();
			console.info(2)
		});
		//alert(1);
		//init();
	},
	hide: function(){
		$(".loadingbox").hide();
	}
};
*/
WG.loading = {
	show: function(){
		var _this = this;
		var _loadinghtml = [
			'<div class="loadingbg" style=""></div>',
			'<div class="loadingtext"><img src="'+ baseUrl +'img/loading.gif">&nbsp;&nbsp;处理中，请稍候...</div>'].join("");
		if($(".loadingbox").length == 0){
			var $loadingbox = $("<div class='loadingbox'></div>");
			$loadingbox.html(_loadinghtml);
			$("body").append($loadingbox);
		};
		$(".loadingbox").fadeIn(500);
		$("body").css("overflow","hidden");
	},
	hide: function(){
		$(".loadingbox").fadeOut(500);
		$("body").css("overflow","");
	}
}
