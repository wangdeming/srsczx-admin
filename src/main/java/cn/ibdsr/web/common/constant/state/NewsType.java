package cn.ibdsr.web.common.constant.state;

/**
 * 新闻资讯栏目类型(1:新闻动态;2:媒体报道;3:合作交流;)
 */
public enum NewsType {
    NEWS_TRENDS(1, "新闻资讯"),
    MEDIA(2, "中心动态"),
    COOPERATION(3, "重要通知");

    int code;
    String message;

    NewsType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (NewsType s : NewsType.values()) {
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
