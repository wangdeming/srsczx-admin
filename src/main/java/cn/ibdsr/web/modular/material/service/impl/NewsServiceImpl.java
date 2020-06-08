package cn.ibdsr.web.modular.material.service.impl;

import cn.ibdsr.core.base.service.impl.AbstractBaseService;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.fastdfs.base.service.FdfsClientService;
import cn.ibdsr.web.common.constant.state.DeleteStatus;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.IsPublish;
import cn.ibdsr.web.common.constant.state.NewsType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.NewsInfoMapper;
import cn.ibdsr.web.common.persistence.model.NewsInfo;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.material.dao.NewsDao;
import cn.ibdsr.web.modular.material.service.INewsService;
import cn.ibdsr.web.modular.material.transfer.NewsDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 新闻资讯管理Service
 * @Version V1.0
 * @CreateDate 2019/8/19 16:37
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      Wujiayun            类说明
 */
@Service
public class NewsServiceImpl extends AbstractBaseService<NewsDTO, NewsInfo> implements INewsService {

    @Resource
    private NewsDao newsDao;

    @Resource
    private NewsInfoMapper newsInfoMapper;
    @Resource
    private FdfsClientService fdfsClientService;

    @Override
    public BaseMapper<NewsInfo> getMapper() {
        return newsInfoMapper;
    }

    @Override
    public NewsInfo getConversionDO() {
        return new NewsInfo();
    }

    @Override
    public NewsDTO getConversionDTO() {
        return new NewsDTO();
    }

    @Override
    public List<Map<String, Object>> list(Page<Map<String, Object>> page, String condition, Integer newsType, Integer isPublish, Integer imageNews) {
        page.setOpenSort(false);
        return newsDao.list(page, condition, newsType, isPublish, imageNews, page.getOrderByField(), page.isAsc());
    }

