microservice-loverent-warehouse-v1
本项目为爱租机库存管理后台应用

swagger2整合说明：
1.pom中引入swagger2相关依赖：
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.7.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.7.0</version>
</dependency>

2.增加swagger2相关配置,具体参考org.gz.warehouse.config.Swagger2Config

3.在控制器上添加相关操作说明 ，具体参考org.gz.warehouse.web.controller.MaterielUnitController

4.在请求参数对象(POJO)上添加相关字段说明，具体参考：org.gz.warehouse.entity.MaterielUnit

5.按序启动microservice-loverent-discovery-v1，microservice-loverent-warehouse-v1，microservice-loverent-api-gateway-v1

6.然后通过网关地址访问，如：http://localhost:19200/api/warehouse/swagger-ui.html

