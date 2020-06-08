package cn.ibdsr.web.modular.system.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.RoleStatus;
import cn.ibdsr.web.common.persistence.dao.UserMapper;
import cn.ibdsr.web.core.shiro.ShiroKit;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2017年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    @Resource
    private UserMapper userMapper;

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", RoleStatus.valueOf((Integer) map.get("status")));
        Long roleId = (Long) map.get("id");
        if (roleId.equals(Const.ADMIN_ROLE_ID.longValue())) {
            if (ShiroKit.getUser().getId().equals(Const.ADMIN_ID.longValue())) {
                map.put("editable", true);
            } else {
                map.put("editable", false);
            }
        } else {
            map.put("editable", true);
        }
        Object userName = map.get("userName");
        if (userName != null) {
            map.put("editable", false);
        }
    }

}
