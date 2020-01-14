package com.wstro.base;

import org.junit.Before;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * 测试Mvc基类
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@WebAppConfiguration
public abstract class BaseAppMvcTest extends BaseAppTest {

    protected MockMvc mvc;

    @Resource
    protected WebApplicationContext webApplicationContext;

    @Before
//    @BeforeClass
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}
