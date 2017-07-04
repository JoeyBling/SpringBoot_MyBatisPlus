/*
 *
 *   H+ - 后台主题UI框架
 *   version 4.5
 *
 */
//自定义js
//公共配置
$(document).ready(function() {
	// MetsiMenu
	$('#side-menu').metisMenu();

	// 打开右侧边栏
	$('.right-sidebar-toggle').click(function() {
		$('#right-sidebar').toggleClass('sidebar-open');
	});

	// 右侧边栏使用slimscroll
	$('.sidebar-container').slimScroll({
		height : '100%',
		railOpacity : 0.4,
		wheelStep : 10
	});

	// Small todo handler
	$('.check-link').click(function() {
		var button = $(this).find('i');
		var label = $(this).next('span');
		button.toggleClass('fa-check-square').toggleClass('fa-square-o');
		label.toggleClass('todo-completed');
		return false;
	});

	// 固定菜单栏
	$(function() {
		$('.sidebar-collapse').slimScroll({
			height : '100%',
			railOpacity : 0.9,
			alwaysVisible : false
		});
	});

	// 菜单切换
	$('.navbar-minimalize').click(function() {
		$("body").toggleClass("mini-navbar");
		SmoothlyMenu();
	});

	// 侧边栏高度
	function fix_height() {
		var heightWithoutNavbar = $("body > #wrapper").height() - 61;
		$(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
	}
	fix_height();

	$(window).bind("load resize click scroll", function() {
		if (!$("body").hasClass('body-small')) {
			fix_height();
		}
	});

	// 侧边栏滚动
	$(window).scroll(function() {
		if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
			$('#right-sidebar').addClass('sidebar-top');
		} else {
			$('#right-sidebar').removeClass('sidebar-top');
		}
	});

	$('.full-height-scroll').slimScroll({
		height : '100%'
	});

	$('#side-menu>li').click(function() {
		if ($('body').hasClass('mini-navbar')) {
			NavToggle();
		}
	});
	$('#side-menu>li li a').click(function() {
		if ($(window).width() < 769) {
			NavToggle();
		}
	});

	$('.nav-close').click(NavToggle);

	// ios浏览器兼容性处理
	if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
		$('#content-main').css('overflow-y', 'auto');
	}

});

$(window).bind("load resize", function() {
	if ($(this).width() < 769) {
		$('body').addClass('mini-navbar');
		$('.navbar-static-side').fadeIn();
	}
});

function NavToggle() {
	$('.navbar-minimalize').trigger('click');
}

function SmoothlyMenu() {
	if (!$('body').hasClass('mini-navbar')) {
		$('#side-menu').hide();
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 100);
	} else if ($('body').hasClass('fixed-sidebar')) {
		$('#side-menu').hide();
		setTimeout(function() {
			$('#side-menu').fadeIn(500);
		}, 300);
	} else {
		$('#side-menu').removeAttr('style');
	}
}

// 判断浏览器是否支持html5本地存储
function localStorageSupport() {
	return (('localStorage' in window) && window['localStorage'] !== null)
}

// 时间设置
function currentTime() {
	var d = new Date(), str = '';
	str += d.getFullYear() + '年';
	str += d.getMonth() + 1 + '月';
	str += d.getDate() + '日';
	str += d.getHours() + '时';
	str += d.getMinutes() + '分';
	str += d.getSeconds() + '秒';
	return str;
}

setInterval(function() {
	$('#time').html(currentTime)
}, 1000);