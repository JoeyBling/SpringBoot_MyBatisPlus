package com.wstro.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.wstro.entity.SysUserEntity;
import com.wstro.service.SysUserLoginLogService;
import com.wstro.service.SysUserRoleService;
import com.wstro.service.SysUserService;
import com.wstro.util.PageUtils;
import com.wstro.util.R;
import com.wstro.util.ShiroUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Controller
@RequestMapping("/admin/sys/user")
public class SysUserController extends AbstractController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysUserLoginLogService sysUserLoginLogService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    @ResponseBody
    public R list(Integer offset, Integer limit, String sort, String order,
                  @RequestParam(name = "search", required = false) String search) {
        String userName = null;
        String email = null;
        Map<String, String> searchList = parseObject(search, "q_userName", "q_email");
        if (null != searchList) {
            userName = searchList.get("q_userName");
            email = searchList.get("q_email");
        }
        offset = (offset / limit) + 1;
        Boolean flag = null; // 排序逻辑
        if (StringUtils.isNoneBlank(order)) {
            if (order.equalsIgnoreCase("asc")) {
                flag = true;
            } else {
                flag = false;
            }
        }
        Page<SysUserEntity> adminList = sysUserService.queryListByPage(offset, limit, email, userName, sort, flag);
        PageUtils pageUtil = new PageUtils(adminList.getRecords(), adminList.getTotal(), adminList.getSize(),
                adminList.getCurrent());
        return R.ok().put("page", pageUtil);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    @ResponseBody
    public R info() {
        return R.ok().put("user", getAdmin());
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @ResponseBody
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.selectById(userId);

        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     *
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions("sys:user:save")
    public R save(@RequestParam("role") Long[] roles, @Valid SysUserEntity user, BindingResult result)
            throws Exception {
        if (result.hasErrors()) { // 验证有误
            return R.error(result.getFieldError().getDefaultMessage());
        }
        if (roles.length < 1) {
            return R.error("请为用户赋予至少一个权限");
        }
        List<Long> roleIdList = new ArrayList<Long>();
        Collections.addAll(roleIdList, roles);
        user.setRoleIdList(roleIdList);
        if (StringUtils.isBlank(user.getPassword())) {
            return R.error("密码不能为空");
        }
        sysUserService.save(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @ResponseBody
    @RequiresPermissions("sys:user:update")
    public R update(SysUserEntity user, @RequestParam("role") Long[] roles) {
        if (roles.length < 1) {
            return R.error("请为用户赋予至少一个权限");
        }
        SysUserEntity userentity = sysUserService.selectById(user.getUserId());
        if (!user.getPassword().trim().equals("")) {
            user.setPassword(user.getPassword());
        }
        List<Long> roleIdList = new ArrayList<Long>();
        Collections.addAll(roleIdList, roles);
        user.setRoleIdList(roleIdList);
        user.setCreateTime(userentity.getCreateTime());
        user.setLastLoginIp(userentity.getLastLoginIp());
        user.setLastLoginTime(userentity.getLastLoginTime());
        if (StringUtils.isBlank(user.getUsername())) {
            return R.error("用户名不能为空");
        }
        if (getAdminId().equals(user.getUserId())) {
            if (user.getStatus().equals(0)) {
                ShiroUtils.logout(); // 退出
            }
        }
        sysUserService.updateUser(user);
        return R.ok();
    }

    /**
     * 修改用户状态
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    @RequiresPermissions("sys:user:update")
    public R updateStatus(Long userId, @RequestParam("state") Boolean status) {
        int updateStatus = sysUserService.updateStatus(userId, status ? 1 : 0);
        if (updateStatus > 0)
            return R.ok();
        else
            return R.error("修改失败!");
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @ResponseBody
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestParam("userIds") String ids) {
        JSONArray jsonArray = JSONArray.parseArray(ids);
        Long[] userIds = toArrays(jsonArray);
        if (userIds.length < 1) {
            return R.error("删除的用户为空");
        }
        if (ArrayUtils.contains(userIds, constant.adminId)) {
            return R.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getAdminId())) {
            return R.error("当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return R.ok();
    }

    /**
     * 自己的个人信息
     *
     * @return
     */
    @RequestMapping("/view")
    public String view(Model model) {
        model.addAttribute("admin", getAdmin());
        return "/admin/sys/info";
    }

    /**
     * 修改头像
     *
     * @param model Model
     * @return
     */
    @RequestMapping("/avatar.html")
    public String avatar(Model model) {
        model.addAttribute("admin", getAdmin());
        return "/admin/sys/avatar";
    }

    /**
     * 更新自己的个人信息
     *
     * @return
     */
    @RequestMapping("/updateView")
    @ResponseBody
    public R updateView(SysUserEntity user) {
        SysUserEntity currentUser = getAdmin();
        currentUser.setEmail(user.getEmail());
        currentUser.setMobile(user.getMobile());
        currentUser.setUsername(user.getUsername());
        currentUser.setSex(user.getSex());
        boolean updateById = sysUserService.updateById(currentUser);
        if (updateById)
            return R.ok();
        else
            return R.error("修改失败!");
    }

    /**
     * 更新自己的头像
     *
     * @return
     */
    @RequestMapping("/updateAvatar")
    @ResponseBody
    public R updateAvatar(String avatarUrl) {
        SysUserEntity currentUser = getAdmin();
        currentUser.setAvatarUrl(avatarUrl);
        int updateAvatar = sysUserService.updateAvatar(currentUser);
        if (updateAvatar > 0) {
            return R.ok();
        } else {
            return R.error("修改失败!");
        }
    }

    /**
     * 修改自己的密码
     *
     * @param oldPassword
     * @param newPassword
     * @param secPassword
     * @return
     */
    @RequestMapping("/updateSelfPassword")
    @ResponseBody
    public R updateSelfPassword(@RequestParam("nowPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword, @RequestParam("confirmPwd") String secPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            return R.error("请输入原密码！");
        }
        if (StringUtils.isBlank(newPassword)) {
            return R.error("请输入新密码！");
        }
        if (StringUtils.isBlank(secPassword)) {
            return R.error("请确认新密码！");
        }
        if (!newPassword.equals(secPassword)) {
            return R.error("两次密码输入不同！");
        }
        oldPassword = new Sha256Hash(oldPassword).toHex();
        newPassword = new Sha256Hash(newPassword).toHex();
        int count = this.sysUserService.updatePassword(getAdminId(), oldPassword, newPassword);
        if (count != 1) {
            return R.error("原密码输入错误！");
        }
        return R.ok();
    }

}
