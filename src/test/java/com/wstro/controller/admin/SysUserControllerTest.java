package com.wstro.controller.admin;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wstro.App;

/**
 * 系统用户控制器测试
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class SysUserControllerTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	MockMvc mvc;

	@Resource
	WebApplicationContext webApplicationConnect;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
		// 防止Shiro报错
		org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager) webApplicationConnect
				.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);
	}

	/**
	 * 测试管理员用户列表
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUserList() throws Exception {
		String uri = "/admin/sys/user/list?offset=0&limit=100";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		mvcResult.getResponse().setCharacterEncoding("utf-8");
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		logger.info(status + "" + content);
	}

}
