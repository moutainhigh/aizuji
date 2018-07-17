package org.gz.risk.intf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.risk.entity.Violation;

public interface ViolationAtomService{
    void addBatch(List<Violation> list);
    
    void add(Violation violation); 
    
    void update(Violation dto);
    
    List<Violation> queryListByUserId(Long userId);
    
    List<Violation> queryList(Violation dto);
    
    List<Violation> selectByMeal(HashMap<String, Object> map);
    
    Violation selectByVio(HashMap<String, Object> map);
    
    Long addGetPK(Violation violation);
    
    void update(Long userId, String key, String value);
    
    Integer selectByMealNum(Long userId, String key);
    
    void updateSwitch(Map<String, Integer> map);
    
    Map<String, Object> querySwitch();
}
