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
 * 政策
 * </p>
 *
 * @author xjc
 * @since 2020-05-26
 */
public class Policy extends Model<Policy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 标题
     */
    private String title;
    /**
     * 来源
     */
    private String source;
    /**
     * 发布时间
     */
    @TableField("publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    @TableField("attachment_name")
    private String attachmentName;
    /**
     * 附件
     */
    private String attachment;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
        return "Policy{" +
                "id=" + id +
                ", level=" + level +
                ", title=" + title +
                ", source=" + source +
                ", publishDate=" + publishDate +
                ", attachmentName=" + attachmentName +
                ", attachment=" + attachment +
                ", status=" + status +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
