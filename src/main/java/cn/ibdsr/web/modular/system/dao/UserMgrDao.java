package cn.ibdsr.web.modular.system.dao;

import cn.ibdsr.web.common.persistence.model.User;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 管理员的dao
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午8:43:52
 */
public interface UserMgrDao {

    /**
     * 查询除当前用户以外的用户列表
     *
     *
     * @param page
     * @param offset  分页偏移量
     * @param limit   分页参数
     * @param keyword 搜索关键字
     * @param id      当前用户id
     * @return
     */
    List<Map<String, Object>> list(@Param("page") Page<Map<String, Object>> page, @Param("keyword") String keyword, @Param("id") Long id);

    /**
     * 修改用户状态
     *
     * @param user
     * @date 2017年2月12日 下午8:42:31
     */
    int setStatus(@Param("userId") Long userId, @Param("status") int status);

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     * @date 2017年2月17日 下午11:07:46
     */
    User getByAccount(@Param("account") String account);

    /**
     * 查询用户详情
     *
     * @param userId 用户id
     * @return
     */
    JSONObject userDetail(@Param("userId") Long userId);

    /**
     * 获取拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @return
     */
    List<JSONObject> getAuthorizedUsersByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取未拥有该角色的用户列表
     *
     * @param roleId 角色id
     * @param userId 当前用户id
     * @return
     */
    List<JSONObject> getUnauthorizedUsersByRoleId(@Param("roleId") Long roleId,@Param("userId") Long userId);
}
