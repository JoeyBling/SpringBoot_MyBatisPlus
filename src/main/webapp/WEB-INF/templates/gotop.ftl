[#-- 滚动顶部插件 --]
<link href="${rc.contextPath}/statics/libs/gotop/gotop.css" media="all" rel="stylesheet"
          type="text/css">
<script src="${rc.contextPath}/statics/libs/gotop/gotop.js"></script>
<a href="#0" class="cd-top">Top</a>
<script>
	$(function(){
		//离顶部的高度
		var offset = ${offset!0},
		//滚动不透明度
		offset_opacity = ${offset_opacity!1200},
		//滚动到顶部的时间
		scroll_top_duration = ${scroll_top_duration!700},
		$back_to_top = $('.cd-top');
		
		$(window).scroll(function(){
			( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
			if( $(this).scrollTop() > offset_opacity ) { 
				$back_to_top.addClass('cd-fade-out');
			}
		});

		//smooth scroll to top
		$back_to_top.on('click', function(event){
			event.preventDefault();
			$('body,html').animate({
				scrollTop: 0 ,
			 	}, scroll_top_duration
			);
		});
	});
</script>