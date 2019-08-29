package com.wstro.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wstro.dao.SysMenuDao;
import com.wstro.entity.SysMenuEntity;
import com.wstro.service.SysMenuService;
import com.wstro.service.SysRoleMenuService;
import com.wstro.service.SysUserService;
import com.wstro.util.Constant;
import com.wstro.util.Constant.MenuType;
import com.wstro.util.EhCacheNames;
import com.wstro.util.EhcacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private Constant constant;
    @Resource
    private EhcacheUtil ehcacheUtil;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = baseMapper.queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        String cacheName = EhCacheNames.menuCacheName + userId;
        Object object = ehcacheUtil.get(EhcacheUtil.ADMINMENUEHCACHENAME, cacheName);
        if (null != object) {
            if (object instanceof List) {
                logger.info("用户菜单列表从EhCache缓存拿值");
                return (List<SysMenuEntity>) object;
            }
        }
        // 系统管理员，拥有最高权限
        if (userId.equals(constant.adminId)) {
            List<SysMenuEntity> allMenuList = getAllMenuList(null);
            ehcacheUtil.put(EhcacheUtil.ADMINMENUEHCACHENAME, cacheName, allMenuList);
            return allMenuList;
        }
        // 用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        List<SysMenuEntity> allMenuList = getAllMenuList(menuIdList);
        ehcacheUtil.put(EhcacheUtil.ADMINMENUEHCACHENAME, cacheName, allMenuList);
        return allMenuList;
    }

    @Override
    public List<SysMenuEntity> queryList(Map<String, Object> map) {
        return baseMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return baseMapper.queryTotal(map);
    }

    @Override
    @Transactional
    public Integer deleteBatch(Long[] menuIds) {
        return baseMapper.deleteBatch(menuIds);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        // 查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        // 递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            if (entity.getType() == MenuType.CATALOG.getValue()) {// 目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }
}
