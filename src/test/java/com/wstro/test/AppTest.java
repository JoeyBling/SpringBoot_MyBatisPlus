package com.wstro.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wstro.App;

/**
 * 测试基类
 * 
 * @author Joey
 * @Email 2434387555@qq.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public abstract class AppTest {

}
