function radio() {
	$('#showHandle input').iCheck({
		checkboxClass : 'icheckbox_flat-green',
		radioClass : 'iradio_flat-green'
	});
	bingICheckClick(); // 重新绑定事件
}

// 筛选数据
function search() {
	var params = $('#table').bootstrapTable('getOptions');
	params.queryParams = function(params) {
		// 定义参数
		var search = {};
		// 遍历form 组装json
		$.each($("#searchform").serializeArray(), function(i, field) {
			// 可以添加提交验证
			search[field.name] = field.value;
		});

		// 参数转为json字符串，并赋给search变量 ,JSON.stringify <ie7不支持，有第三方解决插件
		params.search = JSON.stringify(search);
		return params;
	}
	$('#table').bootstrapTable('refresh', params);
}

// 图标
function formatIcon(value, row, index) {
	if (value == null) {
		return "";
	}
	return "<i class='" + value + "'></i>";
}

// 类型
function formatType(value, row, index) {
	// 0：目录 1：菜单 2：按钮
	if (null == value) {
		return "";
	} else if (value === 0) {
		return '<span class="label label-primary">目录</span>';
	} else if (value === 1) {
		return '<span class="label label-success">菜单</span>';
	} else if (value === 2) {
		return '<span class="label label-warning">按钮</span>';
	}
}

var handle = $("#handle");
var data_update = $(handle).attr("data-update");
var data_delete = $(handle).attr("data-delete");
// BootStrapTable自定义操作
function actionFormatter(e, value, row, index) {
	if (null == data_update && null == data_delete) {
		return "";
	} else if (null == data_update && null != data_delete) {
		return [
				'<a class="remove text-danger" href="javascript:void(0)" title="删除">',
				'<i class="glyphicon glyphicon-remove"></i>删除', '</a>' ]
				.join('');
	} else if (null != data_update && null == data_delete) {
		return [
				'<a class="edit text-warning" href="javascript:void(0)" title="编辑">',
				'<i class="glyphicon glyphicon-edit"></i>编辑', '</a>' ].join('');
	} else {
		return [
				'<a class="edit m-r-sm text-warning" href="javascript:void(0)" title="编辑">',
				'<i class="glyphicon glyphicon-edit"></i>编辑',
				'</a>',
				'<a class="remove text-danger" href="javascript:void(0)" title="删除">',
				'<i class="glyphicon glyphicon-remove"></i>删除', '</a>' ]
				.join('');
	}
}

// Table操作
window.actionEvents = {
	// 编辑
	'click .edit' : function(e, value, row, index) {
		menu_update(index, row.menuId, row);
	},
	// 删除
	'click .remove' : function(e, value, row, index) {
		menu_delete(index, row.menuId);
	}
};

/*
 * 删除菜单
 */
function menu_delete(index, value) {
	var menuIds = new Array();
	menuIds[0] = value;
	layer.confirm('确认要删除该菜单吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			type : 'delete',
			dataType : 'json',
			url : 'menu/delete?menuIds=' + JSON.stringify(menuIds),
			success : function(result) {
				if (result.code === 0) {
					$('#table').bootstrapTable('hideRow', {
						index : index
					});
					layer.msg('该菜单删除成功!', {
						icon : 1,
						time : 1000
					});
				} else {
					layer.alert(result.msg, {
						icon : 2
					});
				}
			}
		})
	});
}

/*
 * 修改菜单
 */
function menu_update(index, value, row) {
	$("#title").text("修改菜单");
	$.get("menu/info/" + row.menuId, function(r) {
		if (r.code === 0) {
			$("input[name='menuId']").val(r.menu.menuId);
			$("input[name='name']").val(r.menu.name);
			$("input[name='parentName']").val(r.menu.parentName);
			$("input[name='url']").val(r.menu.url);
			$("input[name='perms']").val(r.menu.perms);
			$("input[name='orderNum']").val(r.menu.orderNum);
			$("input[name='icon']").val(r.menu.icon);
			$("input[name='parentId']").val(r.menu.parentId);
			$("input[name='type']").removeAttr("checked");
			$("input[name='type'][value=" + r.menu.type + "]").prop("checked",
					true);
			radio(); // 要重新生成样式
			getMenu(row.parentId);
			if (r.menu.type == "0") {
				checkRadio("catalog");
			}
			if (r.menu.type == "1") {
				checkRadio("menu");
			}
			if (r.menu.type == "2") {
				checkRadio("button");
			}
			layer_show("修改菜单", $("#showHandle"), 800, 500);
		} else {
			layer.alert(r.msg, {
				icon : 2
			});
		}
	});
}

/**
 * 批量删除菜单
 */
