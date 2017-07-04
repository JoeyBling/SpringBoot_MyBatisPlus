package com.wstro.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.wstro.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	void saveOrUpdate(Long roleId, List<Long> menuIdList);

	/**
	 * 根据角色ID，获取菜单ID列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

}
