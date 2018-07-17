package org.gz.cache.service.sign;

public interface SignDataCacheService {
	
	/**
	 * 缓存签约记录
	 * @param userId
	 * 			用户ID
	 * @param signSeviceId
	 * 			签约完成唯一ID
	 */
	public void cacheSignRecord(Long userId, String signServiceId);
	
	/**
	 * 移除签约记录
	 * @param userId
	 * 			用户ID
	 * @param signSeviceId
	 * 			签约完成唯一ID
	 */
	public void removeSignRecord(Long userId, String signServiceId);

	/**
	 * 获取用户对应的签约账户accountId
	 * @param userId
	 * 			用户ID
	 * @return
	 */
	public String getPersonAccountByUserId(Long userId);
	
	/**
	 * 设置用户对应的签约账户accountId
	 * @param userId
	 * 			用户ID
	 * @param accountId
	 * 			签约用户账户account
	 * @return
	 */
	public String setPersonAccountByUserId(Long userId, String accountId);
	
	/**
	 * 获取企业对应的签约账户accountId
	 * @param userId
	 * 			用户ID
	 * @return
	 */
	public String getOrganizeAccount();
	
	/**
	 * 设置企业对应的签约账户accountId
	 * @param userId
	 * 			用户ID
	 * @param accountId
	 * 			签约用户账户account
	 * @return
	 */
	public String setOrganizeAccount(String accountId);

	/**
	 * 获取企业印章数据
	 * @return
	 */
	public String getOrganizeTemplateSeal();
	
	/**
	 * 设置企业印章数据
	 * @param sealData
	 * 			企业印章数据
	 * @return
	 */
	public String setOrganizeTemplateSeal(String sealData);
	
	/**
	 * 获取用户对应的签约印章
	 * @param userId
	 * 			用户ID
	 * @return
	 */
	public String getPersonSealDataByUserId(Long userId);
	
	/**
	 * 设置用户对应的签约印章
	 * @param userId
	 * 			用户ID
	 * @param sealData
	 * 			签约用户印章数据
	 * @return
	 */
	public String setPersonSealDataByUserId(Long userId, String sealData);
}
