package cn.ibdsr.web.modular.system.service.impl;

import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.ManagerStatus;
import cn.ibdsr.web.common.constant.state.RoleStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RelationMapper;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.dao.RoleUserMapper;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.RoleUser;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.modular.system.dao.RoleDao;
import cn.ibdsr.web.modular.system.dao.UserMgrDao;
import cn.ibdsr.web.modular.system.service.IRoleService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleDao roleDao;
    @Resource
    RelationMapper relationMapper;
    @Resource
    private RoleUserMapper roleUserMapper;
    @Resource
    private UserMgrDao userMgrDao;

    /**
     * 查询角色列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Page<Map<String, Object>> page, String keyword) {
        return roleDao.list(page, keyword);
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void freeze(Long roleId) {
        Integer userCount = roleUserMapper.selectCount(new EntityWrapper<RoleUser>().eq("role_id", roleId));
        if (userCount >= 1) {
            throw new BussinessException(BizExceptionEnum.ROLE_BING_USER);
        }
        Role role = roleMapper.selectById(roleId);
        if (role == null) {
            throw new BussinessException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        role.setStatus(RoleStatus.DISABLE.getCode());
        roleMapper.updateById(role);
    }

    /**
     * 获取拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<JSONObject> getAuthorizedUsersByRoleId(Long roleId) {
        List<JSONObject> userList = userMgrDao.getAuthorizedUsersByRoleId(roleId);
        for (JSONObject user : userList) {
            Integer status = user.getInteger("status");
            if (ToolUtil.isNotEmpty(status)) {
                user.put("statusName", ManagerStatus.valueOf(status));
            }
        }
        return userList;
    }

    /**
     * 获取未拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<JSONObject> getUnauthorizedUsersByRoleId(Long roleId) {
        List<JSONObject> userList = userMgrDao.getUnauthorizedUsersByRoleId(roleId, ShiroKit.getUser().getId());
        for (JSONObject user : userList) {
            Integer status = user.getInteger("status");
            if (ToolUtil.isNotEmpty(status)) {
                user.put("statusName", ManagerStatus.valueOf(status));
            }
        }
        return userList;
    }

    /**
     * 配置角色权限
     *
     * @param roleId 角色id
     * @param menuIds    权限的id数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAuthority(Long roleId, Long[] menuIds) {
        if (roleId == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        // 删除该角色所有的权限
        this.roleDao.deleteRolesById(roleId);
        // 添加新的权限
        if (menuIds != null && menuIds.length > 0) {
            this.roleDao.setAuthorityBatch(roleId, menuIds);
        }
    }

    /**
     * 授权用户
     *
     * @param roleId    角色id
     * @param userIds   用户id数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorityUser(Long roleId, Long[] userIds) {
        if (roleId == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        // 删除该角色所有的用户
        roleUserMapper.delete(new EntityWrapper<RoleUser>().eq("role_id",roleId));
        // 添加新的用户
        if (userIds != null && userIds.length > 0) {
            roleUserMapper.authorityUserBatch(roleId, userIds);
        }
    }
}
