package cn.ibdsr.web.modular.material.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.common.persistence.model.Policy;
import cn.ibdsr.web.common.persistence.model.PolicyContent;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.service.PolicyService;
import com.alibaba.fastjson.JSONObject;
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
 * @Description 招商政策控制器
 * @Version V1.0
 * @CreateDate 2020/5/26 14:34
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2020/5/26      xxx            类说明
 */
@Controller
@RequestMapping("/policy")
public class PolicyController extends BaseController {

    private String PREFIX = "/material/policy/";

    @Autowired
    private PolicyService policyService;

    /**
     * 跳转到招商政策首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "policy.html";
    }

    /**
     * 跳转到添加招商政策
     */
    @RequestMapping("/policy_add")
    public String policyAdd() {
        return PREFIX + "policy_add.html";
    }

    /**
     * 跳转到修改招商政策
     */
    @RequestMapping("/policy_update/{policyId}")
    public String policyUpdate(@PathVariable Long policyId, Model model) {
        model.addAttribute("policy", policyService.get(policyId));
        model.addAttribute("policyContent", policyService.getContent(policyId));
        return PREFIX + "policy_edit.html";
    }

    /**
     * 获取招商政策列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public JSONObject list(String condition, Integer level, Integer status) {
        return policyService.list(condition, level, status);
    }

    /**
     * 新增招商政策
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public SuccessDataTip add(Policy policy, PolicyContent policyContent) {
        return policyService.add(policy, policyContent);
    }

    /**
     * 删除招商政策
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public SuccessMesTip delete(@RequestParam Long policyId) {
        return policyService.delete(policyId);
    }


    /**
     * 修改招商政策
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public SuccessMesTip update(Policy policy, PolicyContent policyContent) {
        return policyService.update(policy, policyContent);
    }

    /**
     * 跳转到招商政策详情
     */
    @RequestMapping("/policy_detail/{policyId}")
    public String policyDetail(@PathVariable Long policyId, Model model) {
        model.addAttribute("policy", policyService.get(policyId));
        model.addAttribute("policyContent", policyService.getContent(policyId));
        return PREFIX + "policy_detail.html";
    }

    /**
     * 详情
     *
     * @param policyId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public SuccessDataTip detail(@RequestParam Long policyId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("policy", policyService.get(policyId));
        jsonObject.put("policyContent", policyService.getContent(policyId));
        return new SuccessDataTip(jsonObject);
    }

    /**
     * 发布或取消发布
     *
     * @param policyId
     * @param status   发布值为1，取消发布值为0
     * @return
     */
    @RequestMapping(value = "/publish")
    @ResponseBody
    public SuccessMesTip publish(@RequestParam Long policyId, @RequestParam Integer status) {
        return policyService.publish(policyId, status);
    }

    /**
     * 附件下载
     *
     * @param policyId
     * @return
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, @RequestParam Long policyId) throws MalformedURLException {
        Policy policy = policyService.get(policyId);
        FdfsFileUtil.down(response, new URL(policy.getAttachment()), policy.getAttachmentName());
    }

}
