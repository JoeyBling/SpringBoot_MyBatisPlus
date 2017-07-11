// 命名空间定义
var wstro = window.NameSpace || {};

wstro.base = "/";
wstro.url = "http://dev2.66666684.cn/";

// 工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};
T.p = url;

// 全局配置
$.ajaxSetup({
	dataType : "json",
	contentType : "application/json",
	error : function(jqXHR, textStatus, errorThrown) {
		// 取消进度条
		wstro.progressBarShutDown();
		switch (jqXHR.status) {
		case (500):
			layer.alert('服务器系统内部错误', {
				icon : 2
			});
			break;
		case (401):
			layer.alert('未登录', {
				icon : 2
			});
			break;
		case (403):
			layer.alert('无权限执行此操作', {
				icon : 2
			});
			break;
		case (408):
			layer.alert('请求超时', {
				icon : 2
			});
			break;
		default:
			layer.alert('未知错误,请联系管理员', {
				icon : 2
			});
		}
	},
	cache : false
});

// 取得地址栏参数
var Request = {
	QueryString : function(item, paramurl) {
		var reg = new RegExp("(^|&)" + item + "=([^&]*)(&|$)", "i");
		var r = paramurl.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
}

// 取得顶窗口地址栏参数
var topRequest = {
	QueryString : function(item) {
		return Request.QueryString(item, top.location.search);
	}
}

/**
 * 替换字符串
 */
var replace = {
	/**
	 * 所有\改为/
	 */
	FilePath : function(item) {
		return item.replace(/\\/g, "/");
	}
}

// 禁用F5,退格键
function forbidF5(event) {
	var event = event || window.event;
	var evtnode = event.srcElement ? event.srcElement : event.target;
	var key = event.keyCode || event.which;

	// 116 F5 117 F6 8 BACKSPACE
	if (key == 116
			|| key == 117
			|| (key == 8 && evtnode.nodeName != "TEXTAREA" && evtnode.nodeName != "INPUT")) {
		eventstop(event);
	} else {
		return true;
	}
}

function eventstop(event) {
	try {
		event.keyCode = 0;
	} catch (err) {
	}
	try {
		event.preventDefault();
	} catch (err) {
	}
	try {
		event.stopPropagation();
	} catch (err) {
	}
	try {
		event.returnValue = false;
	} catch (err) {
	}
	try {
		event.cancelBubble = true;
	} catch (err) {
	}
	return false;
}

/**
 * Button交互
 * 
 * @param {}
 *            e
 */
function loadingButton(e) {
	var $btn = $(e);
	$btn.attr("disabled", "disabled");
	$btn.button('loading');
	setTimeout(function() {
		$btn.removeAttr("disabled");
		$btn.button('reset');
	}, 2000);
}

/**
 * 时间倒计时
 * 
 * @param {}
 *            x 倒计时最后开始分
 * @param {}
 *            y 倒计时最后开始秒
 * @param {}
 *            element 倒计时显示的元素
 */
function countdown(x, y, element) {
	var d = new Date("1111/1/1,0:" + x + ":" + y);
	var interval = setInterval(function() {
		var m = d.getMinutes();
		var s = d.getSeconds();
		m = m < 10 ? "0" + m : m;
		s = s < 10 ? "0" + s : s;
		$(element).text(m + "分" + s + "秒");
		if (m == 0 && s == 0) {
			clearInterval(interval);
			return;
		}
		d.setSeconds(s - 1);
	}, 1000);
}

/**
 * 将Date转为yyy-MM-dd HH:mm:ss时间格式
 * 
 * @param {}
 *            now
 * @return {}
 */
function formatDate(now) {
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
	if (second >= 1 && second <= 9) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + date + " " + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}

$(function() {
	// 折叠ibox
	$('.collapse-link').click(function() {
		var ibox = $(this).closest('div.ibox');
		var button = $(this).find('i');
		var content = ibox.find('div.ibox-content');
		content.slideToggle(200);
		button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
		ibox.toggleClass('').toggleClass('border-bottom');
		setTimeout(function() {
			ibox.resize();
			ibox.find('[id^=map-]').resize();
		}, 50);
	});

	// 关闭ibox
	$('.close-link').click(function() {
		var content = $(this).closest('div.ibox');
		content.remove();
	});
});

/**
 * 弹出层 title 标题 element 显示的元素 w 弹出层宽度（缺省调默认值） h 弹出层高度（缺省调默认值）
 */
function layer_show(title, element, w, h) {
	if (title == null || title == '') {
		title = false;
	}
	if (w == null || w == '') {
		w = 800;
	}
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	}
	layer.open({
		type : 1,
		area : [ w + 'px', '90%' ],
		shadeClose : true,
		shade : 0.2,
		anim : 1, // 0-6的动画形式，-1不开启
		maxmin : true, // 开启最大化最小化按钮
		fix : false, // 不固定
		scrollbar : false, // 屏蔽游览器滚动条
		title : title,
		content : $(element)
	});
}

/**
 * 弹出层 title 标题 url 请求的url w 弹出层宽度（缺省调默认值） h 弹出层高度（缺省调默认值）
 */
function layer_show_url(title, url, w, h) {
	if (title == null || title == '') {
		title = false;
	}
	if (w == null || w == '') {
		w = 800;
	}
	if (h == null || h == '') {
		h = ($(window).height() - 50);
	}
	layer.open({
		type : 2,
		area : [ w + 'px', h + 'px' ],
		shadeClose : true,
		shade : false,
		anim : 1, // 0-6的动画形式，-1不开启
		maxmin : true, // 开启最大化最小化按钮
		fix : false, // 不固定
		scrollbar : false, // 屏蔽游览器滚动条
		title : title,
		content : url
	});
}

/**
 * bootstrapValidator
 * 
 * @param event
 * @returns boolean
 */
function validateForm(event) {
	$(event).data("bootstrapValidator").resetForm();
	$(event).bootstrapValidator('validate');
	return $(event).data('bootstrapValidator').isValid();
}

// BootStrapTable公用
// 加载服务器数据之前的处理程序，可以用来格式化数据。参数：data为从服务器请求到的数据。
function defaultBootstarpTableHandler(data) {
	if (data.code != 0) {
		layer.alert(data.msg, {
			icon : 2
		});
		return;
	}
	return {
		"total" : data.page.totalCount,// 总记录数
		"rows" : data.page.list
	// 数据
	};
}

/**
 * BootStrap默认日期格式化
 * 
 * @param {}
 *            uuix 时间戳(秒)
 * @return {} yyyy-MM-dd HH:MM
 */
function BootstrapTableformatDate(uuix) {
	if (null == uuix || "" == uuix) {
		return null;
	}
	var now = new Date(uuix * 1000);
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	if (hour >= 1 && hour <= 9) {
		hour = "0" + hour;
	}
	if (minute >= 0 && minute <= 9) {
		minute = "0" + minute;
	}
	if (second >= 1 && second <= 9) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + date + " " + (hour == 0 ? "00" : hour)
			+ ":" + (minute == 0 ? "00" : minute);
}

// JS进度条
// 开始
wstro.progressBarStartUp = function(type) {
	if (typeof NProgress != "undefined") {
		if (type === 1) {
			NProgress.start();
		} else {
			NProgress.inc();
		}
	}
};
// 开始到指定位置 0-1
wstro.progressBarSet = function(speed) {
	if (typeof NProgress != "undefined") {
		NProgress.set(speed);
	}
};
// 结束
wstro.progressBarShutDown = function() {
	if (typeof NProgress != "undefined") {
		NProgress.done();
	}
};