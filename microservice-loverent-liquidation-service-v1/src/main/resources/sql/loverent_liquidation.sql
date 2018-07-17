/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.109
Source Server Version : 50638
Source Host           : 192.168.2.109:3306
Source Database       : loverent_liquidation

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-11 09:55:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE "account_info" (
  "id" smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "accountCode" varchar(16) NOT NULL COMMENT '科目编码',
  "accountDesc" varchar(32) NOT NULL COMMENT '科目描述',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_accountCode" ("accountCode")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科目表';

-- ----------------------------
-- Table structure for account_record
-- ----------------------------
DROP TABLE IF EXISTS `account_record`;
CREATE TABLE "account_record" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  "accountCode" varchar(16) NOT NULL COMMENT '会计科目编码',
  "fundsSN" varchar(30) NOT NULL COMMENT '资金流水号',
  "orderSN" varchar(24) NOT NULL COMMENT '订单号',
  "transactionSN" varchar(24) NOT NULL COMMENT '交易流水号',
  "amount" decimal(15,2) NOT NULL COMMENT '金额',
  "flowType" varchar(8) NOT NULL COMMENT '资金方向(流入/流出)',
  "remark" varchar(128) NOT NULL DEFAULT '' COMMENT '备注',
  "createBy" bigint(20) unsigned NOT NULL COMMENT '创建人',
  "createOn" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科目流水表';

-- ----------------------------
-- Table structure for dict_info
-- ----------------------------
DROP TABLE IF EXISTS `dict_info`;
CREATE TABLE "dict_info" (
  "id" tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "parentCode" varchar(16) NOT NULL COMMENT '父级编码',
  "dictCode" varchar(16) NOT NULL COMMENT '字典编码',
  "dictDesc" varchar(8) NOT NULL COMMENT '字典描述',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_transactionType" ("dictCode")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Table structure for repayment_satistics
-- ----------------------------
DROP TABLE IF EXISTS `repayment_satistics`;
CREATE TABLE "repayment_satistics" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "userId" bigint(20) unsigned NOT NULL COMMENT '用户id',
  "orderSN" varchar(22) NOT NULL COMMENT '订单号',
  "performanceCount" smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '履约次数',
  "breachCount" smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '违约次数',
  "createOn" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "updateOn" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_unique_orderSN" ("orderSN")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='还款统计表';

-- ----------------------------
-- Table structure for repayment_schedule
-- ----------------------------
DROP TABLE IF EXISTS `repayment_schedule`;
CREATE TABLE "repayment_schedule" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "orderSN" varchar(24) NOT NULL COMMENT '订单号',
  "periodOf" tinyint(3) unsigned NOT NULL COMMENT '第几期账期',
  "periods" tinyint(3) unsigned NOT NULL COMMENT '还款期数',
  "paymentDueDate" date NOT NULL COMMENT '还款日期',
  "due" decimal(8,2) NOT NULL COMMENT '应还款',
  "actualRepayment" decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '实际还款',
  "currentBalance" decimal(8,2) NOT NULL DEFAULT '0.00' COMMENT '本期未还款',
  "settleFlag" tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否 结清  0否 1是',
  "settleWay" varchar(16) NOT NULL DEFAULT '' COMMENT '结清方式',
  "settleDate" datetime DEFAULT NULL COMMENT '本期结清时间',
  "overdueDay" smallint(6) NOT NULL DEFAULT '0' COMMENT '本期逾期天数',
  "state" tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '违约状况 0 未违约 1 违约',
  "realName" varchar(8) NOT NULL DEFAULT '',
  "phone" varchar(12) NOT NULL DEFAULT '' COMMENT '手机号',
  "createBy" bigint(20) NOT NULL COMMENT '创建人',
  "createOn" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  "updateBy" bigint(20) NOT NULL COMMENT '更新人',
  "updateOn" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  "enableFlag" tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否有效 0 否 1是',
  "repaymentType" tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '还款类型 1 租金 2滞纳金 3 折旧违约金',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for system_common_config
-- ----------------------------
DROP TABLE IF EXISTS `system_common_config`;
CREATE TABLE "system_common_config" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '系统配置ID',
  "configName" varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置名称',
  "configCode" varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置code',
  "configGroupCode" varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置群组code',
  "configGroupName" varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置群组名称',
  "configContent" varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置内容',
  "type" int(2) DEFAULT NULL COMMENT '类型 0:开关 1:图片 2:文本',
  "status" int(11) DEFAULT NULL COMMENT '状态 0:禁用 1:正常 2:删除',
  "createON" datetime DEFAULT NULL COMMENT '创建时间',
  "updateON" datetime DEFAULT NULL COMMENT '更新时间',
  "sort" int(11) DEFAULT NULL COMMENT '排序',
  "remark" varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY ("id"),
  UNIQUE KEY "u_index_config_code" ("configCode") USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE "transaction_record" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  "transactionSN" varchar(24) NOT NULL COMMENT '交易流水号',
  "orderSN" varchar(24) NOT NULL COMMENT '单据编号',
  "sourceType" varchar(12) NOT NULL COMMENT '来源类别 (销售单、销售退回、采购单、采购退回)',
  "transactionWay" varchar(16) NOT NULL COMMENT '交易方式 支付宝/微信/银行卡代扣 等支付方式',
  "transactionType" varchar(16) NOT NULL COMMENT '交易类型 首期款交易/租金/回收/买断/未收货违约',
  "transactionAmount" decimal(8,2) NOT NULL COMMENT '交易金额',
  "fromAccount" varchar(16) NOT NULL DEFAULT '' COMMENT '交易来源账户',
  "toAccount" varchar(16) NOT NULL DEFAULT '' COMMENT '交易目标账号',
  "transactionSource" varchar(12) NOT NULL COMMENT '交易入口（来源:APP、H5、后台系统）',
  "state" tinyint(3) unsigned NOT NULL COMMENT '交易状态  0待支付 1交易中 2 成功 3 失败 4 作废',
  "resultCode" varchar(16) NOT NULL DEFAULT '' COMMENT '结果编码',
  "failureDesc" varchar(16) NOT NULL DEFAULT '' COMMENT '失败原因',
  "remark" varchar(128) NOT NULL DEFAULT '' COMMENT '备注',
  "version" varchar(22) NOT NULL COMMENT '版本',
  "realName" varchar(8) NOT NULL DEFAULT '' COMMENT '姓名',
  "phone" varchar(12) NOT NULL DEFAULT '' COMMENT '手机号',
  "createBy" bigint(20) unsigned NOT NULL COMMENT '交易发起人',
  "createUsername" varchar(16) NOT NULL DEFAULT '' COMMENT '用户账号',
  "updateUsername" varchar(16) NOT NULL DEFAULT '' COMMENT '更新人用户账号',
  "createOn" datetime NOT NULL COMMENT '交易发起时间',
  "updateOn" datetime NOT NULL COMMENT '交易完成时间',
  "attr1" varchar(32) NOT NULL DEFAULT '' COMMENT '预留字段1',
  "attr2" varchar(32) NOT NULL DEFAULT '' COMMENT '预留字段1',
  "attr3" varchar(32) NOT NULL DEFAULT '' COMMENT '预留字段1',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_transactionSN" ("transactionSN") COMMENT '交易流水唯一索引',
  KEY "index_orderSN" ("orderSN") COMMENT '订单号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易流水表';
