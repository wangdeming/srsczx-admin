package cn.ibdsr.web.modular.material.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.PublishStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RecruitMapper;
import cn.ibdsr.web.common.persistence.model.Recruit;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.material.service.RecruitService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 人才招聘Service
 * @Version V1.0
 * @CreateDate 2020/5/26 14:35
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
@Service
public class RecruitServiceImpl implements RecruitService {

    @Autowired
    private RecruitMapper recruitMapper;

    @Override
    public JSONObject list(String condition, Integer status) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        Wrapper<Recruit> recruitWrapper = new EntityWrapper<>();
        recruitWrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        Integer total = 0;
        List<JSONObject> rows = new ArrayList<>();
        if (!(condition != null && StringUtils.isBlank(condition) && condition.length() > 0)) {
            if (condition != null) {
                recruitWrapper.like("title", condition);
            }
            if (status != null) {
                recruitWrapper.eq("status", status);
            }
            if (page.getOrderByField() != null) {
                if (StringUtils.equals("publishDatetime", page.getOrderByField())) {
                    recruitWrapper.orderBy("publish_datetime", page.isAsc());
                } else if (StringUtils.equals("modifiedTime", page.getOrderByField())) {
                    recruitWrapper.orderBy("modified_time", page.isAsc());
                } else {
                    recruitWrapper.orderBy("show_datetime", page.isAsc());
                }
            } else {
                recruitWrapper.orderBy("show_datetime", page.isAsc());
            }
            total = recruitMapper.selectCount(recruitWrapper);
            RowBounds rowBounds = new RowBounds(page.getOffset(), page.getLimit());
            List<Recruit> recruitList = recruitMapper.selectPage(rowBounds, recruitWrapper);
            rows = JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(recruitList, "yyyy-MM-dd HH:mm:ss"), JSONObject.class);
            for (JSONObject jsonObject : rows) {
                jsonObject.put("statusName", PublishStatus.valueOf(jsonObject.getInteger("status")));
                jsonObject.put("content", jsonObject.getString("content").replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("rows", rows);
        return jsonObject;
    }

    private void verifyParameter(Recruit recruit) {
        if (StringUtils.length(recruit.getTitle()) < 2 || StringUtils.length(recruit.getTitle()) > 80) {
            throw new BussinessException(BizExceptionEnum.RECRUIT_TITLE_ERROR);
        }
        if (StringUtils.length(recruit.getCompany()) < 2 || StringUtils.length(recruit.getCompany()) > 80) {
            throw new BussinessException(BizExceptionEnum.RECRUIT_COMPANY_ERROR);
        }
        if (StringUtils.isBlank(recruit.getContent())) {
            throw new BussinessException(BizExceptionEnum.RECRUIT_CONTENT_ERROR);
        }
        if (recruit.getShowDatetime() == null || recruit.getShowDatetime().after(new Date())) {
            throw new BussinessException(BizExceptionEnum.RECRUIT_SHOW_DATETIME_ERROR);
        }
    }

    @Override
    public SuccessDataTip add(Recruit recruit) {
        verifyParameter(recruit);
        Date now = new Date();
        recruit.setCreatedUser(ShiroKit.getUser().getId());
        recruit.setCreatedTime(now);
        recruit.setModifiedUser(ShiroKit.getUser().getId());
        recruit.setModifiedTime(now);
        recruitMapper.insert(recruit);
        return new SuccessDataTip(recruit.getId());
    }

    @Override
    public Recruit get(Long recruitId) {
        Recruit recruit = recruitMapper.selectById(recruitId);
        if (StringUtils.isNotBlank(recruit.getContent())) {
            recruit.setContent(recruit.getContent().replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
        }
        if (StringUtils.isNotBlank(recruit.getAttachment()) && !StringUtils.contains(recruit.getAttachment(), FdfsFileUtil.PREFIX_IMAGE_URL)) {
            recruit.setAttachment(ImageUtil.setImageURL(recruit.getAttachment()));
        }
        return recruit;
    }

    @Override
    public SuccessMesTip update(Recruit recruit) {
        verifyParameter(recruit);
        recruit.setModifiedUser(ShiroKit.getUser().getId());
        recruit.setModifiedTime(new Date());
        recruitMapper.updateById(recruit);
        return new SuccessMesTip("修改成功");
    }

    @Override
    public SuccessMesTip delete(Long recruitId) {
        Recruit recruit = new Recruit();
        recruit.setId(recruitId);
        recruit.setModifiedUser(ShiroKit.getUser().getId());
        recruit.setModifiedTime(new Date());
        recruit.setIsDeleted(IsDeleted.DELETED.getCode());
        recruitMapper.updateById(recruit);
        return new SuccessMesTip("删除成功");
    }

    @Override
    public SuccessMesTip publish(Long recruitId, Integer status) {
        Recruit recruit = new Recruit();
        recruit.setId(recruitId);
        recruit.setStatus(status);
        if (status.equals(PublishStatus.PUBLISHED.getCode())) {
            recruit.setPublishDatetime(new Date());
        }
        recruitMapper.updateById(recruit);
        return new SuccessMesTip("操作成功");
    }

}
