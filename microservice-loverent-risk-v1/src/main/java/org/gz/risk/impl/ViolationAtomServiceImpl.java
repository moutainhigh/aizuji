package org.gz.risk.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.gz.risk.dao.ViolationDao;
import org.gz.risk.entity.Violation;
import org.gz.risk.intf.ViolationAtomService;
import org.springframework.stereotype.Service;

@Service
public class ViolationAtomServiceImpl implements ViolationAtomService{

	@Resource
    private ViolationDao violationDao;
    
    @Override
    public void addBatch(List<Violation> list) {
        if(list != null && list.size()>0){
            violationDao.addBatch(list);
        }
    }

    @Override
    public void add(Violation violation) {
        violationDao.add(violation);
    }

    @Override
    public void update(Violation dto) {
        violationDao.update(dto);
    }

    @Override
    public List<Violation> queryListByUserId(Long userId) {
        return violationDao.queryListByUserId(userId);
    }

    @Override
    public List<Violation> queryList(Violation dto) {
        return violationDao.queryList(dto);
    }

    @Override
    public List<Violation> selectByMeal(HashMap<String, Object> map) {
        return violationDao.selectByMeal(map);
    }

    @Override
    public Violation selectByVio(HashMap<String, Object> map) {
        return violationDao.selectByVio(map);
    }

    @Override
    public Long addGetPK(Violation violation) {
        return Long.valueOf(violationDao.addGetPK(violation));
    }

    @Override
    public void update(Long userId, String key, String value) {
    	Violation dto = new Violation();
    	dto.setUserId(userId);
    	dto.setViolationKey(key);
    	dto.setViolationValue(value);
        violationDao.update(dto);
    }

    @Override
    public Integer selectByMealNum(Long userId, String meal) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("meal", meal);
        List<Violation> list = violationDao.selectByMeal(map );
        if (list != null) {
            return list.size();
        }
        return 0;
    }
    
    @Override
    public void updateSwitch(Map<String, Integer> map) {
        violationDao.updateSwitch(map);
    }

    @Override
    public Map<String, Object> querySwitch() {
        return violationDao.querySwitch();
    }
    
}
