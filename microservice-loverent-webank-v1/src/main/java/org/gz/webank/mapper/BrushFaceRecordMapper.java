package org.gz.webank.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.webank.entity.BrushFaceRecord;

@Mapper
public interface BrushFaceRecordMapper {
    int deleteByPrimaryKey(Long recordid);

    int insert(BrushFaceRecord record);

    int insertSelective(BrushFaceRecord record);

    BrushFaceRecord selectByPrimaryKey(Long recordid);

    int updateByPrimaryKeySelective(BrushFaceRecord record);

    int updateByPrimaryKey(BrushFaceRecord record);

	List<BrushFaceRecord> selectRecentRecordByUserId(Long userId);

	List<BrushFaceRecord> selectRecentRecordByTel(String tel);
}