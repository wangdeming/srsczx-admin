package cn.ibdsr.web.modular.material.service;

import cn.ibdsr.core.base.service.BaseService;
import cn.ibdsr.web.common.persistence.model.NewsInfo;
import cn.ibdsr.web.modular.material.transfer.NewsDTO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

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
public interface INewsService extends BaseService<NewsDTO, NewsInfo> {

    /**
     * 获取新闻资讯管理列表
     *
     * @param page      分页参数
     * @param condition 搜索关键字
     * @param newsType  栏目类型
     * @param isPublish 发布状态
     * @return
     */
    List<Map<String, Object>> list(Page<Map<String, Object>> page, String condition, Integer newsType, Integer isPublish, Integer imageNews);

    /**
     * 新增新闻资讯
     *
     * @param newsDTO
     */
    Long addNews(NewsDTO newsDTO);

    /**
     * 获取待更新的数据信息
     *
     * @param newsId
     * @return
     */
    JSONObject getUpdateInfo(Long newsId);

    /**
     * 删除新闻资讯
     *
     * @param newsId
     */
    void logicDelete(Long newsId);

    /**
     * 发布
     *
     * @param newsId
     */
    void publish(Long newsId);

    /**
     * 取消发布
     *
     * @param newsId
     */
    void unpublish(Long newsId);

    /**
     * 修改新闻资讯
     *
     * @param newsDTO
     */
    void updateNews(NewsDTO newsDTO);

    /**
     * 前端展示新闻资讯列表
     *
     * @param page     分页参数
     * @param newsType 新闻栏目
     * @return
     */
    List<JSONObject> indexGetList(Page<JSONObject> page, Integer newsType, Integer imageNews);

    /**
     * 查看新闻资讯详情
     *
     * @param newsId 新闻资讯id
     * @return
     */
    JSONObject indexNewsDetail(Long newsId);
}
