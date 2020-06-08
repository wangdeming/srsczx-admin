package cn.ibdsr.web.modular.system.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.core.shiro.ShiroKit;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class UserWarpper extends BaseControllerWarpper {

    public UserWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
        Long userId = (Long) map.get("id");
        if (userId.equals(ShiroKit.getUser().getId())) {
            map.put("editable", false);
        } else {
            map.put("editable", true);
        }
    }

}
