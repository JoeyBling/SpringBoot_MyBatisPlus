package com.wstro.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField
    private String roleName;

    /**
     * 备注
     */
    @TableField
    private String remark;

    /**
     * 创建时间
     */
    @TableField
    private Long createTime;

    @TableField(exist = false)
    private List<Long> menuIdList;

}
