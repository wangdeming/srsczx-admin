package cn.ibdsr.web.modular.system.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.Tip;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.annotion.Permission;
import cn.ibdsr.web.common.constant.BussinessLogType;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.dictmap.systemdict.SystemDict;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.ManagerStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.dao.UserMapper;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.User;
import cn.ibdsr.web.core.log.LogObjectHolder;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.core.util.RegUtil;
import cn.ibdsr.web.modular.system.dao.UserMgrDao;
import cn.ibdsr.web.modular.system.service.IUserService;
import cn.ibdsr.web.modular.system.transfer.UserDto;
import cn.ibdsr.web.modular.system.transfer.UserUpdateDto;
import cn.ibdsr.web.modular.system.warpper.UserWarpper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/system/user/";

    @Resource
    private UserMgrDao managerDao;
    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserService userService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到编辑管理员页面
     * @param userId 用户id
     * @param model
     * @return
     */
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable("userId") Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        model.addAttribute("user", user);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转个人中心
     * @param model
     * @return
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Long userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        user.setAvatar(FdfsFileUtil.PREFIX_IMAGE_URL + user.getAvatar());
        model.addAttribute("user", user);
        if (userId.equals(Const.ADMIN_ID.longValue())) {
            model.addAttribute("roleId", Const.ADMIN_ROLE_ID);
        } else {
            Role role = new Role();
            role.setName(user.getAccount());
            role = roleMapper.selectOne(role);
            model.addAttribute("roleId", role.getId());
        }
        return PREFIX + "user_view.html";
    }

    /**
     * 个人中心跳转用户修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/user_info/edit/{userId}")
    public String userInfoEdit(@PathVariable("userId") Long userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        model.addAttribute("user", user);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view_edit.html";
    }

    /**
     * 跳转至用户详情页面
     * @param userId 用户id
     * @param model
     * @return
     */
    @RequestMapping(path = "/user_details/{userId}")
    public String detail(@PathVariable("userId") Long userId, Model model) {
        JSONObject userInfo = managerDao.userDetail(userId);
        if (userInfo == null) {
            throw new BussinessException(BizExceptionEnum.NO_THIS_USER);
        }
        userInfo.put("statusName", ConstantFactory.me().getStatusName(userInfo.getInteger("status")));
        model.addAttribute("user", userInfo);
        return PREFIX + "user_details.html";
    }

    /**
     * 获取当前登录用户名称
     *
     * @return
     */
    @RequestMapping(path = "/userSelf")
    @ResponseBody
    public Object userSelf() {
        return new SuccessDataTip(ShiroKit.getUser().getName());
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param rePwd  密码确认
     * @return
     */
    @PostMapping(value = "/changePwd")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        if (!RegUtil.checkReg(RegUtil.PASSWORD, newPwd)) {
            throw new BussinessException(BizExceptionEnum.ERROR_PASSWORD_FORMAT);
        }
        Long userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 修改当前用户的头像
     *
     * @param avatarUrl 头像URL
     * @return
     */
    @PostMapping(value = "/changeAvatar")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Object changeAvatar(@RequestParam String avatarUrl) {
        if (StringUtils.isEmpty(avatarUrl)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Long userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        avatarUrl = avatarUrl.replace(FdfsFileUtil.PREFIX_IMAGE_URL, "");
        user.setAvatar(avatarUrl);
        user.updateById();
        return SUCCESS_TIP;
    }

    /**
     * 查询除当前用户以外的用户列表
     *
     * @param keyword 查询关键字
     * @return
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String keyword) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> userList = userService.list(page, keyword, ShiroKit.getUser().getId());
        page.setRecords((List<Map<String, Object>>) new UserWarpper(userList).warp());
        return super.packForBT(page);
    }

    /**
     * 添加管理员
     * @param user 用户对象
     * @param result
     * @return
     */
    @PostMapping("/add")
    @BussinessLog(name = "添加管理员", key = "account", dict = SystemDict.UserDict)
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        StaticCheck.check(user);
        userService.addUser(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @PostMapping(value = "/edit")
    @BussinessLog(name = "修改管理员", logType = BussinessLogType.DETAILEDLOG,dict = SystemDict.UserDict)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip edit(@Valid UserUpdateDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (user.getId() == null) {
            throw new BussinessException(BizExceptionEnum.NULL_USER_ID);
        }
        StaticCheck.check(user);
        User flag = userMapper.selectById(user.getId());
        if (flag == null) {
            throw new BussinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }
        User updateUser = new User();
        BeanUtils.copyProperties(user,updateUser);
        this.userMapper.updateById(updateUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(name = "删除管理员", key = "userId", dict = SystemDict.UserDict)
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        this.managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 重置管理员的密码
     */
    @PostMapping("/reset")
    @BussinessLog(name = "重置管理员密码", key = "userId", dict = SystemDict.UserDict)
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip reset(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userMapper.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/freeze")
    @BussinessLog(name = "冻结用户", key = "userId", dict = SystemDict.UserDict)
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip freeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (userId.equals(ShiroKit.getUser().getId())) { // 不能冻结自己
            throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ME);
        }
        if (Const.ADMIN_ID.equals(userId) ) { // 不能冻结admin
            throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        this.managerDao.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     * @param userId 用户id
     * @return
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(name = "解除冻结用户", key = "userId", dict = SystemDict.UserDict)
    @Permission
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Tip unfreeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (userId.equals(ShiroKit.getUser().getId())) { // 不能解冻自己
            throw new BussinessException(BizExceptionEnum.CANT_UNFREEZE_ME);
        }
        this.managerDao.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 跳转到用户授权界面
     */
    @RequestMapping("/user_authority")
    public String userAuth(@RequestParam(value = "userId") Long userId, Model model) {
        if (userId == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BussinessException(BizExceptionEnum.USER_NOT_EXISTED);
        }
        model.addAttribute("id", user.getId());
        model.addAttribute("account", user.getAccount());
        model.addAttribute("name", user.getName());
        if (userId.equals(Const.ADMIN_ID.longValue())) {
            model.addAttribute("roleId", Const.ADMIN_ROLE_ID);
        } else {
            Role role = new Role();
            role.setName(user.getAccount());
            role = roleMapper.selectOne(role);
            model.addAttribute("roleId", role.getId());
        }
        return PREFIX + "user_authority.html";
    }
}
