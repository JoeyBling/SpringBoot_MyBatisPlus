<!DOCTYPE html>
<html>
<head>
    <title>个人信息</title>
    [#include "/admin/header.ftl"]
    <link rel="stylesheet" href="${rc.contextPath}/statics/common/bootstrap-table/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/statics/common/icheck/flat/green.css"/>
</head>
<body class="gray-bg" style="display:none;">
<div class="wrapper wrapper-content">
    <div id="row" class="row">
        <div class="col-sm-5">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>管理员信息</h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content form-horizontal">
                    <form id="form">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>

                            <div class="col-sm-6">
                                <input name="username" type="text" class="form-control form-disabled"
                                       disabled="disabled" value="${admin.username}">
                            </div>
                            <div class="col-sm-3">
                                <button type="button" class="btn btn-warning pull-right" onclick="change_Password();">
                                    修改密码
                                </button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">性别：</label>
                            <div class="col-sm-6">
                                <input id="sex" type="text" class="form-control" disabled="disabled" value="[#if admin.sex==1]男[#elseif admin.sex==2]女[#else]保密[/#if]">
                                <label class="radio-inline add-radio" style="display: none;">
                                    <input type="radio" name="sex" value="1" [#if admin.sex==1]checked="checked"[/#if]>
                                    男</label>
                                <label class="radio-inline add-radio" style="display: none;">
                                    <input type="radio" name="sex" value="2" [#if admin.sex==2]checked="checked"[/#if]>
                                    女</label>
                                <label class="radio-inline add-radio" style="display: none;">
                                    <input type="radio" name="sex" value="0" [#if admin.sex==0]checked="checked"[/#if]>
                                    保密</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">联系手机：</label>

                            <div class="col-sm-6">
                                <input name="mobile" type="text" class="form-control form-disabled"
                                       disabled="disabled" value="${admin.mobile}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label ">电子邮箱：</label>

                            <div class="col-sm-6 stm-inp">
                                <input name="email" type="text" class="form-control form-disabled" disabled="disabled"
                                       value="${admin.email}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">注册时间：</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control disabled-form-control" disabled="disabled"
                                       value="[@formatTime unix="${admin.createTime?c}" pattern="yyyy-MM-dd HH:mm:ss"]
                                [/@formatTime]">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">最后登录时间：</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control disabled-form-control" disabled="disabled"
                                       value="[@formatTime unix="${admin.lastLoginTime?c}" pattern="yyyy-MM-dd HH:mm:ss"] [/@formatTime]">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">最后登录IP：</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control disabled-form-control" disabled="disabled"
                                       value="${admin.lastLoginIp}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button type="button" class="btn btn-primary pull-left m-r-md" onclick="modify();">
                                    修改信息
                                </button>
                                <button type="button" class="btn btn-success" onclick="save_info();"
                                        style="display: none;">保存修改
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-sm-7">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>管理员登陆记录</h5>

                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <table id="table"
                                           data-striped="true"
                                           data-toggle="table"
                                           data-height="600"
                                           data-search="true"
                                           data-search-on-enter-key="true"
                                           data-search-text=""
                                           data-show-refresh="true"
                                           data-show-toggle="true"
                                           data-show-export="true"
                                           data-url="${rc.contextPath}/admin/sys/log/list"
                                           data-pagination="true"
                                           data-page-size="20"
                                           data-page-list="[20, 50, 100, 200]"
                                           data-side-pagination="server"
                                           data-striped="true"
                                           data-pagination="true"
                                           data-sort-name="loginTime"
                                           data-sort-order="desc"
                                           data-unique-id="logId"
                                           data-response-handler="defaultBootstarpTableHandler">
                                        <thead>
                                        <tr>
                                            <th data-field="loginTime" data-formatter="BootstrapTableformatDate"
                                                data-halign="center" data-align="center" data-sortable="true">登录时间
                                            </th>
                                            <th data-field="loginIp" data-halign="center" data-align="center"
                                                data-sortable="true">登录IP
                                            </th>
                                            <th data-field="operatingSystem" data-halign="center" data-align="center"
                                                data-sortable="true">操作系统
                                            </th>
                                            <th data-field="browser" data-halign="center" data-align="center"
                                                data-sortable="true">浏览器
                                            </th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="ibox-content form-horizontal" id="change_Pass" style="display: none;">
    <div class="form-group">
        <label class="col-sm-4 control-label">原密码：</label>

        <div class="col-sm-6">
            <input name="nowPassword" type="password" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4 control-label">新密码：</label>

        <div class="col-sm-6">
            <input name="newPassword" type="password" class="form-control">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-4 control-label">确认密码：</label>

        <div class="col-sm-6">
            <input name="confirmPwd" type="password" class="form-control">
        </div>
    </div>
</div>
<!-- 自定义js -->
<!-- bootstrapvalidator-master前端验证框架 -->
<script src="${rc.contextPath}/statics/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<!-- Bootstrap table -->
<script src="${rc.contextPath}/statics/common/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/tableExport.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- iCheck -->
<script src="${rc.contextPath}/statics/common/icheck/icheck.min.js"></script>
<script src="${rc.contextPath}/statics/js/admin/sys/adminUserInfo.js"></script>
[#-- 自定义搜索框placeholder --]
[#assign searchText="登录IP"]
[#include "/admin/bootstrapcommon.ftl"]
[#-- 页面加载进度条 --]
[#assign parentName="#row"][#-- 默认为Body --]
[#include "/admin/nprogress.ftl"]
</body>
</html>