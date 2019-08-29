package com.wstro.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.wstro.entity.SysUserLoginLogEntity;
import com.wstro.service.SysUserLoginLogService;
import com.wstro.util.PageUtils;
import com.wstro.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统用户登录日志
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@RestController
@RequestMapping("/admin/sys/log")
public class SysUserLoginLogController extends AbstractController {

    @Resource
    private SysUserLoginLogService sysUserLoginLogService;

    @RequestMapping("/list")
    // @RequiresPermissions("sys:user:list")
    @ResponseBody
    public R list(Integer offset, Integer limit, String sort, String order,
                  @RequestParam(name = "search", required = false) String loginIp, HttpServletRequest request) {
        offset = (offset / limit) + 1;
        Boolean flag = null; // 排序逻辑
        if (StringUtils.isNoneBlank(order)) {
            if (order.equalsIgnoreCase("asc")) {
                flag = true;
            } else {
                flag = false;
            }
        }
        Page<SysUserLoginLogEntity> self = sysUserLoginLogService.getSelf(offset, limit, getAdminId(), loginIp, sort,
                flag);
        PageUtils pageUtil = new PageUtils(self.getRecords(), self.getTotal(), self.getSize(), self.getCurrent());
        return R.ok().put("page", pageUtil);

    }
}
