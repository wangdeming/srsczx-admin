package cn.ibdsr.web.modular.business.service.impl;

import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.DeleteStatus;
import cn.ibdsr.web.common.constant.state.ReadStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.model.Company;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.modular.business.dao.CompanyDao;
import cn.ibdsr.web.modular.business.service.ICompanyService;
import cn.ibdsr.web.modular.business.transfer.CompanyDTO;
import cn.ibdsr.web.modular.business.transfer.CompanyPoiVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
@Service
public class CompanyServiceImpl implements ICompanyService {

    @Resource
    private CompanyDao companyDao;

    /**
     * 获取企业列表
     *
     * @param page   分页参数
     * @param isRead 查阅状态
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Page<Map<String, Object>> page, Integer isRead) {
        page.setOpenSort(false);
        return companyDao.list(page, isRead, page.getOrderByField(), page.isAsc());
    }

    /**
     * 标记入孵记录为已读
     *
     * @param companyIds
     */
    @Override
    public void readCompany(Long... companyIds) {
        Company updateCompany = new Company();
        updateCompany.setModifiedTime(new Date());
        updateCompany.setModifiedUser(ShiroKit.getUser().getId());
        updateCompany.setIsRead(ReadStatus.READ.getCode());
        companyDao.update(updateCompany, new EntityWrapper<Company>().in("id", companyIds));
    }

    /**
     * 获取入孵记录导出列表
     *
     * @param companyIds
     * @return
     */
    @Override
    public List<CompanyPoiVo> getExportList(Long... companyIds) {
        return companyDao.getExportList(companyIds);
    }

    /**
     * 核查入孵记录id参数是否合法
     *
     * @param id    入孵记录id
     * @return
     */
    @Override
    public Company checkMessageId(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        Company company = new Company();
        company.setId(id);
        company.setIsDeleted(DeleteStatus.UN_DELETED.getCode());
        company = companyDao.selectOne(company);
        if (company == null) {
            throw new BussinessException(BizExceptionEnum.COMPANY_NOT_EXIST);
        }
        return company;
    }

    /**
     * 添加入孵申请记录
     *
     * @param param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCompany(CompanyDTO param) {
        Company addCompany = new Company();
        BeanUtils.copyProperties(param, addCompany);
        addCompany.setIsRead(ReadStatus.UNREAD.getCode());
        addCompany.setIsDeleted(DeleteStatus.UN_DELETED.getCode());
        addCompany.setCreatedTime(new Date());
        companyDao.insert(addCompany);
    }

}
