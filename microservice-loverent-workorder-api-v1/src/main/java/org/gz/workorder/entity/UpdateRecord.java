package org.gz.workorder.entity;


/**
 * UpdateRecord 实体类
 * 
 * @author phd
 * @date 2018-01-17
 */
public class UpdateRecord{


    /**
     * id 
     */
    private Long id;
    /**
     * 操作人 
     */
    private String operator;
    /**
     * 类型 
     */
    private Integer type;

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String getOperator() {
        return this.operator;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }
    
}
