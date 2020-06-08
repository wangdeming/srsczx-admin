package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 招聘
 * </p>
 *
 * @author xjc
 * @since 2020-05-26
 */
public class Recruit extends Model<Recruit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String company;
    private String content;
    /**
     * 发布时间
     */
    @TableField("publish_datetime")
    private Date publishDatetime;
    @TableField("attachment_name")
    private String attachmentName;
    private String attachment;
    @TableField("show_datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date showDatetime;
    private Integer status;
    @TableField("created_user")
    private Long createdUser;
    @TableField("created_time")
    private Date createdTime;
    @TableField("modified_user")
    private Long modifiedUser;
    @TableField("modified_time")
    private Date modifiedTime;
    @TableField("is_deleted")
    private Integer isDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getShowDatetime() {
        return showDatetime;
    }

    public void setShowDatetime(Date showDatetime) {
        this.showDatetime = showDatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Recruit{" +
                "id=" + id +
                ", title=" + title +
                ", company=" + company +
                ", content=" + content +
                ", publishDatetime=" + publishDatetime +
                ", attachmentName=" + attachmentName +
                ", attachmentName=" + attachmentName +
                ", attachment=" + attachment +
                ", showDatetime=" + showDatetime +
                ", status=" + status +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
