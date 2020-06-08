package cn.ibdsr.web.core.shiro.factory;

import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.ManagerStatus;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.User;
import cn.ibdsr.web.core.shiro.ShiroUser;
import cn.ibdsr.web.modular.system.dao.MenuDao;
import cn.ibdsr.web.modular.system.dao.RoleDao;
import cn.ibdsr.web.modular.system.dao.UserMgrDao;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private UserMgrDao userMgrDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleDao roleDao;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public User user(String account) {

        User user = userMgrDao.getByAccount(account);

        // 账号不存在
        if (null == user) {
            throw new CredentialsException();
        }
        // 账号被冻结
        if (user.getStatus() != ManagerStatus.OK.getCode()) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());            // 账号id
        shiroUser.setAccount(user.getAccount());// 账号
        shiroUser.setName(user.getName());        // 用户名称

        List<Long> roleList = new ArrayList<>(); // 角色id结合
        List<String> roleNameList = new ArrayList<>(); // 角色名称集合
        List<Integer> systemList = new ArrayList<>(); // 所授权的系统标识集合
        Map<Integer, Long> sysAndRoleMap = new HashMap<>(); // 系统和角色映射关系集合，一对一

        List<Role> roles = roleDao.listRolesByUserId(user.getId());
        for (Role role :
                roles) {
            roleList.add(role.getId());
            roleNameList.add(role.getName());
        }

        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        List<String> resUrls = menuDao.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Long roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        // 密码加盐处理
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
