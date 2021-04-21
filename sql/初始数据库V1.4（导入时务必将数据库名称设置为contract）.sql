/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : contract2

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/04/2021 01:08:12
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
INSERT INTO `admin` VALUES ('177a4c670d2c456f9c676db15e93199a', '彭艳', 'pengyan', 'e10adc3949ba59abbe56e057f20f883e', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('2e1149cba4954319921a3587eb9a7e02', '侯慧聪', 'houhc', '492eda3f31ddbecf8910caa1c2ab2731', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('5dcc246a91994ce5825d25d6ea59d157', '张征', 'zhangzheng', '51dc4aae84781233602db9ca3e20c5c6', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('5ff83c6ca85647948980e244b065629a', '殷庆远', 'yinqy', 'e10adc3949ba59abbe56e057f20f883e', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('87e50b60c1954aa4a1cc01557a040ce3', '周妺', 'zhoumo', '91842173d9eec806b5bfba8b80cf69ac', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('9097218cc658439984d2a40964acbc44', '黄敏', 'huangmin', 'f09932d1f563cd1508f5aa2fe5a55a46', 'admin_role_normal', 1);
INSERT INTO `admin` VALUES ('admin', '管理员-齐志强', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin_role_master', 1);
INSERT INTO `admin` VALUES ('d61f9263466a40068ab6249ac62037e6', '汤浩', 'tanghao', 'e10adc3949ba59abbe56e057f20f883e', 'admin_role_normal', 1);

-- ----------------------------
-- Table structure for agreement
-- ----------------------------
DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement`  (
  `agreement_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `agreement_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同名称',
  `agreement_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合同类型',
  `agreement_innovation` tinyint(1) NULL DEFAULT NULL COMMENT '是否信创',
  `agreement_client` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `agreement_amount` double NULL DEFAULT NULL COMMENT '总金额',
  `agreement_sign_date` datetime(0) NULL DEFAULT NULL COMMENT '签约日期',
  `agreement_provider` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提供者',
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
INSERT INTO `dictionary` VALUES ('agreement_client_1', '1', 1, '联想（北京）有限公司', 'AGREEMENT_CLIENT', NULL);
INSERT INTO `dictionary` VALUES ('agreement_client_2', '2', 2, '联想开天有限公司', 'AGREEMENT_CLIENT', NULL);
INSERT INTO `dictionary` VALUES ('agreement_client_3', '3', 3, '联想长风', 'AGREEMENT_CLIENT', NULL);
INSERT INTO `dictionary` VALUES ('agreement_client_4', '4', 4, '代理商', 'AGREEMENT_CLIENT', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_1', '1', 1, '合同', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_2', '2', 2, '中标通知书', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_3', '3', 3, '订单', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_4', '4', 4, '验收证明', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_5', '5', 5, '框架', 'AGREEMENT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('agreement_type_6', '6', 6, '其他', 'AGREEMENT_TYPE', NULL);
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
INSERT INTO `dictionary` VALUES ('product_series_1', '1', 1, 'ThinkCentre', 'PRODUCT_SERIES', 'product_type_1');
INSERT INTO `dictionary` VALUES ('product_series_10', '10', 10, 'Lenovo', 'PRODUCT_SERIES', 'product_type_3');
INSERT INTO `dictionary` VALUES ('product_series_11', '11', 11, '启天', 'PRODUCT_SERIES', 'product_type_3');
INSERT INTO `dictionary` VALUES ('product_series_12', '12', 12, '打印机预设', 'PRODUCT_SERIES', 'product_type_4');
INSERT INTO `dictionary` VALUES ('product_series_13', '13', 13, '大屏预设', 'PRODUCT_SERIES', 'product_type_7');
INSERT INTO `dictionary` VALUES ('product_series_14', '14', 14, '启天', 'PRODUCT_SERIES', 'product_type_8');
INSERT INTO `dictionary` VALUES ('product_series_15', '15', 15, 'ThinkCentre', 'PRODUCT_SERIES', 'product_type_8');
INSERT INTO `dictionary` VALUES ('product_series_16', '16', 16, 'Idea Centre', 'PRODUCT_SERIES', 'product_type_8');
INSERT INTO `dictionary` VALUES ('product_series_17', '17', 17, 'Yoga', 'PRODUCT_SERIES', 'product_type_8');
INSERT INTO `dictionary` VALUES ('product_series_18', '18', 18, '其他预设', 'PRODUCT_SERIES', 'product_type_9');
INSERT INTO `dictionary` VALUES ('product_series_2', '2', 2, '启天', 'PRODUCT_SERIES', 'product_type_1');
INSERT INTO `dictionary` VALUES ('product_series_3', '3', 3, '开天', 'PRODUCT_SERIES', 'product_type_1');
INSERT INTO `dictionary` VALUES ('product_series_4', '4', 4, '昭阳', 'PRODUCT_SERIES', 'product_type_2');
INSERT INTO `dictionary` VALUES ('product_series_5', '5', 5, 'ThinkPad', 'PRODUCT_SERIES', 'product_type_2');
INSERT INTO `dictionary` VALUES ('product_series_6', '6', 6, 'ThinkBook', 'PRODUCT_SERIES', 'product_type_2');
INSERT INTO `dictionary` VALUES ('product_series_7', '7', 7, 'Lenovo', 'PRODUCT_SERIES', 'product_type_2');
INSERT INTO `dictionary` VALUES ('product_series_8', '8', 8, 'ThinkVision', 'PRODUCT_SERIES', 'product_type_6');
INSERT INTO `dictionary` VALUES ('product_series_9', '9', 9, 'ThinkStation', 'PRODUCT_SERIES', 'product_type_5');
INSERT INTO `dictionary` VALUES ('product_type_1', '1', 1, '台式机', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_2', '2', 2, '笔记本', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_3', '3', 3, '平板', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_4', '4', 4, '打印机', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_5', '5', 5, '工作站', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_6', '6', 6, '显示器', 'PRODUCT_TYPE', NULL);
INSERT INTO `dictionary` VALUES ('product_type_7', '7', 7, '大屏', 'PRODUCT_TYPE', '');
INSERT INTO `dictionary` VALUES ('product_type_8', '8', 8, '一体机', 'PRODUCT_TYPE', '');
INSERT INTO `dictionary` VALUES ('product_type_9', '9', 9, '其他预设保留', 'PRODUCT_TYPE', '');
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
INSERT INTO `dictionary_type` VALUES ('AGREEMENT_CLIENT', '合同乙方', 1);
INSERT INTO `dictionary_type` VALUES ('AGREEMENT_TYPE', '合同类型', 1);
INSERT INTO `dictionary_type` VALUES ('AGREEMENT_UPLOAD_TYPE', '合同上传类型', 0);
INSERT INTO `dictionary_type` VALUES ('LOG_TYPE', '日志类型', 0);
INSERT INTO `dictionary_type` VALUES ('PRODUCT_SERIES', '产品系列', 1);
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
  `log_message2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录内容2',
  `log_message3` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录内容3',
  `log_user_agent` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者UserAgent',
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
INSERT INTO `power` VALUES ('p_02_03', '分享上传管理', '/home/agreement/user/list', 2, 'p_02', 'el-icon-document');

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
INSERT INTO `power_role` VALUES ('11', 'p_02_03', 'admin_role_master');
INSERT INTO `power_role` VALUES ('12', 'p_02_03', 'admin_role_normal');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'UUID',
  `product_agreement` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属合同',
  `product_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品类型',
  `product_series` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品系列',
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
