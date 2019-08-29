package com.wstro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动SpringBoot会执行 @Order标识执行顺序 值越小越先执行
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Component
@Order(0)
public class StartupRunner2 implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.info(">>服务启动执行，执行加载数据等操作0<<");
    }

}
