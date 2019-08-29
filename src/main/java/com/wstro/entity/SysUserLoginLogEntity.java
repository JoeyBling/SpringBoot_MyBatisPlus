package com.wstro.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/***
 * 用户登录日志
 *
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@Data
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

}
