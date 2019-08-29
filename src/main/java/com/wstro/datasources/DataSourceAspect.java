package com.wstro.datasources;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切换数据源Advice
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Aspect
@Order(-10) // 保证该AOP在@Transactional之前执行
@Component
public class DataSourceAspect {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @Before("@annotation(DataSource)")的意思是： @Before：在方法执行之前进行执行： @annotation(DataSource)：
     * 会拦截注解DataSource的方法，否则不拦截;
     */
    @Before(value = "@annotation(dataSource)")
    public void afterReturning(JoinPoint point, DataSource dataSource) throws Throwable {
        // 获取当前的指定的数据源;
        String dsId = dataSource.value();
        // 如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。

        if (!DataSourceContextHolder.containsDataSource(dsId)) {
            logger.warn("数据源[" + dataSource.value() + "]不存在，使用默认数据源 > " + DataSourceContextHolder.PRIMARY_DATA_SOURCE);
        } else {
            logger.info("使用数据源 > " + dataSource.value());
            // 找到的话，那么设置到动态数据源上下文中。
            DataSourceContextHolder.setDbType(dataSource.value());
        }
    }

    @After(value = "@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        logger.info("Revert DataSource  > " + dataSource.value() + " >" + point.getSignature());
        // 方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DataSourceContextHolder.clearDbType();
    }
}