package cn.ibdsr.web.modular.business.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.core.util.RegUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description 入孵申请DTO
 * @Version V1.0
 * @CreateDate 2019/5/13 13:36
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/13      lvyou                入孵申请DTO
 */
public class CompanyDTO extends BaseDTO {


    /**
     * 主键id
     */
	private Long id;
    /**
	 * 企业名称
	 */
	@Verfication(name = "企业名称", notNull = true, maxlength = 20, minlength = 2, regx = {RegUtil.COMPANY_NAME, "格式有误"})
	private String name;
	/**
	 * 企业创立时间
	 */
	@Verfication(name = "成立时间")
	@DateTimeFormat(pattern = "yyyy")
	private Date setTime;
	/**
	 * 企业人数
	 */
	private Integer empNum;
	/**
	 * 主营业务
	 */
	@Verfication(name = "主营业务", notNull = true, maxlength = 200)
	private String coreBusiness;
	/**
	 * 联系人姓名
	 */
	@Verfication(name = "联系人姓名", notNull = true, regx = {RegUtil.CONCAT_NAME, "格式有误"})
	private String contactName;
    /**
     * 联系电话
     */
	@Verfication(name = "联系电话", notNull = true, maxlength = 20, minlength = 2, regx = {CheckUtil.MOBILE_PHONE, "格式有误"})
	private String contactPhone;
    /**
     * 是否已读（0:未读;1:已读;）
     */
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
	private Long createdUser;
    /**
     * 创建时间
     */
	private Date createdTime;
    /**
     * 修改人
     */
	private Long modifiedUser;
	/**
	 * 修改时间
	 */
	private Date modifiedTime;
	/**
	 * 是否删除（0:未删除;1:已删除;）
	 */
	private Integer isDeleted;


	@Override
	public Long getId() {
		return id;
	}

	@Override
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

}
