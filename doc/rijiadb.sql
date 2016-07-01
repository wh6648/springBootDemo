/*
Navicat MySQL Data Transfer

Source Server         : MySql_localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : rijiadb

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-07-01 17:11:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_attendance_type
-- ----------------------------
DROP TABLE IF EXISTS `t_attendance_type`;
CREATE TABLE `t_attendance_type` (
  `id` varchar(36) NOT NULL,
  `schedules` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_attendance_type
-- ----------------------------
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e2-9e29-64046a960832', '晚班', 'B');
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e5-9e29-64006a260831', '晚班', 'A');
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e6-9e29-64006a260831', '中班', 'A');
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e6-9e29-64006a960831', '早班', 'A');
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e6-9e29-64006a960832', '早班', 'B');
INSERT INTO `t_attendance_type` VALUES ('b1557342-33a3-11e6-9e29-64046a960832', '中班', 'B');

-- ----------------------------
-- Table structure for t_daily_paper
-- ----------------------------
DROP TABLE IF EXISTS `t_daily_paper`;
CREATE TABLE `t_daily_paper` (
  `user_id` varchar(36) NOT NULL,
  `work_date` date NOT NULL,
  `machine_type_id` varchar(36) NOT NULL,
  `batch_number` bigint(64) NOT NULL,
  `process_lines_type_id` varchar(36) NOT NULL,
  `point` bigint(64) NOT NULL,
  `batch_count` bigint(64) NOT NULL,
  `work_station` varchar(32) NOT NULL,
  `monitor_user_id` varchar(36) DEFAULT NULL,
  `attendance_type_id` varchar(36) DEFAULT NULL,
  `defect_id` varchar(36) NOT NULL,
  `u_time` datetime DEFAULT NULL,
  `u_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`work_date`,`machine_type_id`,`batch_number`,`process_lines_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_daily_paper
-- ----------------------------

-- ----------------------------
-- Table structure for t_defect_differentiate_type
-- ----------------------------
DROP TABLE IF EXISTS `t_defect_differentiate_type`;
CREATE TABLE `t_defect_differentiate_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_defect_differentiate_type
-- ----------------------------
INSERT INTO `t_defect_differentiate_type` VALUES ('34151590-3c35-11e6-9e29-64006a960834', 'A');
INSERT INTO `t_defect_differentiate_type` VALUES ('38428eba-3c35-11e6-9e29-64006a960834', 'B');
INSERT INTO `t_defect_differentiate_type` VALUES ('3aafb1a1-3c35-11e6-9e29-64006a960834', 'C');
INSERT INTO `t_defect_differentiate_type` VALUES ('3d8fac14-3c35-11e6-9e29-64006a960834', 'D');
INSERT INTO `t_defect_differentiate_type` VALUES ('3ed58dcb-3c35-11e6-9e29-64006a960834', 'E');

-- ----------------------------
-- Table structure for t_defect_item_type
-- ----------------------------
DROP TABLE IF EXISTS `t_defect_item_type`;
CREATE TABLE `t_defect_item_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_defect_item_type
-- ----------------------------
INSERT INTO `t_defect_item_type` VALUES ('34151590-3c35-11e6-9e29-64006a960834', 'A1');
INSERT INTO `t_defect_item_type` VALUES ('38428eba-3c35-11e6-9e29-64006a960834', 'A2');
INSERT INTO `t_defect_item_type` VALUES ('3aafb1a1-3c35-11e6-9e29-64006a960834', 'A3');
INSERT INTO `t_defect_item_type` VALUES ('3d8fac14-3c35-11e6-9e29-64006a960834', 'B1');
INSERT INTO `t_defect_item_type` VALUES ('3ed58dcb-3c35-11e6-9e29-64006a960834', 'B2');
INSERT INTO `t_defect_item_type` VALUES ('41f5499c-3c35-11e6-9e29-64006a960834', 'C1');
INSERT INTO `t_defect_item_type` VALUES ('43c778c1-3c35-11e6-9e29-64006a960834', 'C2');
INSERT INTO `t_defect_item_type` VALUES ('4524a68a-3c35-11e6-9e29-64006a960834', 'C3');
INSERT INTO `t_defect_item_type` VALUES ('466a57c2-3c35-11e6-9e29-64006a960834', 'C4');
INSERT INTO `t_defect_item_type` VALUES ('47c04137-3c35-11e6-9e29-64006a960834', 'C5');
INSERT INTO `t_defect_item_type` VALUES ('ce64aa61-3c34-11e6-9e29-64006a960834', '3434');

-- ----------------------------
-- Table structure for t_defect_list
-- ----------------------------
DROP TABLE IF EXISTS `t_defect_list`;
CREATE TABLE `t_defect_list` (
  `id` varchar(36) NOT NULL,
  `daily_defect_id` varchar(36) NOT NULL,
  `defect_item_id` varchar(36) NOT NULL,
  `defect_differentiate_id` varchar(36) NOT NULL,
  `defect_content` varchar(256) NOT NULL,
  `defect_remarks` varchar(255) DEFAULT NULL,
  `defect_cause` varchar(255) DEFAULT NULL,
  `defect_strategy` varchar(255) DEFAULT NULL,
  `u_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`daily_defect_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_defect_list
-- ----------------------------

-- ----------------------------
-- Table structure for t_login_info
-- ----------------------------
DROP TABLE IF EXISTS `t_login_info`;
CREATE TABLE `t_login_info` (
  `device_token` varchar(255) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY (`device_token`),
  KEY `IND_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_login_info
-- ----------------------------
INSERT INTO `t_login_info` VALUES ('00000000-54b3-e7c7-0000-000046bffd96', '38e62c72-39eb-11e6-9e29-64006a960834', '2016-06-27 15:14:41');

-- ----------------------------
-- Table structure for t_machine_type
-- ----------------------------
DROP TABLE IF EXISTS `t_machine_type`;
CREATE TABLE `t_machine_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_machine_type
-- ----------------------------

-- ----------------------------
-- Table structure for t_position_type
-- ----------------------------
DROP TABLE IF EXISTS `t_position_type`;
CREATE TABLE `t_position_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_position_type
-- ----------------------------
INSERT INTO `t_position_type` VALUES ('03e884b6-39eb-11e6-9e29-64006a960834', '班长');
INSERT INTO `t_position_type` VALUES ('aec37c7c-39ea-11e6-9e29-64006a960834', '普通职员');

-- ----------------------------
-- Table structure for t_process_lines_type
-- ----------------------------
DROP TABLE IF EXISTS `t_process_lines_type`;
CREATE TABLE `t_process_lines_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_process_lines_type
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `position_id` varchar(36) DEFAULT NULL,
  `superior_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('38e62c72-39eb-11e6-9e29-64006a960834', 'user1', '202cb962ac59075b964b07152d234b70', 'aec37c7c-39ea-11e6-9e29-64006a960834', 'b1657342-33a5-11e6-9e29-64006a960836');
INSERT INTO `t_user` VALUES ('b1657342-33a5-11e6-9e29-64006a960836', 'admin', '202cb962ac59075b964b07152d234b70', '03e884b6-39eb-11e6-9e29-64006a960834', '');
INSERT INTO `t_user` VALUES ('fc88d7d1-3c09-11e6-9e29-64006a960834', 'user2', '202cb962ac59075b964b07152d234b70', '03e884b6-39eb-11e6-9e29-64006a960834', 'b1657342-33a5-11e6-9e29-64006a960836');

-- ----------------------------
-- Table structure for t_workbay_type
-- ----------------------------
DROP TABLE IF EXISTS `t_workbay_type`;
CREATE TABLE `t_workbay_type` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_workbay_type
-- ----------------------------
