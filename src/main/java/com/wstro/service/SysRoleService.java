package com.wstro.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wstro.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 查询角色列表
     *
     * @param map Map
     * @return List<SysRoleEntity>
     */
    List<SysRoleEntity> queryList(Map<String, Object> map);

    /**
     * 根据角色ID删除角色
     *
     * @param roleIds 角色ID
     */
    void deleteBatch(Long[] roleIds);

    /**
     * 保存角色
     *
     * @param role SysRoleEntity
     */
    void save(SysRoleEntity role);

    /**
     * 更新角色
     *
     * @param role SysRoleEntity
     */
    void update(SysRoleEntity role);

    /**
     * 查询角色列表
     *
     * @param offset   开始
     * @param limit    条数
     * @param roleName 角色名
     * @param sort     排序字段
     * @param order    是否为升序
     * @return Page<SysRoleEntity>
     */
    Page<SysRoleEntity> queryListByPage(Integer offset, Integer limit, String roleName, String sort, Boolean order);
}
