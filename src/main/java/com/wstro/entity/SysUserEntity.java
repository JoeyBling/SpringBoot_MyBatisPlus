package com.wstro.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 系统用户
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId(type = IdType.AUTO)
	private Long userId;

	/**
	 * 用户名
	 */
	@TableField
	@NotEmpty(message = "用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@TableField
	private transient String password;

	/**
	 * 性别 0=保密/1=男/2=女
	 */
	@TableField
	@NotNull(message = "性别不能为空")
	private Integer sex;

	/**
	 * 邮箱
	 */
	@TableField
	private String email;

	/**
	 * 手机号
	 */
	@TableField
	private String mobile;

	/**
	 * 最后登录时间
	 */
	@TableField
	private Long lastLoginTime;

	/**
	 * 最后登录IP
	 */
	@TableField
	private String lastLoginIp;
	
	/**
	 * 头像缩略图地址
	 */
	private String avatarUrl;

	/**
	 * 状态 0：禁用 1：正常
	 */
	@TableField
	@NotNull(message = "状态不能为空")
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField
	private Long createTime;

	/**
	 * 角色ID列表(排除表字段)
	 */
	@TableField(exist = false)
	private List<Long> roleIdList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
