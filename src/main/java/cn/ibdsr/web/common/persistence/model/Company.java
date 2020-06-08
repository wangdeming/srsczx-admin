package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 入孵申请信息表
 * </p>
 *
 * @author lvyou
 * @since 2019-08-21
 */
public class Company extends Model<Company> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 企业名称
     */
	private String name;
    /**
     * 企业创立时间
     */
	@TableField("set_time")
	private Date setTime;
    /**
     * 企业人数
     */
	@TableField("emp_num")
	private Integer empNum;
    /**
     * 主营业务
     */
	@TableField("core_business")
	private String coreBusiness;
    /**
     * 联系人姓名
     */
	@TableField("contact_name")
	private String contactName;
    /**
     * 联系电话
     */
	@TableField("contact_phone")
	private String contactPhone;
    /**
     * 是否已读（0:未读;1:已读;）
     */
	@TableField("is_read")
	private Integer isRead;
    /**
     * 电子邮箱
     */
	private String email;
	/**
	 * 创建人
	 */
	private String remark;
    /**
     * 创建人
     */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Date createdTime;
    /**
     * 修改人
     */
	@TableField("modified_user")
	private Long modifiedUser;
    /**
     * 修改时间
     */
	@TableField("modified_time")
	private Date modifiedTime;
    /**
     * 是否删除（0:未删除;1:已删除;）
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public Integer getEmpNum() {
		return empNum;
	}

	public void setEmpNum(Integer empNum) {
		this.empNum = empNum;
	}

	public String getCoreBusiness() {
		return coreBusiness;
	}

	public void setCoreBusiness(String coreBusiness) {
		this.coreBusiness = coreBusiness;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Company{" +
			"id=" + id +
			", name=" + name +
			", setTime=" + setTime +
			", empNum=" + empNum +
			", coreBusiness=" + coreBusiness +
			", contactName=" + contactName +
			", contactPhone=" + contactPhone +
			", isRead=" + isRead +
			", email=" + email +
			", remark=" + remark +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
