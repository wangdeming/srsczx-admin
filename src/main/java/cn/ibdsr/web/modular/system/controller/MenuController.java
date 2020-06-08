package cn.ibdsr.web.modular.system.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.Tip;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.node.ZTreeNode;
import cn.ibdsr.core.support.BeanKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.annotion.Permission;
import cn.ibdsr.web.common.constant.BussinessLogType;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.dictmap.systemdict.SystemDict;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.MenuMapper;
import cn.ibdsr.web.common.persistence.model.Menu;
import cn.ibdsr.web.core.log.LogObjectHolder;
import cn.ibdsr.web.modular.system.dao.MenuDao;
import cn.ibdsr.web.modular.system.service.IMenuService;
import cn.ibdsr.web.modular.system.transfer.MenuDTO;
import cn.ibdsr.web.modular.system.warpper.MenuWarpper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static String PREFIX = "/system/menu/";

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;

    @Resource
    IMenuService menuService;

    @Value(value = "${userinfo.menu.id}")
    private String userInfoMenuId;

    /**
     * 跳转到菜单列表列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "menu.html";
    }

    /**
     * 获取菜单列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String keyword) {
        List<Map<String, Object>> menus = this.menuDao.selectMenus(keyword, null);
        return super.warpObject(new MenuWarpper(menus));
    }

    /**
     * 跳转到菜单页面
     */
    @RequestMapping(value = "/menu_add")
    public String menuAdd() {
        return PREFIX + "menu_add.html";
    }

    /**
     * 新增菜单
     */
    @Permission(Const.ADMIN_NAME)
    @PostMapping(value = "/add")
    @BussinessLog(name = "菜单新增", key = "name", dict = SystemDict.MenuDict)
    @ResponseBody
    public Tip add(MenuDTO menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        menuService.addMenu(menu);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到菜单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/menu_edit/{menuId}")
    public String menuEdit(@PathVariable Long menuId, Model model) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Menu menu = this.menuMapper.selectById(menuId);

        //获取父级菜单的id
        Menu temp = new Menu();
        temp.setCode(menu.getPcode());
        Menu pMenu = this.menuMapper.selectOne(temp);

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanKit.beanToMap(menu);
        menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return PREFIX + "menu_edit.html";
    }

    /**
     * 修改菜单
     */
    @Permission(Const.ADMIN_NAME)
    @PostMapping(value = "/edit")
    @BussinessLog(name = "修改菜单", key = "name",logType = BussinessLogType.DETAILEDLOG,dict = SystemDict.MenuDict)
    @ResponseBody
    public Tip edit(@Valid MenuDTO menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        StaticCheck.check(menu);
        menuService.editMenu(menu);
        return SUCCESS_TIP;
    }

    /**
     * 删除菜单
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/remove")
    @BussinessLog(name = "删除菜单", key = "menuId", dict = SystemDict.DeleteDict)
    @ResponseBody
    public Tip remove(@RequestParam Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));

        this.menuService.delMenuContainSubMenus(menuId);
        return SUCCESS_TIP;
    }

    /**
     * 查询角色拥有的权限列表
     *
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public Object menuTreeListByRoleId(@PathVariable Long roleId) {
        if (roleId == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        List<Long> menuIds = this.menuDao.getMenuIdsByRoleId(roleId);
        List<ZTreeNode> roleTreeList;
        if (ToolUtil.isEmpty(menuIds)) {
            roleTreeList = this.menuDao.menuTreeList();
        } else {
            roleTreeList = this.menuDao.menuTreeListByMenuIds(menuIds);
        }
        JSONArray jObjRes = JSON.parseArray(JSONObject.toJSONString(roleTreeList));
        for (int i = 0; i < jObjRes.size(); i++) {
            JSONObject menu = jObjRes.getJSONObject(i);
            long userInfoId = Long.parseLong(userInfoMenuId);
            if (menu.getLong("id").equals(userInfoId)) {
                menu.put("editable", false);
            } else {
                menu.put("editable", true);
            }
        }
        return jObjRes;
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        List<ZTreeNode> roleTreeList = this.menuDao.menuTree();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

}
