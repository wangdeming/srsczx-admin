package generator;

import java.util.Date;

import static cn.ibdsr.core.util.DateUtil.formatDate;

/**
 * @Description 类功能和用法的说明
 * @Version V1.0
 * @CreateDate 2019/3/13 14:18
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/13      wangzhipeng            类说明
 */
public class DateUtil {
    /**
     * 获取yyyy/M/d H:m格式
     *
     * @return
     */
    public static String currentDateTime() {
        return formatDate(new Date(), "yyyy/M/d H:m");
    }

    /**
     * 获取yyyy/M/d格式
     *
     * @return
     */
    public static String currentDate() {
        return formatDate(new Date(), "yyyy/M/d");
    }
}
