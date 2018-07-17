package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.QueryPager;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.MaterielNewConfig;

/**
 * 新旧程度业务处理类
 * 
 * @date 2018年3月2日 下午2:02:02
 */
public interface MaterielNewConfigService {

	/**
	 * 分页查询所有新旧程度配置
	 * @return MaterielNewConfig
     * @param page
	 */
	public ResponseResult<ResultPager<MaterielNewConfig>> queryForPage(QueryPager page);

	/**
	 * 查询所有新旧程度配置
	 * @return MaterielNewConfig
	 *
	 */
	List<MaterielNewConfig> queryAllNewConfigs();

	/**
	 * 新增新旧程度配置
	 * @param config
	 * @return ResponseResult
	 */
	public ResponseResult<MaterielNewConfig> insertMaterielNewConfig(MaterielNewConfig config);

	/**
	 * 修改新旧程度配置
	 * @param config
	 * @return int
	 */
	public ResponseResult<MaterielNewConfig> updateMaterielNewConfig(MaterielNewConfig config);

	/**
	 * 删除新旧程度配置
	 * @param id
	 * @return int
	 */
	public ResponseResult<?> deleteById(Integer id);

	/**
	 * 获取所有新旧配置
	 * @return MaterielNewConfig
	 */
	ResponseResult<List<MaterielNewConfig>> getAllNewConfig();
	
	/**
	 * 获取所有成色配置
	 * @return MaterielNewConfig
	 */
	public ResponseResult<List<MaterielNewConfig>> queryAllavailableNewConfigs();
}
