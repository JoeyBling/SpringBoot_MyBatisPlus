package com.wstro.dao;

import com.wstro.entity.SysRoleEntity;
import com.wstro.util.BaseDao;

/**
 * 角色管理
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

    /**
     * 更新角色
     *
     * @param role SysRoleEntity
     */
    void updateNoMapper(SysRoleEntity role);

}
