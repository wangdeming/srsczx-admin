package cn.ibdsr.web.modular.system.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.core.util.RegUtil;

/**
 * 角色传输bean
 *
 * @author lvyou
 * @Date 2017/5/5 22:40
 */
public class RoleDto extends BaseDTO {

    private Long id;
    @Verfication(name = "角色名称", notNull = true, maxlength = 8, regx = {RegUtil.ROLE_NAME, "1-8个汉字，且在系统中唯一"})
    private String name;
    @Verfication(name = "角色提示", notNull = true, maxlength = 50)
    private String tips;

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

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}

