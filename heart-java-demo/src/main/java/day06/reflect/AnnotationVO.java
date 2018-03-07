package day06.reflect;

import java.util.Date;

import day06.reflect.annotation.Column;
import day06.reflect.annotation.Id;
import day06.reflect.annotation.OrderBy;
import day06.reflect.annotation.Table;

@Table(name = "t_cm_info_inst")
public class AnnotationVO {

	/**
	 * 信息ID
	 */
	@Id
	@Column(name = "infoid", unique = true)
	@OrderBy("infoid desc")
	private String infoID;

	/**
	 * 信息模板
	 */
	@Column(name = "templateid", fuzzy = true)
	@OrderBy("templateid asc")
	private String templateID;

	/**
	 * 信息类型：上传型or编写型
	 */
	@Column(name = "infotype")
	private int infoType;

	/**
	 * 创建时间
	 */
	@Column(name = "createtime")
	private Date createTime;

	/**
	 * 是否消息提醒
	 */
	@Column(name = "rtx")
	private Integer rtx;

	/**
	 * @return Returns 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return Returns 信息ID
	 */
	public String getInfoID() {
		return infoID;
	}

	/**
	 * @param infoID
	 *            信息ID
	 */
	public void setInfoID(String infoID) {
		this.infoID = infoID;
	}

	/**
	 * @return Returns 信息类型：上传型or编写型
	 */
	public int getInfoType() {
		return infoType;
	}

	/**
	 * @param infoType
	 *            信息类型：上传型or编写型
	 */
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}

	/**
	 * @return Returns 信息模板
	 */
	public String getTemplateID() {
		return templateID;
	}

	/**
	 * @param templateID
	 *            信息模板
	 */
	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	/**
	 * @return 返回 rtx。
	 */
	public Integer getRtx() {
		return rtx;
	}

	/**
	 * @param rtx
	 *            要设置的 rtx。
	 */
	public void setRtx(Integer rtx) {
		this.rtx = rtx;
	}

}
