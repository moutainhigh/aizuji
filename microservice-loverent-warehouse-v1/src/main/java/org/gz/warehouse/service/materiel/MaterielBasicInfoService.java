package org.gz.warehouse.service.materiel;

import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielBasicInfoReq;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductMaterielIntroReq;
import org.gz.warehouse.common.vo.ProductMaterielIntroResp;
import org.gz.warehouse.entity.MaterielBasicImages;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;

public interface MaterielBasicInfoService {

	public ResponseResult<MaterielBasicInfo> insert(MaterielBasicInfo m);

	public ResponseResult<MaterielBasicInfo> update(MaterielBasicInfo m);

	/** 
	* @Description: 获取物料详情
	* @param id
	* @return ResponseResult<MaterielBasicInfo>
	*/
	public ResponseResult<MaterielBasicInfo> getDetail(Long id);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<ResultPager<MaterielBasicInfo>>
	* @throws 
	*/
	public ResponseResult<ResultPager<MaterielBasicInfoVO>> queryByPage(MaterielBasicInfoQuery m);
	
    /**
     * 查询物料信息
     * @param m
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2017年12月21日
     */
    public List<MaterielBasicInfoVO> queryList(MaterielBasicInfoQuery m);

	/** 
	* @Description: 
	* @param @param m
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	public ResponseResult<MaterielBasicInfo> setEnableFlag(MaterielBasicInfo m);

	/** 
	* @Description: 查询物料附件图片
	* @param  materielBasicInfoId
	* @return List<MaterielBasicImages>
	*/
	public List<MaterielBasicImages> queryAttaListByMaterielBasicInfoId(Long materielBasicInfoId);

	/** 
	* @Description: 根据物料型号查询主物料信息(不带图文介绍)
	* @param req
	* @return MaterielBasicInfoResp
	*/
	public MaterielBasicInfoResp queryMainMaterielBasicInfo(MaterielBasicInfoReq req);
	
	public MaterielBasicInfoResp queryMainMaterielBasicIntroductionInfo(MaterielBasicInfoReq req);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return Object
	* @throws 
	*/
	public List<ProductMaterielIntroResp> queryProductMaterielIntros(ProductMaterielIntroReq q);
	
}
