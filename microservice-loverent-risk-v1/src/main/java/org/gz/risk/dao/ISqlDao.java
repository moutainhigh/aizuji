package org.gz.risk.dao;

import java.util.List;
import java.util.Map;

public interface ISqlDao {

    <E> E get(String selectId, Object params);
     List<Map<String,Object>> callProc(String selectId, List params) throws Exception;

    <E> List<E> query(String selectId, Object params);

    <E> List<E> query(String selectId, Object params, int start, int limit);

    <E> List<E> query(String selectId, Query params);

    <E, T> Map<E, T> getMap(String selectId, String key, Object params);

    int add(String insertId, Object params);

    int add(String insertId, Object params, boolean autoCommit);

    int update(String updateId, Object params);

    int update(String updateId, Object params, boolean autoCommit);

    int delete(String deleteId, Object params);

    int delete(String deleteId, Object params, boolean autoCommit);

    int save(String insertId, Object params);

    int save(String insertId, Object params, boolean autoCommit);
    

}
