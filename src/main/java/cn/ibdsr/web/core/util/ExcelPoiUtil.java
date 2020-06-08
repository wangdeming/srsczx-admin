package cn.ibdsr.web.core.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description Excel文件操作工具类
 * @Version V1.0
 * @CreateDate 2019/8/27 16:44
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      lvyou                Excel文件操作工具类
 */
public class ExcelPoiUtil {

    /**
     * 构建excel文件对象
     *
     * @param list
     * @param pojoClass
     * @param exportParams
     * @return
     */
    public static Workbook defaultExport(List<?> list, Class<?> pojoClass, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        return workbook;
    }

    /**
     * 导出excel文件
     *
     * @param fileName
     * @param response
     * @param workbook
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(fileName, "UTF-8") );
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BussinessException(BizExceptionEnum.EXPORT_ERROR);
        }
    }

}
