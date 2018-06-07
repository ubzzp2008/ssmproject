$(function(){
	createShade();
})

var timeId = -1;

function showShade(isTimer,_elementId) //全屏幕遮罩
{
	var isTimer = isTimer || false;
	var elementId = _elementId != null && _elementId != undefined ? _elementId : "";
	_showShade(
		);
	if (isTimer) {
		timeId = setInterval("clearTimer()", 3000);
	}
};

function createShade () {
	var shadeHtml = [
		'<div id="shadeDiv" class="shadeDiv"></div>',
		'<div id="loadingDiv" class="loadingDiv">',
		'	<img src="'+appPath+'/img/loading.gif" style="vertical-align:middle;" ><span>&nbsp;&nbsp;处理中，请稍候...</span>',
		'</div>'].join("");

	var $shadeBox = $('#shadeBox');
	if($shadeBox.length == 0){
		var $sbox = $("<div id='shadeBox'></div>");
		$sbox.html(shadeHtml);
		$("body").append($sbox);
	}		
}

function clearTimer() { // 从后台获取计时器结束状态
	jQuery.ajax({
		type: "POST",
		url: appPath + "/getPageTimerStatus",
		dataType: "json",
		success: function(json) {
			if (json.status == "1") {
				if (-1 != timeId) {
					clearInterval(timeId);
				}
				hideShade();
			} else if (json.status == "9"){
				if (-1 != timeId) {
					clearInterval(timeId);
				}
				hideShade();
				alert(json.message);
			}
		},
		error: function() {
			/*if (-1 != timeId) {
				clearInterval(timeId);
			}*/
		}
	});
}

function hideShade() //取消屏幕遮罩 
{
	//var range = getRange();
	var $shadeDiv = $('#shadeDiv');
	var $loadingDiv = $('#loadingDiv');
	$shadeDiv.hide();
	$loadingDiv.hide();
}
//modified by weicd @v3.1 @2012-12-12
function _showShade(elementId) {
	var range = getRange(elementId);

	var shadeDiv = document.getElementById('shadeDiv');
	var loadingDiv = document.getElementById('loadingDiv');

	/*shadeDiv.style.width = range.width + "px";
	shadeDiv.style.height = range.height + range.top + "px";
	shadeDiv.style.display = "block";
	loadingDiv.style.display = "block";*/
	
	if (null != elementId && "" != elementId) {
		shadeDiv.style.width = range.width + "px";
		shadeDiv.style.height = range.height + "px";
		shadeDiv.style.top = range.top + "px";
		shadeDiv.style.left = range.left + "px";
	}else{
		shadeDiv.style.width = range.width + range.left + "px";
		shadeDiv.style.height = range.height + "px";
	}
	//shadeDiv.style.zIndex = getOverlayMaxzIndex() + 2;
	shadeDiv.style.display = "block";
	
	var loadingDivRange = getInfoDisplayRange(range,180,55);
	//var zindex = getOverlayzIndex(this.overlay.overlayDiv);
	//setOverlayzIndex(this.loadingDiv, zindex + 1);
	loadingDiv.style.top = loadingDivRange.top +"px";
	loadingDiv.style.left = loadingDivRange.left +"px";
	loadingDiv.style.display = "block";
}

/**
 * modified by weicd @v3.1 @2012-12-12
 * 得到屏幕遮罩位置和大小
 * @param $_elementId
 * @return
 */
function getRange($_elementId) 
{
	var top = 0;
	var left = 0;
	var height = 0;
	var width = 0;

	if (undefined != $_elementId && null != $_elementId && "" != $_elementId) {
		var _element = document.getElementById($_elementId);
		height = _element.offsetHeight;
		width = _element.offsetWidth;
		top = _element.offsetTop;
		left = _element.offsetLeft;
		var _parent=_element.offsetParent;
		while(_parent){
			top = top + _parent.offsetTop;
		    left = left + _parent.offsetLeft;
		    _parent = _parent.offsetParent;
		}
	}else{
		top = document.body.scrollTop;
		left = document.body.scrollLeft;
		height = document.body.clientHeight < window.innerHeight ?  window.innerHeight : document.getElementsByTagName("html")[0].offsetHeight;;
		var sa = document.getElementsByTagName("html")[0].offsetHeight;
		//if(height < window.innerHeight) height = window.innerHeight
		width = document.body.clientWidth;

		if (top == 0 && left == 0 && height == 0 && width == 0) {
			top = document.documentElement.scrollTop;
			left = document.documentElement.scrollLeft;
			height = document.documentElement.clientHeight;
			width = document.documentElement.clientWidth;
		}
	}
		
	return {
		top : top,
		left : left,
		height : height,
		width : width
	};
}
/**
 * added by weicd @v3.1 @2012-12-12
 * 获取遮罩层上显示信息的位置
 * @param range 遮罩层的位置
 * @param width
 * @param height
 * @return
 */
function getInfoDisplayRange(range,width,height) 
{
	//var range = getRange(overlay.elementId);
	return{
		top : range.top + window.innerHeight/2 - parseInt(height || 0),
		left : range.left + range.width/2 - parseInt(width || 0)/2
	};
}