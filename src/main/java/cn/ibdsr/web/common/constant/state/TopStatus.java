package cn.ibdsr.web.common.constant.state;

/**
 * 是否置顶：0-未置顶；1-已置顶
 */
public enum TopStatus {

    UN_TOP(0, "未置顶"), TOP(1, "已置顶");

    int code;
    String message;

    TopStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (TopStatus temp : TopStatus.values()) {
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
