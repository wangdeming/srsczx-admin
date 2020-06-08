package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.config.properties.WebProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     *
     * @author stylefeng
     * @Date 2017/5/23 22:34
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(WebProperties.class).getKaptchaOpen();
    }
}