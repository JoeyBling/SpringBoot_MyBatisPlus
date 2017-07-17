package com.wstro.service;

import java.util.List;

import javax.annotation.Resource;

import com.wstro.entity.SysUserEntity;

/**
 * 系统用户测试
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
public class SysUserServiceTest {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 查询列表
	 */
	// @Test
	public void selectList() {
		List<SysUserEntity> selectList = sysUserService.selectList(null);
		for (SysUserEntity sysUserEntity : selectList) {
			System.out.println(sysUserEntity);
		}
	}

}
