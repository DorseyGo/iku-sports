CREATE DATABASE `iku`;
USE `iku`;

-- -----------------
-- activities
-- -----------------
CREATE TABLE IF NOT EXISTS `activity` (
  `id` INT(4) NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(255) NOT NULL COMMENT 'the image represents the activity',
  `link` VARCHAR(24) DEFAULT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

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