package cn.ibdsr.web.modular.material.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.persistence.model.Recruit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description 人才招聘控制器
 * @Version V1.0
 * @CreateDate 2020/5/26 14:35
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
@Controller
@RequestMapping("/recruit")
public class RecruitController extends BaseController {

    private String PREFIX = "/material/recruit/";

    @Autowired
    private RecruitService recruitService;

    /**
     * 跳转到人才招聘首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "recruit.html";
    }

    /**
     * 跳转到添加人才招聘
     */
    @RequestMapping("/recruit_add")
    public String recruitAdd() {
        return PREFIX + "recruit_add.html";
    }

    /**
     * 跳转到修改人才招聘
     */
    @RequestMapping("/recruit_update/{recruitId}")
    public String recruitUpdate(@PathVariable Long recruitId, Model model) {
        model.addAttribute("recruit", recruitService.get(recruitId));
        return PREFIX + "recruit_edit.html";
    }

    /**
     * 获取人才招聘列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer status) {
        return recruitService.list(condition, status);
    }

    /**
     * 新增人才招聘
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public SuccessDataTip add(Recruit recruit) {
        return recruitService.add(recruit);
    }

    /**
     * 删除人才招聘
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long recruitId) {
        return recruitService.delete(recruitId);
    }


    /**
     * 修改人才招聘
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Recruit recruit) {
        return recruitService.update(recruit);
    }

    /**
     * 跳转到人才招聘详情
     */
    @RequestMapping("/recruit_detail/{recruitId}")
    public String recruitDetail(@PathVariable Long recruitId, Model model) {
        model.addAttribute("recruit", recruitService.get(recruitId));
        return PREFIX + "recruit_detail.html";
    }

    /**
     * 详情
     */
    @RequestMapping("/detail")
    @ResponseBody
    public SuccessDataTip recruitDetail(@RequestParam Long recruitId) {
        return new SuccessDataTip(recruitService.get(recruitId));
    }

    /**
     * 发布或取消发布
     *
     * @param recruitId
     * @param status    发布值为1，取消发布值为0
     * @return
     */
    @RequestMapping(value = "/publish")
    @ResponseBody
    public SuccessMesTip publish(@RequestParam Long recruitId, @RequestParam Integer status) {
        return recruitService.publish(recruitId, status);
    }

    /**
     * 附件下载
     *
     * @param recruitId
     * @return
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, @RequestParam Long recruitId) throws MalformedURLException {
        Recruit recruit = recruitService.get(recruitId);
        FdfsFileUtil.down(response, new URL(recruit.getAttachment()), recruit.getAttachmentName());
    }


}
