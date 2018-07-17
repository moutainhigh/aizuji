package org.gz.risk.intf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.gz.risk.entity.Violation;

/**
 * 
 * @Description:阿福接口数据爬取类
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月17日 	Created
 */
public interface AFuAtomService {

	/**
	 * 阿福借款情况
	 * @param userId
	 * @param name
	 * @param idNo
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException 
	 */
	public List<Violation> addQueryLoan(Long userId, String name, String idNo) throws IOException;
	
	/**
	 * 阿福黑名单
	 * @param userId
	 * @param name
	 * @param idNo
	 * @param mobile
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Violation> addQueryBlackList(Long userId, String name, String idNo, String mobile) throws IOException;
	
	/**
	 * 阿福致诚分
	 * @param name
	 * @param idNo
	 * @param userId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Violation> addQueryCreditScore(String name, String idNo, Long userId) throws IOException;
	
	/**
	 * 被查询情况
	 * @param name
	 * @param idNo
	 * @param userId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Violation> addGetQueriedHistory(String name, String idNo, Long userId) throws IOException;
	
}
