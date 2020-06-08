package cn.ibdsr.web.common.constant.dictmap.systemdict;


/**
 * 字典常量
 *
 * @author fengshuonan
 * @date 2017年5月16日21:44:56
 */
public interface SystemDict {

     static final String basePath = "cn.ibdsr.web.common.constant.dictmap.systemdict.";

    /**
     * 系统管理员字典
     */
    String UserDict = basePath+"UserDict";

    /**
     * 角色管理员字典
     */
    String RoleDict = basePath+"RoleDict";

    /**
     * 删除业务的字典
     */
    String DeleteDict = basePath+"DeleteDict";

    /**
     * 部门管理业务的字典
     */
    String DeptDict = basePath+"DeptDict";

    /**
     * 菜单管理业务的字典
     */
    String MenuDict = basePath+"MenuDict";

    /**
     * 字典管理业务的字典
     */
    String DictMap = basePath+"DictMap";

    /**
     * 通知管理业务的字典
     */
    String NoticeMap = basePath+"NoticeMap";

}
