/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.109
Source Server Version : 50638
Source Host           : 192.168.2.109:3306
Source Database       : loverent_warehouse

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-04 14:11:01
*/
use loverent_warehouse;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for materiel_basic_images
-- ----------------------------
DROP TABLE IF EXISTS `materiel_basic_images`;
CREATE TABLE "materiel_basic_images" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  "materielBasicInfoId" bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '物料主键ID',
  "attaUrl" varchar(150) NOT NULL DEFAULT '' COMMENT '附件地址',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料基本信息附件表';

-- ----------------------------
-- Table structure for materiel_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `materiel_basic_info`;
CREATE TABLE "materiel_basic_info" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  "materielName" varchar(200) NOT NULL DEFAULT '' COMMENT '物料名称',
  "materielCode" varchar(30) DEFAULT '' COMMENT '物料编码',
  "materielClassId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '物料分类ID',
  "materielBrandId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '物料品牌ID',
  "materielModelId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '物料型号ID',
  "specBatchNo" varchar(200) NOT NULL DEFAULT '' COMMENT '规格批次号（用于标记每个型号的多个规格）',
  "materielUnitId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '物料单位ID',
  "materielBarCode" varchar(30) DEFAULT NULL COMMENT '物料条形码,保留使用',
  "materielCostPrice" decimal(10,2) unsigned DEFAULT '0.00' COMMENT '物料成本价',
  "createBy" bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
  "createOn" datetime NOT NULL COMMENT '创建时间',
  "updateBy" bigint(20) unsigned DEFAULT '0' COMMENT '更新人ID',
  "updateOn" datetime DEFAULT NULL,
  "remark" varchar(255) DEFAULT NULL COMMENT '备注',
  "enableFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '启用/停用标志 0:禁用 1:启用',
  "thumbnailUrl" varchar(150) DEFAULT NULL COMMENT '缩略图路径',
  "materielFlag" tinyint(1) unsigned DEFAULT '0' COMMENT '主物料标志 0：否 1：是',
  "materielIntroduction" longtext COMMENT '物料介绍',
  "version" bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料基本信息表';

-- ----------------------------
-- Table structure for materiel_brand
-- ----------------------------
DROP TABLE IF EXISTS `materiel_brand`;
CREATE TABLE "materiel_brand" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "brandName" varchar(30) NOT NULL DEFAULT '' COMMENT '品牌名称',
  "brandCode" varchar(30) NOT NULL COMMENT '品牌编码',
  "remark" varchar(255) DEFAULT '' COMMENT '描述',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_materiel_brand_brandName" ("brandName"),
  UNIQUE KEY "uk_materiel_brand_brandCode" ("brandCode")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料品牌表';

-- ----------------------------
-- Table structure for materiel_class
-- ----------------------------
DROP TABLE IF EXISTS `materiel_class`;
CREATE TABLE "materiel_class" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "typeName" varchar(30) NOT NULL DEFAULT '' COMMENT '类别名称 ',
  "typeCode" varchar(30) DEFAULT '' COMMENT '物料分类编码',
  "typeLevel" tinyint(4) unsigned NOT NULL COMMENT '分类层级',
  "sortOrder" tinyint(4) unsigned DEFAULT '0' COMMENT '排列顺序',
  "parentId" smallint(6) DEFAULT NULL COMMENT '父分类ID',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_materiel_class_typeName" ("typeName","parentId"),
  UNIQUE KEY "uk_materiel_class_typeCode" ("typeCode")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料分类表';

-- ----------------------------
-- Table structure for materiel_class_brand
-- ----------------------------
DROP TABLE IF EXISTS `materiel_class_brand`;
CREATE TABLE "materiel_class_brand" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "materielClassId" smallint(6) NOT NULL COMMENT '分类id',
  "materielBrandId" smallint(6) NOT NULL COMMENT '品牌id',
  PRIMARY KEY ("id"),
  UNIQUE KEY "Index 2" ("materielBrandId","materielClassId")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料分类品牌映射表';

