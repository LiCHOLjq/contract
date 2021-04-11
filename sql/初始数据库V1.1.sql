/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : contract

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 12/04/2021 00:55:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `admin_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `admin_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `admin_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `admin_role` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户角色 外键-dictionary',
  `admin_useful` tinyint(1) NULL DEFAULT NULL COMMENT '用户可用性',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', '有点脑残的管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin_role_master', 1);

-- ----------------------------
-- Table structure for agreement
-- ----------------------------
DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement`  (
  `agreement_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `agreement_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同名称',
  `agreement_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同类型',
  `agreement_innovation` tinyint(1) NULL DEFAULT NULL COMMENT '是否信创',
  `agreement_client` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `agreement_amount` double NULL DEFAULT NULL COMMENT '总金额',
  `agreement_sign_date` datetime(0) NULL DEFAULT NULL COMMENT '签约日期',
  `agreement_provider` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提供者',
  `agreement_text` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `agreement_upload_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传类型',
  `agreement_upload_admin` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `agreement_upload_date` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  `agreement_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `agreement_extend` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件扩展名',
  PRIMARY KEY (`agreement_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `cart_admin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属用户',
  `cart_agreement` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属协议',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `dictionary_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `dictionary_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项代码',
  `dictionary_sort` int(0) NULL DEFAULT NULL COMMENT '字典项排序',
  `dictionary_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项名称',
  `dictionary_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型 外键-dictionary_type. type_id',
  `dictionary_father` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典父节点 外键-dictionary.dictionary_id ',
  PRIMARY KEY (`dictionary_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('admin_role_master', '1', 1, '超级管理员', 'ADMIN_ROLE', NULL);
INSERT INTO `dictionary` VALUES ('admin_role_normal', '2', 2, '普通管理员', 'ADMIN_ROLE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_1', '1', 1, '框架', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_2', '2', 2, '中标通知书', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_3', '3', 3, '订单', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_4', '4', 4, '验收证明', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_5', '5', 5, '其他', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_upload_type_admin', '1', 1, '管理员上传', 'AGREEMENT_UPLOAD_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_upload_type_user', '2', 2, '用户上传', 'AGREEMENT_UPLOAD_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_agreement_add', '8', 8, '添加合同', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_agreement_delete', '10', 10, '删除合同', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_agreement_update', '9', 9, '修改合同', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_login', '1', 1, '管理员登录', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_access', '2', 2, '进入分享页面', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_add', '5', 5, '添加分享链接', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_delete', '7', 7, '删除分享链接', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_download', '4', 4, '分享页面下载文件', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_update', '6', 6, '更新分享链接', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('log_type_share_upload', '3', 3, '分享页面上传文件', 'LOG_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_1', '1', 1, '台式机', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_2', '2', 2, '笔记本', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_3', '3', 3, '平板', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_4', '4', 4, '打印机', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_5', '5', 5, '工作站', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_6', '6', 6, '显示器', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_7', '7', 7, '其他', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('share_type_download', '2', 2, '下载文件', 'SHARE_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('share_type_upload', '1', 1, '上传文件', 'SHARE_TYPE', NULL);

-- ----------------------------
-- Table structure for dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_type`;
CREATE TABLE `dictionary_type`  (
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型名称',
  `type_edit` tinyint(1) NULL DEFAULT NULL COMMENT '能否编辑',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dictionary_type
-- ----------------------------
INSERT INTO `dictionary_type` VALUES ('ADMIN_ROLE', '用户角色', 0);
INSERT INTO `dictionary_type` VALUES ('AGREEMENT_TYPE', '合同类型', 1);
INSERT INTO `dictionary_type` VALUES ('AGREEMENT_UPLOAD_TYPE', '合同上传类型', 0);
INSERT INTO `dictionary_type` VALUES ('LOG_TYPE', '日志类型', 0);
INSERT INTO `dictionary_type` VALUES ('PRODUCT_TYPE', '产品类型', 1);
INSERT INTO `dictionary_type` VALUES ('SHARE_TYPE', '分享类型', 0);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录id',
  `log_date` datetime(0) NULL DEFAULT NULL COMMENT '记录时间',
  `log_admin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者用户id',
  `log_admin_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者姓名',
  `log_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录类型LOG_TYPE 外键-dictionary.dictionary_id',
  `log_remote_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者ip',
  `log_message1` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录内容1',
  `log_message2` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录内容2',
  `log_message3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录内容3',
  `log_user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者UserAgent',
  `log_x_requested_width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者X-Requested-With',
  `log_long_message` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '记录长内容',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for power
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power`  (
  `power_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `power_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `power_router` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限路由',
  `power_level` int(0) NULL DEFAULT NULL COMMENT '级别',
  `power_father` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父权限',
  `power_icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`power_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES ('p_01', '系统管理', '/home', 1, NULL, 'el-icon-setting');
INSERT INTO `power` VALUES ('p_01_01', '用户管理', '/home/admin', 2, 'p_01', 'el-icon-user');
INSERT INTO `power` VALUES ('p_01_02', '字典管理', '/home/dictionary', 2, 'p_01', 'el-icon-notebook-2');
INSERT INTO `power` VALUES ('p_01_03', '日志管理', '/home/log', 2, 'p_01', 'el-icon-date');
INSERT INTO `power` VALUES ('p_02', '合同管理', '/home', 1, NULL, 'el-icon-document-copy');
INSERT INTO `power` VALUES ('p_02_01', '合同管理', '/home/agreement/list', 2, 'p_02', 'el-icon-document');
INSERT INTO `power` VALUES ('p_02_02', '分享管理', '/home/share', 2, 'p_02', 'el-icon-share');

-- ----------------------------
-- Table structure for power_role
-- ----------------------------
DROP TABLE IF EXISTS `power_role`;
CREATE TABLE `power_role`  (
  `power_role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `power_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色 外键-role.role_id',
  PRIMARY KEY (`power_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power_role
-- ----------------------------
INSERT INTO `power_role` VALUES ('01', 'p_01', 'admin_role_master');
INSERT INTO `power_role` VALUES ('02', 'p_01_01', 'admin_role_master');
INSERT INTO `power_role` VALUES ('03', 'p_01_02', 'admin_role_master');
INSERT INTO `power_role` VALUES ('04', 'p_01_03', 'admin_role_master');
INSERT INTO `power_role` VALUES ('05', 'p_02', 'admin_role_master');
INSERT INTO `power_role` VALUES ('06', 'p_02_01', 'admin_role_master');
INSERT INTO `power_role` VALUES ('07', 'p_02_02', 'admin_role_master');
INSERT INTO `power_role` VALUES ('08', 'p_02', 'admin_role_normal');
INSERT INTO `power_role` VALUES ('09', 'p_02_01', 'admin_role_normal');
INSERT INTO `power_role` VALUES ('10', 'p_02_02', 'admin_role_normal');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `product_agreement` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属合同',
  `product_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品类型',
  `product_model` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品型号',
  `product_number` double NULL DEFAULT NULL COMMENT '产品数量',
  `product_sort` int(0) NULL DEFAULT NULL COMMENT '产品排序',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '合同产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `share_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `share_admin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享人',
  `share_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享类型',
  `share_begin_date` datetime(0) NULL DEFAULT NULL COMMENT '分享开始时间',
  `share_end_date` datetime(0) NULL DEFAULT NULL COMMENT '分享结束时间',
  `share_has_password` tinyint(1) NULL DEFAULT NULL COMMENT '是否密码保护',
  `share_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `share_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`share_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for share_agreement
-- ----------------------------
DROP TABLE IF EXISTS `share_agreement`;
CREATE TABLE `share_agreement`  (
  `share_agreement_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `share_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属分享',
  `agreement_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享开始时间',
  PRIMARY KEY (`share_agreement_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享协议表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
