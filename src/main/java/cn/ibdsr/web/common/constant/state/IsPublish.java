package cn.ibdsr.web.common.constant.state;

/**
 * @Description 是否发布
 * @Version V1.0
 * @CreateDate 2019/4/26 13:43
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/26      wangzhipeng            类说明
 */
public enum IsPublish {
    UNPUBLISH(0, "未发布"), PUBLISH(1, "已发布");

    int code;
    String message;

    IsPublish(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (IsPublish ms : IsPublish.values()) {
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
