package cn.ibdsr.web.modular.material.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 新闻资讯管理Dao
 * @Version V1.0
 * @CreateDate 2019/8/19 16:37
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      Wujiayun            类说明
 */
public interface NewsDao {

    /**
     * 获取新闻资讯管理列表
     */
    List<Map<String, Object>> list(@Param("page") Page<Map<String, Object>> page,
                                   @Param("condition") String condition,
                                   @Param("newsType") Integer newsType,
                                   @Param("isPublish") Integer isPublish,
                                   @Param("imageNews") Integer imageNews,
                                   @Param("orderField") String orderField,
                                   @Param("isAsc") boolean isAsc);

    /**
     * 前端展示新闻资讯列表
     *
     * @param page     分页参数
     * @param newsType 新闻栏目
     * @return
     */
    List<JSONObject> indexGetList(@Param("page") Page<JSONObject> page, @Param("newsType") Integer newsType, @Param("imageNews") Integer imageNews);

    /**
     * 查看新闻资讯详情
     *
     * @param newsId 新闻资讯id
     * @return
     */
    JSONObject indexNewsDetail(@Param("newsId") Long newsId);
}
