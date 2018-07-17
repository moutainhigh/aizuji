package org.gz.risk.intf;

import java.util.List;

import org.gz.risk.entity.Violation;

/**
 * @Description:百荣接口数据爬取接口
 * Author	Version		Date		Changes
 * zhangguoliang 	1.0  		2017年7月17日 	Created
 */
public interface BaiRongAtomService {
    
    /**
     * @Description: 身份二要素
     * @param userId
     * @param idCard
     * @param name
     * @return
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> idCard(Long userId, String idCard, String name) throws Exception;
    
    /**
     * @Description: 百荣手机三要素
     * @param userId
     * @param idCard
     * @param cell
     * @param name
     * @return
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> cellPhone(Long userId, String idCard, String cell, String name) throws Exception;
    
    /**
     * @Description: 银行卡四要数 
     * @param userId
     * @param idCard
     * @param cell
     * @param name
     * @param bankId
     * @return
     * @throws Exception
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> bankFour(Long userId, String idCard, String cell, String name, String bankId) throws Exception;
    
    /**
     * @Description: 百荣特殊名单 
     * @param userId
     * @param id
     * @param cell
     * @param name
     * @param kinshipTel
     * @param spouseTel
     * @param emergencyContactPhone
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> addViolation(Long userId, String id, String cell, String name, String kinshipTel, String spouseTel, String emergencyContactPhone);

    /**
     * @Description: 百荣借款设备
     * @param userId
     * @param id
     * @param cell
     * @param name
     * @param af_swift_number
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> addLoanEquipment(Long userId, String id, String cell, String name, String af_swift_number);
    
    /**
     * @Description: 百荣注册设备
     * @param userId
     * @param cell
     * @param af_swift_number
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> addRegisterEquipment(Long userId, String cell, String af_swift_number);
    
    /**
     * @Description: 百荣登录设备
     * @param userId
     * @param af_swift_number
     * @return
     * @throws
     * createBy:zhangguoliang            
     * createDate:2017年7月17日
     */
    public List<Violation> addSignEquipment(Long userId, String af_swift_number);
}
