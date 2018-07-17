/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.109
Source Server Version : 50638
Source Host           : 192.168.2.109:3306
Source Database       : loverent_warehouse

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2018-01-04 13:54:53
*/

use loverent_warehouse;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Records of materiel_brand
-- ----------------------------
INSERT INTO `materiel_brand` VALUES ('1', 'apple', 'Apple', 'Apple');
INSERT INTO `materiel_brand` VALUES ('2', 'vivo', 'Vivo', 'Vivo');
INSERT INTO `materiel_brand` VALUES ('3', 'oppo', 'Oppo', 'Oppo');
INSERT INTO `materiel_brand` VALUES ('4', 'meitu', 'Meitu', 'Meitu');

-- ----------------------------
-- Records of materiel_class
-- ----------------------------
INSERT INTO `materiel_class` VALUES ('0', '全部分类', 'C00000', '1', '1', '-1');
INSERT INTO `materiel_class` VALUES ('1', '智能手机', 'C00001', '2', '1', '0');

-- ----------------------------
-- Records of materiel_class_brand
-- ----------------------------
INSERT INTO `materiel_class_brand` VALUES ('1', '1', '1');
INSERT INTO `materiel_class_brand` VALUES ('2', '1', '2');
INSERT INTO `materiel_class_brand` VALUES ('3', '1', '3');
INSERT INTO `materiel_class_brand` VALUES ('4', '1', '4');
-- ----------------------------
-- Records of materiel_model
-- ----------------------------
INSERT INTO `materiel_model` VALUES ('1', 'iPhone 7', 'iPhone 7', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('2', 'iPhone 7 Plus', 'iPhone 7 Plus', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('3', 'iPhone 8', 'iPhone 8', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('4', 'iPhone 8 Plus', 'iPhone 8 Plus', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('5', 'iPhone X', 'iPhone X', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('6', 'iPhone 9', 'iPhone 9', '1', '1', '1');
INSERT INTO `materiel_model` VALUES ('7', 'vivo Xpaly 6', 'vivo Xpaly 6', '1', '2', '1');
INSERT INTO `materiel_model` VALUES ('8', 'vivo X9s', 'vivo X9s', '1', '2', '1');
INSERT INTO `materiel_model` VALUES ('9', 'vivo X20', 'vivo X20', '1', '2', '1');
INSERT INTO `materiel_model` VALUES ('10', 'vivo X20 Plus', 'vivo X20 Plus', '1', '2', '1');
INSERT INTO `materiel_model` VALUES ('11', '美图 T8s', '美图 T8s', '1', '4', '1');
INSERT INTO `materiel_model` VALUES ('12', '美图 M8s', '美图 M8s', '1', '4', '1');
INSERT INTO `materiel_model` VALUES ('13', 'OPPO R11s', 'OPPO R11s', '1', '3', '1');

-- ----------------------------
-- Records of materiel_model_spec
-- ----------------------------
INSERT INTO `materiel_model_spec` VALUES ('1', '5', '2', '全网通', 'S00001');
INSERT INTO `materiel_model_spec` VALUES ('2', '5', '3', '银色', 'S00001');
INSERT INTO `materiel_model_spec` VALUES ('3', '5', '1', '64G', 'S00001');
INSERT INTO `materiel_model_spec` VALUES ('4', '5', '2', '全网通', 'S00004');
INSERT INTO `materiel_model_spec` VALUES ('5', '5', '3', '深空灰色', 'S00004');
INSERT INTO `materiel_model_spec` VALUES ('6', '5', '1', '64G', 'S00004');
INSERT INTO `materiel_model_spec` VALUES ('7', '5', '2', '全网通', 'S00007');
INSERT INTO `materiel_model_spec` VALUES ('8', '5', '3', '银色', 'S00007');
INSERT INTO `materiel_model_spec` VALUES ('9', '5', '1', '256G', 'S00007');
INSERT INTO `materiel_model_spec` VALUES ('10', '5', '2', '全网通', 'S00010');
INSERT INTO `materiel_model_spec` VALUES ('11', '5', '3', '深空灰色', 'S00010');
INSERT INTO `materiel_model_spec` VALUES ('12', '5', '1', '256G', 'S00010');
INSERT INTO `materiel_model_spec` VALUES ('13', '4', '2', '全网通', 'S00013');
INSERT INTO `materiel_model_spec` VALUES ('14', '4', '3', '银色', 'S00013');
INSERT INTO `materiel_model_spec` VALUES ('15', '4', '1', '256G', 'S00013');
INSERT INTO `materiel_model_spec` VALUES ('16', '4', '2', '全网通', 'S00016');
INSERT INTO `materiel_model_spec` VALUES ('17', '4', '3', '深空灰色', 'S00016');
INSERT INTO `materiel_model_spec` VALUES ('18', '4', '1', '256G', 'S00016');
INSERT INTO `materiel_model_spec` VALUES ('19', '4', '2', '全网通', 'S00019');
INSERT INTO `materiel_model_spec` VALUES ('20', '4', '3', '金色', 'S00019');
INSERT INTO `materiel_model_spec` VALUES ('21', '4', '1', '256G', 'S00019');
INSERT INTO `materiel_model_spec` VALUES ('22', '4', '2', '全网通', 'S00022');
INSERT INTO `materiel_model_spec` VALUES ('23', '4', '3', '金色', 'S00022');
INSERT INTO `materiel_model_spec` VALUES ('24', '4', '1', '64G', 'S00022');
INSERT INTO `materiel_model_spec` VALUES ('25', '4', '2', '全网通', 'S00025');
INSERT INTO `materiel_model_spec` VALUES ('26', '4', '3', '银色', 'S00025');
INSERT INTO `materiel_model_spec` VALUES ('27', '4', '1', '64G', 'S00025');
INSERT INTO `materiel_model_spec` VALUES ('28', '4', '2', '全网通', 'S00028');
INSERT INTO `materiel_model_spec` VALUES ('29', '4', '3', '深空灰色', 'S00028');
INSERT INTO `materiel_model_spec` VALUES ('30', '4', '1', '64G', 'S00028');
INSERT INTO `materiel_model_spec` VALUES ('31', '3', '2', '全网通', 'S00031');
INSERT INTO `materiel_model_spec` VALUES ('32', '3', '3', '金色', 'S00031');
INSERT INTO `materiel_model_spec` VALUES ('33', '3', '1', '64G', 'S00031');
INSERT INTO `materiel_model_spec` VALUES ('34', '3', '2', '全网通', 'S00034');
INSERT INTO `materiel_model_spec` VALUES ('35', '3', '3', '银色', 'S00034');
INSERT INTO `materiel_model_spec` VALUES ('36', '3', '1', '64G', 'S00034');
INSERT INTO `materiel_model_spec` VALUES ('37', '3', '2', '全网通', 'S00037');
INSERT INTO `materiel_model_spec` VALUES ('38', '3', '3', '深空灰色', 'S00037');
INSERT INTO `materiel_model_spec` VALUES ('39', '3', '1', '64G', 'S00037');
INSERT INTO `materiel_model_spec` VALUES ('40', '3', '2', '全网通', 'S00040');
INSERT INTO `materiel_model_spec` VALUES ('41', '3', '3', '金色', 'S00040');
INSERT INTO `materiel_model_spec` VALUES ('42', '3', '1', '256G', 'S00040');
INSERT INTO `materiel_model_spec` VALUES ('43', '3', '2', '全网通', 'S00043');
INSERT INTO `materiel_model_spec` VALUES ('44', '3', '3', '银色', 'S00043');
INSERT INTO `materiel_model_spec` VALUES ('45', '3', '1', '256G', 'S00043');
INSERT INTO `materiel_model_spec` VALUES ('46', '3', '2', '全网通', 'S00046');
INSERT INTO `materiel_model_spec` VALUES ('47', '3', '3', '深空灰色', 'S00046');
INSERT INTO `materiel_model_spec` VALUES ('48', '3', '1', '256G', 'S00046');
INSERT INTO `materiel_model_spec` VALUES ('49', '2', '2', '全网通', 'S00049');
INSERT INTO `materiel_model_spec` VALUES ('50', '2', '3', '红色', 'S00049');
INSERT INTO `materiel_model_spec` VALUES ('51', '2', '1', '32G', 'S00049');
INSERT INTO `materiel_model_spec` VALUES ('52', '2', '2', '全网通', 'S00052');
INSERT INTO `materiel_model_spec` VALUES ('53', '2', '3', '玫瑰金', 'S00052');
INSERT INTO `materiel_model_spec` VALUES ('54', '2', '1', '32G', 'S00052');
INSERT INTO `materiel_model_spec` VALUES ('55', '2', '2', '全网通', 'S00055');
INSERT INTO `materiel_model_spec` VALUES ('56', '2', '3', '黑色', 'S00055');
INSERT INTO `materiel_model_spec` VALUES ('57', '2', '1', '32G', 'S00055');
INSERT INTO `materiel_model_spec` VALUES ('58', '2', '2', '全网通', 'S00058');
INSERT INTO `materiel_model_spec` VALUES ('59', '2', '3', '香槟金', 'S00058');
INSERT INTO `materiel_model_spec` VALUES ('60', '2', '1', '32G', 'S00058');
INSERT INTO `materiel_model_spec` VALUES ('61', '2', '2', '全网通', 'S00061');
INSERT INTO `materiel_model_spec` VALUES ('62', '2', '3', '亮白色', 'S00061');
INSERT INTO `materiel_model_spec` VALUES ('63', '2', '1', '32G', 'S00061');
INSERT INTO `materiel_model_spec` VALUES ('64', '2', '2', '全网通', 'S00064');
INSERT INTO `materiel_model_spec` VALUES ('65', '2', '3', '亮黑色', 'S00064');
INSERT INTO `materiel_model_spec` VALUES ('66', '2', '1', '32G', 'S00064');
INSERT INTO `materiel_model_spec` VALUES ('67', '2', '2', '全网通', 'S00067');
INSERT INTO `materiel_model_spec` VALUES ('68', '2', '3', '红色', 'S00067');
INSERT INTO `materiel_model_spec` VALUES ('69', '2', '1', '128G', 'S00067');
INSERT INTO `materiel_model_spec` VALUES ('70', '2', '2', '全网通', 'S00070');
INSERT INTO `materiel_model_spec` VALUES ('71', '2', '3', '玫瑰金', 'S00070');
INSERT INTO `materiel_model_spec` VALUES ('72', '2', '1', '128G', 'S00070');
INSERT INTO `materiel_model_spec` VALUES ('73', '2', '2', '全网通', 'S00073');
INSERT INTO `materiel_model_spec` VALUES ('74', '2', '3', '黑色', 'S00073');
INSERT INTO `materiel_model_spec` VALUES ('75', '2', '1', '128G', 'S00073');
INSERT INTO `materiel_model_spec` VALUES ('76', '2', '2', '全网通', 'S00076');
INSERT INTO `materiel_model_spec` VALUES ('77', '2', '3', '香槟金', 'S00076');
INSERT INTO `materiel_model_spec` VALUES ('78', '2', '1', '128G', 'S00076');
INSERT INTO `materiel_model_spec` VALUES ('79', '2', '2', '全网通', 'S00079');
INSERT INTO `materiel_model_spec` VALUES ('80', '2', '3', '亮白色', 'S00079');
INSERT INTO `materiel_model_spec` VALUES ('81', '2', '1', '128G', 'S00079');
INSERT INTO `materiel_model_spec` VALUES ('82', '2', '2', '全网通', 'S00082');
INSERT INTO `materiel_model_spec` VALUES ('83', '2', '3', '亮黑色', 'S00082');
INSERT INTO `materiel_model_spec` VALUES ('84', '2', '1', '128G', 'S00082');
INSERT INTO `materiel_model_spec` VALUES ('85', '1', '2', '全网通', 'S00085');
INSERT INTO `materiel_model_spec` VALUES ('86', '1', '3', '红色', 'S00085');
INSERT INTO `materiel_model_spec` VALUES ('87', '1', '1', '32G', 'S00085');
INSERT INTO `materiel_model_spec` VALUES ('88', '1', '2', '全网通', 'S00088');
INSERT INTO `materiel_model_spec` VALUES ('89', '1', '3', '玫瑰金', 'S00088');
INSERT INTO `materiel_model_spec` VALUES ('90', '1', '1', '32G', 'S00088');
INSERT INTO `materiel_model_spec` VALUES ('91', '1', '2', '全网通', 'S00091');
INSERT INTO `materiel_model_spec` VALUES ('92', '1', '3', '黑色', 'S00091');
INSERT INTO `materiel_model_spec` VALUES ('93', '1', '1', '32G', 'S00091');
INSERT INTO `materiel_model_spec` VALUES ('94', '1', '2', '全网通', 'S00094');
INSERT INTO `materiel_model_spec` VALUES ('95', '1', '3', '香槟金', 'S00094');
INSERT INTO `materiel_model_spec` VALUES ('96', '1', '1', '32G', 'S00094');
INSERT INTO `materiel_model_spec` VALUES ('97', '1', '2', '全网通', 'S00097');
INSERT INTO `materiel_model_spec` VALUES ('98', '1', '3', '亮白色', 'S00097');
INSERT INTO `materiel_model_spec` VALUES ('99', '1', '1', '32G', 'S00097');
INSERT INTO `materiel_model_spec` VALUES ('100', '1', '2', '全网通', 'S00100');
INSERT INTO `materiel_model_spec` VALUES ('101', '1', '3', '亮黑色', 'S00100');
INSERT INTO `materiel_model_spec` VALUES ('102', '1', '1', '32G', 'S00100');
INSERT INTO `materiel_model_spec` VALUES ('103', '1', '2', '全网通', 'S00103');
INSERT INTO `materiel_model_spec` VALUES ('104', '1', '3', '红色', 'S00103');
INSERT INTO `materiel_model_spec` VALUES ('105', '1', '1', '128G', 'S00103');
INSERT INTO `materiel_model_spec` VALUES ('106', '1', '2', '全网通', 'S00106');
INSERT INTO `materiel_model_spec` VALUES ('107', '1', '3', '玫瑰金', 'S00106');
INSERT INTO `materiel_model_spec` VALUES ('108', '1', '1', '128G', 'S00106');
INSERT INTO `materiel_model_spec` VALUES ('109', '1', '2', '全网通', 'S00109');
INSERT INTO `materiel_model_spec` VALUES ('110', '1', '3', '黑色', 'S00109');
INSERT INTO `materiel_model_spec` VALUES ('111', '1', '1', '128G', 'S00109');
INSERT INTO `materiel_model_spec` VALUES ('112', '1', '2', '全网通', 'S00112');
INSERT INTO `materiel_model_spec` VALUES ('113', '1', '3', '香槟金', 'S00112');
INSERT INTO `materiel_model_spec` VALUES ('114', '1', '1', '128G', 'S00112');
INSERT INTO `materiel_model_spec` VALUES ('115', '1', '2', '全网通', 'S00115');
INSERT INTO `materiel_model_spec` VALUES ('116', '1', '3', '亮白色', 'S00115');
INSERT INTO `materiel_model_spec` VALUES ('117', '1', '1', '128G', 'S00115');
INSERT INTO `materiel_model_spec` VALUES ('118', '1', '2', '全网通', 'S00118');
INSERT INTO `materiel_model_spec` VALUES ('119', '1', '3', '亮黑色', 'S00118');
INSERT INTO `materiel_model_spec` VALUES ('120', '1', '1', '128G', 'S00118');
INSERT INTO `materiel_model_spec` VALUES ('121', '11', '2', '全网通', 'S00121');
INSERT INTO `materiel_model_spec` VALUES ('122', '11', '3', '烈焰红', 'S00121');
INSERT INTO `materiel_model_spec` VALUES ('123', '11', '1', '128G', 'S00121');
INSERT INTO `materiel_model_spec` VALUES ('124', '11', '2', '全网通', 'S00124');
INSERT INTO `materiel_model_spec` VALUES ('125', '11', '3', '莫奈粉', 'S00124');
INSERT INTO `materiel_model_spec` VALUES ('126', '11', '1', '128G', 'S00124');
INSERT INTO `materiel_model_spec` VALUES ('127', '11', '2', '全网通', 'S00127');
INSERT INTO `materiel_model_spec` VALUES ('128', '11', '3', '冰川蓝', 'S00127');
INSERT INTO `materiel_model_spec` VALUES ('129', '11', '1', '128G', 'S00127');
INSERT INTO `materiel_model_spec` VALUES ('130', '11', '2', '全网通', 'S00130');
INSERT INTO `materiel_model_spec` VALUES ('131', '11', '3', '暗夜紫', 'S00130');
INSERT INTO `materiel_model_spec` VALUES ('132', '11', '1', '128G', 'S00130');
INSERT INTO `materiel_model_spec` VALUES ('133', '12', '2', '全网通', 'S00133');
INSERT INTO `materiel_model_spec` VALUES ('134', '12', '3', '芭比粉', 'S00133');
INSERT INTO `materiel_model_spec` VALUES ('135', '12', '1', '64G', 'S00133');
INSERT INTO `materiel_model_spec` VALUES ('136', '12', '2', '全网通', 'S00136');
INSERT INTO `materiel_model_spec` VALUES ('137', '12', '3', '宝贝蓝', 'S00136');
INSERT INTO `materiel_model_spec` VALUES ('138', '12', '1', '64G', 'S00136');
INSERT INTO `materiel_model_spec` VALUES ('139', '12', '2', '全网通', 'S00139');
INSERT INTO `materiel_model_spec` VALUES ('140', '12', '3', 'HelloKitty配色', 'S00139');
INSERT INTO `materiel_model_spec` VALUES ('141', '12', '1', '64G', 'S00139');
INSERT INTO `materiel_model_spec` VALUES ('142', '12', '2', '全网通', 'S00142');
INSERT INTO `materiel_model_spec` VALUES ('143', '12', '3', '芭比粉', 'S00142');
INSERT INTO `materiel_model_spec` VALUES ('144', '12', '1', '128G', 'S00142');
INSERT INTO `materiel_model_spec` VALUES ('145', '12', '2', '全网通', 'S00145');
INSERT INTO `materiel_model_spec` VALUES ('146', '12', '3', '宝贝蓝', 'S00145');
INSERT INTO `materiel_model_spec` VALUES ('147', '12', '1', '128G', 'S00145');
INSERT INTO `materiel_model_spec` VALUES ('148', '12', '2', '全网通', 'S00148');
INSERT INTO `materiel_model_spec` VALUES ('149', '12', '3', 'HelloKitty配色', 'S00148');
INSERT INTO `materiel_model_spec` VALUES ('150', '12', '1', '128G', 'S00148');
INSERT INTO `materiel_model_spec` VALUES ('151', '9', '2', '全网通', 'S00151');
INSERT INTO `materiel_model_spec` VALUES ('152', '9', '3', '金色', 'S00151');
INSERT INTO `materiel_model_spec` VALUES ('153', '9', '1', '64G', 'S00151');
INSERT INTO `materiel_model_spec` VALUES ('154', '9', '2', '全网通', 'S00154');
INSERT INTO `materiel_model_spec` VALUES ('155', '9', '3', '磨砂黑', 'S00154');
INSERT INTO `materiel_model_spec` VALUES ('156', '9', '1', '64G', 'S00154');
INSERT INTO `materiel_model_spec` VALUES ('157', '9', '2', '全网通', 'S00157');
INSERT INTO `materiel_model_spec` VALUES ('158', '9', '3', '玫瑰金', 'S00157');
INSERT INTO `materiel_model_spec` VALUES ('159', '9', '1', '64G', 'S00157');
INSERT INTO `materiel_model_spec` VALUES ('160', '10', '2', '全网通', 'S00160');
INSERT INTO `materiel_model_spec` VALUES ('161', '10', '3', '金色', 'S00160');
INSERT INTO `materiel_model_spec` VALUES ('162', '10', '1', '64G', 'S00160');
INSERT INTO `materiel_model_spec` VALUES ('163', '10', '2', '全网通', 'S00163');
INSERT INTO `materiel_model_spec` VALUES ('164', '10', '3', '磨砂黑', 'S00163');
INSERT INTO `materiel_model_spec` VALUES ('165', '10', '1', '64G', 'S00163');
INSERT INTO `materiel_model_spec` VALUES ('166', '10', '2', '全网通', 'S00166');
INSERT INTO `materiel_model_spec` VALUES ('167', '10', '3', '玫瑰金', 'S00166');
INSERT INTO `materiel_model_spec` VALUES ('168', '10', '1', '64G', 'S00166');
INSERT INTO `materiel_model_spec` VALUES ('169', '8', '2', '全网通', 'S00169');
INSERT INTO `materiel_model_spec` VALUES ('170', '8', '3', '金色', 'S00169');
INSERT INTO `materiel_model_spec` VALUES ('171', '8', '1', '64G', 'S00169');
INSERT INTO `materiel_model_spec` VALUES ('172', '8', '2', '全网通', 'S00172');
INSERT INTO `materiel_model_spec` VALUES ('173', '8', '3', '磨砂黑', 'S00172');
INSERT INTO `materiel_model_spec` VALUES ('174', '8', '1', '64G', 'S00172');
INSERT INTO `materiel_model_spec` VALUES ('175', '8', '2', '全网通', 'S00175');
INSERT INTO `materiel_model_spec` VALUES ('176', '8', '3', '玫瑰金', 'S00175');
INSERT INTO `materiel_model_spec` VALUES ('177', '8', '1', '64G', 'S00175');
INSERT INTO `materiel_model_spec` VALUES ('178', '7', '2', '全网通', 'S00178');
INSERT INTO `materiel_model_spec` VALUES ('179', '7', '3', '金色', 'S00178');
INSERT INTO `materiel_model_spec` VALUES ('180', '7', '1', '64G', 'S00178');
INSERT INTO `materiel_model_spec` VALUES ('181', '7', '2', '全网通', 'S00181');
INSERT INTO `materiel_model_spec` VALUES ('182', '7', '3', '磨砂黑', 'S00181');
INSERT INTO `materiel_model_spec` VALUES ('183', '7', '1', '64G', 'S00181');
INSERT INTO `materiel_model_spec` VALUES ('184', '7', '2', '全网通', 'S00184');
INSERT INTO `materiel_model_spec` VALUES ('185', '7', '3', '玫瑰金', 'S00184');
INSERT INTO `materiel_model_spec` VALUES ('186', '7', '1', '64G', 'S00184');
INSERT INTO `materiel_model_spec` VALUES ('187', '7', '2', '全网通', 'S00187');
INSERT INTO `materiel_model_spec` VALUES ('188', '7', '3', '金色', 'S00187');
INSERT INTO `materiel_model_spec` VALUES ('189', '7', '1', '128G', 'S00187');
INSERT INTO `materiel_model_spec` VALUES ('190', '7', '2', '全网通', 'S00190');
INSERT INTO `materiel_model_spec` VALUES ('191', '7', '3', '磨砂黑', 'S00190');
INSERT INTO `materiel_model_spec` VALUES ('192', '7', '1', '128G', 'S00190');
INSERT INTO `materiel_model_spec` VALUES ('193', '7', '2', '全网通', 'S00193');
INSERT INTO `materiel_model_spec` VALUES ('194', '7', '3', '玫瑰金', 'S00193');
INSERT INTO `materiel_model_spec` VALUES ('195', '7', '1', '128G', 'S00193');
INSERT INTO `materiel_model_spec` VALUES ('196', '13', '2', '全网通', 'S00196');
INSERT INTO `materiel_model_spec` VALUES ('197', '13', '3', '香槟色', 'S00196');
INSERT INTO `materiel_model_spec` VALUES ('198', '13', '1', '64G', 'S00196');
INSERT INTO `materiel_model_spec` VALUES ('199', '13', '2', '全网通', 'S00199');
INSERT INTO `materiel_model_spec` VALUES ('200', '13', '3', '黑色', 'S00199');
INSERT INTO `materiel_model_spec` VALUES ('201', '13', '1', '64G', 'S00199');
INSERT INTO `materiel_model_spec` VALUES ('202', '13', '2', '全网通', 'S00202');
INSERT INTO `materiel_model_spec` VALUES ('203', '13', '3', '红色', 'S00202');
INSERT INTO `materiel_model_spec` VALUES ('204', '13', '1', '64G', 'S00202');

-- ----------------------------
-- Records of materiel_spec
-- ----------------------------
INSERT INTO `materiel_spec` VALUES ('1', '内存', '内存', '1', '2');
INSERT INTO `materiel_spec` VALUES ('2', '网络', '网络', '1', '1');
INSERT INTO `materiel_spec` VALUES ('3', '颜色', '颜色', '1', '3');

-- ----------------------------
-- Records of materiel_unit
-- ----------------------------
INSERT INTO `materiel_unit` VALUES ('1', '台', 'PCS', '1', '用于手机');


-- ----------------------------
-- Table structure for product_lease_term
-- ----------------------------
DROP TABLE IF EXISTS `product_lease_term`;
CREATE TABLE "product_lease_term" (
  "id" smallint(6) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  "termValue" smallint(6) NOT NULL DEFAULT '0' COMMENT '租期值（单位：月）',
  "termDesc" varchar(50) NOT NULL DEFAULT '0' COMMENT '租期描述',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='产品租期表';

-- ----------------------------
-- Records of product_lease_term
-- ----------------------------
INSERT INTO `product_lease_term` VALUES ('3', '12', '12个月');


-- ----------------------------
-- Records of supplier_basic_info
-- ----------------------------
INSERT INTO `supplier_basic_info` VALUES ('1', '迪信通', 'DXT', '深圳', '胡生', '18612569874');

-- ----------------------------
-- Records of warehouse_info
-- ----------------------------
INSERT INTO `warehouse_info` VALUES ('1', '0000', '总仓', '0', '赵云', '18588888888', '', '', '1', '0', '1', '1', '2017-12-16 15:47:49', '2017-12-16 15:47:49');
INSERT INTO `warehouse_info` VALUES ('2', 'S001', '深圳仓库', '1', '赵云', '18588888888', '', '', '1', '0', '1', '1', '2017-12-16 15:51:15', '2017-12-16 16:00:57');
INSERT INTO `warehouse_info` VALUES ('3', 'S011', '新机仓', '2', '赵云', '18588888888', '', '', '1', '0', '1', '1', '2017-12-16 15:53:30', '2017-12-16 15:53:30');
INSERT INTO `warehouse_info` VALUES ('4', 'S012', '二手机仓', '2', '赵云', '18588888888', '', '', '1', '0', '1', '1', '2017-12-26 17:58:12', '2017-12-26 17:58:14');


-- ----------------------------
-- Records of warehouse_location
-- ----------------------------
INSERT INTO `warehouse_location` VALUES ('1', 'AVAILABLE', '可售', '当前可以售卖', '0', '1', '1', '1', '2017-12-16 10:33:14', '2017-12-16 10:33:14');
INSERT INTO `warehouse_location` VALUES ('2', 'SALES', '待售', '表示已拣货，等待交给物流', '0', '1', '1', '1', '2017-12-16 10:50:06', '2017-12-18 13:59:25');
INSERT INTO `warehouse_location` VALUES ('3', 'RESERVE', '冻结', '表示预留', '0', '1', '1', '1', '2017-12-18 14:03:20', '2017-12-22 15:48:32');
INSERT INTO `warehouse_location` VALUES ('4', 'TRANSIT_OUT', '出库在途', '表示商品已出库，运输中', '0', '1', '1', '1', '2017-12-26 15:37:35', '2017-12-26 15:37:38');
INSERT INTO `warehouse_location` VALUES ('5', 'TRANSIT_IN', '入库在途', '表示商品在入库途中', '0', '0', '1', '1', '2017-12-26 15:38:57', '2017-12-26 15:39:00');
INSERT INTO `warehouse_location` VALUES ('6', 'RENTING', '在租', '表示商品在用户手中', '0', '0', '1', '1', '2017-12-26 16:05:09', '2017-12-26 16:05:12');
INSERT INTO `warehouse_location` VALUES ('7', 'GOOD', '好机', '退回检测后是好的机器', '0', '0', '1', '1', '2017-12-26 16:05:58', '2017-12-26 16:06:00');
INSERT INTO `warehouse_location` VALUES ('8', 'FAULTY', '坏机', '退回检测后是坏的机器', '0', '0', '1', '1', '2017-12-26 16:06:43', '2017-12-26 16:06:45');
INSERT INTO `warehouse_location` VALUES ('9', 'DELIVER_GAP', '物流差异', '入库时，物流收货的差异', '0', '0', '1', '1', '2017-12-26 16:07:26', '2017-12-26 16:07:28');
INSERT INTO `warehouse_location` VALUES ('10', 'PENDING_GAP', '盘库差异', '盘库时，盘点的差异', '0', '0', '1', '1', '2017-12-26 16:08:27', '2017-12-26 16:08:29');
INSERT INTO `warehouse_location` VALUES ('11', 'BUY_END', '买断', '用户提前买断，正常买断', '0', '0', '1', '1', '2018-01-02 10:20:25', '2018-01-02 10:20:27');

-- ----------------------------
-- Records of warehouse_location_relation
-- ----------------------------
INSERT INTO `warehouse_location_relation` VALUES ('3', '1');
INSERT INTO `warehouse_location_relation` VALUES ('3', '2');
INSERT INTO `warehouse_location_relation` VALUES ('3', '3');
INSERT INTO `warehouse_location_relation` VALUES ('3', '4');
INSERT INTO `warehouse_location_relation` VALUES ('3', '5');
INSERT INTO `warehouse_location_relation` VALUES ('3', '6');
INSERT INTO `warehouse_location_relation` VALUES ('3', '7');
INSERT INTO `warehouse_location_relation` VALUES ('3', '8');
INSERT INTO `warehouse_location_relation` VALUES ('3', '9');
INSERT INTO `warehouse_location_relation` VALUES ('3', '10');
INSERT INTO `warehouse_location_relation` VALUES ('3', '11');

SET FOREIGN_KEY_CHECKS=1;