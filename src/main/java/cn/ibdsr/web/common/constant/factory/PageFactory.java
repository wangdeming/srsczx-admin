package cn.ibdsr.web.common.constant.factory;

import cn.ibdsr.core.support.HttpKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.Order;
import com.baomidou.mybatisplus.plugins.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        //每页多少条数据
        int limit = Integer.parseInt(request.getParameter("limit"));
        //每页的偏移量(本页当前有多少条)
        int offset = Integer.parseInt(request.getParameter("offset"));
        //排序字段名称
        String sort = request.getParameter("sort");
        //asc或desc(升序或降序)
        String order = request.getParameter("order");
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            page.setAsc(Order.ASC.getDes().equals(order));
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            page.setAsc(Order.ASC.getDes().equals(order));
            return page;
        }
    }
}
