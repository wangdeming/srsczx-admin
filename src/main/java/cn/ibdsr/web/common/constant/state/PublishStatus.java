package cn.ibdsr.web.common.constant.state;

/**
 * 是否发布：0-未发布；1-已发布
 */
public enum PublishStatus {

    UNPUBLISHED(0, "未发布"), PUBLISHED(1, "已发布");

    int code;
    String message;

    PublishStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (PublishStatus temp : PublishStatus.values()) {
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
