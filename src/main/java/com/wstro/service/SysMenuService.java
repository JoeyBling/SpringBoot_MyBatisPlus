package com.wstro.service;

import com.baomidou.mybatisplus.service.IService;
import com.wstro.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 获取不包含按钮的菜单列表
     *
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     *
     * @param userId 用户ID
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> getUserMenuList(Long userId);

    /**
     * 查询菜单列表
     *
     * @param map Map
     * @return List<SysMenuEntity>
     */
    List<SysMenuEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @param map Map
     * @return int
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 根据菜单ID删除菜单
     *
     * @param menuIds 菜单ID
     * @return 影响行数
     */
    Integer deleteBatch(Long[] menuIds);
}
