CREATE DATABASE IF NOT EXISTS `iku`;
USE `iku`;

-- -----------------
-- course categories
-- -----------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(24) NOT NULL UNIQUE COMMENT 'the category name',
  `display_name` VARCHAR(24) NOT NULL COMMENT 'the display name',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT 'the icon for this category',
  `sequence` TINYINT(2) NOT NULL DEFAULT '0' COMMENT 'the sequence',
  `last_modified_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

-- -----------------
-- activities
-- -----------------
CREATE TABLE IF NOT EXISTS `activity` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(255) NOT NULL COMMENT 'the image represents the activity',
  `title` VARCHAR(32) DEFAULT NULL COMMENT 'the brief of activity',
  `link` VARCHAR(24) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  `category_id` TINYINT(2) NOT NULL DEFAULT '-1' COMMENT 'the category id',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

--
-- the course
--
CREATE TABLE IF NOT EXISTS `course` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL COMMENT 'the course name',
  `background_img` VARCHAR(255) COMMENT 'the background image url address',
  `joiner` BIGINT(20) DEFAULT '0' COMMENT 'the joiner of this course',
  `level` CHAR(1) NOT NULL DEFAULT '1' COMMENT '1, for primary, 2 for intermediate, 3 for senior',
  `fee` BIGINT(20) NOT NULL DEFAULT '0',
  `category_id` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

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
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

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
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

--
-- order
--
CREATE TABLE IF NOT EXISTS `order`(
  `id` VARCHAR(32) NOT NULL,
  `fee` BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'the original fee',
  `discount` FLOAT NOT NULL DEFAULT '1.00f' COMMENT 'the discount',
  `money_paid` BIGINT(20) NOT NULL DEFAULT '0' COMMENT 'money paid by user',
  `paid_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `refund_money` BIGINT(20) NOT NULL DEFAULT '0',
  `status` CHAR(1) DEFAULT '0' COMMENT '0 for unpaid, 1 for paid, 2 for refund, 3 for cancel',
  `course_id` TINYINT(2) NOT NULL,
  `user_id` CHAR(32) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

--
-- the student
--
CREATE TABLE IF NOT EXISTS `user`(
  `id` CHAR(32) NOT NULL ,
  `open_id` VARCHAR(32) NOT NULL ,
  `name` VARCHAR(32) NOT NULL COMMENT 'the name',
  `avatar_url` VARCHAR(255) DEFAULT NULL COMMENT 'the heading image url',
  `gender` CHAR(1) DEFAULT 'U' COMMENT '0 for unknown, 2 for female, 1 for male',
  `telephone` VARCHAR(32) DEFAULT NULL ,
  `province` VARCHAR(32) DEFAULT NULL ,
  `city` VARCHAR(32) DEFAULT NULL ,
  `country` VARCHAR(32) DEFAULT NULL ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

--
-- article
--
CREATE TABLE IF NOT EXISTS `article`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(32) NOT NULL,
  `cover` VARCHAR(255) DEFAULT NULL COMMENT 'the url of cover',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  `author` VARCHAR(24) DEFAULT NULL ,
  `category_id` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

--
-- article content
--
CREATE TABLE IF NOT EXISTS `article_content`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `material` VARCHAR(255) NOT NULL,
  `material_type` CHAR(1) NOT NULL DEFAULT '0' COMMENT '0 for text, 1 for image, 2 for video',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `article_id` INT NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

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
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8