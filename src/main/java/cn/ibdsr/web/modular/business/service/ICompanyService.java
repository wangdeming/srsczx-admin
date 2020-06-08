package cn.ibdsr.web.modular.business.service;

import cn.ibdsr.web.common.persistence.model.Company;
import cn.ibdsr.web.modular.business.transfer.CompanyDTO;
import cn.ibdsr.web.modular.business.transfer.CompanyPoiVo;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 招商管理Service
 * @Version V1.0
 * @CreateDate 2019/8/29 9:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                招商管理Service
 */
public interface ICompanyService {

    /**
     * 获取企业列表
     *
     * @param page      分页参数
     * @param isRead    查阅状态
     * @return
     */
    List<Map<String,Object>> list(Page<Map<String,Object>> page, Integer isRead);

    /**
     * 标记入孵记录为已读
     *
     * @param companyIds
     */
    void readCompany(Long... companyIds);

    /**
     * 核查入孵记录id参数是否合法
     *
     * @param id    入孵记录id
     * @return
     */
    Company checkMessageId(Long id);

    /**
     * 获取入孵记录导出列表
     *
     * @param companyIds
     * @return
     */
    List<CompanyPoiVo> getExportList(Long... companyIds);

    /**
     * 添加入孵申请记录
     *
     * @param param
     */
    void addCompany(CompanyDTO param);
}
