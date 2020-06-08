package cn.ibdsr.web.modular.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 角色相关业务
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:11:57
 */
public interface IRoleService {

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param menuIds    权限的id
     * @date 2017年2月13日 下午8:26:53
     */
    void setAuthority(Long roleId, Long[] menuIds);

    /**
     * 查询角色列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @return
     */
    List<Map<String,Object>> list(Page<Map<String,Object>> page, String keyword);

    /**
     * 禁用角色
     *
     * @param roleId 角色id
     */
    void freeze(Long roleId);

    /**
     * 获取拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @return
     */
    List<JSONObject> getAuthorizedUsersByRoleId(Long roleId);

    /**
     * 授权用户
     *
     * @param roleId    角色id
     * @param userIds   用户id数组
     */
    void authorityUser(Long roleId, Long[] userIds);

    /**
     * 获取未拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @return
     */
    List<JSONObject> getUnauthorizedUsersByRoleId(Long roleId);
}
