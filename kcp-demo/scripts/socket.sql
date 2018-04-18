/*
Navicat MySQL Data Transfer

Source Server         : socket
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : socket

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-02-08 16:54:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ammeter_device
-- ----------------------------
DROP TABLE IF EXISTS `ammeter_device`;
CREATE TABLE `ammeter_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键  递增 1',
  `imsi` varchar(50) DEFAULT NULL COMMENT '通讯imsi 唯一标识',
  `address` varchar(20) DEFAULT NULL COMMENT '通讯地址',
  `meter_no` varchar(11) DEFAULT NULL COMMENT '表号',
  `ass_manage_code` varchar(50) DEFAULT NULL,
  `rated_voltage` varchar(10) DEFAULT NULL COMMENT '额定电压',
  `rated_current` varchar(10) DEFAULT NULL,
  `max_current` varchar(10) DEFAULT NULL,
  `active_acc_level` varchar(10) DEFAULT NULL COMMENT '有功准确度等级',
  `reactive_acc_level` varchar(10) DEFAULT NULL COMMENT '无功准确度等级',
  `active_constant` int(5) DEFAULT NULL COMMENT '电表有功常数',
  `reactive_constant` int(5) DEFAULT NULL COMMENT '电表无功常数',
  `type` varchar(10) DEFAULT NULL COMMENT '电表型号',
  `product_date` datetime DEFAULT NULL COMMENT '生产日期',
  `protocol_no` varchar(20) DEFAULT NULL COMMENT '协议版本号',
  `software_no` varchar(50) DEFAULT NULL COMMENT '厂家软件版本号',
  `hardware_no` varchar(50) DEFAULT NULL COMMENT '厂家硬件版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_imsi` (`imsi`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ammeter_device
-- ----------------------------
INSERT INTO `ammeter_device` VALUES ('1', '460001357924680', '127.0.0.1', 'AM-1', 'CODE-1-001', '11', '11', '11', '11', '11', '11', '11', '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', '11', '11');
INSERT INTO `ammeter_device` VALUES ('2', '460001357924681', '127.0.0.1', 'AM-2', 'CODE-2-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('3', '460001357924682', '127.0.0.1', 'AM-3', 'CODE-3-001', '12', '13', '13', '13', '13', '13', '13', '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', '13', '13');
INSERT INTO `ammeter_device` VALUES ('4', '460001357924683', '127.0.0.1', 'AM-4', 'CODE-4-001', '12', '12', '12', '12', null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('5', '460001357924684', '127.0.0.1', 'AM-5', 'CODE-5-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('6', '460018261434510', '127.0.0.1', 'AM-6', 'CODE-6-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('7', '460018261434511', '127.0.0.1', 'AM-7', 'CODE-7-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('8', '460018261434512', '127.0.0.1', 'AM-8', 'CODE-8-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('9', '460018261434513', '127.0.0.1', 'AM-9', 'CODE-9-001', '1222.1', '133', '122', '133', '133', '133', '133', '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', '133', '123.2');
INSERT INTO `ammeter_device` VALUES ('11', '460018261434514', '127.0.0.1', 'AM-11', 'CODE-11-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('12', '460018261434515', '127.0.0.1', 'AM-12', 'CODE-12-001', '11.1', '11.1', '11.1', '11.1', '11.1', '11', '11', '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', '11.1', '11.1');
INSERT INTO `ammeter_device` VALUES ('13', '460018261434516', '127.0.0.1', 'AM-13', 'CODE-13-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('14', '460018261434517', '127.0.0.1', 'AM-14', 'CODE-14-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('15', '460007261510236', '127.0.0.1', 'AM-15', 'CODE-15-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('16', '460007261510230', '127.0.0.1', 'AM-16', 'CODE-16-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('17', '460007261510231', '127.0.0.1', 'AM-17', 'CODE-17-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('18', '460007261510232', '49.73.28.184', 'AM-18', 'CODE-18-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('19', '460007261510237', '49.73.28.184', 'AM-19', 'CODE-19-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('20', '460007261510233', '49.73.28.184', 'AM-20', 'CODE-20-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('21', '460007261510234', '49.73.28.184', 'AM-21', 'CODE-21-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('22', '460007261510235', '127.0.0.1', 'AM-22', 'CODE-22-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('23', '460036261510466', '127.0.0.1', 'AM-23', 'CODE-23-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('24', '460036261510465', '49.72.227.94', 'AM-24', 'CODE-24-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('25', '460036261510464', '49.75.177.65', 'AM-25', 'CODE-25-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('26', '460036261510463', '49.75.177.65', 'AM-26', 'CODE-26-001', null, null, null, null, null, null, null, '智能电表-P01', '2015-09-08 00:00:00', '2017-09-11/SN-001', null, null);
INSERT INTO `ammeter_device` VALUES ('27', '460036261510461', '117.136.68.188', null, null, null, null, null, null, null, null, null, null, '2015-09-08 00:00:00', null, null, null);
INSERT INTO `ammeter_device` VALUES ('28', '460036261510462', '114.217.192.10', null, null, null, null, null, null, null, null, null, null, '2015-09-08 00:00:00', null, null, null);
INSERT INTO `ammeter_device` VALUES ('29', '123456788', '49.85.230.125', null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `ammeter_device` VALUES ('30', '460111174805253', '117.61.9.102', null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for ammeter_monthly_report
-- ----------------------------
DROP TABLE IF EXISTS `ammeter_monthly_report`;
CREATE TABLE `ammeter_monthly_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ammeter_id` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '1-正向  2-反向   3-所有',
  `send_date` datetime DEFAULT NULL,
  `active_energy` varchar(11) DEFAULT NULL COMMENT '（上1次）整点冻结正向有功总电能',
  `reactive_energy` varchar(11) DEFAULT NULL COMMENT '（上1次）整点冻结反向有功总电能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ammeter_monthly_report
-- ----------------------------

-- ----------------------------
-- Table structure for ammeter_network
-- ----------------------------
DROP TABLE IF EXISTS `ammeter_network`;
CREATE TABLE `ammeter_network` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `ammeter_id` int(11) DEFAULT NULL COMMENT '电表Id',
  `rssi` varchar(255) DEFAULT '',
  `rsrq` varchar(255) DEFAULT NULL,
  `celli` varchar(255) DEFAULT NULL,
  `record_hour` int(11) DEFAULT NULL COMMENT '记录的时间点',
  `record_day` varchar(255) DEFAULT NULL COMMENT '年月日',
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ammeter_network
-- ----------------------------
INSERT INTO `ammeter_network` VALUES ('1', '1', '-10', '-70', '-30', '9', '2017-09-24', '2017-09-23 10:54:56');
INSERT INTO `ammeter_network` VALUES ('2', '1', '-20', '-70', '-30', '10', '2017-09-24', '2017-09-23 10:55:27');
INSERT INTO `ammeter_network` VALUES ('3', '1', '-50', '-70', '124', '15', '2017-09-24', '2017-09-23 15:18:36');
INSERT INTO `ammeter_network` VALUES ('4', '2', '10', '-70', '30', '1', '2017-09-25', '2017-09-25 22:00:38');
INSERT INTO `ammeter_network` VALUES ('5', '2', '11', '-70', '31', '2', '2017-09-25', '2017-09-25 22:01:16');
INSERT INTO `ammeter_network` VALUES ('6', '3', '15', '-70', '200', '19', '2017-09-27', '2017-09-27 19:49:46');
INSERT INTO `ammeter_network` VALUES ('7', '3', '20', '-75', '190', '20', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('8', '3', '21', '-78', '190', '21', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('9', '3', '20', '-75', '190', '22', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('10', '3', '20', '-79', '190', '23', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('11', '3', '20', '-85', '190', '24', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('12', '1', '21', '-78', '190', '1', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('13', '1', '20', '-75', '190', '2', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('14', '1', '20', '-79', '190', '3', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('15', '1', '20', '-85', '190', '4', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('16', '1', '21', '-78', '190', '5', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('17', '1', '20', '-75', '190', '6', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('18', '1', '20', '-79', '190', '7', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('19', '1', '20', '-85', '190', '8', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('20', '1', '21', '-78', '190', '9', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('21', '1', '20', '-75', '190', '10', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('22', '1', '20', '-79', '190', '11', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('23', '1', '20', '-85', '190', '12', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('24', '1', '21', '-78', '190', '13', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('25', '1', '20', '-75', '190', '14', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('26', '1', '20', '-79', '190', '15', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('27', '1', '20', '-85', '190', '16', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('28', '1', '21', '-78', '190', '17', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('29', '1', '20', '-75', '190', '18', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('30', '1', '20', '-79', '190', '19', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('31', '1', '20', '-85', '190', '20', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('32', '1', '21', '-78', '190', '21', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('33', '1', '20', '-75', '190', '22', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('34', '1', '20', '-79', '190', '23', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('35', '1', '20', '-85', '190', '24', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('36', '1', '21', '-78', '190', '1', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('37', '1', '20', '-75', '190', '2', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('38', '1', '20', '-79', '190', '3', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('39', '1', '20', '-85', '190', '4', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('40', '1', '21', '-78', '190', '5', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('41', '1', '20', '-75', '190', '6', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('42', '1', '20', '-79', '190', '7', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('43', '1', '20', '-85', '190', '8', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('44', '1', '21', '-78', '190', '9', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('45', '1', '20', '-75', '190', '10', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('46', '1', '20', '-79', '190', '11', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('47', '1', '20', '-85', '190', '12', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('48', '1', '21', '-78', '190', '13', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('49', '1', '20', '-75', '190', '14', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('50', '1', '20', '-79', '190', '15', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('51', '1', '20', '-85', '190', '16', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('52', '1', '21', '-78', '190', '17', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('53', '1', '20', '-75', '190', '18', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('54', '1', '20', '-79', '190', '19', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('55', '1', '20', '-85', '190', '20', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('56', '1', '21', '-78', '190', '21', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('57', '1', '20', '-75', '190', '22', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('58', '1', '20', '-79', '190', '23', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('59', '1', '20', '-85', '190', '24', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('60', '1', '21', '-78', '190', '1', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('61', '1', '20', '-75', '190', '2', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('62', '1', '20', '-79', '190', '3', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('63', '1', '20', '-85', '190', '4', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('64', '1', '21', '-78', '190', '5', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('65', '1', '20', '-75', '190', '6', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('66', '1', '20', '-79', '190', '7', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('67', '1', '20', '-85', '190', '8', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('68', '1', '21', '-78', '190', '9', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('69', '1', '20', '-78', '190', '10', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('70', '1', '20', '-78', '190', '11', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('71', '1', '20', '-78', '190', '12', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('72', '1', '21', '-78', '190', '13', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('73', '1', '20', '-75', '190', '14', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('74', '1', '20', '-79', '190', '15', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('75', '1', '20', '-85', '190', '16', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('76', '1', '21', '-78', '190', '17', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('77', '1', '20', '-78', '190', '18', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('78', '1', '20', '-79', '190', '19', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('79', '1', '20', '-85', '190', '20', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('80', '1', '21', '-78', '190', '21', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('81', '1', '20', '-77', '190', '22', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('82', '1', '20', '-79', '190', '23', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('83', '1', '20', '-80', '190', '24', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('84', '1', '21', '-78', '190', '1', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('85', '1', '20', '-75', '190', '2', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('86', '1', '20', '-79', '190', '3', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('87', '1', '20', '-85', '190', '4', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('88', '1', '21', '-78', '190', '5', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('89', '1', '20', '-75', '190', '6', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('90', '1', '20', '-79', '190', '7', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('91', '1', '20', '-85', '190', '8', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('92', '1', '21', '-78', '190', '9', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('93', '1', '20', '-78', '190', '10', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('94', '1', '20', '-78', '190', '11', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('95', '1', '20', '-78', '190', '12', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('96', '1', '21', '-78', '190', '13', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('97', '1', '20', '-75', '190', '14', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('98', '1', '20', '-79', '190', '15', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('99', '1', '20', '-85', '190', '16', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('100', '1', '21', '-78', '190', '17', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('101', '1', '20', '-78', '190', '18', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('102', '1', '20', '-79', '190', '19', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('103', '1', '20', '-85', '190', '20', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('104', '1', '21', '-78', '190', '21', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('105', '1', '20', '-77', '190', '22', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('106', '1', '20', '-79', '190', '23', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('107', '1', '20', '-80', '190', '24', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('108', '16', '21', '-78', '190', '1', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('109', '16', '20', '-75', '190', '2', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('110', '16', '20', '-79', '190', '3', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('111', '16', '20', '-85', '190', '4', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('112', '16', '21', '-78', '190', '5', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('113', '16', '20', '-75', '190', '6', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('114', '16', '20', '-79', '190', '7', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('115', '16', '20', '-85', '190', '8', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('116', '16', '21', '-78', '190', '9', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('117', '16', '20', '-78', '190', '10', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('118', '16', '20', '-78', '190', '11', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('119', '16', '20', '-78', '190', '12', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('120', '16', '21', '-78', '190', '13', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('121', '16', '20', '-75', '190', '14', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('122', '16', '20', '-79', '190', '15', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('123', '16', '20', '-85', '190', '16', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('124', '16', '21', '-78', '190', '17', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('125', '16', '20', '-78', '190', '18', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('126', '16', '20', '-79', '190', '19', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('127', '16', '20', '-85', '190', '20', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('128', '16', '21', '-78', '190', '21', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('129', '16', '20', '-77', '190', '22', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('130', '16', '20', '-79', '190', '23', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('131', '16', '20', '-80', '190', '24', '2017-09-27', '2017-09-27 19:51:15');
INSERT INTO `ammeter_network` VALUES ('132', '16', '21', '-78', '190', '1', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('133', '16', '20', '-75', '190', '2', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('134', '16', '20', '-79', '190', '3', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('135', '16', '20', '-85', '190', '4', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('136', '16', '21', '-78', '190', '5', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('137', '16', '20', '-75', '190', '6', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('138', '16', '20', '-79', '190', '7', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('139', '16', '20', '-85', '190', '8', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('140', '16', '21', '-78', '190', '9', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('141', '16', '20', '-78', '190', '10', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('142', '16', '20', '-78', '190', '11', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('143', '16', '20', '-78', '190', '12', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('144', '16', '21', '-78', '190', '13', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('145', '16', '20', '-75', '190', '14', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('146', '16', '20', '-79', '190', '15', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('147', '16', '20', '-85', '190', '16', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('148', '16', '21', '-78', '190', '17', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('149', '16', '20', '-78', '190', '18', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('150', '16', '20', '-79', '190', '19', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('151', '16', '20', '-85', '190', '20', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('152', '16', '21', '-78', '190', '21', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('153', '16', '20', '-77', '190', '22', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('154', '16', '20', '-79', '190', '23', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('155', '16', '20', '-80', '190', '24', '2017-09-28', '2017-09-28 19:51:15');
INSERT INTO `ammeter_network` VALUES ('156', '16', '21', '-78', '190', '1', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('157', '16', '20', '-75', '190', '2', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('158', '16', '20', '-79', '190', '3', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('159', '16', '20', '-85', '190', '4', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('160', '16', '21', '-78', '190', '5', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('161', '16', '20', '-75', '190', '6', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('162', '16', '20', '-79', '190', '7', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('163', '16', '20', '-85', '190', '8', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('164', '16', '21', '-78', '190', '9', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('165', '16', '20', '-78', '190', '10', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('166', '16', '20', '-78', '190', '11', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('167', '16', '20', '-78', '190', '12', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('168', '16', '21', '-78', '190', '13', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('169', '16', '20', '-75', '190', '14', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('170', '16', '20', '-79', '190', '15', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('171', '16', '20', '-85', '190', '16', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('172', '16', '21', '-78', '190', '17', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('173', '16', '20', '-78', '190', '18', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('174', '16', '20', '-79', '190', '19', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('175', '16', '20', '-85', '190', '20', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('176', '16', '21', '-78', '190', '21', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('177', '16', '20', '-77', '190', '22', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('178', '16', '20', '-79', '190', '23', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('179', '16', '20', '-80', '190', '24', '2017-09-29', '2017-09-29 19:51:15');
INSERT INTO `ammeter_network` VALUES ('180', '16', '21', '-78', '190', '1', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('181', '16', '20', '-75', '190', '2', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('182', '16', '20', '-79', '190', '3', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('183', '16', '20', '-85', '190', '4', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('184', '16', '21', '-78', '190', '5', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('185', '16', '20', '-75', '190', '6', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('186', '16', '20', '-79', '190', '7', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('187', '16', '20', '-85', '190', '8', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('188', '16', '21', '-78', '190', '9', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('189', '16', '20', '-78', '190', '10', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('190', '16', '20', '-78', '190', '11', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('191', '16', '20', '-78', '190', '12', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('192', '16', '21', '-78', '190', '13', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('193', '16', '20', '-75', '190', '14', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('194', '16', '20', '-79', '190', '15', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('195', '16', '20', '-85', '190', '16', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('196', '16', '21', '-78', '190', '17', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('197', '16', '20', '-78', '190', '18', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('198', '16', '20', '-79', '190', '19', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('199', '16', '20', '-85', '190', '20', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('200', '16', '21', '-78', '190', '21', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('201', '16', '20', '-77', '190', '22', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('202', '16', '20', '-79', '190', '23', '2017-09-30', '2017-09-30 19:51:15');
INSERT INTO `ammeter_network` VALUES ('203', '16', '20', '-80', '190', '24', '2017-09-30', '2017-09-30 19:51:15');

-- ----------------------------
-- Table structure for ammeter_report
-- ----------------------------
DROP TABLE IF EXISTS `ammeter_report`;
CREATE TABLE `ammeter_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ammeter_id` int(11) NOT NULL,
  `active_energy` varchar(20) DEFAULT NULL COMMENT '（上1次）整点冻结正向有功总电能',
  `reactive_energy` varchar(20) DEFAULT NULL COMMENT '（上1次）整点冻结反向有功总电能',
  `send_time` datetime DEFAULT NULL COMMENT '用电时间',
  `hour` int(11) DEFAULT NULL COMMENT '用电时间 小时',
  `type` int(2) DEFAULT NULL COMMENT '1-正向  2-反向   3-所有',
  `date_time` varchar(11) DEFAULT NULL COMMENT '用电时间  年月日',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=380 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ammeter_report
-- ----------------------------
INSERT INTO `ammeter_report` VALUES ('1', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('2', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('3', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('4', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('5', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('6', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('7', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('8', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('9', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('10', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('11', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('12', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('13', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('14', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('15', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('16', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('17', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('18', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('19', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('20', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('21', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('22', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('23', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('24', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('25', '2', '1.11', '112', '2017-09-16 14:41:05', '8', '2', '2017-09-16');
INSERT INTO `ammeter_report` VALUES ('26', '9', '1.2022', null, '2017-09-17 14:19:51', '13', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('31', '9', '1.44', null, '2017-09-17 15:12:24', '14', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('32', '12', '0.011000000000000001', null, '2017-09-17 15:27:23', '14', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('33', '13', '1.3322999999999998', null, '2017-09-17 15:48:09', '14', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('34', '14', '1.2023000000000001', null, '2017-09-17 16:21:52', '15', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('35', '15', '1.3322', null, '2017-09-17 16:27:16', '15', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('36', '13', '1.3322', null, '2017-09-17 16:30:25', '15', null, '2017-09-17');
INSERT INTO `ammeter_report` VALUES ('37', '12', '1.0110999999999999', null, '2017-09-19 00:28:44', '-1', null, '2017-09-19');
INSERT INTO `ammeter_report` VALUES ('40', '13', '1.101', null, '2017-09-19 01:03:22', '1', null, '2017-09-19');
INSERT INTO `ammeter_report` VALUES ('41', '22', '1.2311', null, '2017-09-19 22:55:12', '22', null, '2017-09-19');
INSERT INTO `ammeter_report` VALUES ('42', '25', '1.202', null, '2017-09-23 15:01:52', '15', null, '2017-09-23');
INSERT INTO `ammeter_report` VALUES ('43', '26', '1.101', null, '2017-09-23 15:18:04', '15', null, '2017-09-23');
INSERT INTO `ammeter_report` VALUES ('44', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('45', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('46', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('47', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('48', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('49', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('50', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('51', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('52', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('53', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('54', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('55', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('56', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('57', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('58', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('59', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('60', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('61', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('62', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('63', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('64', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('65', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('66', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('67', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-24');
INSERT INTO `ammeter_report` VALUES ('68', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('69', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('70', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('71', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('72', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('73', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('74', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('75', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('76', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('77', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('78', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('79', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('80', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('81', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('82', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('83', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('84', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('85', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('86', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('87', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('88', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('89', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('90', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('91', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-26');
INSERT INTO `ammeter_report` VALUES ('92', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('93', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('94', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('95', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('96', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('97', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('98', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('99', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('100', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('101', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('102', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('103', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('104', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('105', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('106', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('107', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('108', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('109', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('110', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('111', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('112', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('113', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('114', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('115', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('116', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('117', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('118', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('119', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('120', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('121', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('122', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('123', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('124', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('125', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('126', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('127', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('128', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('129', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('130', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('131', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('132', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('133', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('134', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('135', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('136', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('137', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('138', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('139', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('140', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('141', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('142', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('143', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('144', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('145', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('146', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('147', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('148', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('149', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('150', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('151', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('152', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('153', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('154', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('155', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('156', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('157', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('158', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('159', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('160', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('161', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('162', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('163', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('164', '1', '10.002', '200.3', '2017-09-16 09:04:57', '1', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('165', '1', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('166', '1', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('167', '1', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('168', '1', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('169', '1', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('170', '1', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('171', '1', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('172', '1', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('173', '1', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('174', '1', '11.1', '250', '2017-09-16 14:37:48', '11', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('175', '1', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('176', '1', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('177', '1', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('178', '1', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('179', '1', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('180', '1', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('181', '1', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('182', '1', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('183', '1', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('184', '1', '10.9', '228', '2017-09-16 14:36:44', '21', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('185', '1', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('186', '1', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('187', '1', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('188', '2', '10.002', '200.3', '2017-09-16 09:04:57', '2', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('189', '2', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('190', '2', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('191', '2', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('192', '2', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('193', '2', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('194', '2', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('195', '2', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('196', '2', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('197', '2', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('198', '2', '11.1', '250', '2017-09-16 14:37:48', '12', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('199', '2', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('200', '2', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('201', '2', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('202', '2', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('203', '2', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('204', '2', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('205', '2', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('206', '2', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('207', '2', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('208', '2', '10.9', '228', '2017-09-16 14:36:44', '22', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('209', '2', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('210', '2', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('211', '2', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('212', '2', '10.002', '200.3', '2017-09-16 09:04:57', '2', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('213', '2', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('214', '2', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('215', '2', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('216', '2', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('217', '2', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('218', '2', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('219', '2', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('220', '2', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('221', '2', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('222', '2', '11.1', '250', '2017-09-16 14:37:48', '12', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('223', '2', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('224', '2', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('225', '2', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('226', '2', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('227', '2', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('228', '2', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('229', '2', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('230', '2', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('231', '2', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('232', '2', '10.9', '228', '2017-09-16 14:36:44', '22', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('233', '2', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('234', '2', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('235', '2', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('236', '2', '10.002', '200.3', '2017-09-16 09:04:57', '2', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('237', '2', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('238', '2', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('239', '2', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('240', '2', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('241', '2', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('242', '2', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('243', '2', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('244', '2', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('245', '2', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('246', '2', '11.1', '250', '2017-09-16 14:37:48', '12', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('247', '2', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('248', '2', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('249', '2', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('250', '2', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('251', '2', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('252', '2', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('253', '2', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('254', '2', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('255', '2', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('256', '2', '10.9', '228', '2017-09-16 14:36:44', '22', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('257', '2', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('258', '2', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('259', '2', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('260', '2', '10.002', '200.3', '2017-09-16 09:04:57', '2', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('261', '2', '10.023', '220.4', '2017-09-16 15:34:11', '2', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('262', '2', '10.03', '222', '2017-09-16 14:36:04', '3', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('263', '2', '10.05', '225', '2017-09-16 14:36:25', '4', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('264', '2', '10.08', '228', '2017-09-16 14:36:44', '5', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('265', '2', '10.09', '230', '2017-09-16 14:37:27', '6', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('266', '2', '10.1', '250', '2017-09-16 14:37:48', '7', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('267', '2', '10.8', '280', '2017-09-16 14:38:10', '8', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('268', '2', '10.9', '228', '2017-09-16 14:36:44', '9', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('269', '2', '11', '230', '2017-09-16 14:37:27', '10', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('270', '2', '11.1', '250', '2017-09-16 14:37:48', '12', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('271', '2', '11.2', '280', '2017-09-16 14:38:10', '12', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('272', '2', '10.002', '200.3', '2017-09-16 09:04:57', '13', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('273', '2', '10.023', '220.4', '2017-09-16 15:34:11', '14', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('274', '2', '10.03', '222', '2017-09-16 14:36:04', '15', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('275', '2', '10.05', '225', '2017-09-16 14:36:25', '16', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('276', '2', '10.08', '228', '2017-09-16 14:36:44', '17', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('277', '2', '10.09', '230', '2017-09-16 14:37:27', '18', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('278', '2', '10.1', '250', '2017-09-16 14:37:48', '19', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('279', '2', '10.8', '280', '2017-09-16 14:38:10', '20', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('280', '2', '10.9', '228', '2017-09-16 14:36:44', '22', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('281', '2', '11', '230', '2017-09-16 14:37:27', '22', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('282', '2', '11.1', '250', '2017-09-16 14:37:48', '23', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('283', '2', '11.2', '280', '2017-09-16 14:38:10', '24', '2', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('284', '3', '10.002', '200.3', '2017-09-16 09:04:57', '3', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('285', '3', '10.023', '220.4', '2017-09-16 15:34:11', '3', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('286', '3', '10.03', '222', '2017-09-16 14:36:04', '3', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('287', '3', '10.05', '225', '2017-09-16 14:36:25', '4', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('288', '3', '10.08', '228', '2017-09-16 14:36:44', '5', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('289', '3', '10.09', '230', '2017-09-16 14:37:27', '6', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('290', '3', '10.1', '250', '2017-09-16 14:37:48', '7', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('291', '3', '10.8', '280', '2017-09-16 14:38:10', '8', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('292', '3', '10.9', '228', '2017-09-16 14:36:44', '9', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('293', '3', '11', '230', '2017-09-16 14:37:27', '10', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('294', '3', '11.1', '250', '2017-09-16 14:37:48', '13', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('295', '3', '11.2', '280', '2017-09-16 14:38:10', '13', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('296', '3', '10.002', '200.3', '2017-09-16 09:04:57', '13', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('297', '3', '10.023', '220.4', '2017-09-16 15:34:11', '14', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('298', '3', '10.03', '222', '2017-09-16 14:36:04', '15', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('299', '3', '10.05', '225', '2017-09-16 14:36:25', '16', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('300', '3', '10.08', '228', '2017-09-16 14:36:44', '17', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('301', '3', '10.09', '230', '2017-09-16 14:37:27', '18', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('302', '3', '10.1', '250', '2017-09-16 14:37:48', '19', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('303', '3', '10.8', '280', '2017-09-16 14:38:10', '20', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('304', '3', '10.9', '228', '2017-09-16 14:36:44', '23', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('305', '3', '11', '230', '2017-09-16 14:37:27', '23', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('306', '3', '11.1', '250', '2017-09-16 14:37:48', '23', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('307', '3', '11.2', '280', '2017-09-16 14:38:10', '24', '3', '2017-09-27');
INSERT INTO `ammeter_report` VALUES ('308', '3', '10.002', '200.3', '2017-09-16 09:04:57', '3', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('309', '3', '10.023', '220.4', '2017-09-16 15:34:11', '3', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('310', '3', '10.03', '222', '2017-09-16 14:36:04', '3', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('311', '3', '10.05', '225', '2017-09-16 14:36:25', '4', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('312', '3', '10.08', '228', '2017-09-16 14:36:44', '5', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('313', '3', '10.09', '230', '2017-09-16 14:37:27', '6', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('314', '3', '10.1', '250', '2017-09-16 14:37:48', '7', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('315', '3', '10.8', '280', '2017-09-16 14:38:10', '8', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('316', '3', '10.9', '228', '2017-09-16 14:36:44', '9', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('317', '3', '11', '230', '2017-09-16 14:37:27', '10', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('318', '3', '11.1', '250', '2017-09-16 14:37:48', '13', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('319', '3', '11.2', '280', '2017-09-16 14:38:10', '13', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('320', '3', '10.002', '200.3', '2017-09-16 09:04:57', '13', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('321', '3', '10.023', '220.4', '2017-09-16 15:34:11', '14', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('322', '3', '10.03', '222', '2017-09-16 14:36:04', '15', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('323', '3', '10.05', '225', '2017-09-16 14:36:25', '16', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('324', '3', '10.08', '228', '2017-09-16 14:36:44', '17', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('325', '3', '10.09', '230', '2017-09-16 14:37:27', '18', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('326', '3', '10.1', '250', '2017-09-16 14:37:48', '19', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('327', '3', '10.8', '280', '2017-09-16 14:38:10', '20', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('328', '3', '10.9', '228', '2017-09-16 14:36:44', '23', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('329', '3', '11', '230', '2017-09-16 14:37:27', '23', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('330', '3', '11.1', '250', '2017-09-16 14:37:48', '23', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('331', '3', '11.2', '280', '2017-09-16 14:38:10', '24', '3', '2017-09-28');
INSERT INTO `ammeter_report` VALUES ('332', '3', '10.002', '200.3', '2017-09-16 09:04:57', '3', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('333', '3', '10.023', '220.4', '2017-09-16 15:34:11', '3', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('334', '3', '10.03', '222', '2017-09-16 14:36:04', '3', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('335', '3', '10.05', '225', '2017-09-16 14:36:25', '4', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('336', '3', '10.08', '228', '2017-09-16 14:36:44', '5', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('337', '3', '10.09', '230', '2017-09-16 14:37:27', '6', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('338', '3', '10.1', '250', '2017-09-16 14:37:48', '7', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('339', '3', '10.8', '280', '2017-09-16 14:38:10', '8', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('340', '3', '10.9', '228', '2017-09-16 14:36:44', '9', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('341', '3', '11', '230', '2017-09-16 14:37:27', '10', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('342', '3', '11.1', '250', '2017-09-16 14:37:48', '13', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('343', '3', '11.2', '280', '2017-09-16 14:38:10', '13', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('344', '3', '10.002', '200.3', '2017-09-16 09:04:57', '13', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('345', '3', '10.023', '220.4', '2017-09-16 15:34:11', '14', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('346', '3', '10.03', '222', '2017-09-16 14:36:04', '15', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('347', '3', '10.05', '225', '2017-09-16 14:36:25', '16', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('348', '3', '10.08', '228', '2017-09-16 14:36:44', '17', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('349', '3', '10.09', '230', '2017-09-16 14:37:27', '18', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('350', '3', '10.1', '250', '2017-09-16 14:37:48', '19', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('351', '3', '10.8', '280', '2017-09-16 14:38:10', '20', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('352', '3', '10.9', '228', '2017-09-16 14:36:44', '23', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('353', '3', '11', '230', '2017-09-16 14:37:27', '23', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('354', '3', '11.1', '250', '2017-09-16 14:37:48', '23', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('355', '3', '11.2', '280', '2017-09-16 14:38:10', '24', '3', '2017-09-29');
INSERT INTO `ammeter_report` VALUES ('356', '3', '10.002', '200.3', '2017-09-16 09:04:57', '3', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('357', '3', '10.023', '220.4', '2017-09-16 15:34:11', '3', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('358', '3', '10.03', '222', '2017-09-16 14:36:04', '3', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('359', '3', '10.05', '225', '2017-09-16 14:36:25', '4', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('360', '3', '10.08', '228', '2017-09-16 14:36:44', '5', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('361', '3', '10.09', '230', '2017-09-16 14:37:27', '6', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('362', '3', '10.1', '250', '2017-09-16 14:37:48', '7', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('363', '3', '10.8', '280', '2017-09-16 14:38:10', '8', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('364', '3', '10.9', '228', '2017-09-16 14:36:44', '9', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('365', '3', '11', '230', '2017-09-16 14:37:27', '10', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('366', '3', '11.1', '250', '2017-09-16 14:37:48', '13', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('367', '3', '11.2', '280', '2017-09-16 14:38:10', '13', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('368', '3', '10.002', '200.3', '2017-09-16 09:04:57', '13', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('369', '3', '10.023', '220.4', '2017-09-16 15:34:11', '14', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('370', '3', '10.03', '222', '2017-09-16 14:36:04', '15', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('371', '3', '10.05', '225', '2017-09-16 14:36:25', '16', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('372', '3', '10.08', '228', '2017-09-16 14:36:44', '17', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('373', '3', '10.09', '230', '2017-09-16 14:37:27', '18', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('374', '3', '10.1', '250', '2017-09-16 14:37:48', '19', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('375', '3', '10.8', '280', '2017-09-16 14:38:10', '20', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('376', '3', '10.9', '228', '2017-09-16 14:36:44', '23', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('377', '3', '11', '230', '2017-09-16 14:37:27', '23', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('378', '3', '11.1', '250', '2017-09-16 14:37:48', '23', '3', '2017-09-30');
INSERT INTO `ammeter_report` VALUES ('379', '3', '11.2', '280', '2017-09-16 14:38:10', '24', '3', '2017-09-30');

-- ----------------------------
-- Table structure for ammeter_working_info
-- ----------------------------
DROP TABLE IF EXISTS `ammeter_working_info`;
CREATE TABLE `ammeter_working_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ammeter_id` int(11) NOT NULL,
  `zero_line_current` varchar(8) DEFAULT NULL,
  `grid_frequency` varchar(8) DEFAULT NULL COMMENT '电网频率',
  `average_power_minute` varchar(8) DEFAULT NULL COMMENT 'average power',
  `active_demand` varchar(8) DEFAULT NULL COMMENT '当前有功需量',
  `reactive_demand` varchar(8) DEFAULT NULL COMMENT '当前无功需量',
  `apparent_demand` varchar(8) DEFAULT NULL COMMENT '当前视在需量',
  `temperature` varchar(5) DEFAULT NULL COMMENT '表内温度',
  `clock_cell_voltage` varchar(5) DEFAULT NULL COMMENT '时钟电池电压',
  `no_power_cell_voltage` varchar(5) DEFAULT NULL COMMENT '停电抄表电池电压',
  `battery_working_time` varchar(11) DEFAULT NULL,
  `active_power` varchar(8) DEFAULT NULL COMMENT '(当前)正向有功总电能',
  `reverse_active_power` varchar(8) DEFAULT NULL COMMENT '(当前)反向有功总电能',
  `status` int(2) DEFAULT '1' COMMENT '合闸  1 ，拉闸 2 ，合闸进行中 3，拉闸进行中  4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ammeter_working_info
-- ----------------------------
INSERT INTO `ammeter_working_info` VALUES ('1', '1', '11', '11', '11', '11', '12', '12', '12', '12', '12', '12', '12', '12', '1');
INSERT INTO `ammeter_working_info` VALUES ('2', '3', '1233.22', null, '123', null, null, null, null, null, null, null, null, null, '4');
INSERT INTO `ammeter_working_info` VALUES ('4', '2', '120.3', null, null, null, null, null, null, null, null, null, null, null, '2');
INSERT INTO `ammeter_working_info` VALUES ('5', '4', '120.223', null, null, null, null, null, null, null, null, null, null, null, '3');
INSERT INTO `ammeter_working_info` VALUES ('6', '9', '144', '133', '133', '133', '133', '133', '133', '133', '133', '133', '144', '133', '4');
INSERT INTO `ammeter_working_info` VALUES ('7', '11', '133', null, null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO `ammeter_working_info` VALUES ('8', '12', '11.1', '+11.1', '11.1', '11.1', '11.1', '11.1', '11.1', '11.1', '11.1', '11.1', '101.11', '11.1', '4');
INSERT INTO `ammeter_working_info` VALUES ('9', '13', null, null, null, null, null, null, null, null, null, null, '110.1', null, '4');
INSERT INTO `ammeter_working_info` VALUES ('10', '14', null, null, null, null, null, null, null, null, null, null, '120.23', null, '4');
INSERT INTO `ammeter_working_info` VALUES ('11', '15', null, null, null, null, null, null, null, null, null, null, '133.22', null, null);
INSERT INTO `ammeter_working_info` VALUES ('12', '22', null, null, null, null, null, null, null, null, null, null, '123.11', null, '4');
INSERT INTO `ammeter_working_info` VALUES ('13', '25', null, null, null, null, null, null, null, null, null, null, '120.2', null, null);
INSERT INTO `ammeter_working_info` VALUES ('14', '26', null, null, null, null, null, null, null, null, null, null, '110.1', null, null);
INSERT INTO `ammeter_working_info` VALUES ('15', '30', null, null, null, null, null, null, null, null, null, null, null, null, '4');
INSERT INTO `ammeter_working_info` VALUES ('16', '29', null, null, null, null, null, null, null, null, null, null, null, null, '4');

-- ----------------------------
-- Table structure for bas_city
-- ----------------------------
DROP TABLE IF EXISTS `bas_city`;
CREATE TABLE `bas_city` (
  `id` int(11) NOT NULL,
  `city_code` varchar(10) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(50) DEFAULT NULL COMMENT '城市名称',
  `zip_code` varchar(6) DEFAULT NULL COMMENT '邮编',
  `province_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属省份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市';

-- ----------------------------
-- Records of bas_city
-- ----------------------------
INSERT INTO `bas_city` VALUES ('1', '1', '1', '1', '1');
INSERT INTO `bas_city` VALUES ('2', '1', '2', '2', '2');
INSERT INTO `bas_city` VALUES ('3', '3', '3', '3', '3');

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL COMMENT '参数名',
  `param_1` varchar(5) DEFAULT NULL COMMENT '参数1',
  `param_2` varchar(5) DEFAULT NULL,
  `param_3` varchar(5) DEFAULT NULL,
  `param_4` varchar(5) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '类型 1- 工作参数  2 固有参数',
  `param_id` int(11) NOT NULL,
  `status` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', '零线电流', '02', '80', '00', '01', '1', '1', '1');
INSERT INTO `sys_dictionary` VALUES ('2', '电网频率', '02', '80', '00', '02', '1', '2', '1');
INSERT INTO `sys_dictionary` VALUES ('3', '一分钟有功总平均功率', '02', '80', '00', '03', '1', '3', '1');
INSERT INTO `sys_dictionary` VALUES ('4', '当前有功需量', '02', '80', '00', '04', '1', '4', '1');
INSERT INTO `sys_dictionary` VALUES ('5', '当前无功需量', '02', '80', '00', '05', '1', '5', '1');
INSERT INTO `sys_dictionary` VALUES ('6', '当前视在需量', '02', '80', '00', '06', '1', '6', '1');
INSERT INTO `sys_dictionary` VALUES ('7', '表内温度', '02', '80', '00', '07', '1', '7', '1');
INSERT INTO `sys_dictionary` VALUES ('8', '时钟电池电压(内部)', '02', '80', '00', '08', '1', '8', '1');
INSERT INTO `sys_dictionary` VALUES ('9', '停电抄表电池电压 (外部)', '02', '80', '00', '09', '1', '9', '1');
INSERT INTO `sys_dictionary` VALUES ('10', '内部电池工作时间', '02', '80', '00', '0A', '1', '10', '1');
INSERT INTO `sys_dictionary` VALUES ('11', '(当前)正向有功总电能', '02', '01', '00', '00', '1', '11', '1');
INSERT INTO `sys_dictionary` VALUES ('12', '(当前)反向有功总电能', '02', '01', '00', '01', '1', '12', '1');
INSERT INTO `sys_dictionary` VALUES ('13', '通信地址', '04', '00', '04', '01', '2', '13', '1');
INSERT INTO `sys_dictionary` VALUES ('14', '表号', '04', '00', '04', '02', '2', '14', '1');
INSERT INTO `sys_dictionary` VALUES ('15', '资产管理编码(ASCII码)', '04', '00', '04', '03', '2', '15', '1');
INSERT INTO `sys_dictionary` VALUES ('16', '额定电压(ASCII码)', '04', '00', '04', '04', '2', '16', '1');
INSERT INTO `sys_dictionary` VALUES ('17', '额定电流/基本电流(ASCII码)', '04', '00', '04', '05', '2', '17', '1');
INSERT INTO `sys_dictionary` VALUES ('18', '最大电流(ASCII码)', '04', '00', '04', '06', '2', '18', '1');
INSERT INTO `sys_dictionary` VALUES ('19', '有功准确度等级(ASCII码)', '04', '00', '04', '07', '2', '19', '1');
INSERT INTO `sys_dictionary` VALUES ('20', '无功准确度等级(ASCII码)', '04', '00', '04', '08', '2', '20', '1');
INSERT INTO `sys_dictionary` VALUES ('21', '电表有功常数', '04', '00', '04', '09', '2', '21', '1');
INSERT INTO `sys_dictionary` VALUES ('22', '电表无功常数', '04', '00', '04', '0A', '2', '22', '1');
INSERT INTO `sys_dictionary` VALUES ('23', '电表型号(ASCII码)', '04', '00', '04', '0B', '2', '23', '1');
INSERT INTO `sys_dictionary` VALUES ('24', '生产日期(ASCII码)', '04', '00', '04', '0C', '2', '24', '1');
INSERT INTO `sys_dictionary` VALUES ('25', '协议版本号(ASCII码', '04', '00', '04', '0D', '2', '25', '1');
INSERT INTO `sys_dictionary` VALUES ('26', '厂家软件版本号(ASCII码)', '04', '80', '00', '01', '2', '26', '1');
INSERT INTO `sys_dictionary` VALUES ('27', '厂家硬件版本号(ASCII码)', '04', '80', '00', '02', '2', '27', '1');
INSERT INTO `sys_dictionary` VALUES ('28', '厂家编号(ASCII码)(唯一标识)', '04', '80', '00', '03', '2', '28', '1');
INSERT INTO `sys_dictionary` VALUES ('29', '拉闸合闸状态', 'G0', '00', '00', '1A', '3', '29', '0');
INSERT INTO `sys_dictionary` VALUES ('30', '整点冻结正向有功总电能', '05', '04', '01', '01', '4', '30', '1');
INSERT INTO `sys_dictionary` VALUES ('31', '整点冻结反向有功总电能', '05', '04', '02', '01', '4', '31', '1');
INSERT INTO `sys_dictionary` VALUES ('32', '无线数据', '00', '00', '00', '01', '5', '32', '1');
INSERT INTO `sys_dictionary` VALUES ('33', '关闸状态', 'G0', '00', '00', '1B', '3', '33', '0');
