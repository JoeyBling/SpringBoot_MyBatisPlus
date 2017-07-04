package com.wstro.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(fixedRate在项目启动时会启动，cron不会启动)
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@Component // 此注解必加
@EnableScheduling // 此注解必加
public class TestTask {

	private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

	//@Scheduled(fixedRate = 50000) // 每5秒执行一次
	public void task() {
		logger.info("测试定时任务!");
	}

	//@Scheduled(cron = "0/100 * * * * ?") // 每2秒执行一次
	public void doSomething() throws Exception {
		logger.info("每2秒执行一个的定时任务：" + new Date());
	}

}
