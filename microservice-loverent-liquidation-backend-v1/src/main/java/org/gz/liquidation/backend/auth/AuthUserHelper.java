package org.gz.liquidation.backend.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 访问授权用户获取帮助类
 * @author phd
 */
@Component
public class AuthUserHelper {
    private static Map<String, AuthUser> userMap;

    @Value("${userNames}")
    private String                     userNames;

    @Value("${userPassWords}")
    private String                     userPassWords;

    @Value("${userIds}")
    private String                     userIds;

    /**
     * 校验用户名密码是否正确
     */
    public AuthUser getUser(String userName, String passWord) {
        if (AuthUserHelper.userMap == null) {
            initUserMap();
        }
        
        AuthUser resultUser = null ;
        if (AuthUserHelper.userMap.containsKey(userName)) {
            AuthUser user = AuthUserHelper.userMap.get(userName);
            if (passWord.equals(user.getPassWord())) {
                resultUser = new AuthUser();
                resultUser.setId(user.getId());
                resultUser.setUserName(userName);
            }
        }
        return resultUser;
    }

    /**
     * 初始化配置文件中的用户到静态map
     */
    private void initUserMap() {
        String[] userNameArr = userNames.split(",");
        String[] passWordArr = userPassWords.split(",");
        String[] userIdArr = userIds.split(",");
        AuthUserHelper.userMap = new HashMap<>();
        AuthUser user = null;
        for (int i = 0; i < userNameArr.length; i++) {
            user = new AuthUser();
            user.setId(Integer.valueOf(userIdArr[i]));
            user.setUserName(userNameArr[i]);
            user.setPassWord(passWordArr[i]);
            AuthUserHelper.userMap.put(userNameArr[i], user);
        }
    }

}
