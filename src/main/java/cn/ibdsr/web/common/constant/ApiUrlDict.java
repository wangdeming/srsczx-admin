package cn.ibdsr.web.common.constant;

/**
 * @Description API接口常量类
 * @Version V1.0
 * @CreateDate 2019/7/16 17:33
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/7/16      lvyou                API接口常量类
 */
public interface ApiUrlDict {

    //获取景区管理员信息接口地址
    String DETAIL_USER_BY_AREAID = "/api/user/detailUserByAreaId";

    //景区管理员授权接口地址
    String USER_AUTH = "/api/user/auth";

    //重置景区管理员密码接口地址
    String RESET_PWS = "/api/user/resetPwd";

    //创建系统角色接口地址
    String INIT_SYSTEM_ROLE = "/api/user/initSystemRole";

    //获取采集字段列表接口地址
    String DATA_FIELD_LIST_BY_CHANNEL_TYPE = "/api/dataField/listByChannelType";
}
