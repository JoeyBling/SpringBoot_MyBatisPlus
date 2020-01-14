package com.wstro.shiro;

import com.wstro.entity.SysMenuEntity;
import com.wstro.entity.SysUserEntity;
import com.wstro.service.SysMenuService;
import com.wstro.service.SysUserService;
import com.wstro.util.Constant;
import com.wstro.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Shiro认证
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public class UserRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    // @Resource
    private SysMenuService sysMenuService;

    // @Resource
    private SysUserService sysUserService;

    // @Resource
    private Constant constant;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        preHandleNull();
        logger.info("授权(验证权限时调用)");
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList = null;

        // 系统管理员，拥有最高权限
        if (constant.adminId.equals(userId)) {
            List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserService.queryAllPerms(userId);
        }

        // 用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        preHandleNull();
        logger.info("认证(登录时调用)");
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        // 查询用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        // 密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 前置处理非空对象
     * shiroFilter在Spring自动装配bean之前实例化
     * 相关联的Bean都被初始化完成且没有被代理（包括BeanPostProcessor也会无效）导致事务失效等......
     * 使用动态获取代理对象即可解决
     */
    protected void preHandleNull() {
        if (null == sysMenuService) {
            sysMenuService = SpringContextUtils.getBean(SysMenuService.class);
        }
        if (null == sysUserService) {
            sysUserService = SpringContextUtils.getBean(SysUserService.class);
        }
        if (null == constant) {
            constant = SpringContextUtils.getBean(Constant.class);
        }
    }

}
