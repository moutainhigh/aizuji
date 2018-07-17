microservice-loverent-v1
此工程是使用SpringCloud搭建的Maven聚合项目。它包含了子项目的共用属性，以及依赖版本配置。

子项目命名规则：
microservice-loverent-{modulename}-v1

子项目根包命名规则：
org.gzjf.{modulename}

子项目实体\JavaBean\DTO包命名规则：
org.gzjf.{modulename}.entity
org.gzjf.{modulename}.dto

子项目mapper根包命名规则：
org.gzjf.{modulename}.mapper

子项目service包命名规则：
org.gzjf.{modulename}.service

子项目controller包命名规则:
org.gzjf.{modulename}.web.controller

子项目工具包命名规则:
org.gzjf.{modulename}.utils

##########################################################################################
基础服务
##########################################################################################
├─microservice-loverent-discovery-v1    服务治理暴露项目
├───microservice-loverent-common-v1     工具包

microservice-loverent-api-gateway-v1   网关暴露项目

├─microservice-loverent-sms-v1    短信发送暴露项目
├───microservice-loverent-sms-api-v1      接口依赖

microservice-loverent-mq-v1   队列服务



##########################################################################################
业务服务
##########################################################################################
├─microservice-loverent-app-v1    app接口暴露服务
├───microservice-loverent-cache-v1      缓存
├───microservice-loverent-webank-v1      微信

├─microservice-loverent-warehouse-v1    库存项目暴露服务
├───microservice-loverent-warehouse-api-v1      接口
├───microservice-loverent-warehouse-common-v1      工具类

├─microservice-loverent-order-api-v1    订单项目接口暴露服务
├─microservice-loverent-order-backend-v1    订单项目管理页面暴露服务
├───microservice-loverent-order-server-v1      接口
├───microservice-loverent-order-common-v1      工具类

├─microservice-loverent-oss-backend-v1    运营系统项目管理页面暴露服务
├─microservice-loverent-oss-server-v1     运营系统项目接口暴露服务
├───microservice-loverent-oss-common-v1      工具类

├─microservice-loverent-user-v1    用户服务项目接口暴露服务
├───microservice-loverent-user-api-v1   接口



microservice-loverent-thirdParty-v1  第三方接口服务