package cn.ibdsr.web.modular.system.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.Tip;
import cn.ibdsr.core.cache.CacheKit;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.annotion.Permission;
import cn.ibdsr.web.common.constant.BussinessLogType;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.cache.Cache;
import cn.ibdsr.web.common.constant.dictmap.systemdict.SystemDict;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.RoleStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.core.log.LogObjectHolder;
import cn.ibdsr.web.modular.system.service.IRoleService;
import cn.ibdsr.web.modular.system.transfer.RoleDto;
import cn.ibdsr.web.modular.system.warpper.RoleWarpper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
 * 角色控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static String PREFIX = "/system/role";

    @Resource
    RoleMapper roleMapper;

    @Resource
    IRoleService roleService;

    /**
     * 跳转到角色列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/role.html";
    }

    /**
     * 获取角色列表
     *
     * @param keyword 角色名称
     * @return
     */
    @Permission
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String keyword) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> roleList = roleService.list(page, keyword);
        page.setRecords((List<Map<String, Object>>) new RoleWarpper(roleList).warp());
        return super.packForBT(page);
    }

    /**
     * 跳转到添加角色
     */
    @RequestMapping(value = "/role_add")
    public String roleAdd() {
        return PREFIX + "/role_add.html";
    }

    /**
     * 角色新增
     * @param role 角色对象
     * @param result
     * @return
     */
    @RequestMapping(value = "/add")
    @BussinessLog(name = "添加角色", key = "name", dict = SystemDict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip add(@Valid RoleDto role, BindingResult result) {
        if (role == null || result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Integer count = roleMapper.selectCount(new EntityWrapper<Role>().eq("name", role.getName()));
        if (count >= 1) {
            throw new BussinessException(BizExceptionEnum.ROLE_NAME_EXIST);
        }
        StaticCheck.check(role);

        Role updateRole = new Role();
        BeanUtils.copyProperties(role,updateRole);
        this.roleMapper.insert(updateRole);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改角色页面
     * @param roleId 角色id
     * @param model
     * @return
     */
    @Permission
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleMapper.selectById(roleId);
        model.addAttribute("role", role);
        LogObjectHolder.me().set(role);
        return PREFIX + "/role_edit.html";
    }

    /**
     * 角色修改
     *
     * @param role 角色对象
     * @param result
     * @return
     */
    @RequestMapping(value = "/edit")
    @BussinessLog(name = "修改角色", key = "name",logType = BussinessLogType.DETAILEDLOG,dict = SystemDict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip edit(@Valid RoleDto role, BindingResult result) {
        if (role == null || role.getId() == null || result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (roleMapper.selectById(role.getId()) == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        Integer count = roleMapper.selectCount(new EntityWrapper<Role>().eq("name", role.getName()));
        if (count >= 1) {
            throw new BussinessException(BizExceptionEnum.ROLE_NAME_EXIST);
        }
        StaticCheck.check(role);
        Role updateRole = new Role();
        BeanUtils.copyProperties(role,updateRole);
        roleMapper.updateById(updateRole);
        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 禁用角色
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/freeze")
    @BussinessLog(name = "禁用角色", key = "roleId", dict = SystemDict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能禁用超级管理员角色
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
            throw new BussinessException(BizExceptionEnum.ADMIN_CANT_FREEZE);
        }

        this.roleService.freeze(roleId);
        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 启用角色
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/open")
    @BussinessLog(name = "启用角色", key = "roleId", dict = SystemDict.RoleDict)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip open(@RequestParam Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        role.setStatus(RoleStatus.ENABLE.getCode());
        roleMapper.updateById(role);
        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到角色详情页面
     */
    @RequestMapping(value = "/detailHtml/{roleId}")
    public String detail(@PathVariable("roleId") Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleMapper.selectById(roleId);
        if (role == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        model.addAttribute("roleId", role.getId());
        model.addAttribute("roleName", role.getName());
        model.addAttribute("tips", role.getTips());
        model.addAttribute("status", role.getStatus());
        model.addAttribute("statusName", RoleStatus.valueOf(role.getStatus()));
        return PREFIX + "/role_detail.html";
    }

    /**
     * 获取拥有该角色的用户列表
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping(value = "/authorizedUsers")
    @ResponseBody
    public Object getAuthorizedUsers(@RequestParam(value = "roleId") Long roleId){
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return roleService.getAuthorizedUsersByRoleId(roleId);
    }

    /**
     * 获取未拥有该角色的用户列表
     *
     * @param roleId 角色ID
     * @return
     */
    @GetMapping(value = "/unauthorizedUsers")
    @ResponseBody
    public Object getUnauthorizedUsers(@RequestParam(value = "roleId") Long roleId){
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return roleService.getUnauthorizedUsersByRoleId(roleId);
    }

    /**
     * 跳转权限管理页面
     *
     * @param roleId 角色id
     * @param model
     * @return
     */
    @RequestMapping(value = "/role_menu_edit/{roleId}")
    public String roleMenuEdit(@PathVariable("roleId") Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", role.getName());
        return PREFIX + "/authority_mgr.html";
    }


    /**
     * 跳转用户授权页面
     *
     * @param roleId 角色id
     * @param model
     * @return
     */
    @RequestMapping(value = "/role_user_edit/{roleId}")
    public String roleUserEdit(@PathVariable("roleId") Long roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", role.getName());
        return PREFIX + "/authority_user.html";
    }

    /**
     * 权限管理
     */
    @PostMapping("/authorityMenu")
    @BussinessLog(name = "权限管理", key = "roleId", dict = SystemDict.RoleDict)
    @ResponseBody
    public Tip authorityMenu(@RequestParam("roleId") Long roleId, @RequestParam("menuIds") Long[] menuIds) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        roleService.setAuthority(roleId, menuIds);
        return SUCCESS_TIP;
    }

    /**
     * 授权用户
     */
    @PostMapping("/authorityUser")
    @BussinessLog(name = "授权用户", key = "roleId", dict = SystemDict.RoleDict)
    @ResponseBody
    public Tip authorityUser(@RequestParam("roleId") Long roleId, @RequestParam("userIds") Long[] userIds) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (Const.ADMIN_ROLE_ID.longValue() == roleId) {
            throw new BussinessException(BizExceptionEnum.ADMIN_CANT_ALLOT);
        }
        this.roleService.authorityUser(roleId, userIds);
        return SUCCESS_TIP;
    }


}
