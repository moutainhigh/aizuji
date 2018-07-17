package org.gz.risk.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.gz.risk.dao.util.JsonUtil;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
@Repository
public class SimpleDaoSpringImpl extends SqlSessionDaoSupport implements ISqlDao {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDaoSpringImpl.class);
    @Resource
    private DataSource dataSource;
    @Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {  
        super.setSqlSessionFactory(sqlSessionFactory);  
    }  

	@Override
    public <E> E get(String selectId, Object params) {
        SqlSession session = getSqlSession();
        return session.selectOne(selectId, params);
    }
	@Override
    public List<Map<String,Object>> callProc(String selectId, List params)throws Exception  {
//        logger.debug(" SimpleDaoSpringImpl.callProc selectId = {} params = {}",JsonUtil.toJson(selectId),JsonUtil.toJson(params));
		 SqlSession session = getSqlSession();
		 Connection con =  session.getConnection();
    	MappedStatement map =session.getConfiguration().getMappedStatement(selectId);
    	BoundSql boundSql = map.getBoundSql(params);
    	List<Map<String,Object>> valueMap = null;
    	
        CallableStatement cs = null;
        ResultSet rest = null;
        
		try {
			con = session.getConnection();
			if(con.isClosed()){
				con=	dataSource.getConnection();
			}
			cs = con.prepareCall(boundSql.getSql());
			for (int i = 0;i <params.size();i++) {
				cs.setObject(i+1, params.get(i));
			}
	
			 rest =  cs.executeQuery();
			 valueMap = resultSetToList( rest);
		} catch (Exception e) {
			throw e;
		}finally{
			if(cs != null){
				cs.close();
			}
			if(rest != null){
				rest.close();
			}
			if(con != null){
				con.close();
			}
		}
   
        return valueMap;
    }
    private static List<Map<String,Object>> resultSetToList(ResultSet rs) throws java.sql.SQLException {   
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
    	try {
    		   if (rs == null){
    	        	  return Collections.EMPTY_LIST;  
    	        }
    	           
    	        ResultSetMetaData md = rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
    	        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
    	    
    	        Map<String,Object> rowData = new HashMap<String,Object>();   
    	        while (rs.next()) {   
    	         rowData = new HashMap(columnCount);   
    	         for (int i = 1; i <= columnCount; i++) {   
    	                 rowData.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));   
    	         }   
    	         list.add(rowData);   
    	        }   
		} catch (Exception e) {
			throw e;
		}
     
        return list;   
}  
    @Override
    public <E> List<E> query(String selectId, Object params) {
        SqlSession session = getSqlSession();
        return session.selectList(selectId, params);
    }

    @Override
    public <E> List<E> query(String selectId, Object params, int start, int limit) {
        RowBounds rowBound = new RowBounds(start, limit);
        SqlSession session = getSqlSession();
        return session.selectList(selectId, params, rowBound);
    }


    @Override
    public <E, T> Map<E, T> getMap(String selectId, String key, Object params) {
        SqlSession session = getSqlSession();
        return session.selectMap(selectId, params, key);
    }

    @Override
    public int add(String insertId, Object params) {
        SqlSession session = getSqlSession();
        return session.insert(insertId, params);
    }

    @Override
    public int add(String insertId, Object params, boolean autoCommit) {
        SqlSession session = getSqlSession();
        try {
            session.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return session.insert(insertId, params);
    }


    @Override
    public int update(String updateId, Object params) {
        SqlSession session = getSqlSession();
        return session.update(updateId, params);
    }

    @Override
    public int update(String updateId, Object params, boolean autoCommit) {
        SqlSession session = getSqlSession();
        try {
            session.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return session.update(updateId, params);
    }

    @Override
    public int delete(String deleteId, Object params) {
        SqlSession session = getSqlSession();
        return session.delete(deleteId, params);
    }

    @Override
    public int delete(String deleteId, Object params, boolean autoCommit) {
        SqlSession session = getSqlSession();
        try {
            session.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return session.delete(deleteId, params);
    }

    @Override
    public int save(String insertId, Object params) {
        SqlSession session = getSqlSession();
        return session.insert(insertId, params);
    }

    @Override
    public int save(String insertId, Object params, boolean autoCommit) {
        SqlSession session = getSqlSession();
        try {
            session.getConnection().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return session.insert(insertId, params);
    }

    @Override
    public <E> List<E> query(String selectId, Query params) {
        SqlSession session = getSqlSession();
        return session.selectList(selectId, params);
    }

	
}
