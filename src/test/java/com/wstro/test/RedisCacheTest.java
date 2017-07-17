package com.wstro.test;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis读写测试(项目打包会报错java.lang.ClassNotFoundException:
 * com.jagregory.shiro.freemarker.ShiroTags)
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
public class RedisCacheTest {

	@Resource
	StringRedisTemplate stringRedisTemplate;

	//@Test
	public void redisTest() throws Exception {
		// 保存字符串
		stringRedisTemplate.opsForValue().set("aaa", "111");
		// 读取字符串
		String aaa = stringRedisTemplate.opsForValue().get("aaa");
		System.out.println(aaa);
	}
}