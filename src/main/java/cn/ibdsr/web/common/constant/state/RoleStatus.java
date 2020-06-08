package cn.ibdsr.web.common.constant.state;

/**
 * 角色状态
 *
 * @author lvyou
 * @Date 2017年1月10日 下午9:54:13
 */
public enum RoleStatus {
    ENABLE(1, "启用"), DISABLE(2, "禁用");

    int code;
    String message;

    RoleStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (RoleStatus temp : RoleStatus.values()) {
                if (temp.getCode() == value) {
                    return temp.getMessage();
                }
            }
            return "";
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
