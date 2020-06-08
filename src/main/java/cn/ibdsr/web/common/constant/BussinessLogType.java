package cn.ibdsr.web.common.constant;

/**
 * @Description description
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/18 13:14
 * @Modifed by
 */
public interface BussinessLogType {

    /**
     * 简单日志，考虑request请求
     */
    String REQ_SIMPLELOG = "reqsimplelog";

    /**
     * 详细日志
     */
    String DETAILEDLOG = "detailedlog";

    /**
     * 简单日志，不考虑request请求
     */
    String NOREQ_SIMPLELOG="noreqsimplelog";

}
