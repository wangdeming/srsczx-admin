package cn.ibdsr.web.common.constant;

/**
 * 一些服务的快捷获取
 *
 * @author fengshuonan
 * @date 2017-03-30 15:58
 */
public class Cst {

    private static Cst cst = new Cst();

    private Cst() {
    }

    public static Cst me() {
        return cst;
    }

}
