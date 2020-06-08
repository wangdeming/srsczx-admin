package cn.ibdsr.web.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要做业务日志的方法
 *
 * @author fengshuonan
 * @date 2017-03-31 12:46
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String name() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    /**
     * 类型分类看：BussinessLogType
     * 详细日志形式为：key=value;;;字段名称:字段1,旧值:value1,新值:value2;;;字段名称:字段2,旧值:value3,新值:value4;;;
     * 简单日志形式为：key=value，说明如下：
     * 1、当类型为reqsimplelog时需要request请求中带有key，并根据key进行包装。
     * 2、当类型为noreqsimplelog时不需要request请求中带有key，key=value由用户去拼接。
     */
    String logType() default "reqsimplelog";

    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
    String dict() default "SystemDict";
}
