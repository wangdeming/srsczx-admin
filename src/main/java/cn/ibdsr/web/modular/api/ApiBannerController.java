package cn.ibdsr.web.modular.api;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.persistence.model.Banner;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.service.IBannerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 前台轮播图控制器
 * @Version V1.0
 * @CreateDate 2019/9/5 9:32
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                前台轮播图控制器
 */
@RestController
@RequestMapping(value = "/api/banner")
public class ApiBannerController extends BaseController {

    @Resource
    private IBannerService bannerService;

    /**
     * 获取轮播图列表
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public Object bannerList() {
        List<Banner> bannerList = bannerService.list();
        for (Banner banner : bannerList) {
            String imagePath = banner.getPath();
            banner.setPath(FdfsFileUtil.setFileURL(imagePath));
        }
        return new SuccessDataTip(bannerList);
    }

}
