package com.wstro.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误控制器
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Controller
public class MainsiteErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError() {
        return "/error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
