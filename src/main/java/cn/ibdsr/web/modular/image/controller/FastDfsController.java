package cn.ibdsr.web.modular.image.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.exception.FastdfsException;
import cn.ibdsr.core.exception.GunsExceptionEnum;
import cn.ibdsr.fastdfs.base.service.FdfsClientService;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description fastDFS文件上传接口
 * @Version V1.0
 * @CreateDate 2019/8/22 17:27
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/4/29      lvyuou               fastDFS文件上传接口
 */
@Controller
@RequestMapping("/fastDfs")
public class FastDfsController extends BaseController {

    @Resource
    private FdfsClientService fdfsClientService;

    private static void checkMaxSize(MultipartFile file, Long maxSize) {
        if (file != null) {
            long size = file.getSize();
            if (size > maxSize) {
                throw new BussinessException(BizExceptionEnum.FILE_SIZE_TOO_LONG);
            }
        }
    }

    /**
     * 上传文件 包括 图片和文件
     * 默认图片上传最大 5M
     *
     * @author pizi
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "maxSize", required = false, defaultValue = "1048576") Long maxSize) {
        String path = null;
        try {
            if (file != null && file.getBytes().length != 0) {
                checkMaxSize(file, maxSize);
                path = this.fdfsClientService.uploadFile(file.getBytes(), FilenameUtils.getExtension(file.getOriginalFilename()));
            } else {
                throw new BussinessException(BizExceptionEnum.FILE_CANT_BE_NULL);
            }
        } catch (IOException ignored) {
        }
        if (StringUtils.isEmpty(path)) {
            throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return new SuccessDataTip(FdfsFileUtil.setFileURL(path));
    }

    /**
     * 删除文件
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("filePath") String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        try {
            fdfsClientService.deleteFile(FdfsFileUtil.setFileURL(filePath));
        } catch (Exception var3) {
            throw new FastdfsException(GunsExceptionEnum.FILE_DELETE_NOT_FOUND, "文件路径".concat(filePath));
        }
        return new SuccessTip();
    }

}
