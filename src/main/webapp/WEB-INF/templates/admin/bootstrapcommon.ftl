<script>
	(function ($) {
    	'use strict';
			$.fn.bootstrapTable.locales['zh-CN'] = {
				formatSearch: function () {
			            return '${searchText}';
			        }
	        };
	        $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
	})(jQuery);
</script>