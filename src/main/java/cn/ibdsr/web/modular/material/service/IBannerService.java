package cn.ibdsr.web.modular.material.service;


import cn.ibdsr.web.common.persistence.model.Banner;

import java.util.List;

/**
 * @Description 轮播图管理Service
 * @Version V1.0
 * @CreateDate 2019/8/19 16:38
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      lvyou                轮播图管理Service
 */
public interface IBannerService {

    /**
     * 获取轮播图列表
     *
     * @return
     */
    List<Banner> list();

    /**
     * 保存轮播图信息
     *
     * @param bannerParam 轮播图数组
     */
    void saveBannerList(List<String> bannerParam);

}
