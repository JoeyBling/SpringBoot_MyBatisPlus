数据库文件: /sql/wstro.sql  直接运行mysql
   			更改配置文件application-*.properties的数据库连接信息
			spring.datasource.url
			spring.datasource.username
			spring.datasource.password
			
项目整合了多数据源,注解方法  @DataSource(这里写数据源名称) 如 DataSourceContextHolder.DATA_SOURCE_B 建议数据源名称都定义在此类中，方便维护
多数据源需要自己去开启，具体在DataSourceConfig.java
						


启动说明:
	项目依赖mysql、Redis服务。

	启动命令:
		(如果有问题。请尝试强制删除target目录下的所有文件)
		mvn clean package -P build tomcat7:run-war-only -f pom.xml
	
	打包命令:
		(如果有问题。请尝试强制删除target目录下的所有文件)
		mvn clean package spring-boot:repackage
		会在target目录生成wstro.war  直接部署Tomcat运行
	
	访问地址:
			localhost:8080/admin
			
	注意：本项目使用的是  JDK1.8 
		 可自行修改 pom文件的 maven.compiler.source  maven.compiler.target为1.7


项目使用技术:SpringBoot Spring SpringMVC MyBatis MyBatisPlus Freemaker ApacheShiro 

数据库:MySql


部署:application.properties更改指定部署模式还是开发模式 dev / prod  
        分别对应application-dev.properties  /  application-prod.properties


	修改dev / prod 文件 
		SEO:
			seo.author 作者
			seo.keywords 关键词
			seo.description 网页描述  (如果是中文，请进行Unicode转码  http://tool.chinaz.com/tools/unicode.aspx)
		
		server.port 服务端口  (部署在Tomcat上以Tomcat为准)
		server.contextPath 服务器上文文路径 请和servlet.contextPath统一   (部署在Tomcat上以Tomcat为准)
		
		spring.mail 设置邮件的端口 账号及密码
		
		spring.redis 设置Redis 服务器地址 密码 及端口
		
		spring.datasource.url 设置数据库连接信息  账号(username) 及 密码(password)


开发者:
	
	调试直接运行  com.wstro.App.java Run As  java Application
	
	打包:
		mvn运行  mvn clean package spring-boot:repackage
		最后在target目录下面生成一个war包 直接部署Tomcat运行
	
	
	此处Redis缓存注解和EhCache缓存注解只能使用1个
	使用
		@Primary标注