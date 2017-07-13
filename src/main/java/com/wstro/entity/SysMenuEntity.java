package com.wstro.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/***
 * 菜单管理
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.AUTO)
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@TableField
	private Long parentId;

	/**
	 * 父菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 菜单名称
	 */
	@TableField
	private String name;

	/**
	 * 菜单URL
	 */
	@TableField
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@TableField
	private String perms;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	@TableField
	private Integer type;

	/**
	 * 菜单图标
	 */
	@TableField
	private String icon;

	/**
	 * 排序
	 */
	@TableField
	private Integer orderNum;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;

	@TableField(exist = false)
	private List<?> list;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SysMenuEntity [menuId=" + menuId + ", parentId=" + parentId + ", parentName=" + parentName + ", name="
				+ name + ", url=" + url + ", perms=" + perms + ", type=" + type + ", icon=" + icon + ", orderNum="
				+ orderNum + ", open=" + open + ", list=" + list + "]";
	}
}
