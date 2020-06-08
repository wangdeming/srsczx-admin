package cn.ibdsr.web.modular.system.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class NoticeWrapper extends BaseControllerWarpper {

    public NoticeWrapper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        Long creater = (Long) map.get("creater");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }

}
