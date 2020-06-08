package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 轮播图表
 * </p>
 *
 * @author lvyou
 * @since 2019-08-21
 */
public class Banner extends Model<Banner> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图片路径
	 */
	private String path;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		return "Banner{" +
				"id=" + id +
				", sort=" + sort +
				", path=" + path +
				", createdUser=" + createdUser +
				", createdTime=" + createdTime +
				", modifiedUser=" + modifiedUser +
				", modifiedTime=" + modifiedTime +
				", isDeleted=" + isDeleted +
				"}";
	}
}
