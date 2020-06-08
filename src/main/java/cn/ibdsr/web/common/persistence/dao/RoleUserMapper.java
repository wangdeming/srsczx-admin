package cn.ibdsr.web.common.persistence.dao;

import cn.ibdsr.web.common.persistence.model.RoleUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 角色用户关联表 Mapper 接口
 * </p>
 *
 * @author taoll
 * @since 2019-02-21
 */
public interface RoleUserMapper extends BaseMapper<RoleUser> {

    /**
     * 批量授权用户
     *
     * @param roleId    角色id
     * @param userIds   用户id数组
     */
    void authorityUserBatch(@Param("roleId") Long roleId,@Param("userIds") Long[] userIds);
}