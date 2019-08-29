package com.wstro.dao;

import com.wstro.entity.SysUserEntity;
import com.wstro.util.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {

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
     * @return List<Long>
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     *
     * @param username 用户名
     * @return 用户名
     */
    SysUserEntity queryByUserName(String username);

    /**
     * 修改密码
     *
     * @param map Map Map<String, Object>
     * @return int
     */
    int updatePassword(Map<String, Object> map);

    /**
     * 修改用户
     *
     * @param entity
     */
    void updateUser(SysUserEntity entity);

    /**
     * 更新头像
     *
     * @param entity SysUserEntity
     * @return int
     */
    int updateAvatar(SysUserEntity entity);

    /**
     * 根据用户ID更新用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态
     * @return 更新行数
     */
    int updateStatus(@Param("userId") Long userId, @Param("status") int status);
}
