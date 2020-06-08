package cn.ibdsr.web.common.constant.state;

/**
 * 删除标识
 *
 * @author lvyou
 * @Date 2019年7月25日 上午9:54:13
 */
public enum  DeleteStatus {

    DELETED(1, "已删除"), UN_DELETED(0, "未删除");

    int code;
    String message;

    DeleteStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (DeleteStatus temp : DeleteStatus.values()) {
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
