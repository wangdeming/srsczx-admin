package cn.ibdsr.web.modular.business.dao;

import cn.ibdsr.web.common.persistence.dao.CompanyMapper;
import cn.ibdsr.web.modular.business.transfer.CompanyPoiVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 招商管理Dao
 * @Version V1.0
 * @CreateDate 2019/8/29 9:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/29      lvyou                招商管理Dao
 */
public interface CompanyDao extends CompanyMapper {

    /**
     * 获取企业列表
     *
     * @param page          分页参数
     * @param isRead        查阅状态
     * @param orderField    排序字段
     * @param isAsc         排序方式
     * @return
     */
    List<Map<String,Object>> list(@Param("page") Page<Map<String,Object>> page,
                                  @Param("isRead") Integer isRead,
                                  @Param("orderField") String orderField,
                                  @Param("isAsc") boolean isAsc);

    /**
     * 获取入孵记录导出列表
     *
     * @param companyIds
     * @return
     */
    List<CompanyPoiVo> getExportList(@Param("companyIds") Long... companyIds);
}
