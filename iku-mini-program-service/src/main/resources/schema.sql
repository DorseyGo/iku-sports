CREATE DATABASE IF NOT EXISTS `iku`;
USE `iku`;

-- -----------------
-- system configuration
-- -----------------
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `sys_key` VARCHAR(32) NOT NULL UNIQUE COMMENT 'the key of the configuration',
  `sys_value` VARCHAR(64) NOT NULL COMMENT 'the value of the configuration',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

-- -----------------
-- course categories
-- -----------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(24) NOT NULL UNIQUE COMMENT 'the category name',
  `display_name` VARCHAR(24) NOT NULL COMMENT 'the display name',
  `sequence` TINYINT(2) NOT NULL DEFAULT '0' COMMENT 'the sequence',
  `last_modified_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

-- -----------------
-- activities
-- -----------------
CREATE TABLE IF NOT EXISTS `activity` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(255) NOT NULL COMMENT 'the image represents the activity',
  `title` VARCHAR(32) DEFAULT NULL COMMENT 'the brief of activity',
  `link` VARCHAR(24) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- the course
--
CREATE TABLE IF NOT EXISTS `course` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL COMMENT 'the course name',
  `level` CHAR(1) NOT NULL DEFAULT '1' COMMENT '1, for basic, 2 for intermediate, 3 for senior, 4 for advanced',
  `fee` BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'the charge in FEN RMB',
  `description` VARCHAR(255) DEFAULT NULL COMMENT 'the description of the course',
  `category_id` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- the class
--
CREATE TABLE IF NOT EXISTS `class` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(64) NOT NULL,
  `chapter` TINYINT(3) DEFAULT '1' COMMENT 'chapter of class',
  `cover` VARCHAR(255) DEFAULT NULL COMMENT 'the cover',
  `video_url` VARCHAR(255) COMMENT 'the video URL address',
  `content` VARCHAR(255) COMMENT 'the content',
  `watches` BIGINT(20) COMMENT 'the watches for this class',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `course_id` TINYINT(2) NOT NULL,
  `coach_id` INT(4) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- teacher
--
CREATE TABLE IF NOT EXISTS `coach`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL COMMENT 'the name of teacher',
  `heading_img_url` VARCHAR(255) DEFAULT NULL,
  `title` VARCHAR(36) DEFAULT NULL,
  `gender` CHAR(1) NOT NULL DEFAULT 'U' COMMENT 'U for unknown, F for female, M for male',
  `age` INT(3) NOT NULL DEFAULT '0',
  `nationality` VARCHAR(30) NOT NULL DEFAULT 'CN' COMMENT 'the nationality, CN for China',
  `level` TINYINT(2) NOT NULL DEFAULT '1' COMMENT '1 for junior, 2 for middle, 3 for HIGHER, 4 for senior',
  `introduce` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- order
--
CREATE TABLE IF NOT EXISTS `order`(
  `id` VARCHAR(32) NOT NULL,
  `fee` BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'the original fee',
  `discount` DECIMAL(3, 2) DEFAULT NULL COMMENT 'the discount',
  `money_paid` BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'money paid by user',
  `money_refund` BIGINT(20) NOT NULL DEFAULT '0',
  `status` CHAR(1) DEFAULT '0' COMMENT '0 for unpaid, 1 for paid, 2 for refund, 3 for cancel',
  `product_id` VARCHAR(32) NOT NULL COMMENT 'the product ID',
  `product_type` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '0 for course, 1 for sports goods',
  `transaction_id` CHAR(32) DEFAULT NULL COMMENT 'the transaction id, such as wechat',
  `user_id` CHAR(32) NOT NULL COMMENT 'the user id',
  `paid_time` DATETIME DEFAULT NULL COMMENT 'the time when paid',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'the order created time',
  `last_modify_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'the time when order modified',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- the student
