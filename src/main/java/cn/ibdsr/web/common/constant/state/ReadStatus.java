package cn.ibdsr.web.common.constant.state;

/**
 * 阅读状态：0-未读；1-已读
 */
public enum ReadStatus {

    UNREAD(0, "未读"), READ(1, "已读");

    int code;
    String message;

    ReadStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (ReadStatus temp : ReadStatus.values()) {
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
