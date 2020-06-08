package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 新闻资讯表
 * </p>
 *
 * @author lvyou
 * @since 2019-08-21
 */
@TableName("news_info")
public class NewsInfo extends Model<NewsInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 栏目类型(1:新闻动态;2:媒体报道;3:合作交流;)
	 */
	@TableField("news_type")
	private Integer newsType;
	/**
	 * 是否图片新闻（0:不是;1:是;）
	 */
	@TableField("image_news")
	private Integer imageNews;
	/**
	 * 正文文件
	 */
	@TableField("main_content")
	private String mainContent;
	/**
	 * 附件文件
	 */
	@TableField("extra_file")
	private String extraFile;
	/**
	 * 附件文件名称
	 */
	@TableField("file_name")
	private String fileName;
    /**
     * 展示时间
     */
	@TableField("show_time")
	private Date showTime;
    /**
     * 封面图片
     */
	@TableField("cover_image")
	private String coverImage;
    /**
     * 是否发布（0:未发布;1:已发布;）
     */
	@TableField("is_publish")
	private Integer isPublish;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "NewsInfo{" +
				"id=" + id +
				", title=" + title +
				", newsType=" + newsType +
				", imageNews=" + imageNews +
				", mainContent=" + mainContent +
				", extraFile=" + extraFile +
				", fileName=" + fileName +
				", showTime=" + showTime +
				", coverImage=" + coverImage +
				", isPublish=" + isPublish +
				", createdUser=" + createdUser +
				", createdTime=" + createdTime +
				", modifiedUser=" + modifiedUser +
				", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
