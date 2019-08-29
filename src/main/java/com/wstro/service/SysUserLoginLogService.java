package com.wstro.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wstro.entity.SysUserLoginLogEntity;

/**
 * 用户登录日志
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysUserLoginLogService extends IService<SysUserLoginLogEntity> {

    /**
     * 根据管理员ID获取登录日志
     *
     * @param offset  开始
     * @param limit   条数
     * @param adminId 管理员ID
     * @param loginIp 登录IP(筛选模糊查询)
     * @param sort    排序字段
     * @param order   是否为升序
     * @return Page<SysUserLoginLogEntity>
     */
    Page<SysUserLoginLogEntity> getSelf(Integer offset, Integer limit, Long adminId, String loginIp, String sort,
                                        Boolean order);

}
