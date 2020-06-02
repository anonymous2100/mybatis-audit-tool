use db_test;

DROP TABLE IF EXISTS t_user;

CREATE TABLE t_user (
	id int (11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
	user_name varchar (256) DEFAULT NULL COMMENT '姓名',
	password varchar (256) COMMENT '密码',
	phone_number varchar (128) DEFAULT NULL COMMENT '手机号',
	email varchar (256) DEFAULT NULL COMMENT '邮箱',
	gender int (1) DEFAULT '0' COMMENT '员工性别，0-男，1-女',
	birth_date date DEFAULT NULL COMMENT '出生日期',
	city varchar (256) DEFAULT NULL COMMENT '城市',
	province varchar (256) DEFAULT NULL COMMENT '省份',
	country varchar (256) DEFAULT NULL COMMENT '国家',
	address varchar (512) comment '地址',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
	is_delete int (2) DEFAULT 0 COMMENT '是否删除标记，0-未删除，1-删除',
	PRIMARY KEY (id)
) ENGINE = InnoDB AUTO_INCREMENT = 514 DEFAULT CHARSET = utf8mb4 COMMENT = '用户信息表';

--DROP TABLE
--IF EXISTS t_department;
--
--CREATE TABLE t_department (
--	id int (11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
--	parent_id int (11) NOT NULL COMMENT '父id',
--	department_name varchar (256) NOT NULL COMMENT '部门名称',
--	description varchar (512) comment '说明',
--	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--	update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
--	is_delete tinyint (2) DEFAULT '0' COMMENT '是否删除标记，0-未删除，1-删除',
--	PRIMARY KEY (id)
--) ENGINE = InnoDB AUTO_INCREMENT = 19 DEFAULT CHARSET = utf8mb4 COMMENT = '部门信息表';

