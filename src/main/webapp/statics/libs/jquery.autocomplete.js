jQuery.AutoComplete = function(selector, e) {
	var elt = $(selector);
	var strHtml = '<div class="AutoComplete" id="AutoComplete">'
			+ '        <ul class="AutoComplete_ul">'
			+ '            <li class="AutoComplete_title">请选择邮箱后缀</li>'
			+ '            <li hz="@qq.com"></li>'
			+ '            <li hz="@163.com"></li>'
			+ '            <li hz="@126.com"></li>'
			+ '            <li hz="@sohu.com"></li>'
			+ '            <li hz="@sina.com"></li>' + '        </ul>'
			+ '    </div>';
	$('body').append(strHtml);
	var autoComplete, autoLi;
	autoComplete = $('#AutoComplete');
	autoComplete.data('elt', elt);
	autoLi = autoComplete.find('li:not(.AutoComplete_title)');
	autoLi.mouseover(function() {
		$(this).siblings().filter('.hover').removeClass('hover');
		$(this).addClass('hover');
	}).mouseout(function() {
		$(this).removeClass('hover');
	}).mousedown(function() {
		autoComplete.data('elt').val($(this).text()).change();
		autoComplete.hide();
		if (typeof e === 'function') {
			e();
		}
	});
	// 用户名补全+翻动
	elt.keyup(
			function(e) {
				if (/13|38|40|116/.test(e.keyCode) || this.value == '') {
					return false;
				}
				var username = this.value;

				autoLi.each(
						function() {
							this.innerHTML = username.replace(/\@+.*/, '')
									+ $(this).attr('hz');
							if (this.innerHTML.indexOf(username) >= 0) {
								$(this).show();
							} else {
								$(this).hide();
							}
						}).filter('.hover').removeClass('hover');
				autoComplete.show().css({
					left : $(this).offset().left,
					top : $(this).offset().top + $(this).outerHeight(true) - 1,
					position : 'absolute',
					zIndex : '99999999'
				});
				if (autoLi.filter(':visible').length == 0) {
					autoComplete.hide();
				} else {
					autoLi.filter(':visible').eq(0).addClass('hover');
				}
			}).keydown(
			function(e) {
				if (e.keyCode == 38) { // 上
					autoLi.filter('.hover').prev().not('.AutoComplete_title')
							.addClass('hover').next().removeClass('hover');
				} else if (e.keyCode == 40) { // 下
					autoLi.filter('.hover').next().addClass('hover').prev()
							.removeClass('hover');
				} else if (e.keyCode == 13) { // Enter
					autoLi.filter('.hover').mousedown();
				}
			}).focus(function() {
		autoComplete.data('elt', $(this));
	}).blur(function() {
		autoComplete.hide();
	});
};