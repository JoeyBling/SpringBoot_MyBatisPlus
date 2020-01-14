package com.wstro.base;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * 测试Shiro基类
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public abstract class BaseAppShiroTest extends BaseAppMvcTest {

    protected Subject subject;
    protected MockHttpServletRequest mockHttpServletRequest;
    protected MockHttpServletResponse mockHttpServletResponse;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);

        // 防止Shiro报错
        SecurityManager securityManager = webApplicationContext
                .getBean(SecurityManager.class);
        SecurityUtils.setSecurityManager(securityManager);

        // Shiro认证登陆
        login(constant.defaultAdminName, new Sha256Hash(constant.defaultAdminPwd).toHex());
        log.info("Shiro认证登陆成功");
    }

    private void login(String username, String password) {
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);
        subject.login(token);
        ThreadContext.bind(subject);
    }

}
