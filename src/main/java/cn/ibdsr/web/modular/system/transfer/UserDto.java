package cn.ibdsr.web.modular.system.transfer;

import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.core.util.RegUtil;

import java.util.Date;

/**
 * 用户传输bean
 * 
 * @author stylefeng
 * @Date 2017/5/5 22:40
 */
public class UserDto extends UserUpdateDto {

	@Verfication(name = "登录账号", notNull = true, maxlength = 20, regx = {RegUtil.ACCOUNT, "长度为6-30字符，允许包含字母、数字、符号或任意组合，不包含空格"})
	private String account;
	@Verfication(name = "登录密码", regx = {RegUtil.PASSWORD, "长度为6-30字符，允许包含字母、数字、符号或任意组合，不包含空格"})
	private String password;
	private String salt;
	private Integer status;
	private Date createtime;
	private Integer version;
	private String avatar;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