--
CREATE TABLE IF NOT EXISTS `user`(
  `id` CHAR(32) NOT NULL ,
  `open_id` VARCHAR(32) NOT NULL ,
  `session_key` VARCHAR(64) NOT NULL ,
  `name` CHAR(32) DEFAULT NULL COMMENT 'the name',
  `avatar_url` VARCHAR(255) DEFAULT NULL COMMENT 'the heading image url',
  `gender` CHAR(1) DEFAULT 'U' COMMENT '0 for unknown, 2 for female, 1 for male',
  `telephone` VARCHAR(32) DEFAULT NULL ,
  `province` VARCHAR(32) DEFAULT NULL ,
  `city` VARCHAR(32) DEFAULT NULL ,
  `country` VARCHAR(32) DEFAULT NULL ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `teaching_style` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(24) NOT NULL COMMENT 'the title of this teaching video',
  `cover` VARCHAR(64) DEFAULT NULL COMMENT 'the relative path for video cover',
  `labels` VARCHAR(32) DEFAULT NULL COMMENT 'the labels for this video',
  `video` VARCHAR(64) DEFAULT NULL COMMENT 'the relative path for video',
  `watches` BIGINT NOT NULL DEFAULT '0' COMMENT 'the watches',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

--
-- favorite
--
CREATE TABLE `favorite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(32) NOT NULL,
  `favorite_id` int NOT NULL,
  `favorite_type` int DEFAULT '1' COMMENT '1 for article , 2 for class, 3 for coach',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=UTF8MB4;

DROP TABLE IF EXISTS `class_watched_his`;
CREATE TABLE `class_watched_his` (
  `class_id` INT NOT NULL COMMENT 'the class ID',
  `user_id` VARCHAR(32) NOT NULL COMMENT 'the user id',
  `watch_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`class_id`, `user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

drop table if exists arrange_class;

/*==============================================================*/
/* Table: arrange_class                                         */
/*==============================================================*/
create table if not exists `arrange_class` (
   `id`                   int not null auto_increment,
   `course_id`            TINYINT(2) comment '课程ID',
   `class_id`             int comment '课程ID',
   `coach_id`             int comment '教练ID',
   `site`                 varchar(256) comment '上课地点',
   `begin_time`           datetime comment '开课时间',
   `end_time`             datetime comment '结束时间',
   `duration`             int comment '时长(分钟)',
   `headcount`            int comment '课程总人数',
   `ordercount`           int comment '预约人数',
   `create_time`          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id),
   index idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 comment '课程安排表';

alter table arrange_class comment '课程排班表';

drop table if exists appointment;

/*==============================================================*/
/* Table: appointment                                           */
/*==============================================================*/
create table if not exists `appointment` (
   `id`                   int not null auto_increment,
   `arrange_id`           int comment '排班课程ID',
   `user_id`              varchar(32) comment '用户ID',
   `status`               int default 1 comment '状态:0-取消预约; 1-预约未出席; 2-确认出席; 3-完成',
   `update_time`          datetime default CURRENT_TIMESTAMP on update current_timestamp comment '更新时间',
   `create_time`          datetime default CURRENT_TIMESTAMP comment '创建时间/预约时间',
   primary key (id),
   index idx_user_arrange_id (user_id, arrange_id),
   index idx_user_status (user_id, status)
) Engine=InnoDB default charset=utf8mb4 comment '预约';

alter table artical comment '文章列表';

drop table if exists article;

/*==============================================================*/
/* Table: article                                           */
/*==============================================================*/
create table article
(id                   int not null auto_increment comment '文章id',
   user_id              varchar(32) comment '用户ID',
   status               int comment '状态:0-未发布;1-已发布',
   titile               TINYTEXT comment '标题',
   body_text            TEXT comment '文章内容',
   picture              VARCHAR (30) comment '文章配图地址',
   video                varchar(30) comment '视频地址',
   update_time          date comment '更新时间',
   create_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP comment '创建时间/预约时间',
   primary key (id)
);

alter table article comment '文章列表';

-- 消息通知
create table if not exists `message` (
    `id`                int not null auto_increment,
    `title`             varchar(255) not null default '' comment '消息通知标题',
    `content`           varchar(1024) comment '消息内容',
    `sender_id`         varchar(32)  not null default '0' comment '发送者 id，默认为系统发送',
    `sender_name`       varchar(255) not null default '系统通知' comment '发送者名字',
    `receiver_id`       varchar(32) not null default '' comment '接收者 id',
    `receiver_name`     varchar(255) not null default '' comment '接收者名字',
    `is_deleted`        tinyint default 0 comment '是否删除，0-有效; 1-删除',
    `business_id`       int not null comment '业务 id',
    `type`              tinyint not null default 0 comment '0-课程预约',
    `create_time`       datetime default CURRENT_TIMESTAMP comment '消息创建时间/发送时间',
    primary key (id),
    index idx_receiver_id (receiver_id)
) ENGINE=InnoDB default charset=utf8mb4 comment '消息通知';
