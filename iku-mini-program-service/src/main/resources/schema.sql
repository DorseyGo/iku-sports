CREATE DATABASE `iku`;
USE `iku`;

-- -----------------
-- course categories
-- -----------------
CREATE TABLE IF NOT EXISTS `category` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(24) NOT NULL COMMENT 'the category name',
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
  `link` VARCHAR(24) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  `category_id` TINYINT(2) NOT NULL DEFAULT '-1' COMMENT 'the category id',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `course_kind` (
  `id` TINYINT(2) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(24) NOT NULL COMMENT 'the kind name',
  `icon` VARCHAR(255) DEFAULT NULL COMMENT 'the icon for this kind of course',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  `category_id` TINYINT(2) NOT NULL,
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `course` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(24) NOT NULL,
  `num_classes` INT(4) NOT NULL DEFAULT "0" COMMENT 'the number of classes',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `kind_id` TINYINT(2) NOT NULL COMMENT 'the kind id',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `class` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `course_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;