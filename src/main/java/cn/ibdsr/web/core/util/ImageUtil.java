package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.fastdfs.config.FastdfsProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 图片路径拼接工具类
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public class ImageUtil{


    /**图片访问的前缀*/
    public static String PREFIX_IMAGE_URL =
            SpringContextHolder.getBean(FastdfsProperties.class).getVisit();

    /**
     * @Description 裁剪 图片的URL
     * 比如将http://172.16.1.200:10080/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * 截取为 group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * @Date 2017/8/17 15:33
     * @param targetImageURL 目标的图片的URL
     * @throws
     * @return
     */
    public  static String cutImageURL(String targetImageURL) {

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
     * 拼接多个以","隔开的图片字符串
     *
     * @param imgstr 以","隔开的图片字符串
     * @return
     */
    public static List<String> setImageStrURL2List(String imgstr) {
        if (ToolUtil.isEmpty(imgstr)) {
            return null;
        }
        List<String> imgList = Arrays.asList(imgstr.split(","));
        for (int i = 0, length = imgList.size(); i < length; i++) {
            imgList.set(i, ImageUtil.setImageURL(imgList.get(i)));
        }
        return imgList;
    }

    /**
     * @Description 补全 图片的URL
     * 比如将/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * 补全为 http://172.16.1.200:10080/group1/M00/00/00/rBAByFmDy-WAT7FXAALo7BXRELY084.jpg
     * @Date 2017/8/17 15:33
     * @param targetImageURL 目标的图片的URL
     * @throws
     * @return
     */
    public  static String setImageURL(String targetImageURL) {

        if (StringUtils.isEmpty(targetImageURL) || "null".equals(targetImageURL)) {
            return "";
        }
        int imageIndex = targetImageURL.indexOf(PREFIX_IMAGE_URL);
        if (imageIndex > -1) {
            return targetImageURL;
        }
        return PREFIX_IMAGE_URL + targetImageURL;
    }
}
