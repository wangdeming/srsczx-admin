package cn.ibdsr.web.modular.system.service.impl;

import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.ManagerStatus;
import cn.ibdsr.web.common.constant.state.RoleStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RelationMapper;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.dao.RoleUserMapper;
import cn.ibdsr.web.common.persistence.dao.UserMapper;
import cn.ibdsr.web.common.persistence.model.Relation;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.RoleUser;
import cn.ibdsr.web.common.persistence.model.User;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.modular.system.dao.UserMgrDao;
import cn.ibdsr.web.modular.system.factory.UserFactory;
import cn.ibdsr.web.modular.system.service.IUserService;
import cn.ibdsr.web.modular.system.transfer.UserDto;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户业务层
 *
 * @author lvyou
 * @Date 2017年1月11日 下午1:08:17
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMgrDao userDao;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleUserMapper roleUserMapper;
    @Value(value = "${userinfo.menu.id}")
    private String userInfoMenuId;
    @Resource
    private RelationMapper relationMapper;

    /**
     * 查询除当前用户以外的用户列表
     *
     * @param page    分页参数
     * @param keyword 搜索关键字
     * @param id      当前用户id
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Page<Map<String, Object>> page, String keyword, Long id) {
        List<Map<String, Object>> userList = userDao.list(page, keyword, id);
        return userList;
    }

    /**
     * 添加用户账号
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDto user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(Const.DEFAULT_PWD);
        }
        // 判断账号是否重复
        {
            User theUser = userDao.getByAccount(user.getAccount());
            if (theUser != null) {
                throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
            }
        }

        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        User addUser = UserFactory.createUser(user);
        this.userMapper.insert(addUser);

        //创建用户专属角色
        Role role = new Role();
        role.setName(addUser.getAccount());
        role.setTips(addUser.getName());
        role.setStatus(RoleStatus.ENABLE.getCode());
        roleMapper.insert(role);

        //绑定角色用户关联关系
        RoleUser roleUserRel = new RoleUser();
        roleUserRel.setRoleId(role.getId());
        roleUserRel.setUserId(addUser.getId());
        roleUserMapper.insert(roleUserRel);

        //角色个人中心赋权
        Relation relation = new Relation();
        relation.setMenuid(Long.parseLong(userInfoMenuId));
        relation.setRoleid(role.getId());
        relationMapper.insert(relation);
    }
}
