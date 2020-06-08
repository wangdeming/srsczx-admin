package cn.ibdsr.web.common.constant.state;

/**
 * 新闻资讯栏目类型(1:新闻动态;2:媒体报道;3:合作交流;)
 */
public enum PolicyLevel {
    COUNTRY(1, "国家"),
    PROVINCE(2, "省级"),
    CITY(3, "市级"),
    DISTRICT(4, "区县级");

    int code;
    String message;

    PolicyLevel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (PolicyLevel s : PolicyLevel.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
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
