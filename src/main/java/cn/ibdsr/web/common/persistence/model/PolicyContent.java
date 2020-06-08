package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 政策内容
 * </p>
 *
 * @author xjc
 * @since 2020-05-26
 */
@TableName("policy_content")
public class PolicyContent extends Model<PolicyContent> {

	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 政策内容
	 */
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PolicyContent{" +
				"id=" + id +
				", content=" + content +
				"}";
	}
}