function del(tableName) {
	var s = $(tableName).bootstrapTable('getSelections');
	if (s.length < 1) {
		layer.alert('请至少选择一条数据', {
			icon : 2
		});
		return false;
	}
	var menuIds = new Array();
	for (var i = 0; i < s.length; i++) {
		menuIds[i] = s[i].menuId;
	}
	layer.confirm('确认要删除选中的菜单吗？', {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			type : 'delete',
			dataType : 'json',
			url : 'menu/delete?menuIds=' + JSON.stringify(menuIds),
			success : function(result) {
				if (result.code === 0) {
					layer.msg('删除成功!', {
						icon : 1,
						time : 1000
					}, function() {
						location.reload();
					});
				} else {
					layer.alert(result.msg, {
						icon : 2
					});
				}
			}
		})
	});
}

var parentName;
var parentId;

/**
 * 保存或更新
 */
function saveOrUpdate(e) {
	loadingButton($(e));
	var menuId = $("input[name='menuId']").val();
	menuId = menuId == "" ? null : menuId;
	var type = $("input[name='type']:checked").val();
	var name = $("input[name='name']").val();
	var menuUrl = $("input[name='url']").val();
	var perms = $("input[name='perms']").val();
	var orderNum = $("input[name='orderNum']").val();
	var icon = $("input[name='icon']").val();
	var parentId = $("input[name='parentId']").val();
	var url = menuId == null ? "menu/save" : "menu/update";
	if (null == name || "" == name) {
		layer.msg('菜单名称不能为空!', {
			icon : 2,
			time : 1000
		});
		return false;
	}
	if (null == parentId || "" == parentId) {
		layer.msg('请先选择上级菜单!', {
			icon : 2,
			time : 1000
		});
		return false;
	}
	$.ajax({
		type : "POST",
		url : url,
		headers : {
			'Content-Type' : 'application/x-www-form-urlencoded'
		},
		data : {
			menuId : menuId,
			type : type,
			name : name,
			url : menuUrl,
			perms : perms,
			orderNum : orderNum,
			icon : icon,
			parentId : parentId
		},
		success : function(r) {
			if (r.code === 0) {
				layer.msg('操作成功!', {
					icon : 1,
					time : 1000
				}, function() {
					location.reload();
				});
			} else {
				layer.alert(r.msg, {
					icon : 2
				});
			}
		}
	});
}

// 新建菜单
function add(s) {
	$("#title").text("新建菜单");
	$("input[name='menuId']").val("");
	$("input[name='name']").val("");
	$("input[name='parentName']").val("");
	$("input[name='url']").val("");
	$("input[name='perms']").val("");
	$("input[name='orderNum']").val("");
	$("input[name='icon']").val("");
	$("input[name='parentId']").val("");
	$("input[name='type']").removeAttr("checked");
	$("input[name='type'][value=" + 0 + "]").prop("checked", true);
	radio(); // 要重新生成样式
	getMenu(0);
	checkRadio("catalog");
	layer_show("新建菜单", $(s), 800, 500);
}

var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "parentId",
			rootPId : -1
		},
		key : {
			url : "nourl"
		}
	}
};
var ztree;

function getMenu(menuId) {
	// 加载菜单树
	$.get("menu/select", function(r) {
		if (r.code === 0) {
			ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
			var node = ztree.getNodeByParam("menuId", menuId);
			if (null != node) {
				ztree.selectNode(node);
				parentName = node.name;
			} else {
				parentName = "一级菜单";
			}
			parentId = menuId;
			$("input[name='parentId']").val(parentId);
			$("input[name='parentName']").val(parentName);
		} else {
			layer.alert(r.msg, {
				icon : 2
			});
		}
	})
}

// ShowMenu
function showMenuTree() {
	layer.open({
		type : 1,
		offset : '50px',
		skin : 'layui-layer-molv',
		title : "选择菜单",
		area : [ '300px', '450px' ],
		shade : 0,
		shadeClose : false,
		content : jQuery("#menuLayer"),
		btn : [ '确定', '取消' ],
		btn1 : function(index) {
			var node = ztree.getSelectedNodes();
			// 选择上级菜单
			parentId = node[0].menuId;
			parentName = node[0].name;
			$("input[name='parentId']").val(parentId);
			$("input[name='parentName']").val(parentName);
			layer.close(index);
		}
	});
}

function bingICheckClick() {
	// 捕捉ICheck单机事件
	$("input").on("ifClicked", function(event) {
		checkRadio($(event.target).attr("data-event"));
	});
}

/**
 * 不同展示界面
 * 
 * @param event
 * @returns
 */
function checkRadio(event) {
	if (event === "catalog") {
		$("#catalogTable").css("display", "block");
		$("#buttonTable").css("display", "none");
		$("#menuTable").css("display", "none");
		$("#iconDiv").css("display", "block");
	}
	if (event === "button") {
		$("#catalogTable").css("display", "none");
		$("#buttonTable").css("display", "block");
		$("#menuTable").css("display", "none");
		$("#iconDiv").css("display", "none");
	}
	if (event === "menu") {
		$("#catalogTable").css("display", "block");
		$("#buttonTable").css("display", "block");
		$("#menuTable").css("display", "block");
		$("#iconDiv").css("display", "block");
	}
}