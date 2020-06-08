package cn.ibdsr.web.core.util;

import cn.ibdsr.core.check.CheckUtil;

public class RegUtil extends CheckUtil {
    public static final String ACCOUNT = "^[0-9a-z]{4,20}$";
    public static final String PASSWORD = "^[\\da-zA-Z_!@#$%^&*]{6,30}$";
    public static final String NAME = "^[\\u4e00-\\u9fa5]{2,5}$";
    /**角色名称，1-8个汉字*/
    public static final String ROLE_NAME = "^[\\u4e00-\\u9fa5]{1,8}$";
    /**
     * 企业名称，2-20个汉字
     */
    public static final String COMPANY_NAME = "^[\\u4e00-\\u9fa5]{2,20}$";
    /**
     * 联系人名称，2-4个汉字
     */
    public static final String CONCAT_NAME = "^[\\u4e00-\\u9fa5]{2,6}$";
}
