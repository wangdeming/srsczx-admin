package cn.ibdsr.web.modular.system.dao;

import cn.ibdsr.core.node.MenuNode;
import cn.ibdsr.core.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 菜单相关的dao
 *
 * @author fengshuonan
 * @date 2017年2月12日 下午8:43:52
 */
public interface MenuDao {

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(@Param("keyword") String keyword, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取可配置的菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Long roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNode> getMenusByRoleIds(List<Long> roleIds);

    /**
     * 获取完整菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTree();
}
