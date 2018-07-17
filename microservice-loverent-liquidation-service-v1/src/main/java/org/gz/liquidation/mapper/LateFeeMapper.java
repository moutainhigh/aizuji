package org.gz.liquidation.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.entity.LateFeeEntity;

@Mapper
public interface LateFeeMapper {

    int insert(LateFeeEntity record);

    int insertSelective(LateFeeEntity record);

    LateFeeEntity selectByPrimaryKey(Integer id);
    /**
     * 
     * @Description: 选择性更新
     * @param lateFeeEntity
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月8日
     */
    int updateByPrimaryKeySelective(LateFeeEntity lateFeeEntity);

    int updateByPrimaryKey(LateFeeEntity record);
    /**
     * 
     * @Description: 批量新增
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月8日
     */
    void batchInsert(List<LateFeeEntity> list);
    /**
     * 
     * @Description:查询滞纳金列表
     * @param list 订单号集合
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月8日
     */
    List<LateFeeEntity> selectListByOrderSNs(List<String> list);
    /**
     * 
     * @Description: 批量更新 
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月9日
     */
    void batchUpdate(List<LateFeeEntity> list);
    /**
     * 
     * @Description: 批量失效
     * @param list
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月9日
     */
    void batchUpdateEnableFlag(List<LateFeeEntity> list);
    /**
     * 
     * @Description: 批量查询数据是否存在
     * @param list 订单号集合
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月12日
     */
    List<LateFeeEntity> batchQueryIsExist(List<String> list);
    /**
     * 
     * @Description: 查询订单号的应付滞纳金
     * @param orderSN 订单号
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月12日
     */
    BigDecimal lateFeePayable(String orderSN);
    /**
     * 
     * @Description: 根据条件查询数据
     * @param lateFeeEntity 实体对象参数
     * @return 实体对象
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月12日
     */
    LateFeeEntity selectByCondition(LateFeeEntity lateFeeEntity);
    /**
     * 
     * @Description: 查询滞纳金信息(未还和已还)
     * @param lateFeeEntity
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月19日
     */
    LateFeeEntity selectLateFeeInfo(LateFeeEntity lateFeeEntity);
    /**
     * 
     * @Description: 查询未结清的滞纳金列表数据
     * @param orderSN 订单号
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月19日
     */
    List<LateFeeEntity> selectUnsettledList(String orderSN);
}