package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.warehouse.WarehouseReturnImages;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecord;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecordVO;

/**
 * @Title:
 * @Description: 还机记录数据处理接口
 * @author daiqingwen
 * @date 2018年3月6日 上午10:13
 */
@Mapper
public interface WarehouseReturnMapper {

    /**
     * 查询所有还机记录
     * @return WarehouseReturnRecord
     * @param page
     */
    List<WarehouseReturnRecord>  queryPageList(WarehouseReturnRecordVO page);

    /**
     * 新增还机记录
     * @param returnRecord
     *
     */
    int inserRecord(WarehouseReturnRecord returnRecord);

    /**
     * 修改还机记录
     * @param returnRecord
     *
     */
    int updateRecord(WarehouseReturnRecord returnRecord);

    /**
     * 删除还机记录
     * @param ids
     *
     */
    int deleteRecord(Long[] ids);

    /**
     * 删除还机记录附件
     * @param ids
     */
    void deleteRecordImages(Long[] ids);

    /**
     * 删除原始附件
     * @param id
     */
    void deleteOriginalImages(Long id);

    /**
     * 批量插入新附件
     * @param item
     */
    void insertRecordImages(List<WarehouseReturnImages> item);

    /**
     * 获取还机记录总数
     * @return int
     * @param returnRecord
     */
    int queryPageCount(WarehouseReturnRecordVO returnRecord);

    /**
     * 获取还机记录详情
     * @param id
     * @return
     */
    List<WarehouseReturnImages> queryRecordDetail(Long id);

	/** 
	* @Description: 根据主键查询还机详情
	* @param  id
	* @return WarehouseReturnRecord
	*/
	WarehouseReturnRecord selectByPrimaryKey(@Param("id")Long id);
}
