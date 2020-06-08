package cn.ibdsr.web.modular.api;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.ErrorTip;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.NewsType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.service.INewsService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @Description 前台首页展示控制器
 * @Version V1.0
 * @CreateDate 2019/9/5 9:32
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                前台首页展示控制器
 */
@RestController
@RequestMapping(value = "/api/news")
public class ApiNewsController extends BaseController {

    @Resource
    private INewsService newsService;

    /**
     * 前端首页新闻资讯列表
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public Object newsInfoList(Integer newsType, Integer imageNews) {
        Page<JSONObject> page = new PageFactory<JSONObject>().defaultPage();
        List<JSONObject> records = newsService.indexGetList(page, newsType, imageNews);
        for (JSONObject temp : records) {
            String coverImage = temp.getString("coverImage");
            String extraFile = temp.getString("extraFile");
            temp.put("coverImage", FdfsFileUtil.setFileURL(coverImage));
            temp.put("extraFile", FdfsFileUtil.setFileURL(extraFile));
        }
        page.setRecords(records);
        return super.packForBT(page);
    }

    /**
     * 前端首页新闻资讯列表
     *
     * @return
     */
    @RequestMapping(value = "/detail")
    public Object newsInfoDetail(@RequestParam Long newsId) {
        if (ToolUtil.isEmpty(newsId)) {
            return new ErrorTip(BizExceptionEnum.NEWS_ID_IS_NULL.getCode(), BizExceptionEnum.NEWS_ID_IS_NULL.getMessage());
        }
        JSONObject newsDetail = newsService.getUpdateInfo(newsId);
        if (newsDetail == null) {
            return new ErrorTip(BizExceptionEnum.NEWS_INFO_NOT_EXIST.getCode(), BizExceptionEnum.NEWS_INFO_NOT_EXIST.getMessage());
        }
        String coverImage = newsDetail.getString("coverImage");
        String extraFile = newsDetail.getString("extraFile");
        Integer newsType = newsDetail.getInteger("newsType");
        newsDetail.put("coverImage", FdfsFileUtil.setFileURL(coverImage));
        newsDetail.put("extraFile", FdfsFileUtil.setFileURL(extraFile));
        newsDetail.put("newsType", NewsType.valueOf(newsType));
        return new SuccessDataTip(newsDetail);
    }

    /**
     * 前端首页新闻资讯列表
     *
     * @return
     */
    @GetMapping(value = "/extraFile/download")
    public void download(HttpServletResponse response, Long newsId) throws MalformedURLException {
        if (ToolUtil.isEmpty(newsId)) {
            throw new BussinessException(BizExceptionEnum.NEWS_ID_IS_NULL);
        }
        JSONObject newsDetail = newsService.getUpdateInfo(newsId);
        if (newsDetail == null) {
            throw new BussinessException(BizExceptionEnum.NEWS_INFO_NOT_EXIST);
        }
        FdfsFileUtil.down(response, new URL(newsDetail.getString("extraFile")), newsDetail.getString("fileName"));
    }
}
