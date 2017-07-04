package com.wstro.util;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 因为MyBatisPlus的缘故，不能放在Dao对应的包里 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 * @param <T>
 */
public interface BaseDao<T> extends BaseMapper<T> {

	/**
	 * 查询实体集合
	 * 
	 * @param map
	 *            Map
	 * @return List<T>
	 */
	List<T> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 删除多个实体
	 * 
	 * @param id
	 *            long[]
	 * @return 影响行数
	 */
	int deleteBatch(Long[] id);

}
