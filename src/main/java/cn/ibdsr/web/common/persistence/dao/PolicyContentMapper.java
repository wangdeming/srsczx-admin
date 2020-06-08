package cn.ibdsr.web.common.persistence.dao;

import cn.ibdsr.web.common.persistence.model.PolicyContent;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 政策内容 Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2020-05-26
 */
public interface PolicyContentMapper extends BaseMapper<PolicyContent> {
    Integer insertA(PolicyContent policyContent);
}