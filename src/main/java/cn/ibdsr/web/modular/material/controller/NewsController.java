package cn.ibdsr.web.modular.material.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.material.service.INewsService;
import cn.ibdsr.web.modular.material.transfer.NewsDTO;
import cn.ibdsr.web.modular.material.warpper.NewsWarpper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description 新闻资讯管理控制器
 * @Version V1.0
 * @CreateDate 2019/8/19 16:37
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      Wujiayun            类说明
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    private String PREFIX = "/material/news/";

    @Resource
    private INewsService newsService;

    /**
     * 跳转到新闻资讯管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "news.html";
    }

    /**
     * 获取新闻资讯管理列表
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "condition", required = false) String condition,
                       @RequestParam(value = "newsType", required = false) Integer newsType,
                       @RequestParam(value = "isPublish", required = false) Integer isPublish, Integer imageNews) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> records = newsService.list(page, condition, newsType, isPublish, imageNews);
        page.setRecords((List<Map<String, Object>>) new NewsWarpper(records).warp());
        return super.packForBT(page);
    }

    /**
     * 跳转到添加新闻资讯管理
     */
    @RequestMapping("/news_add")
    public String newsAdd() {
        return PREFIX + "news_add.html";
    }

    /**
     * 新增新闻资讯管理
     *
     * @param newsDTO
     * @return
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestBody NewsDTO newsDTO) {
        return new SuccessDataTip(newsService.addNews(newsDTO));
    }

    /**
     * 跳转到修改新闻资讯管理
     */
    @RequestMapping("/news_update/{newsId}")
    public String newsUpdate(@PathVariable Long newsId, Model model) {
        JSONObject newsInfo = newsService.getUpdateInfo(newsId);
        model.addAttribute("newsInfo", newsInfo);
        return PREFIX + "news_edit.html";
    }

    /**
     * 删除新闻资讯管理
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long newsId) {
        newsService.logicDelete(newsId);
        return SUCCESS_TIP;
    }


    /**
     * 修改新闻资讯管理
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public Object update(@RequestBody NewsDTO newsDTO) {
        newsService.updateNews(newsDTO);
        return new SuccessTip();
    }

    /**
     * 新闻资讯管理详情
     */
    @RequestMapping(value = "/news_detail/{newsId}")
    public String detail(@PathVariable Long newsId, Model model) {
        JSONObject newsInfo = newsService.getUpdateInfo(newsId);
        model.addAttribute("newsInfo", newsInfo);
        return PREFIX + "news_detail.html";
    }

    /**
     * 发布
     */
    @RequestMapping(value = "/publish")
    @ResponseBody
    public Object publish(@RequestParam Long newsId) {
        newsService.publish(newsId);
        return SUCCESS_TIP;
    }

    /**
     * 取消发布
     */
    @RequestMapping(value = "/unpublish")
    @ResponseBody
    public Object unpublish(@RequestParam Long newsId) {
        newsService.unpublish(newsId);
        return SUCCESS_TIP;
    }

}
