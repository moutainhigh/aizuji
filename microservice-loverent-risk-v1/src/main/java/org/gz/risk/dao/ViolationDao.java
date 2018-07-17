/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.risk.auditing.entity.ViolationEntity;
import org.gz.risk.entity.Violation;
import org.springframework.stereotype.Repository;
/**
 * User Dao
 * 
 * @date 2017-07-03
 */
@Repository
public class ViolationDao {

    @Resource
    private SimpleDaoSpringImpl dao;
    
    public void addBatch(List<Violation> list) {
        if (list != null && list.size() != 0) {
            dao.add("mapper.ViolationEntityMapper.addBatch", list);
        }
    }
    public int addGetPK(Violation violation) {
       return  dao.add("mapper.ViolationEntityMapper.addGetPK", violation);
    }
    public void addViolation(List<Violation> list) {
        if (list != null && list.size() != 0) {
            dao.add("mapper.ViolationEntityMapper.addViolation", list);
        }
    }

    public Long getUserId(Long id) {
        return dao.get("mapper.ViolationEntityMapper.getUserId", id);
    }

    public void addViolationLoan(List<Violation> list) {
        if (list != null && list.size() != 0) {
            dao.add("mapper.ViolationEntityMapper.addViolationLoan", list);
        }
    }

    public List<Violation> queryListByUserId(Long userId) {
        return dao.query("mapper.ViolationEntityMapper.selectByUserId", userId);
    }
    public List<Violation> queryList(Violation dto) {
        return dao.query("mapper.ViolationEntityMapper.queryList", dto);
    }
    
    public ViolationEntity select(Map map) {
        return dao.get("mapper.ViolationEntityMapper.select", map);
    }

    public List<Violation> selectByKey(Map map) {
        return dao.query("mapper.ViolationEntityMapper.selectByKey", map);
    }

    
    public List<Violation> selectByMeal(HashMap<String, Object> map) {
    	return dao.query("mapper.ViolationEntityMapper.selectByMeal", map);
    }
    public void add(Violation entity) {
        dao.add("mapper.ViolationEntityMapper.add", entity);
    }
    public Violation selectByVio(HashMap<String, Object> map) {
    	return dao.get("mapper.ViolationEntityMapper.selectByVio", map);
    }
    public void update(Violation entity) {
        dao.update("mapper.ViolationEntityMapper.updateByPrimaryKeySelective", entity);
    }

    public List<Long> allUserId() {
        return dao.query("mapper.ViolationEntityMapper.allUserId", null);
    }

    public List<Long> allSmsId() {
        return dao.query("mapper.ViolationEntityMapper.allSmsId", null);
    }

    public void updateSwitch(Map<String, Integer> value) {
        dao.update("mapper.ViolationEntityMapper.updateSwitch", value);
    }

    public Map<String, Object> querySwitch() {
        return dao.get("mapper.ViolationEntityMapper.querySwitch", null);
    }
    
}
