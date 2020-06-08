/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.fastdfs.config.FastdfsProperties;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Description 系统图片工具类
 * @Version V1.0
 * @CreateDate 2018/4/26 8:59
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/4/26      ZhangLin               类说明
 */
public class FdfsFileUtil {


    /**
     * 图片访问的前缀
     */
    public static String PREFIX_IMAGE_URL =
            SpringContextHolder.getBean(FastdfsProperties.class).getVisit();

    /**
     * @param targetImageURL 目标的图片的URL
     * @return
     * @throws
     * @Description 裁剪 图片的URL
     * 比如将http://172.16.1.200:10080/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * 截取为 group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * @Date 2017/8/17 15:33
     */
    public static String cutFileURL(String targetImageURL) {

        if (StringUtils.isEmpty(targetImageURL)) {
            return targetImageURL;
        }
        int imageIndex = targetImageURL.indexOf(PREFIX_IMAGE_URL);
        if (imageIndex < 0) {
            return targetImageURL;
        }
        return targetImageURL.substring(imageIndex + PREFIX_IMAGE_URL.length());
    }

    /**
     * @param targetImageURL 目标的图片的URL
     * @return
     * @throws
     * @Description 补全 图片的URL
     * 比如将/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * 补全为 http://172.16.1.200:10080/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * @Date 2017/8/17 15:33
     */
    public static String setFileURL(String targetImageURL) {

        if (StringUtils.isEmpty(targetImageURL) || "null".equals(targetImageURL)) {
            return "";
        }
        int imageIndex = targetImageURL.indexOf(PREFIX_IMAGE_URL);
        if (imageIndex > -1) {
            return targetImageURL;
        }
        return PREFIX_IMAGE_URL + targetImageURL;
    }

    public static void down(HttpServletResponse response, URL url, String fileName) {
        OutputStream outStream = null;
        InputStream inputStream = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            outStream = response.getOutputStream();

            //构建zip压缩包 start
            byte[] buffer = new byte[1024];
            inputStream = url.openStream();
            int len;
            //读入需要下载的文件的内容，打包到zip文件
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            //构建zip压缩包 end
        } catch (Exception e) {
            throw new BussinessException(BizExceptionEnum.DOWNLOAD_ERROR);
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new BussinessException(BizExceptionEnum.DOWNLOAD_ERROR);
            }
        }
    }

}
