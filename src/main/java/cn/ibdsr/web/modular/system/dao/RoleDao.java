package cn.ibdsr.web.modular.system.dao;

import cn.ibdsr.web.common.persistence.model.Role;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色相关的dao
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午8:43:52
 */
public interface RoleDao {

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRolesById(@Param("roleId") Long roleId);

    /**
     * 查询某个用户的所有角色
     * @param userId
     * @return
     */
    List<Role> listRolesByUserId(@Param("userId") Long userId);

    /**
     * 获取角色列表
     *
     * @param page      分页参数
     * @param keyword   搜索关键字
     * @return
     */
    List<Map<String,Object>> list(@Param("page") Page<Map<String,Object>> page,@Param("keyword") String keyword);

    /**
     * 批量添加角色权限
     *
     * @param roleId    角色id
     * @param menuIds   权限id数组
     */
    void setAuthorityBatch(@Param("roleId") Long roleId,@Param("menuIds") Long[] menuIds);
}
