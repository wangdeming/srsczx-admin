package cn.ibdsr.web.modular.system.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.core.util.RegUtil;

/**
 * 用户传输bean
 * 
 * @author stylefeng
 * @Date 2017/5/5 22:40
 */
public class UserUpdateDto extends BaseDTO {

	private Long id;
	@Verfication(name = "用户姓名", notNull = true, regx = {RegUtil.NAME, "长度为2-5个中文汉字"})
	private String name;
	@Verfication(name = "电子邮箱", regx = {CheckUtil.EMAIL, "邮箱格式有误"})
	private String email;
	@Verfication(name = "联系电话", regx = {CheckUtil.MOBILE_PHONE, "联系电话格式有误"})
	private String phone;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