-- ----------------------------
-- Table structure for materiel_model
-- ----------------------------
DROP TABLE IF EXISTS `materiel_model`;
CREATE TABLE "materiel_model" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "materielModelName" varchar(50) NOT NULL COMMENT '物料型号名称',
  "materielModelDesc" varchar(500) DEFAULT '' COMMENT '物料型号描述',
  "materielClassId" smallint(6) unsigned NOT NULL COMMENT '物料分类ID',
  "materielBrandId" smallint(6) NOT NULL COMMENT '品牌id',
  "enableFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '启用/信用标志 0:禁用 1:启用',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料型号表';

-- ----------------------------
-- Table structure for materiel_model_spec
-- ----------------------------
DROP TABLE IF EXISTS `materiel_model_spec`;
CREATE TABLE "materiel_model_spec" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "materielModelId" smallint(6) NOT NULL COMMENT '物料型号id',
  "materielSpecId" smallint(6) NOT NULL COMMENT '物料规格id',
  "materielSpecValue" varchar(100) NOT NULL COMMENT '物料规格值',
  "specBatchNo" varchar(200) NOT NULL COMMENT '规格批次号（用于标记每个型号的多个规格）',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料型号规格映射表';


-- ----------------------------
-- Table structure for materiel_spec
-- ----------------------------
DROP TABLE IF EXISTS `materiel_spec`;
CREATE TABLE "materiel_spec" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  "specName" varchar(30) NOT NULL DEFAULT '' COMMENT '规格名称',
  "remark" varchar(50) DEFAULT '' COMMENT '备注',
  "enableFlag" tinyint(1) unsigned DEFAULT '0' COMMENT '启用标志 0:否 1:是',
  "sortOrder" smallint(6) NOT NULL DEFAULT '0' COMMENT '排序字段',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_materiel_spec" ("specName") USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料规格表';

-- ----------------------------
-- Table structure for materiel_unit
-- ----------------------------
DROP TABLE IF EXISTS `materiel_unit`;
CREATE TABLE "materiel_unit" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "unitName" varchar(30) NOT NULL DEFAULT '' COMMENT '单位名称 ',
  "unitCode" varchar(30) NOT NULL DEFAULT '' COMMENT '单位编码',
  "enableFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '启用标志 0:否 1:是',
  "remark" varchar(50) DEFAULT '' COMMENT '备注',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料单位表';

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE "product_info" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "productNo" varchar(11) NOT NULL DEFAULT '' COMMENT '产品编号（生成规则：FP+8位自增长）',
  "materielId" bigint(11) NOT NULL COMMENT '物料id',
  "leaseTermId" smallint(6) NOT NULL COMMENT '产品租期id',
  "leaseAmount" decimal(10,2) NOT NULL COMMENT '租金',
  "premium" decimal(10,2) NOT NULL COMMENT '产品一次性保险费',
  "floatAmount" decimal(10,2) NOT NULL COMMENT '溢价',
  "signContractAmount" decimal(10,2) NOT NULL COMMENT '产品签约价值',
  "showAmount" decimal(10,2) NOT NULL COMMENT '产品显示价值',
  "sesameCredit" int(11) NOT NULL COMMENT '芝麻信用',
  "isDeleted" tinyint(4) NOT NULL COMMENT '是否删除（0：否；1：是）',
  "depreciateAmouts" varchar(200) NOT NULL COMMENT '租期折旧费（多个折旧费按，分隔）',
  "createBy" bigint(11) NOT NULL COMMENT '创建人id',
  "createOn" datetime NOT NULL COMMENT '创建时间',
  "updateBy" bigint(11) NOT NULL COMMENT '更新人id',
  "updateOn" datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "productNo" ("productNo")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品信息表';

