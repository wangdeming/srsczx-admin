package cn.ibdsr.web.modular.api;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.web.modular.business.service.ICompanyService;
import cn.ibdsr.web.modular.business.transfer.CompanyDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description 前台入孵申请控制器
 * @Version V1.0
 * @CreateDate 2019/9/5 9:32
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                前台入孵申请控制器
 */
@RestController
@RequestMapping(value = "/api/business")
public class ApiCompanyController extends BaseController {

    @Resource
    private ICompanyService companyService;

    /**
     * 提交入孵申请信息
     *
     * @param param 企业数据
     * @return
     */
    @PostMapping(value = "/company/commit")
    public Object commitMessage(@Valid CompanyDTO param) {
        StaticCheck.check(param);
        companyService.addCompany(param);
        return new SuccessTip();
    }

}
