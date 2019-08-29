package com.wstro.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理员页面视图
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Controller
public class SysPageController {

    @RequestMapping("/admin/{path}/{url}.html")
    public String page(@PathVariable("path") String path, @PathVariable("url") String url) {
        return "/admin/" + path + "/" + url;
    }

    @RequestMapping("/admin/{url}.html")
    public String pageUrl(@PathVariable("url") String url) {
        return "/admin/" + url;
    }

}
