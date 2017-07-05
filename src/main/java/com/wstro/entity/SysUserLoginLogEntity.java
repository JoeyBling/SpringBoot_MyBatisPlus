package com.wstro.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/***
 * 用户登录日志
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@TableName("sys_user_login_log")
public class SysUserLoginLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 登录日志ID
	 */
	@TableId(type = IdType.AUTO)
	private Long logId;

	/**
	 * 登录时间
	 */
	@TableField
	private Long loginTime;

	/**
	 * 登录IP
	 */
	@TableField
	private String loginIp;

	/**
	 * 用户ID
	 */
	@TableField
	private Long userId;

	/**
	 * 操作系统
	 */
	@TableField
	private String operatingSystem;

	/**
	 * 浏览器
	 */
	@TableField
	private String browser;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

}
