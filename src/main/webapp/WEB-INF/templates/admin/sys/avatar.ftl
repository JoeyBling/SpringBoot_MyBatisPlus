<!DOCTYPE html>
<html>
<head>
    <title>上传头像</title>
    [#include "/admin/header.ftl"]
    <link href="${rc.contextPath}/statics/common/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet"
          type="text/css">
    <link href="${rc.contextPath}/statics/common/bootstrap-fileinput/themes/explorer/theme.css" media="all" rel="stylesheet"
          type="text/css">
    <style>
        .kv-avatar .file-preview-frame, .kv-avatar .file-preview-frame:hover {
            margin: 0;
            padding: 0;
            border: none;
            box-shadow: none;
            text-align: center;
        }
    </style>
</head>
<body style="display:none;">
<div id="row" class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5>上传头像</h5>

                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>
                    <a class="close-link">
                        <i class="fa fa-times"></i>
                    </a>
                </div>
            </div>
            <div class="ibox-content">
                <form id="avatarForm" class="form-horizontal">
                    <input id="portrait" name="portrait" type="hidden"
                           value="${admin.avatarUrl!""}">
                    <div class="kv-avatar center-block">
                        <input id="portraitFile"
                               name="portraitFile" type="file"
                               class=""></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-1">
                            <button class="btn btn-primary" type="button" id="submit">提交修改</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
[#-- 滚动顶部插件 --]
[#include "/gotop.ftl"]
</body>
<script src="${rc.contextPath}/statics/common/bootstrap-fileinput/js/plugins/sortable.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-fileinput/js/fileinput.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-fileinput/themes/explorer/theme.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-fileinput/js/locales/zh.js"></script>
<script>
	var flag = "${admin.avatarUrl!""}";
	//提交修改
    $("#submit").click(function(){
    	var portrait = $('#portrait').val();
    	if(flag===portrait){
    		 layer.alert('请先上传头像', {
                icon: 5
            });
            return false;
    	}
    	$.ajax({
				data : {
					avatarUrl : portrait
				},
				dataType : "json",
				headers : {
					'Content-Type' : 'application/x-www-form-urlencoded'
				},
				url : '${rc.contextPath}/admin/sys/user/updateAvatar',
				success : function(result) {
					if (result.code === 0) {
						layer.alert("头像修改成功!", {
							title : '提示框',
							icon : 1
						},function(){
							location.reload();
						});
					} else {
						layer.alert(result.msg, {
							title : '提示框',
							icon : 0
						});
					}
				}
			});
    });


    //删除与删除地方不能匹配，上传成功的地方需要处理下
    var portraitFileList = new Array();
    $(document).ready(function () {
        initportraitFileinput();
    });
    

    function initportraitFileinput(previewJson, previewConfigJson) {
        var portraitsettings = {
            language: 'zh', //设置语言
            overwriteInitial: true,
            maxFileSize: 1500,
            showClose: false,
            showCaption: false,
            browseLabel: '选择文件',
            uploadUrl: "${rc.contextPath}/file/upload", //上传的地址
            allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
            uploadAsync: true, //默认异步上传
            showUpload: false, //是否显示上传按钮
            showRemove: false, //显示移除按钮
            showPreview: true, //是否显示预览
            browseLabel: '',
            removeLabel: '',
            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
            removeTitle: 'Cancel or reset changes',
            elErrorContainer: '#kv-avatar-errors-1',
            msgErrorClass: 'alert alert-block alert-danger',
            preferIconicPreview: true,
            defaultPreviewContent: '<img style="widht:100px;height:100px;" src="${rc.contextPath}/${admin.avatarUrl!""}" />',
            uploadExtraData: function () {		//额外上传参数
                return {"uploadType": "0"};
            }
        };
        $.extend(portraitsettings, {});
        $("#portraitFile").fileinput(portraitsettings);

    }

    function portraituploadExtraData() {
        var uploadExtraData = {};
        return uploadExtraData;
    }
    $("#portraitFile").on("filebatchselected", function (event, files) {
        $(this).fileinput("upload");
    });

    //异步上传返回结果处理
    $("#portraitFile").on("fileuploaded", function (event, data, previewId, index) {
        portraitFileList.splice(0, portraitFileList.length);
        portraituploadsuccess(event, data, previewId, index);
    });


    //同步上传返回结果处理
    $("#portraitFile").on("filebatchuploadsuccess", function (event, data, previewId, index) {
        portraitFileList.splice(0, portraitFileList.length);
        portraituploadsuccess(event, data, previewId, index);
    });


    function portraituploadsuccess(event, data, previewId, index) {
        var response = data.response;
        if (response.code === 0) {
            portraitFileList.push({data: response, previewId: previewId})
            updateportraitBind();
        } else {
            layer.alert(response.msg, {
                icon: 2
            });
        }
    }

    function updateportraitBind() {
        var fileids = "";
        for (var i = 0; i < portraitFileList.length; i++) {
            var fileid = portraitFileList[i].data.filePath;
            if (fileids != '') {
                fileids += ",";
            }
            fileids += fileid;
        }
        $('#portrait').val(fileids);
        try {
        } catch (err) {

        }
    }

    //删除回调
    $("#portraitFile").on('filesuccessremove', function (event, id) {
        updateportraitBind();
    });

    //预览删除
    $("#portraitFile").on('filedeleted', function (event, id, jqXHR, data) {
        updateportraitBind();
    });

    //清除所有的
    $("#portraitFile").on('fileclear', function (event) {
        updateportraitBind();
    });

</script>
[#-- 页面加载进度条 --]
[#assign parentName="#row"][#-- 默认为Body --]
[#include "/admin/nprogress.ftl"]
</html>