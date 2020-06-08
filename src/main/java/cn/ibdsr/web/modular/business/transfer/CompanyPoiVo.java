package cn.ibdsr.web.modular.business.transfer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * @Description 入孵记录导出vo类
 * @Version V1.0
 * @CreateDate 2019/8/19 16:44
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      lvyou                入孵记录导出vo类
 */
@ExcelTarget("companyVo")
public class CompanyPoiVo {

    @Excel(name = "序号")
    private Long num;
    @Excel(name = "企业名称")
    private String name;
    @Excel(name = "成立时间")
    private String setTime;
    // @Excel(name = "企业人数")
    // private Integer empNum;
    @Excel(name = "主营业务")
    private String coreBusiness;
    @Excel(name = "联系人姓名")
    private String contactName;
    @Excel(name = "联系电话")
    private String contactPhone;
    // @Excel(name = "电子邮箱")
    // private String email;
    // @Excel(name = "备注信息")
    // private String remark;
    @Excel(name = "投递时间")
    private String createdTime;
    @Excel(name = "状态", replace = {"已读_1", "未读_0"})
    private String isRead;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    // public Integer getEmpNum() {
    //     return empNum;
    // }
    //
    // public void setEmpNum(Integer empNum) {
    //     this.empNum = empNum;
    // }

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

    // public String getEmail() {
    //     return email;
    // }
    //
    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getRemark() {
    //     return remark;
    // }
    //
    // public void setRemark(String remark) {
    //     this.remark = remark;
    // }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
