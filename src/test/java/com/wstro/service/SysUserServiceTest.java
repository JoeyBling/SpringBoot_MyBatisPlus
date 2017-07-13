package com.wstro.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wstro.App;
import com.wstro.entity.SysUserEntity;

/**
 * 系统用户测试
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class SysUserServiceTest {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 查询列表
	 */
	@Test
	public void selectList() {
		List<SysUserEntity> selectList = sysUserService.selectList(null);
		for (SysUserEntity sysUserEntity : selectList) {
			System.out.println(sysUserEntity);
		}
	}

}
