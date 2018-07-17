package org.gz.oss.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.Advertising;
import org.gz.oss.common.entity.AdvertisingVO;

import java.util.List;
import java.util.Map;

/**
 * 广告位数据库接口
 * @author daiqingwen
 * @date 2018/3/13
 */

@Mapper
public interface AdvertisingDao {

    /**
     * 查询
     * @return ResponseResult<List<Advertising>>
     */
    List<Advertising> queryAdverList();

    /**
     * 新增
     * @param vo
     * @return ResponseResult
     */
    int insert(AdvertisingVO vo);

    /**
     * 修改
     * @param vo
     * @return ResponseResult
     */
    int update(AdvertisingVO vo);

    /**
     * 刪除
     * @param id
     * @return ResponseResult
     */
    int delete(int id);

    /**
     * 先将当前广告位的上一个广告位的位置设置为当前广告位
     * @param map
     */
    void moveNewAdv(Map<String, Object> map);

    /**
     * 再将当前广告位的位置设置为上一个广告位的位置
     * @param map
     */
    void moveCurrentAdve(Map<String, Object> map);

    /**
     * 根据广告位ID，获取广告位信息
     * @param id 广告位ID
     * @return ResponseResult
     */
    Advertising getAdvertisingById(int id);

    /**
     * 广告位上移/下移
     * @param a
     * @return ResponseResult
     */
    void move(AdvertisingVO a);
}
