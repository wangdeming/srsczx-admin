package cn.ibdsr.web.common.constant.state;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件后缀类型
 */
public enum ResumeExtType {

    DOC(0, "doc"), DOCX(1, "docx"), PDF(1, "pdf"),;

    int code;
    String message;

    ResumeExtType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (ResumeExtType temp : ResumeExtType.values()) {
                if (temp.getCode() == value) {
                    return temp.getMessage();
                }
            }
            return "";
        }
    }

    public static ResumeExtType objOf(String message) {
        if (StringUtils.isEmpty(message)) {
            return null;
        } else {
            for (ResumeExtType temp : ResumeExtType.values()) {
                if (temp.getMessage().equals(message)) {
                    return temp;
                }
            }
            return null;
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
