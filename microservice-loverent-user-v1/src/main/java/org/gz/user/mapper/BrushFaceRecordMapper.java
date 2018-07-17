package org.gz.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.BrushFaceRecord;

/**
 * ocr记录
 * 
 * @author yangdx
 *
 */
@Mapper
public interface BrushFaceRecordMapper {
    int deleteByPrimaryKey(Long recordid);

    int insert(BrushFaceRecord record);

    int insertSelective(BrushFaceRecord record);

    BrushFaceRecord selectByPrimaryKey(Long recordid);

    int updateByPrimaryKeySelective(BrushFaceRecord record);

    int updateByPrimaryKey(BrushFaceRecord record);
}