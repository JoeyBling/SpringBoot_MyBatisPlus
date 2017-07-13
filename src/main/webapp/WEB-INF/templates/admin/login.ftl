<!DOCTYPE html>
<html>
<head>
    <title>XX管理系统</title>
    [#include "/admin/header.ftl"]
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
    <link rel="stylesheet" href="${rc.contextPath}/statics/css/admin/AdminLTE.min.css"/>
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${rc.contextPath}/statics/css/admin/all-skins.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/statics/css/admin/main.css"/>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <div class="alert alert-danger" style="text-align:center;" role="alert">
        您的浏览器版本太低，如果您使用的是IE浏览器，你将IE浏览器升级到IE9以上，其他浏览器请将浏览器内核切换至极速模式&nbsp;<b><a href="http://chrome.360.cn"
                                                                              target="_blank">下载360极速浏览器</a></b></div>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<script zIndex="-1" src="${rc.contextPath}/statics/js/admin/canvas-nest.min.js"></script>
<div class="login-box">
    <div class="login-logo">
        <b>XX管理系统</b>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">管理员登录</p>

        <div style="display:none;" class="alert alert-danger alert-dismissible">
            <h4 style="margin-bottom: 0px;" class="errText"><i class="fa fa-exclamation-triangle"></i></h4>
        </div>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" name="username" placeholder="账号">
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="password" class="form-control" name="password" placeholder="密码">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <input type="text" class="form-control" name="captcha" onkeyup="if(event.keyCode==13) javascript:login();"
                   placeholder="验证码">
            <span class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
            <img alt="如果看不清楚，请单击图片刷新！" class="pointer" src="${rc.contextPath}/admin/captcha.jpg" onclick="refreshCode()">
            &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="refreshCode()">点击刷新</a>
        </div>


        <div class="row">
            <div class="col-xs-8">
                <div class="checkbox icheck">
                </div>
            </div>
            <div class="col-xs-4">
                <button type="button" class="btn btn-primary btn-block btn-flat" onclick="login()">登录</button>
            </div>
        </div>

    </div>
    <!-- /.login-box-body -->
</div>
<script type="text/javascript">
    $(function () {
        if (self != top) {
            top.location.href = self.location.href;
        }
    });

    function refreshCode() {
        $(".pointer").attr("src", "${rc.contextPath}/admin/captcha.jpg?t=" + new Date().getTime());
    }
    
    function login(event) {
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        var captcha = $("input[name='captcha']").val();
        var data = "username=" + username + "&password=" + password + "&captcha=" + captcha;
        $.ajax({
            type: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            url: "${rc.contextPath}/admin/sys/login",
            data: data,
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {//登录成功
                    parent.location.href = '${rc.contextPath}/admin/index.html';
                } else {
                    $(".alert-dismissible").show();
                    $(".errText").text(result.msg);
                    refreshCode();
                }
            }
        });
    }
</script>
</body>
</html>