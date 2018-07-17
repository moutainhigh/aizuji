/**
 * 
 */
package org.gz.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.common.entity.QueryPager;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielNewConfig;

/**
 * @Title: 
 * @author hxj
 * @date 2018年3月2日 下午2:09:38
 */
@Mapper
public interface MaterielNewConfigMapper {

	/** 
	* 分頁查询所有新旧程度配置
	* @return List<MaterielNewConfig>
	 * @param page
	*/
	List<MaterielNewConfig> queryNewConfigPage(QueryPager page);

	/**
	 * 新增新旧程度配置
	 * @param config
	 * @return int
	 */
	int insertNewConfigs(MaterielNewConfig config);

	/**
	 * 修改新旧程度配置
	 * @param config
	 * @return int
	 */
	int updateNewConfigs(MaterielNewConfig config);

	/**
	 * 根据新旧配置ID查询新旧程度配置
	 * @param id
	 * @return MaterielBasicInfo
	 */
	List<MaterielBasicInfo> queryBasicInfo(int id);

	/**
	 * 删除新旧程度配置
	 * @param id
	 * @return int
	 */
	int deleteById(Integer id);

	/**
	 * 根据新旧配置名称和值查询数据
	 * @param config
	 * @return MaterielNewConfig
	 */
	List<MaterielNewConfig> queryConfigsByNameAndValue(MaterielNewConfig config);

	/**
	 * 查询新旧程度配置总数
	 * @return
	 */
    int queryNewConfigsCout();

	/**
	 * 查询所有新旧程度配置
	 * @return
	 */
	List<MaterielNewConfig> queryAllNewConfig();

	/**
	 * 查询所有可用成色配置
	 * @return
	 */
	List<MaterielNewConfig> queryAllavailableNewConfigs();
	
}
