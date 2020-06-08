package cn.ibdsr.web.modular.material.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description 新闻资讯DTO
 * @Version V1.0
 * @CreateDate 2019/5/13 13:36
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/13      Wujiayun            类说明
 */
public class NewsDTO extends BaseDTO {
    /**
     * 标题
     */
    @Verfication(name = "标题", notNull = true, minlength = 2, maxlength = 80)
    private String title;
    /**
     * 栏目类型(1:新闻动态;2:媒体报道;3:合作交流;)
     */
    @Verfication(name = "栏目类型", notNull = true)
    private Integer newsType;
    @Verfication(name = "图片新闻", notNull = true)
    private Integer imageNews;
    /**
     * 正文内容
     */
    @Verfication(name = "正文内容", notNull = true)
    private String mainContent;
    /**
     * 附件文件
     */
    private String extraFile;
    /**
     * 展示时间
     */
    @Verfication(name = "展示时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date showTime;
    /**
     * 封面图片
     */
    @Verfication(name = "封面图片", notNull = true)
    private String coverImage;
    /**
     * 是否发布（0:未发布;1:已发布;）
     */
    private Integer isPublish;
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
    /**
     * 附件文件名称
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public Integer getImageNews() {
        return imageNews;
    }

    public void setImageNews(Integer imageNews) {
        this.imageNews = imageNews;
    }

    public String getMainContent() {
        return mainContent;
    }

    public void setMainContent(String mainContent) {
        this.mainContent = mainContent;
    }

    public String getExtraFile() {
        return extraFile;
    }

    public void setExtraFile(String extraFile) {
        this.extraFile = extraFile;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
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
}