-- ----------------------------
-- Table structure for product_lease_term
-- ----------------------------
DROP TABLE IF EXISTS `product_lease_term`;
CREATE TABLE "product_lease_term" (
  "id" smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "termValue" smallint(6) NOT NULL DEFAULT '0' COMMENT '租期值（单位：月）',
  "termDesc" varchar(50) NOT NULL DEFAULT '0' COMMENT '租期描述',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品租期表';

-- ----------------------------
-- Table structure for purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE "purchase_order" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "purchaseType" tinyint(1) unsigned DEFAULT '1' COMMENT '采购类型 1：标准采购',
  "purchaseNo" varchar(30) NOT NULL DEFAULT '' COMMENT '采购申请单号',
  "warehouseInfoId" smallint(6) unsigned NOT NULL COMMENT '采购仓库ID',
  "supplierId" smallint(6) unsigned NOT NULL COMMENT '供应商ID，关联supplier_basic_inifo主键',
  "expectedReceiveDate" date DEFAULT NULL COMMENT '预计接收日期',
  "realReceiveDate" datetime DEFAULT NULL COMMENT '实际接收时间',
  "approvedDateTime" datetime DEFAULT NULL COMMENT '审批时间',
  "approvedId" bigint(20) unsigned DEFAULT '0' COMMENT '审批人ID',
  "approvedName" varchar(20) DEFAULT NULL COMMENT '审批人名称',
  "statusFlag" tinyint(2) unsigned NOT NULL DEFAULT '10' COMMENT '采购申请单标志 10:草稿，20：已提交  30：已通过 40:已拒绝 50：已收货 90:已删除/已丢弃',
  "sumAmount" decimal(10,2) DEFAULT NULL COMMENT '采购物料合计金额',
  "createBy" bigint(20) unsigned NOT NULL COMMENT '创建人ID',
  "applyName" varchar(20) DEFAULT '' COMMENT '冗余存储:申请人名称',
  "createOn" datetime NOT NULL COMMENT '创建时间',
  "updateBy" bigint(20) unsigned NOT NULL COMMENT '更新人ID',
  "updateOn" datetime NOT NULL COMMENT '更新时间',
  "remark" varchar(255) DEFAULT NULL COMMENT '备注',
  "version" bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '并发版本控制号',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uk_purchase_apply_order_purchaseNo" ("purchaseNo"),
  KEY "fk_supplier_basic_info_id" ("supplierId"),
  CONSTRAINT "fk_supplier_basic_info_id" FOREIGN KEY ("supplierId") REFERENCES "supplier_basic_info" ("id")
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COMMENT='物料采购申请表';

-- ----------------------------
-- Table structure for purchase_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order_detail`;
CREATE TABLE "purchase_order_detail" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  "purchaseApplyOrderId" bigint(20) unsigned NOT NULL,
  "materielBasicId" bigint(20) unsigned NOT NULL COMMENT '物料基础信息ID',
  "sortOrder" bigint(20) unsigned NOT NULL COMMENT '排列顺序',
  "suggestQuantity" smallint(6) unsigned DEFAULT '0' COMMENT '建议数量',
  "applyQuantity" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '申请数量',
  "approvedQuantity" smallint(6) unsigned DEFAULT '0' COMMENT '审批数量',
  "realRecvQuantity" smallint(6) unsigned DEFAULT '0' COMMENT '实收数量',
  "diffQuantity" smallint(6) DEFAULT '0' COMMENT '差异数量=收货数量 -批准数量',
  "unitPrice" decimal(8,2) unsigned NOT NULL COMMENT '单价',
  PRIMARY KEY ("id"),
  KEY "fk_purchase_apply_order_id" ("purchaseApplyOrderId"),
  KEY "fk_materiel_basic_info_id" ("materielBasicId"),
  CONSTRAINT "fk_materiel_basic_info_id" FOREIGN KEY ("materielBasicId") REFERENCES "materiel_basic_info" ("id"),
  CONSTRAINT "fk_purchase_apply_order_id" FOREIGN KEY ("purchaseApplyOrderId") REFERENCES "purchase_order" ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料采购明细表';

-- ----------------------------
-- Table structure for purchase_order_recv
-- ----------------------------
DROP TABLE IF EXISTS `purchase_order_recv`;
CREATE TABLE "purchase_order_recv" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  "purchaseApplyOrderDetailId" bigint(20) unsigned NOT NULL COMMENT '采购订单申请明细ID',
  "recvBatchNo" varchar(50) DEFAULT NULL COMMENT '用于跟踪数据的接收批次号，系统自动生成',
  "batchNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品批次号',
  "snNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品SN',
  "imieNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品IMIE',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料采购收货表';

-- ----------------------------
-- Table structure for supplier_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `supplier_basic_info`;
CREATE TABLE "supplier_basic_info" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  "supplierName" varchar(50) NOT NULL COMMENT '供应商名称',
  "supplierCode" varchar(30) NOT NULL COMMENT '供应商编码',
  "supplierAddress" varchar(100) DEFAULT '' COMMENT '供应商地址',
  "contact" varchar(20) DEFAULT '' COMMENT '联系人',
  "contactTel" varchar(20) DEFAULT '' COMMENT '联系电话',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商基础信息表';

-- ----------------------------
-- Table structure for warehouse_adjust_basic
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_adjust_basic`;
CREATE TABLE "warehouse_adjust_basic" (
  "id" bigint(20) NOT NULL,
  "applicantSeq" varchar(30) DEFAULT NULL COMMENT '申请单号',
  "applicantId" bigint(20) unsigned NOT NULL COMMENT '申请人ID',
  "applicantName" varchar(20) NOT NULL COMMENT '申请人姓名',
  "applicantDateTime" datetime NOT NULL COMMENT '申请时间',
  "approverId" bigint(20) unsigned DEFAULT NULL COMMENT '审批人ID',
  "approverName" varchar(20) DEFAULT NULL COMMENT '审批人姓名',
  "statusFlag" tinyint(2) unsigned NOT NULL COMMENT '状态标志 10：草稿 20:已提交 30：已审批 40：已拒绝 50：已废弃 90：已删除',
  "remark" varchar(200) DEFAULT NULL COMMENT '备注',
  "version" bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '并发版本控制号',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调整申请基表';

-- ----------------------------
-- Table structure for warehouse_adjust_list
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_adjust_list`;
CREATE TABLE "warehouse_adjust_list" (
  "id" bigint(20) NOT NULL,
  "adjustBasicId" bigint(20) unsigned NOT NULL COMMENT '关联warehouse_adjust_basic表主键',
  "materielBasicId" bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '物料基础信息ID',
  "sourceWarehouseId" smallint(6) unsigned DEFAULT '0' COMMENT '源仓库ID',
  "sourceWarehouseLocationId" smallint(6) unsigned DEFAULT '0' COMMENT '源库位ID',
  "sortOrder" bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '排列顺序',
  "destWarehouseId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '目标仓库ID',
  "destWarehouseLocationId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '目标库位ID',
  "expectedQuantity" smallint(6) DEFAULT NULL,
  "scanQuantity" smallint(6) unsigned NOT NULL COMMENT '扫描数量',
  "adjustReason" varchar(200) DEFAULT NULL COMMENT '调整原因',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调整清单表';

-- ----------------------------
-- Table structure for warehouse_adjust_list_detail
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_adjust_list_detail`;
CREATE TABLE "warehouse_adjust_list_detail" (
  "id" bigint(20) NOT NULL,
  "adjustListId" bigint(20) unsigned NOT NULL COMMENT '关联warehouse_adjust_list表主键',
  "batchNo" varchar(50) NOT NULL COMMENT '商品批次号',
  "snNo" varchar(50) NOT NULL COMMENT '商品SN',
  "imieNo" varchar(50) NOT NULL COMMENT '商品IMIE号',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调整清单明细表';

-- ----------------------------
-- Table structure for warehouse_change_record
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_change_record`;
CREATE TABLE "warehouse_change_record" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "warehouseId" smallint(6) NOT NULL,
  "warehouseLocationId" smallint(6) NOT NULL,
  "materielBasicId" bigint(20) unsigned NOT NULL COMMENT '物料基础信息ID',
  "storageBatchNo" varchar(50) NOT NULL COMMENT '入库批次号(warehouse_commodity_info.storageBatchNo)',
  "adjustBatchNo" varchar(50) NOT NULL COMMENT '调整批次号',
  "adjustType" tinyint(2) unsigned NOT NULL COMMENT '调整类型 1:采购 2:销售 3：人工',
  "sourceOrderNo" varchar(30) NOT NULL COMMENT '源单号:其值为：采购单号或销售单号',
  "changeQuantity" smallint(6) NOT NULL COMMENT '变更数量,可为正数或负数',
  "adjustReason" varchar(100) DEFAULT NULL COMMENT '调整原因',
  "operatorId" bigint(20) unsigned NOT NULL COMMENT '创建人/操作人/调整人ID',
  "operatorName" varchar(20) NOT NULL COMMENT '创建人/操作人/调整人名称',
  "operateOn" datetime NOT NULL COMMENT '创建时间/调整时间/操作时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存变化表';

-- ----------------------------
-- Table structure for warehouse_commodity_info
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_commodity_info`;
CREATE TABLE "warehouse_commodity_info" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "warehouseId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '商品所在仓库ID',
  "warehouseLocationId" smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '商品所在库位ID',
  "materielBasicId" bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '物料基础信息ID',
  "storageBatchNo" varchar(50) NOT NULL DEFAULT '' COMMENT '入库批次号',
  "batchNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品批次号',
  "snNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品SN',
  "imieNo" varchar(50) NOT NULL DEFAULT '' COMMENT '商品IMIE号',
  "operatorId" bigint(20) NOT NULL,
  "operatorName" varchar(20) NOT NULL COMMENT '操作人名称',
  "operateOn" datetime NOT NULL COMMENT '创建时间/操作时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存商品映射表';

-- ----------------------------
-- Table structure for warehouse_commodity_track
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_commodity_track`;
CREATE TABLE "warehouse_commodity_track" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "warehouseId" smallint(6) NOT NULL,
  "warehouseLocationId" smallint(6) NOT NULL,
  "materielBasicId" bigint(20) unsigned NOT NULL COMMENT '物料基础信息ID',
  "storageBatchNo" varchar(50) NOT NULL COMMENT '入库批次号(warehouse_commodity_info.storageBatchNo)',
  "adjustBatchNo" varchar(50) NOT NULL COMMENT '调整批次号',
  "adjustType" tinyint(2) unsigned NOT NULL COMMENT '调整类型 1:采购 2:销售 3：人工',
  "sourceOrderNo" varchar(30) NOT NULL COMMENT '源单号:其值为：采购单号或销售单号',
  "batchNo" varchar(50) NOT NULL COMMENT '商品批次号',
  "snNo" varchar(50) DEFAULT NULL COMMENT '商品SN号',
  "imieNo" varchar(50) DEFAULT NULL COMMENT 'IMIE号',
  "adjustReason" varchar(100) DEFAULT NULL COMMENT '调整原因',
  "operatorId" bigint(20) unsigned NOT NULL COMMENT '创建人/操作人/调整人ID',
  "operatorName" varchar(20) NOT NULL COMMENT '创建人/操作人/调整人名称',
  "operateOn" datetime NOT NULL COMMENT '创建时间/调整时间/操作时间',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存商品跟踪表';

-- ----------------------------
-- Table structure for warehouse_info
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_info`;
CREATE TABLE "warehouse_info" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  "warehouseCode" char(4) NOT NULL COMMENT '仓库编码',
  "warehouseName" varchar(16) NOT NULL COMMENT '仓库名称',
  "parentId" smallint(6) NOT NULL COMMENT '所属仓库',
  "contacts" varchar(16) NOT NULL COMMENT '联系人',
  "phone" varchar(12) NOT NULL COMMENT '联系方式',
  "address" varchar(128) NOT NULL DEFAULT '',
  "remark" varchar(128) NOT NULL DEFAULT '',
  "enableFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用 0 否 1是',
  "delFlag" tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识 0 否 1是',
  "createBy" bigint(20) unsigned NOT NULL COMMENT '创建人',
  "updateBy" bigint(20) unsigned NOT NULL COMMENT '修改人',
  "createOn" datetime NOT NULL COMMENT '创建时间',
  "updateOn" datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_warehouseCode" ("warehouseCode"),
  KEY "index_warehouseName" ("warehouseName")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库基本表';

-- ----------------------------
-- Table structure for warehouse_location
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_location`;
CREATE TABLE "warehouse_location" (
  "id" smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  "locationCode" varchar(20) NOT NULL COMMENT '库位编码',
  "locationName" varchar(20) NOT NULL COMMENT '库位名称',
  "remark" varchar(128) NOT NULL DEFAULT '' COMMENT '备注',
  "delFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识 0 否 1 是',
  "enableFlag" tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否启用 0 否 1 是',
  "createBy" bigint(20) NOT NULL COMMENT '创建人',
  "updateBy" bigint(20) NOT NULL COMMENT '修改人',
  "createOn" datetime NOT NULL COMMENT '创建时间',
  "updateOn" datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY ("id"),
  UNIQUE KEY "index_locationCode" ("locationCode") USING BTREE COMMENT '仓位编码索引',
  KEY "index_locationName" ("locationName") COMMENT '仓位名称索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓位表';

-- ----------------------------
-- Table structure for warehouse_location_relation
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_location_relation`;
CREATE TABLE "warehouse_location_relation" (
  "warehouseId" smallint(6) NOT NULL,
  "locationId" smallint(6) NOT NULL,
  PRIMARY KEY ("locationId","warehouseId"),
  UNIQUE KEY "uk_warehouseId_location" ("warehouseId","locationId"),
  KEY "index_warehouseId" ("warehouseId"),
  KEY "index_locationId" ("locationId")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库仓位映射表';

-- ----------------------------
-- Table structure for warehouse_picking_record
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_picking_record`;
CREATE TABLE "warehouse_picking_record" (
  "id" bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  "sourceOrderNo" varchar(30) NOT NULL DEFAULT '' COMMENT '销售单号',
  "applyDateTime" datetime NOT NULL COMMENT '申请日期',
  "materielBasicId" bigint(20) unsigned NOT NULL COMMENT '物料ID',
  "materielName" varchar(30) NOT NULL COMMENT '物料名称',
  "materielCode" varchar(30) NOT NULL,
  "materielSpecValue" varchar(30) NOT NULL COMMENT '物料规格值',
  "materielUnitName" varchar(10) NOT NULL COMMENT '物料单位名称',
  "pickQuantity" smallint(6) unsigned NOT NULL DEFAULT '1',
  "batchNo" varchar(50) NOT NULL COMMENT '商品批次号',
  "snNo" varchar(50) NOT NULL COMMENT '商品sn号',
  "imieNo" varchar(50) NOT NULL COMMENT 'IMIE号',
  "logisticsNo" varchar(50) DEFAULT NULL COMMENT '物流单号',
  "fillReceiptId" bigint(20) DEFAULT NULL COMMENT '填单人ID',
  "fillReceiptName" varchar(20) DEFAULT NULL COMMENT '填单人姓名',
  "fillReceiptOn" datetime DEFAULT NULL COMMENT '填单时间',
  "operatorId" bigint(20) NOT NULL,
  "operatorName" varchar(20) NOT NULL COMMENT '操作人名称',
  "operateOn" datetime NOT NULL COMMENT '操作时间',
  "statusFlag" tinyint(4) unsigned NOT NULL COMMENT '20：已拣货 30:已出库',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='拣货记录';
