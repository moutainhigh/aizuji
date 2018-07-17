package org.gz.workorder.entity;


/**
 * SearchRecord 实体类
 * 
 * @author phd
 * @date 2018-01-17
 */
public class SearchRecord{


    /**
     * id 
     */
    private Long id;
    /**
     * 操作人 
     */
    private String operator;
    /**
     * 内容 
     */
    private String content;

    
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
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }
    
}
