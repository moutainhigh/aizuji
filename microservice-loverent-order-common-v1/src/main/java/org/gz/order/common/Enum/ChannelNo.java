package org.gz.order.common.Enum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下单渠道
 * @author phd
 */
public enum ChannelNo {
	        
	IOS("ios","ios"),
	ANDROID("android","android"),
	H5("h5","h5");
	
	private String code;
    private String message;
    
    ChannelNo(String code, String message) {
        this.code = code;
        this.message = message;
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static ChannelNo getByCode(String code) {
        if (code != null) {
            for (ChannelNo objEnum : ChannelNo.values()) {
                if (objEnum.getCode().equals(code)) {
                    return objEnum;
                }
            }
        }
        return null;
    }
	
	public static List<Map<String, String>> all(){
		List<Map<String, String>> rlist=new ArrayList<>();
    	Map<String, String> rmap = null;
    	for(ChannelNo uite : values()){
    		rmap=new HashMap<String, String>();
    		rmap.put("code", uite.getCode());
    		rmap.put("message", uite.getMessage());
    		rlist.add(rmap);
    	}
    	return rlist;
    }
		
}
