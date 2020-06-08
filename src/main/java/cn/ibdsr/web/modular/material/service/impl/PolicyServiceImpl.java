package cn.ibdsr.web.modular.material.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.PolicyLevel;
import cn.ibdsr.web.common.constant.state.PublishStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.PolicyContentMapper;
import cn.ibdsr.web.common.persistence.dao.PolicyMapper;
import cn.ibdsr.web.common.persistence.model.Policy;
import cn.ibdsr.web.common.persistence.model.PolicyContent;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.material.service.PolicyService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 招商政策Service
 * @Version V1.0
 * @CreateDate 2020/5/26 14:34
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private PolicyContentMapper policyContentMapper;

    @Override
    public JSONObject list(String condition, Integer level, Integer status) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        Wrapper<Policy> policyWrapper = new EntityWrapper<>();
        policyWrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        Integer total = 0;
        List<JSONObject> rows = new ArrayList<>();
        if (!(condition != null && StringUtils.isBlank(condition) && condition.length() > 0)) {
            policyWrapper.like("title", condition);
            if (level != null) {
                policyWrapper.eq("level", level);
            }
            if (status != null) {
                policyWrapper.eq("status", status);
            }
            if (page.getOrderByField() != null) {
                if (StringUtils.equals("modifiedTime", page.getOrderByField())) {
                    policyWrapper.orderBy("modified_time", page.isAsc());
                } else {
                    policyWrapper.orderBy("publish_date", page.isAsc());
                }
            } else {
                policyWrapper.orderBy("publish_date", page.isAsc());
            }
            total = policyMapper.selectCount(policyWrapper);
            RowBounds rowBounds = new RowBounds(page.getOffset(), page.getLimit());
            List<Policy> policyList = policyMapper.selectPage(rowBounds, policyWrapper);
            rows = JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(policyList, "yyyy-MM-dd HH:mm:ss"), JSONObject.class);
            for (JSONObject jsonObject : rows) {
                jsonObject.put("level", PolicyLevel.valueOf(jsonObject.getInteger("level")));
                jsonObject.put("statusName", PublishStatus.valueOf(jsonObject.getInteger("status")));
                jsonObject.put("content", policyContentMapper.selectById(jsonObject.getLong("id")).getContent().replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("rows", rows);
        return jsonObject;
    }

    private void verifyParameter(Policy policy, PolicyContent policyContent) {
        if (policy.getLevel() == null) {
            throw new BussinessException(BizExceptionEnum.POLICY_LEVEL_ERROR);
        }
        if (StringUtils.length(policy.getTitle()) < 2 || StringUtils.length(policy.getTitle()) > 80) {
            throw new BussinessException(BizExceptionEnum.POLICY_TITLE_ERROR);
        }
        if (policy.getPublishDate() == null || policy.getPublishDate().after(new Date())) {
            throw new BussinessException(BizExceptionEnum.POLICY_PUBLISH_DATE_ERROR);
        }
        if (StringUtils.isBlank(policyContent.getContent())) {
            throw new BussinessException(BizExceptionEnum.POLICY_CONTENT_ERROR);
        }
        if (StringUtils.isBlank(policy.getSource())) {
            throw new BussinessException(BizExceptionEnum.POLICY_SOURCE_ERROR);
        }
    }

    @Override
    @Transactional
    public SuccessDataTip add(Policy policy, PolicyContent policyContent) {
        verifyParameter(policy, policyContent);
        Date now = new Date();
        policy.setCreatedUser(ShiroKit.getUser().getId());
        policy.setCreatedTime(now);
        policy.setModifiedUser(ShiroKit.getUser().getId());
        policy.setModifiedTime(now);
        policyMapper.insert(policy);
        policyContent.setId(policy.getId());
        policyContentMapper.insertA(policyContent);
        return new SuccessDataTip(policy.getId());
    }

    @Override
    public Policy get(Long policyId) {
        Policy policy = policyMapper.selectById(policyId);
        if (StringUtils.isNotBlank(policy.getAttachment()) && !StringUtils.contains(policy.getAttachment(), FdfsFileUtil.PREFIX_IMAGE_URL)) {
            policy.setAttachment(ImageUtil.setImageURL(policy.getAttachment()));
        }
        return policy;
    }

    @Override
    public PolicyContent getContent(Long policyId) {
        PolicyContent policyContent = policyContentMapper.selectById(policyId);
        if (StringUtils.isNotBlank(policyContent.getContent())) {
            policyContent.setContent(policyContent.getContent().replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
        }
        return policyContent;
    }

    @Override
    @Transactional
    public SuccessMesTip update(Policy policy, PolicyContent policyContent) {
        verifyParameter(policy, policyContent);
        policy.setModifiedUser(ShiroKit.getUser().getId());
        policy.setModifiedTime(new Date());
        policyMapper.updateById(policy);
        policyContentMapper.updateById(policyContent);
        return new SuccessMesTip("修改成功");
    }

    @Override
    public SuccessMesTip delete(Long policyId) {
        Policy policy = new Policy();
        policy.setId(policyId);
        policy.setModifiedUser(ShiroKit.getUser().getId());
        policy.setModifiedTime(new Date());
        policy.setIsDeleted(IsDeleted.DELETED.getCode());
        policyMapper.updateById(policy);
        return new SuccessMesTip("删除成功");
    }

    @Override
    public SuccessMesTip publish(Long policyId, Integer status) {
        Policy policy = new Policy();
        policy.setId(policyId);
        policy.setStatus(status);
        policyMapper.updateById(policy);
        return new SuccessMesTip("操作成功");
    }

}
