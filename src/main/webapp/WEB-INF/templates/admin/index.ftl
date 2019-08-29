<!DOCTYPE html>
<html>
<head>
    <title>XX管理系统</title>
    [#include "/admin/header.ftl"]
    <!-- iCheck -->
    <script src="${rc.contextPath}/statics/common/icheck/icheck.min.js"></script>
    <!-- bootstrapvalidator-master前端验证框架 -->
    <script src="${rc.contextPath}/statics/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
    <!-- 自定义js -->
    <script src="${rc.contextPath}/statics/js/admin/hplus.js"></script>
    <script src="${rc.contextPath}/statics/js/admin/contabs.js"></script>

</head>
<body class="fixed-sidebar full-height-layout gray-bg">
<!--左侧导航开始-->
<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close"><i class="fa fa-times-circle"></i></div>
    <div class="sidebar-collapse">
        <ul class="nav" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element"><span> </span>
                    <span class="default-avatar">
                		<img alt="image" width="58" height="58" class="img-circle"
                             src="${rc.contextPath}/${admin.avatarUrl!""}"></span>
                    <a
                            data-toggle="dropdown" class="dropdown-toggle" href="javascript:void(0);"> <span
                                class="clear"> <span
                                    class="block m-t-xs"><strong
                                        class="font-bold">${admin.username}</strong></span> <span
                                    class="text-muted text-xs block">${admin.email}<b class="caret"></b></span> </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li><a class="J_menuItem" href="${rc.contextPath}/admin/sys/user/view"
                               data-index="1">个人资料</a></li>
                        <li><a class="J_menuItem" href="${rc.contextPath}/admin/sys/user/avatar.html"
                               data-index="1">修改头像</a></li>
                        <li class="divider"></li>
                        <li><a href="${rc.contextPath}/admin/sys/logout">安全退出</a></li>
                    </ul>
                </div>
            </li>
            [#list menuList as list]
                <li><a href="javascript:void(0);" aria-expanded="false"> <i
                                class="${list.icon}"></i> <span class="nav-label">${list.name}</span> <span
                                class="fa arrow"></span> </a>
                    <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">

                        [#if list.list??]
                            [#list list.list as children]
                                <li><a class="J_menuItem" href="${rc.contextPath}/${children.url}"
                                       data-index="${children_index}"><i class="${children.icon}"></i>${children.name}
                                    </a></li>
                            [/#list]
                        [/#if]
                    </ul>
                </li>
            [/#list]
        </ul>
    </div>
</nav>
<!--左侧导航结束-->
<!--右侧部分开始-->
<div id="page-wrapper" class="gray-bg dashbard-1">
    <div class="row border-bottom">
        <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
                                          href="javascript:void(0);"><i
                            class="fa fa-bars"></i> </a>

                <div role="search" class="navbar-form-custom">
                    <div class="form-group">
                        <a class="form-control" id="time"></a>
                    </div>
                </div>
            </div>
            <ul class="nav navbar-top-links navbar-right">

            </ul>
        </nav>
    </div>
    <div class="row content-tabs">
        <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i></button>
        <nav class="page-tabs J_menuTabs">
            <div class="page-tabs-content"><a href="javascript:;" class="active J_menuTab" data-id="">首页</a></div>
        </nav>
        <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i></button>
        <div class="btn-group roll-nav roll-right">
            <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                <li class="divider"></li>
                <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
            </ul>
        </div>
        <a href="${rc.contextPath}/admin/sys/logout" class="roll-nav roll-right J_tabExit"><i
                    class="fa fa fa-sign-out"></i>
            退出</a></div>
    <div class="row J_mainContent" id="content-main">
        <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${rc.contextPath}/admin/sys/user/view"
                frameborder="0" data-id=""
                seamless></iframe>
    </div>
    <div class="footer">
        <div class="pull-right">© 2017-2018<a href="https://zhousiwei.gitee.io/"
                                              target="_Blank"> 試毅-思伟 </a>
        </div>
    </div>
</div>
</body>
</html>