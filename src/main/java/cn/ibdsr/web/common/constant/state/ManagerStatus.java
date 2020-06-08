package cn.ibdsr.web.common.constant.state;

/**
 * 管理员的状态
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:54:13
 */
public enum ManagerStatus {

    OK(1, "正常"), FREEZED(2, "已冻结"), DELETED(3, "被删除");

    int code;
    String message;

    ManagerStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (ManagerStatus ms : ManagerStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
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
