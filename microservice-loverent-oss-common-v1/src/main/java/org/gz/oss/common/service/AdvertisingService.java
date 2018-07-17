package org.gz.oss.common.service;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Advertising;
import org.gz.oss.common.entity.AdvertisingVO;

import java.util.List;

/**
 * 广告位业务接口
 * @author daiqingwen
 * @date 2018/3/13
 */
public interface AdvertisingService {

    /**
     * 查询广告位列表
     * @return ResponseResult<List<Advertising>>
     */
    ResponseResult<List<Advertising>> queryAdverList();

    /**
     * 新增
     * @param vo
     * @return ResponseResult
     */
    ResponseResult<?> insert(AdvertisingVO vo);

    /**
     * 修改
     * @param vo
     * @return ResponseResult
     */
    ResponseResult<?> update(AdvertisingVO vo);

    /**
     * 刪除
     * @param id
     * @return ResponseResult
     */
    ResponseResult<?> delete(int id);

    /**
     * 广告位上移/下移
     * @param vo
     * @return ResponseResult
     */
    ResponseResult<?> move(AdvertisingVO vo);

    /**
     * 根据广告位ID，获取广告位信息
     * @param id 广告位ID
     * @return ResponseResult
     */
    ResponseResult<Advertising> getAdvertisingById(int id);
}
