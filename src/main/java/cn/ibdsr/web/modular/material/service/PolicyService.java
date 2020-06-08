package cn.ibdsr.web.modular.material.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.persistence.model.Policy;
import cn.ibdsr.web.common.persistence.model.PolicyContent;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description 招商政策Service
 * @Version V1.0
 * @CreateDate 2020/5/26 14:34
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
public interface PolicyService {
    JSONObject list(String condition, Integer level, Integer status);

    SuccessDataTip add(Policy policy, PolicyContent policyContent);

    Policy get(Long policyId);

    PolicyContent getContent(Long policyId);

    SuccessMesTip update(Policy policy, PolicyContent policyContent);

    SuccessMesTip delete(Long policyId);

    SuccessMesTip publish(Long policyId, Integer status);
}
