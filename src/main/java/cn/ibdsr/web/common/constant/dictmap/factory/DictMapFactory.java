package cn.ibdsr.web.common.constant.dictmap.factory;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;
import cn.ibdsr.web.common.constant.dictmap.base.SystemDict;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;


/**
 * 字典的创建工厂
 *
 * @author fengshuonan
 * @date 2017-05-06 15:12
 */
public class DictMapFactory {

    /**
     * 通过类名创建具体的字典类
     */
    public static AbstractDictMap createDictMap(String className) {
        if("SystemDict".equals(className)){
            return new SystemDict();
        }else{
            try {

                Class<AbstractDictMap> clazz = (Class<AbstractDictMap>) Class.forName(className);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BussinessException(BizExceptionEnum.ERROR_CREATE_DICT);
            }
        }
    }
}
