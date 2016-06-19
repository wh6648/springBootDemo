/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : rijiadb

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2016-06-19 15:11:38
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
INSERT INTO `t_login_info` VALUES ('00000000-54b3-e7c7-0000-000046bffd96', 'b1557342-33a3-11e6-9e29-64006a960834', '2016-06-19 15:10:44');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('69fe4b69-35bc-11e6-b93e-0a002700000c', 'user1', '202cb962ac59075b964b07152d234b70');
INSERT INTO `t_user` VALUES ('b1557342-33a3-11e6-9e29-64006a960834', 'abc', '202cb962ac59075b964b07152d234b70');
