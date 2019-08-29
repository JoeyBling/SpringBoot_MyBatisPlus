package com.wstro.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wstro.entity.SysUserEntity;

import java.util.List;

/**
 * 系统用户
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     * @return List<String>
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId 用户ID
     * @return 菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     *
     * @param username 用户名
     * @return SysUserEntity
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 删除用户
     *
     * @param userIds long[]
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     * @return int
     */
    int updatePassword(Long userId, String password, String newPassword);

    /**
     * 保存用户
     *
     * @param user SysUserEntity
     * @throws Exception
     */
    void save(SysUserEntity user) throws Exception;

    /**
     * 修改用户
     *
     * @param entity
     */
    void updateUser(SysUserEntity entity);

    /**
     * 查询管理员列表
     *
     * @param offset   开始
     * @param limit    条数
     * @param email    邮箱
     * @param userName 管理员名称
     * @param sort     排序字段
     * @param order    是否为升序
     * @return Page<SysUserEntity>
     */
    Page<SysUserEntity> queryListByPage(Integer offset, Integer limit, String email, String userName, String sort,
                                        Boolean order);

    /**
     * 更新用户头像
     *
     * @param entity 系统用户
     * @return 更新行数
     */
    int updateAvatar(SysUserEntity entity);

    /**
     * 根据用户ID更新用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态
     * @return 更新行数
     */
    int updateStatus(Long userId, int status);
}
