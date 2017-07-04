/*
SQLyog Trial v12.3.3 (64 bit)
MySQL - 5.6.35-log : Database - wstro
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wstro` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wstro`;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) values 
(1,0,'系统管理','','',0,'fa fa-cog',0),
(2,1,'管理员列表','sys/user.html',NULL,1,'fa fa-user',1),
(3,1,'角色管理','sys/role.html',NULL,1,'fa fa-user-secret',2),
(4,1,'菜单管理','sys/menu.html',NULL,1,'fa fa-th-list',3),
(5,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0),
(6,2,'新增','','sys:user:save,sys:role:select',2,'',0),
(7,2,'修改','','sys:user:update,sys:role:select',2,'',0),
(8,2,'删除',NULL,'sys:user:delete',2,NULL,0),
(9,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0),
(10,3,'新增',NULL,'sys:role:save,sys:menu:perms',2,NULL,0),
(11,3,'修改',NULL,'sys:role:update,sys:menu:perms',2,NULL,0),
(12,3,'删除',NULL,'sys:role:delete',2,NULL,0),
(13,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0),
(14,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0),
(15,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0),
(16,4,'删除',NULL,'sys:menu:delete',2,NULL,0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`role_name`,`remark`,`create_time`) values 
(13,'admin','admina',1498801511);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values 
(223,13,1),
(224,13,2),
(225,13,5),
(226,13,6),
(227,13,3),
(228,13,9),
(229,13,4),
(230,13,13);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别 0=保密/1=男/2=女',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `last_login_time` int(11) DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登录IP',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`user_id`,`username`,`password`,`sex`,`email`,`mobile`,`last_login_time`,`last_login_ip`,`status`,`create_time`) values 
(1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',2,'2434387555@qq.com','13647910242',1499134791,'192.168.1.88',1,1498801511),
(7,'lastone','d50b4c0cab140a3310e256d86fd3cd33c02f145635df4694e71df062c1679a8f',2,'asd@qq.com','13456465465',1499069279,'192.168.1.88',1,1499069190);

/*Table structure for table `sys_user_login_log` */

DROP TABLE IF EXISTS `sys_user_login_log`;

CREATE TABLE `sys_user_login_log` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '登录日志ID',
  `login_time` int(11) DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(20) DEFAULT NULL COMMENT '登录IP',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `operating_system` varchar(50) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=273 DEFAULT CHARSET=utf8 COMMENT='用户登录表';

/*Data for the table `sys_user_login_log` */

insert  into `sys_user_login_log`(`log_id`,`login_time`,`login_ip`,`user_id`,`operating_system`,`browser`) values 
(144,1498643487,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(145,1498643522,'192.168.1.13',1,'WINDOWS_7','CHROME'),
(146,1498644136,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(147,1498696770,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(148,1498696935,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(149,1498697948,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(150,1498698072,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(151,1498698196,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(152,1498699082,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(153,1498699432,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(154,1498699706,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(155,1498699646,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(156,1498699799,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(157,1498700536,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(158,1498703621,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(159,1498703553,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(160,1498703797,'192.168.1.26',1,'WINDOWS_7','CHROME39'),
(161,1498703941,'192.168.1.88',1,'WINDOWS_7','CHROME'),
(162,1498704012,'192.168.1.26',1,'WINDOWS_7','CHROME39'),
(163,1498704110,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(164,1498704322,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(165,1498704455,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(166,1498704533,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(167,1498704682,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(168,1498706068,'192.168.1.26',1,'WINDOWS_7','CHROME39'),
(169,1498707433,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(170,1498707589,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(171,1498708225,'192.168.1.88',1,'WINDOWS_7','CHROME'),
(172,1498714561,'192.168.1.13',1,'WINDOWS_7','CHROME'),
(173,1498714892,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(174,1498714912,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(175,1498717536,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(176,1498719628,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(177,1498719718,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(178,1498720250,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(179,1498721509,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(180,1498721472,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(181,1498721707,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(182,1498722682,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(183,1498722697,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(184,1498722774,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(185,1498722797,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(186,1498723998,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(187,1498724109,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(188,1498724427,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(189,1498724612,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(190,1498726269,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(191,1498726979,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(192,1498727250,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(193,1498729214,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(194,1498730522,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(195,1498730704,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(196,1498784021,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(197,1498784093,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(198,1498784793,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(199,1498785172,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(200,1498786010,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(201,1498786323,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(202,1498786548,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(203,1498786548,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(204,1498786716,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(205,1498786626,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(206,1498786988,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(207,1498787137,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(208,1498787414,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(209,1498787655,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(210,1498787901,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(211,1498788939,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(212,1498789243,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(213,1498792867,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(214,1498793558,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(215,1498793866,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(216,1498794052,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(217,1498794300,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(218,1498801042,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(219,1498800942,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(220,1498802396,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(221,1498802454,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(222,1498803515,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(223,1498804811,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(224,1498805228,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(225,1498805241,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(226,1498805469,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(227,1498805899,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(228,1498806813,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(229,1498806800,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(230,1498806995,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(231,1498807290,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(232,1498807203,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(233,1498807725,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(234,1498808125,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(235,1498809962,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(236,1498811173,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(237,1498811214,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(238,1498811582,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(239,1498811989,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(240,1498812194,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(241,1498812256,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(242,1498812373,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(243,1498812703,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(244,1498812751,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(245,1498814101,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(246,1498814281,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(247,1498814294,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(248,1498815015,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(249,1499042315,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(250,1499043527,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(251,1499049141,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(252,1499051653,'192.168.1.88',1,'WINDOWS_7','IE9'),
(253,1499051758,'192.168.1.88',1,'WINDOWS_7','CHROME51'),
(254,1499052573,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(255,1499052849,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(256,1499053115,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(257,1499060576,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(258,1499062246,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(259,1499066402,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(260,1499066583,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(261,1499066779,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(262,1499067338,'0:0:0:0:0:0:0:1',1,'WINDOWS_7','CHROME49'),
(263,1499069038,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(264,1499069279,'192.168.1.88',7,'WINDOWS_7','IE9'),
(265,1499069518,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(266,1499069582,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(267,1499070803,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(268,1499070825,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(269,1499071525,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(270,1499131270,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(271,1499134326,'192.168.1.88',1,'WINDOWS_7','CHROME49'),
(272,1499134791,'192.168.1.88',1,'WINDOWS_7','CHROME49');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values 
(57,7,13),
(58,1,13);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
