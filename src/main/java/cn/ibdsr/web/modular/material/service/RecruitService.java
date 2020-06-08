package cn.ibdsr.web.modular.material.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.persistence.model.Recruit;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description 人才招聘Service
 * @Version V1.0
 * @CreateDate 2020/5/26 14:35
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
public interface RecruitService {
    JSONObject list(String condition, Integer status);

    SuccessDataTip add(Recruit recruit);

    Recruit get(Long recruitId);

    SuccessMesTip update(Recruit recruit);

    SuccessMesTip delete(Long recruitId);

    SuccessMesTip publish(Long recruitId, Integer status);
}
