[#-- 邮箱自动补全 --]
<style>
	#AutoComplete{background:#fff;border:1px solid #4190db;display:none;width:300px;overflow:hidden;}
	#AutoComplete ul{list-style-type:none;margin:0;padding:0;}
	#AutoComplete li{color:#333;cursor:pointer;font:12px/22px \5b8b\4f53;text-indent:5px;}
	#AutoComplete .hover{background:#6eb6fe;color:#fff;}
</style>

<script src="${rc.contextPath}/statics/libs/jquery.autocomplete.js"></script>
<script>
	 $(function(){
	 	//邮箱补全
	 	$.AutoComplete("${inputEmail}",function(){
	 		[#if form??]
				$('${form}').data('bootstrapValidator').resetForm();
				$("${form}").bootstrapValidator('validate');
			[/#if]
	 	});
	 });
</script>