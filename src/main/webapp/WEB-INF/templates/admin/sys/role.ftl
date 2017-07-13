<!DOCTYPE html>
<html>
<head>
    <title>角色管理</title>
    [#include "/admin/header.ftl"]
    <link rel="stylesheet" href="${rc.contextPath}/statics/common/bootstrap-table/bootstrap-table.min.css"/>
</head>
<body class="gray-bg" style="display:none;">
<div class="wrapper wrapper-content">
    <div id="row" class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>角色列表</h5>

                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <div id="toolbar" class="btn-group m-t-sm">
                                        [@shiro.hasPermission name="sys:role:save"]
                                        <button type="button" style="margin-right:10px;" class="btn btn-primary"
                                                title="创建角色" onclick="add($('#showHandle'))"><i
                                                class="glyphicon glyphicon-plus"></i> 创建角色
                                        </button>
                                        [/@shiro.hasPermission]
                                        [@shiro.hasPermission name="sys:role:delete"]
                                        <button type="button" style="margin-right:10px;" class="btn btn-danger"
                                                title="批量删除角色" onclick="del($('#table'))"><i
                                                class="glyphicon glyphicon-remove"></i> 批量删除角色
                                        </button>
                                        [/@shiro.hasPermission]
                                    </div>
                                    <table id="table"
                                           data-toggle="table"
                                           data-height="600"
                                           data-search="true"
                                           data-search-on-enter-key="true"
                                           data-show-refresh="true"
                                           data-show-toggle="true"
                                           data-show-export="true"
                                           data-show-columns="true"
                                           data-url="${rc.contextPath}/admin/sys/role/list" [#-- 服务器数据URL --]
                                           data-pagination="true"
                                           data-page-size="20"
                                           data-page-list="[20, 50, 100, 200]"
                                           data-side-pagination="server"
                                           data-striped="true"
                                           data-pagination="true"
                                           data-sort-name="createTime" [#-- 默认排序字段 --]
                                           data-sort-order="desc" [#-- 默认排序顺序 可选asc desc --]
                                           data-toolbar="#toolbar" [#-- 指定工具类元素 --]
                                           data-click-to-select="true" [#-- 设置true 将在点击行时，自动选择rediobox 和 checkbox --]
                                           data-single-select="false" [#-- 设置True 将禁止多选 --]
                                           data-unique-id="roleId" [#-- 填写主键ID即可 --][#-- 官方文档:http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/ --]
                                           data-response-handler="defaultBootstarpTableHandler">
                                    <thead>
                                    <tr>
                                        <th data-checkbox="true"></th>
                                        <th data-field="roleName" data-halign="center" data-align="center"
                                            data-sortable="true">角色名称
                                        </th>
                                        <th data-field="remark" data-halign="center" data-align="center"
                                            data-sortable="true">备注
                                        </th>
                                        <th data-field="createTime" data-formatter="BootstrapTableformatDate"
                                            data-sortable="true" data-halign="center" data-align="center">创建时间
                                        </th>
                                        <th data-formatter="actionFormatter" data-events="actionEvents"
                                            data-halign="center" data-align="center">操作
                                        </th>
                                    </tr>
                                    </thead>
                                    </table>
                                    <input type='hidden' id="handle"
                                         [@shiro.hasPermission name="sys:role:update" ] data-update="true" [/@shiro.hasPermission]
                                         [@shiro.hasPermission name="sys:role:delete"]  data-delete="true"[/@shiro.hasPermission]/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="showHandle" style="display:none;">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                <h5><span id="title">创建角色<span><small></small></h5>
                <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                        class="close-link"><i class="fa fa-times"></i></a></div>
            </div>
            <div class="ibox-content form-horizontal">
                <form id="form">
                    <input type='hidden' name="roleId"/>

                    <div class="form-group m-t">
                        <label class="col-sm-2 col-xs-offset-1 control-label">角色名称：</label>

                        <div class="col-sm-6">
                            <input type="text" maxlength="10" class="form-control" name="roleName">
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">备注：</label>

                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="remark">
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">授权：</label>

                        <div class="col-sm-6">
                            <ul id="menuTree" class="ztree"></ul>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-12 text-center">
                            <button type="button" class="btn btn-success" onclick="saveOrUpdate(this);">提交
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<link rel="stylesheet" href="${rc.contextPath}/statics/common/ztree/css/metroStyle/metroStyle.css">
<script src="${rc.contextPath}/statics/common/ztree/js/jquery.ztree.all.min.js"></script>
<!-- bootstrapvalidator-master前端验证框架 -->
<script src="${rc.contextPath}/statics/common/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<!-- Bootstrap table -->
<script src="${rc.contextPath}/statics/common/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/tableExport.js"></script>
<script src="${rc.contextPath}/statics/common/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- 自定义js -->
<script src="${rc.contextPath}/statics/js/admin/sys/role.js"></script>
[#-- 自定义搜索框placeholder --]
[#assign searchText="角色名称"]
[#include "/admin/bootstrapcommon.ftl"]
[#-- 页面加载进度条 --]
[#assign parentName="#row"][#-- 默认为Body --]
[#include "/admin/nprogress.ftl"]
</body>
</html>