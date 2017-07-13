[#-- 页面加载进度条 --]
[#if isFirst??]	[#-- 是否是第一次加载 --]
	<script>
		NProgress.settings.parent = "${parentName!"body"}";
	</script>
[#else]
	<link rel="stylesheet" href="${rc.contextPath}/statics/libs/nprogress/nprogress.css"/>
	<script src="${rc.contextPath}/statics/libs/nprogress/nprogress.js"></script>
	<script>
    $('body').show();
    NProgress.settings.parent = "${parentName!"body"}";
    $('.version').text(NProgress.version);
    NProgress.start();
    setTimeout(function() { NProgress.done(); $('.fade').removeClass('out'); }, 1000);
	</script>
[/#if]