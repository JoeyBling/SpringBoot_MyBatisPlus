package com.wstro.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.wstro.datasources.DataSourceContextHolder;
import com.wstro.datasources.DynamicDataSource;

/**
 * Druid数据源配置
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@Configuration
@EnableTransactionManagement // 启注解事务管理
public class DataSourceConfig {
	private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${custom.datasource.ds1.url}")
	private String dbUrl2;

	@Value("${custom.datasource.ds1.username}")
	private String username2;

	@Value("${custom.datasource.ds1.password}")
	private String password2;

	@Value("${custom.datasource.ds1.driverClassName}")
	private String driverClassName2;

	@Value("${spring.datasource.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.filters}")
	private String filters;

	@Value("${spring.datasource.connectionProperties}")
	private String connectionProperties;

	/**
	 * 注册DruidServlet
	 * 
	 * @return ServletRegistrationBean
	 */
	@Bean
	public ServletRegistrationBean druidServletRegistrationBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		// 白名单：
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not
		// permitted to view this page.
		// 登录查看信息的账号密码.
		servletRegistrationBean.addInitParameter("loginUsername", "joey");
		servletRegistrationBean.addInitParameter("loginPassword", "jay");
		// 是否能够重置数据.
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * 注册DruidFilter拦截
	 * 
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean druidFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new WebStatFilter());
		Map<String, String> initParams = new HashMap<String, String>();
		// 设置忽略请求
		initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.setInitParameters(initParams);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 配置DataSource(因为使用了多数据源注解掉@Bean)
	 * 
	 * @return DataSource
	 * @throws SQLException
	 */
	// @Bean(initMethod = "init", destroyMethod = "close")
	// @Qualifier(DataSourceContextHolder.PRIMARY_DATA_SOURCE)
	public DataSource primaryDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(dbUrl);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		druidDataSource.setDriverClassName(driverClassName);
		// configuration
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			List<Filter> proxyFilters = new ArrayList<Filter>();
			WallFilter statFilter = new WallFilter();
			WallConfig config = new WallConfig();
			config.setMultiStatementAllow(true); // 批量操作
			statFilter.setConfig(config);
			proxyFilters.add(statFilter);
			druidDataSource.setProxyFilters(proxyFilters);
			druidDataSource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		druidDataSource.setConnectionProperties(connectionProperties);
		return druidDataSource;
	}

	/**
	 * 配置DataSource2(因为使用了多数据源注解掉@Bean)
	 * 
	 * @return DataSource2
	 * @throws SQLException
	 */
	// @Bean(initMethod = "init", destroyMethod = "close")
	// @Qualifier(DataSourceContextHolder.DATA_SOURCE_B)
	public DataSource dataSource2() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(dbUrl2);
		druidDataSource.setUsername(username2);
		druidDataSource.setPassword(password2);
		druidDataSource.setDriverClassName(driverClassName2);
		// configuration
		druidDataSource.setInitialSize(initialSize);
		druidDataSource.setMinIdle(minIdle);
		druidDataSource.setMaxActive(maxActive);
		druidDataSource.setMaxWait(maxWait);
		druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		druidDataSource.setValidationQuery(validationQuery);
		druidDataSource.setTestWhileIdle(testWhileIdle);
		druidDataSource.setTestOnBorrow(testOnBorrow);
		druidDataSource.setTestOnReturn(testOnReturn);
		druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			List<Filter> proxyFilters = new ArrayList<Filter>();
			WallFilter statFilter = new WallFilter();
			WallConfig config = new WallConfig();
			config.setMultiStatementAllow(true); // 批量操作
			statFilter.setConfig(config);
			proxyFilters.add(statFilter);
			druidDataSource.setProxyFilters(proxyFilters);
			druidDataSource.setFilters(filters);
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		druidDataSource.setConnectionProperties(connectionProperties);
		return druidDataSource;
	}

	/**
	 * 使用多数据源(包括一个数据源)要注解掉DataSource的@Bean
	 * 数据源增加或减少要和DataSourceContextHolder类一起改动
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Bean
	@Qualifier("dataSource")
	public DynamicDataSource dynamicDataSource() throws SQLException {
		// 默认数据源
		DataSource primaryDataSource = primaryDataSource();
		// 第二个数据源
		// DataSource dataSource2 = dataSource2();
		DynamicDataSource dataSource = new DynamicDataSource();
		// 这里设置默认数据源
		dataSource.setDefaultTargetDataSource(primaryDataSource);
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 多数据源(如果使用一个的话就配置一个)
		// 要添加的话在写一个获取数据源的方法
		targetDataSources.put(DataSourceContextHolder.PRIMARY_DATA_SOURCE, primaryDataSource);
		// targetDataSources.put(DataSourceContextHolder.DATA_SOURCE_B,
		// dataSource2);
		dataSource.setTargetDataSources(targetDataSources);
		return dataSource;
	}

}
