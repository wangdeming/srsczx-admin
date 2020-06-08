package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.ToolUtil;

import java.io.File;

/**
 * @Description description
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/9 15:11
 * @Modifed by
 */
public class FileUtil {
    /**
     * 根据路径判断文件是否存在，如果存在则返回true
     * @param path
     * @return
     */
    public static Boolean isFileExist(String path){
        if(!ToolUtil.isEmpty(path)) {
            File file = new File(path);
            Boolean isExists = file.exists();
            return isExists;
        }else {
            return false;
        }
    }
}
