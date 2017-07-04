package com.wstro.config;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;

@Configuration
@MapperScan("com.wstro.dao*")
public class MybatisPlusConfig {

	/**
	 * mybatis-plus分页插件<br>
	 * 文档：http://mp.baomidou.com<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDialectType("mysql");
		return paginationInterceptor;
	}

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * XML文件热加载
	 * 
	 * @return MybatisMapperRefresh
	 * @throws Exception
	 */
	public MybatisMapperRefresh mybatisMapperRefresh() throws Exception {
		MybatisMapperRefresh mybatisMapperRefresh = new MybatisMapperRefresh(sqlSessionFactory, true);
		return mybatisMapperRefresh;
	}
}
