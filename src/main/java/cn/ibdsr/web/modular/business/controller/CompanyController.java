package cn.ibdsr.web.modular.business.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.util.DateUtil;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.ReadStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.model.Company;
import cn.ibdsr.web.core.util.ExcelPoiUtil;
import cn.ibdsr.web.modular.business.service.ICompanyService;
import cn.ibdsr.web.modular.business.transfer.CompanyPoiVo;
import cn.ibdsr.web.modular.business.warpper.CompanyWarpper;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description 招商管理控制器
 * @Version V1.0
 * @CreateDate 2019/8/29 9:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                招商管理控制器
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    private String PREFIX = "/business/company/";

    @Resource
    private ICompanyService companyService;

    /**
     * 跳转到招商管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "company.html";
    }

    /**
     * 获取企业列表
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "isRead", required = false) Integer isRead) {
        if (isRead != null && StringUtils.isEmpty(ReadStatus.valueOf(isRead))) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> records = companyService.list(page, isRead);
        page.setRecords((List<Map<String, Object>>) new CompanyWarpper(records).warp());
        return super.packForBT(page);
    }

    /**
     * 标记入孵记录为已读
     */
    @PostMapping(value = "/read")
    @ResponseBody
    public Object read(@RequestParam(value = "companyIds") Long... companyIds) {
        if (companyIds == null || companyIds.length <= 0) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        companyService.readCompany(companyIds);
        return new SuccessTip();
    }

    /**
     * 跳转到留言管理首页
     */
    @GetMapping(value = "/company_detail/{id}")
    public String companyDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return PREFIX + "company_detail.html";
    }

    /**
     * 查看入孵记录详情
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    public Object detail(@RequestParam(value = "id") Long id) {
        Company company = companyService.checkMessageId(id);
        String setTime = DateUtil.format(company.getSetTime(), "yyyy-MM-dd");
        JSONObject companyJson = JSONObject.parseObject(JSONObject.toJSONString(company));
        companyService.readCompany(company.getId());
        companyJson.put("setTime", setTime);
        return new SuccessDataTip(companyJson);
    }

    /**
     * 导出留言详情
     */
    @GetMapping(value = "/export")
    public void export(HttpServletResponse response, @RequestParam(value = "companyIds") Long... companyIds) {
        if (companyIds == null || companyIds.length <= 0) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        //构建excel导出数据 start
        List<CompanyPoiVo> records = companyService.getExportList(companyIds);
        Workbook workbook = ExcelPoiUtil.defaultExport(records, CompanyPoiVo.class, new ExportParams("入孵记录", "入孵记录表", ExcelType.XSSF));
        ExcelPoiUtil.downLoadExcel("入孵记录表.xlsx", response, workbook);
    }
}
