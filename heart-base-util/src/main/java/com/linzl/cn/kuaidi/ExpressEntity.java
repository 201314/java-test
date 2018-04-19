package com.linzl.cn.kuaidi;
import java.util.List;

/**
 * 快递单信息
 * 
 * @author liny
 *
 */
public class ExpressEntity {

	/**
	 * 返回信息
	 */
    private String message;
    
    /**
     * 单号
     */
    private String nu;
    
    /**
     * 
     */
    private String ischeck;
    
    /**
     * 
     */
    private String condition;
    
    /**
     * 快递公司编码
     */
    private String com;
    
    /**
     * 快递公司
     */
    private String courierCompany;
    
    /**
     * 快递状态,200：正常,201:单号不存在或者已经过期,400:参数错误,001还没有寄样，002，根据taskId和taskNumber找不到邀请记录
     */
    private String status;
    
    /**
     * 
     */
    private String state;
    
    /**
     * 返回详细信息
     */
    private List<DataEntity> data;
    
    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setNu(String nu) {
         this.nu = nu;
     }
     public String getNu() {
         return nu;
     }

    public void setIscheck(String ischeck) {
         this.ischeck = ischeck;
     }
     public String getIscheck() {
         return ischeck;
     }

    public void setCondition(String condition) {
         this.condition = condition;
     }
     public String getCondition() {
         return condition;
     }

    public void setCom(String com) {
         this.com = com;
     }
     public String getCom() {
         return com;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setState(String state) {
         this.state = state;
     }
     public String getState() {
         return state;
     }

    public void setData(List<DataEntity> data) {
         this.data = data;
     }
     public List<DataEntity> getData() {
         return data;
     }
	public String getCourierCompany() {
		return courierCompany;
	}
	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}
     
}