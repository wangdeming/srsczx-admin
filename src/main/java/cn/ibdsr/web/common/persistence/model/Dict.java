package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public class Dict extends Model<Dict> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 排序
     */
	private Integer num;
    /**
     * 父级字典
     */
	private Long pid;
    /**
     * 名称
     */
	private String name;
    /**
     * 提示
     */
	private String tips;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Dict{" +
			"id=" + id +
			", num=" + num +
			", pid=" + pid +
			", name=" + name +
			", tips=" + tips +
			"}";
	}
}
