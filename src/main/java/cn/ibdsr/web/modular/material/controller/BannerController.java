package cn.ibdsr.web.modular.material.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.persistence.model.Banner;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.service.IBannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 轮播图管理控制器
 * @Version V1.0
 * @CreateDate 2019/8/19 16:38
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      lvyou                轮播图管理控制器
 */
@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

    private String PREFIX = "/material/banner/";

    @Resource
    private IBannerService bannerService;

    /**
     * 跳转到轮播图管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "banner.html";
    }

    /**
     * 获取轮播图管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        List<Banner> records = bannerService.list();
        for (Banner temp : records) {
            String path = temp.getPath();
            if (StringUtils.isNotEmpty(path) && path.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) == -1) {
                path = FdfsFileUtil.setFileURL(path);
                temp.setPath(path);
            }
        }
        return new SuccessDataTip(records);
    }

    /**
     * 新增轮播图管理
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestParam(value = "bannerParam") List<String> bannerParam) {
        bannerService.saveBannerList(bannerParam);
        return super.SUCCESS_TIP;
    }
}
