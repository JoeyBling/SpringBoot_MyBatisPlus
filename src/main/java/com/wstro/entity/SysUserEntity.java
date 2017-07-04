package com.wstro.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@Data
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
	 * 状态 0：禁用 1：正常
	 */
	@TableField
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

}
