package org.gz.webank.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.webank.entity.BrushFaceRecord;
import org.gz.webank.mapper.BrushFaceRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 人脸识别记录
 * 
 * @author yangdx
 *
 */
@Service
@Slf4j
public class BrushFaceRecordService {

	@Autowired
	private BrushFaceRecordMapper burshFaceRecordMapper;
	
	/**
	 * 添加识别记录
	 * @param record
	 * @return
	 */
	public void addBrushRecord(BrushFaceRecord record) {
		try {
			log.info("addBrushRecord() --> start insert BrushFaceRecord to db, record: {}", JSONObject.toJSONString(record));
			burshFaceRecordMapper.insertSelective(record);
		} catch (Exception e) {
			log.error("addBrushRecord() --> insert BrushFaceRecord to db failed!, record: {}, e: {}", 
					JSONObject.toJSONString(record), e.getMessage());
		}
	}
	
	public BrushFaceRecord selectByPrimaryKey(Long recordId) {
		BrushFaceRecord record = burshFaceRecordMapper.selectByPrimaryKey(recordId);
		return record;
	}
	
	/**
	 * 查询用户刷脸记录
	 * @param userId
	 * @return
	 */
	public BrushFaceRecord selectRecentRecordByUserId(Long userId) {
		List<BrushFaceRecord> records = burshFaceRecordMapper.selectRecentRecordByUserId(userId);
		if (records != null && !records.isEmpty()) {
			return records.get(0);
		}
		return null;
	}
	
	/**
	 * 查询用户刷脸记录
	 * @param userId
	 * @return
	 */
	public BrushFaceRecord selectRecentRecordByTel(String tel) {
		List<BrushFaceRecord> records = burshFaceRecordMapper.selectRecentRecordByTel(tel);
		if (records != null && !records.isEmpty()) {
			return records.get(0);
		}
		return null;
	}

	/**
	 * 更新
	 * @param record
	 */
	public void updateByPrimaryKeySelective(BrushFaceRecord record) {
		burshFaceRecordMapper.updateByPrimaryKeySelective(record);
	}
	
}
