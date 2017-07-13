<!DOCTYPE html>
<html>
<head>
    <title>菜单管理</title>
    [#include "/admin/header.ftl"]
    <link rel="stylesheet" href="${rc.contextPath}/statics/common/bootstrap-table/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="${rc.contextPath}/statics/common/icheck/flat/green.css"/>
    <!-- iCheck -->
    <script src="${rc.contextPath}/statics/common/icheck/icheck.min.js"></script>
</head>
<body class="gray-bg" style="display:none;">
<div class="wrapper wrapper-content">
    <div id="row" class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>菜单列表</h5>

                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <form id="searchform">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"
                                                type="button" aria-expanded="false">菜单名称
                                        </button>
                                    </div>
                                    <input type="text" class="form-control" name="q_menuName" placeholder="">
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <div class="input-group-btn">
                                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"
                                                type="button" aria-expanded="false">上级菜单
                                        </button>
                                    </div>
                                    <input type="text" class="form-control" name="q_parentName" placeholder="">
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <button type="button" class="btn btn-primary" onclick="search()">
                                    <i class="fa fa-search"></i>&nbsp;搜索
                                </button>
                            </div>
                        </form>

                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <div id="toolbar" class="btn-group m-t-sm">
                                        [@shiro.hasPermission name="sys:menu:save"]
                                        <button type="button" style="margin-right:10px;" class="btn btn-primary"
                                                title="创建菜单" onclick="add($('#showHandle'))"><i
                                                class="glyphicon glyphicon-plus"></i> 创建菜单
                                        </button>
                                        [/@shiro.hasPermission]
                                        [@shiro.hasPermission name="sys:menu:delete"]
                                        <button type="button" style="margin-right:10px;" class="btn btn-danger"
                                                title="批量删除菜单" onclick="del($('#table'))"><i
                                                class="glyphicon glyphicon-remove"></i> 批量删除菜单
                                        </button>
                                        [/@shiro.hasPermission]
                                    </div>
                                    <table id="table"
                                           data-toggle="table"
                                           data-height="650"
                                           data-search="false"
                                           data-search-on-enter-key="false"
                                           data-show-refresh="true"
                                           data-show-toggle="true"
                                           data-show-export="true"
                                           data-show-columns="true"
                                           data-url="${rc.contextPath}/admin/sys/menu/list" [#-- 服务器数据URL --]
                                           data-pagination="true"
                                           data-page-size="20"
                                           data-page-list="[20, 50, 100, 200]"
                                           data-side-pagination="server"
                                           data-striped="true"
                                           data-pagination="true"
                                           data-sort-name="parentName" [#-- 默认排序字段 --]
                                           data-sort-order="asc" [#-- 默认排序顺序 可选asc desc --]
                                           data-toolbar="#toolbar" [#-- 指定工具类元素 --]
                                           data-click-to-select="true" [#-- 设置true 将在点击行时，自动选择rediobox 和 checkbox --]
                                           data-single-select="false" [#-- 设置True 将禁止多选 --]
                                           data-unique-id="menuId" [#-- 填写主键ID即可 --][#-- 官方文档:http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/ --]
	                                       data-response-handler="defaultBootstarpTableHandler"
	                                       data-striped="true">
                                    <thead>
                                    <tr>
                                        <th data-checkbox="true"></th>
                                        <th data-field="name" data-halign="center" data-align="center"
                                            data-sortable="true">菜单名称
                                        </th>
                                        <th data-field="parentName" data-halign="center" data-align="center"
                                            data-sortable="true">上级菜单
                                        </th>
                                        <th data-field="icon" data-formatter="formatIcon" data-sortable="true"
                                            data-halign="center" data-align="center">菜单图标
                                        </th>
                                        <th data-field="url" data-sortable="true" data-halign="center"
                                            data-align="center">菜单URL
                                        </th>
                                        <th data-field="perms" data-sortable="true" data-halign="center"
                                            data-align="center">授权标识
                                        </th>
                                        <th data-field="type" data-formatter="formatType" data-sortable="true"
                                            data-halign="center" data-align="center">类型
                                        </th>
                                        <th data-field="orderNum" data-sortable="true" data-halign="center"
                                            data-align="center">排序号
                                        </th>
                                        <th data-formatter="actionFormatter" data-events="actionEvents"
                                            data-halign="center" data-align="center">操作
                                        </th>
                                    </tr>
                                    </thead>
                                    </table>
                                    <input type='hidden' id="handle"
                                        [@shiro.hasPermission name="sys:menu:update" ] data-update="true" [/@shiro.hasPermission]
                                    	[@shiro.hasPermission name="sys:menu:delete"] data-delete="true"[/@shiro.hasPermission]/>
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
                <h5><span id="title">创建菜单<span><small></small></h5>
                <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                        class="close-link"><i class="fa fa-times"></i></a></div>
            </div>
            <div class="ibox-content">
                <form id="form">
                    <input type='hidden' name="menuId"/>

                    <div class="form-group m-t">
                        <label class="col-sm-2 col-xs-offset-1 control-label">类型：</label>

                        <div class="col-sm-6">
                            <label class="radio-inline add-radio">
                                <input type="radio" name="type" value="0" data-event="catalog">目录</label>
                            <label class="radio-inline add-radio">
                                <input type="radio" name="type" value="1" data-event="menu">菜单</label>
                            <label class="radio-inline add-radio">
                                <input type="radio" name="type" value="2" data-event="button">按钮</label>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">菜单名称：</label>

                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name">
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">上级菜单：</label>

                        <div class="col-sm-6">
                            <input type='hidden' name="parentId"/>
                            <input type="text" name='parentName' class="form-control" style="cursor:pointer;"
                                   onclick="showMenuTree()" readonly="readonly" placeholder="一级菜单"/>
                        </div>
                    </div>
                    <div id="menuTable">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">菜单URL：</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="url">
                            </div>
                        </div>
                    </div>
                    <div id="buttonTable">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">授权标识：</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control" name="perms"
                                       placeholder="多个用逗号分隔，如：user:list,user:create">
                            </div>
                        </div>
                    </div>
                    <div id="catalogTable">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">排序号：</label>

                            <div class="col-sm-6">
                                <input type="number" class="form-control" name="orderNum">
                            </div>
                        </div>
                    </div>
                    <div id="iconDiv">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">图标：</label>

                            <div class="col-sm-6">
                                <div class="input-group m-b"> <span class="input-group-btn">
	              	 <button type="button" class="btn btn-primary"
                             onclick="layer_show_url('菜单图标','${rc.contextPath}/admin/sys/menu_icon.html','800','600')" title="图标">
                         选择
                     </button>
	                </span><input type="text" class="form-control" name="icon"></div>
                                <code style="margin-top:4px;display: block;">获取图标：http://fontawesome.io/icons/</code>
                            </div>
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

<!-- 选择菜单 -->
<div id="menuLayer" style="display: none;padding:10px;">
    <ul id="menuTree" class="ztree"></ul>
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
<script src="${rc.contextPath}/statics/js/admin/sys/menu.js"></script>
[#-- 页面加载进度条 --]
[#assign parentName="#row"][#-- 默认为Body --]
[#include "/admin/nprogress.ftl"]
</body>
</html>