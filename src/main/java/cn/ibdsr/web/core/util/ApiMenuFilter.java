package cn.ibdsr.web.core.util;

import cn.ibdsr.core.node.MenuNode;
import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.config.properties.WebProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口文档显示过滤
 *
 * @author fengshuonan
 * @date 2017-08-17 16:55
 */
public class ApiMenuFilter extends MenuNode {


    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
        WebProperties webProperties = SpringContextHolder.getBean(WebProperties.class);
        if (!webProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
