/*
Navicat MySQL Data Transfer

Source Server         : MySql_localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : rijiadb

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-06-24 17:39:58
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
  `machine_type` varchar(32) NOT NULL,
  `batch_number` bigint(20) NOT NULL,
  `line_type` varchar(8) NOT NULL,
  `point` bigint(20) NOT NULL,
  `batch_count` bigint(20) NOT NULL,
  `work_station` varchar(32) NOT NULL,
  `monitor_user_id` varchar(36) DEFAULT NULL,
  `attendance_type_id` varchar(36) DEFAULT NULL,
  `fault_list_id` varchar(36) NOT NULL,
  `u_time` datetime NOT NULL,
  `u_user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`work_date`,`machine_type`,`batch_number`,`line_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_daily_paper
-- ----------------------------

-- ----------------------------
-- Table structure for t_fault_code
-- ----------------------------
DROP TABLE IF EXISTS `t_fault_code`;
CREATE TABLE `t_fault_code` (
  `id` varchar(36) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fault_code
-- ----------------------------

-- ----------------------------
-- Table structure for t_fault_list
-- ----------------------------
DROP TABLE IF EXISTS `t_fault_list`;
CREATE TABLE `t_fault_list` (
  `id` varchar(36) NOT NULL,
  `daily_fault_id` varchar(36) NOT NULL,
  `fault_code_id` varchar(36) NOT NULL,
  `fault_content` varchar(256) NOT NULL,
  `u_time` datetime NOT NULL,
  PRIMARY KEY (`id`,`daily_fault_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_fault_list
-- ----------------------------

-- ----------------------------
-- Table structure for t_login_info
-- ----------------------------
DROP TABLE IF EXISTS `t_login_info`;
CREATE TABLE `t_login_info` (
  `device_token` varchar(255) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY (`device_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_login_info
-- ----------------------------
INSERT INTO `t_login_info` VALUES ('00000000-54b3-e7c7-0000-000046bffd96', '38e62c72-39eb-11e6-9e29-64006a960834', '2016-06-24 17:08:28');

-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS `t_position`;
CREATE TABLE `t_position` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_position
-- ----------------------------
INSERT INTO `t_position` VALUES ('03e884b6-39eb-11e6-9e29-64006a960834', '班长');
INSERT INTO `t_position` VALUES ('aec37c7c-39ea-11e6-9e29-64006a960834', '普通职员');

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