    /**
     * 新增新闻资讯
     *
     * @param newsDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addNews(NewsDTO newsDTO) {
        if (ToolUtil.isEmpty(newsDTO.getShowTime())) {
            newsDTO.setShowTime(new Date());
        }

        checkInsert(newsDTO);
        // 裁剪上传图片URL前缀
        cutImageUrl(newsDTO);

        newsDTO.setCreatedTime(new Date());
        newsDTO.setCreatedUser(ShiroKit.getUser().getId());
        newsDTO.setModifiedTime(new Date());
        newsDTO.setModifiedUser(ShiroKit.getUser().getId());
        newsDTO.setIsDeleted(IsDeleted.NORMAL.getCode());
        return this.insert(newsDTO).getId();
    }

    @Override
    public void checkInsert(NewsDTO newsDTO) {
        if (StringUtils.isEmpty(NewsType.valueOf(newsDTO.getNewsType()))) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        if (newsDTO.getShowTime().after(new Date())) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_SHOW_TIME_ERROR);
        }
    }

    /**
     * 裁剪图片的URL前缀
     *
     * @param newsDTO 新闻信息
     */
    private void cutImageUrl(NewsDTO newsDTO) {
        String coverImage = newsDTO.getCoverImage();
        String extraFile = newsDTO.getExtraFile();
        if (StringUtils.isNotEmpty(coverImage) && coverImage.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) != -1) {
            newsDTO.setCoverImage(ImageUtil.cutImageURL(coverImage));
        }
        if (StringUtils.isNotEmpty(extraFile) && extraFile.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) != -1) {
            newsDTO.setExtraFile(ImageUtil.cutImageURL(extraFile));
        }
    }

    @Override
    public void checkUpdate(NewsDTO newsDTO) {
        if (newsDTO.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        if (StringUtils.isEmpty(NewsType.valueOf(newsDTO.getNewsType()))) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        if (newsDTO.getShowTime().after(new Date())) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_SHOW_TIME_ERROR);
        }
    }

    /**
     * 修改新闻资讯
     *
     * @param newsDTO
     */
    @Override
    public void updateNews(NewsDTO newsDTO) {
        if (ToolUtil.isEmpty(newsDTO.getShowTime())) {
            newsDTO.setShowTime(new Date());
        }

        checkUpdate(newsDTO);
        // 裁剪上传图片URL前缀
        cutImageUrl(newsDTO);

        newsDTO.setModifiedUser(ShiroKit.getUser().getId());
        newsDTO.setModifiedTime(new Date());

        //下面字段不允许更新
        newsDTO.setCreatedTime(null);
        newsDTO.setCreatedUser(null);
        newsDTO.setIsDeleted(null);
        this.updateById(newsDTO);
    }

    @Override
    public JSONObject getUpdateInfo(Long newsId) {
        if (ToolUtil.isEmpty(newsId)) {
            throw new BussinessException(BizExceptionEnum.NEWS_ID_IS_NULL);
        }
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setId(newsId);
        newsInfo.setIsDeleted(DeleteStatus.UN_DELETED.getCode());
        newsInfo = newsInfoMapper.selectOne(newsInfo);
        if (ToolUtil.isEmpty(newsInfo)) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_NOT_EXIST);
        }

        // 相关图片路径拼接
        combineImageUrl(newsInfo);
        JSONObject jObjRes = JSON.parseObject(JSONObject.toJSONString(newsInfo));
        return jObjRes;
    }

    /**
     * 拼接图片的URL前缀
     *
     * @param newsInfo
     */
    private void combineImageUrl(NewsInfo newsInfo) {
        String coverImage = newsInfo.getCoverImage();
        String extraFile = newsInfo.getExtraFile();
        if (StringUtils.isNotEmpty(coverImage) && coverImage.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) == -1) {
            newsInfo.setCoverImage(ImageUtil.setImageURL(coverImage));
        }
        if (StringUtils.isNotEmpty(extraFile) && extraFile.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) == -1) {
            newsInfo.setExtraFile(ImageUtil.setImageURL(extraFile));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void logicDelete(Long newsId) {
        if (ToolUtil.isEmpty(newsId)) {
            throw new BussinessException(BizExceptionEnum.NEWS_ID_IS_NULL);
        }
        NewsInfo newsInfo = newsInfoMapper.selectById(newsId);
        if (newsInfo == null) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_NOT_EXIST);
        }
        newsInfo.setModifiedTime(new Date());
        newsInfo.setModifiedUser(ShiroKit.getUser().getId());
        newsInfo.setIsDeleted(IsDeleted.DELETED.getCode());
        newsInfo.updateById();
        String extraFilePath = newsInfo.getExtraFile();
        if (StringUtils.isNotEmpty(extraFilePath) && extraFilePath.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) != -1) {
            extraFilePath = FdfsFileUtil.cutFileURL(extraFilePath);
            fdfsClientService.deleteFile(extraFilePath);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long newsId) {
        if (ToolUtil.isEmpty(newsId)) {
            throw new BussinessException(BizExceptionEnum.NEWS_ID_IS_NULL);
        }
        NewsInfo newsInfo = newsInfoMapper.selectById(newsId);
        if (newsInfo == null) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_NOT_EXIST);
        }
        newsInfo.setIsPublish(IsPublish.PUBLISH.getCode());
        newsInfo.setModifiedUser(ShiroKit.getUser().getId());
        newsInfo.setModifiedTime(new Date());
        newsInfo.updateById();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unpublish(Long newsId) {
        if (ToolUtil.isEmpty(newsId)) {
            throw new BussinessException(BizExceptionEnum.NEWS_ID_IS_NULL);
        }
        NewsInfo newsInfo = newsInfoMapper.selectById(newsId);
        if (newsInfo == null) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_NOT_EXIST);
        }
        newsInfo.setIsPublish(IsPublish.UNPUBLISH.getCode());
        newsInfo.setModifiedUser(ShiroKit.getUser().getId());
        newsInfo.setModifiedTime(new Date());
        newsInfo.updateById();
    }

    /**
     * 前端展示新闻资讯列表
     *
     * @param page     分页参数
     * @param newsType 新闻栏目
     * @return
     */
    @Override
    public List<JSONObject> indexGetList(Page<JSONObject> page, Integer newsType, Integer imageNews) {
        return newsDao.indexGetList(page, newsType, imageNews);
    }

    /**
     * 查看新闻资讯详情
     *
     * @param newsId 新闻资讯id
     * @return
     */
    @Override
    public JSONObject indexNewsDetail(Long newsId) {
        return newsDao.indexNewsDetail(newsId);
    }
}
