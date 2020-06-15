package com.wstro.controller.admin;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.wstro.entity.SysMenuEntity;
import com.wstro.entity.SysUserEntity;
import com.wstro.entity.SysUserLoginLogEntity;
import com.wstro.service.SysMenuService;
import com.wstro.service.SysUserLoginLogService;
import com.wstro.service.SysUserService;
import com.wstro.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * 管理员登录相关
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Controller
@RequestMapping("/admin")
public class SysLoginController extends AbstractController {

    @Resource
    private Producer producer;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysUserLoginLogService sysUserLoginLogService;

    /**
     * 默认访问页面
     *
     * @return String
     */
    @RequestMapping("/")
    public String redirect() {
        if (ShiroUtils.isLogin()) {
            return "redirect:/admin/index.html";
        } else {
            return "/admin/login";
        }
    }

    /**
     * 管理员首页
     *
     * @return String
     */
    @RequestMapping("/index.html")
    public String index(Model model) {
        model.addAttribute("admin", getAdmin());
        // 用户菜单列表
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getAdminId());
        model.addAttribute("menuList", menuList);
        return "/admin/index";
    }

    /**
     * 管理员验证码
     *
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 管理员登录
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @return Map
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @Transactional
    public R login(String username, String password, String captcha, HttpServletRequest request) throws IOException {
        if (StringUtils.isBlank(username)) {
            return R.error("请输入用户名！");
        }
        if (StringUtils.isBlank(password)) {
            return R.error("请输入密码！");
        }
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(kaptcha)) {
            return R.error("验证码不正确");
        }
        try {
            Subject subject = ShiroUtils.getSubject();
            // sha256加密
            password = new Sha256Hash(password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(e.getMessage());
        } catch (LockedAccountException e) {
            return R.error(e.getMessage());
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }
        SysUserEntity user = getAdmin();
        // 登录IP
        String ipAddress = GetIpAddress.getIpAddress(request);
        // 当前时间戳
        Long currentUnixTime = DateUtils.getCurrentUnixTime();
        user.setLastLoginIp(ipAddress);
        user.setLastLoginTime(currentUnixTime);
        boolean updateById = sysUserService.updateById(user);
        if (!updateById) {
            return R.error("系统异常，请联系管理员!");
        }
        // 记录登录日志
        SysUserLoginLogEntity entity = new SysUserLoginLogEntity();
        entity.setBrowser(HttpUtil.getUserBrowser(request));
        entity.setLoginTime(currentUnixTime);
        entity.setOperatingSystem(HttpUtil.getUserOperatingSystem(request));
        entity.setUserId(getAdminId());
        entity.setLoginIp(ipAddress);
        boolean insert = sysUserLoginLogService.insert(entity);
        if (!insert) { // 这里只能抛异常回滚事务
            throw new RRException("系统异常，请联系管理员!");
        }
        return R.ok();
    }

    /**
     * 管理员退出
     *
     * @return String
     * @throws Exception
     */
    @RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
    public String logout() throws Exception {
        if (ShiroUtils.isLogin()) {
            String cacheName = EhCacheNames.menuCacheName + ShiroUtils.getUserId();
            ehcacheUtil.remove(EhcacheUtil.ADMINMENUEHCACHENAME, cacheName);
            logger.info("管理员退出，清空用户菜单列表Cache");
            Long userId = ShiroUtils.getUserId();
            if (userId != null && userId.equals(Long.valueOf(constant.adminId))) { // 系统管理员(退出系统备份数据库)
            }
            ShiroUtils.logout();
        }

        return "redirect:/admin/login.html";
    }

}
