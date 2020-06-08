package cn.ibdsr.web.core.aop;

import cn.ibdsr.core.support.HttpKit;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.constant.BussinessLogType;
import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;
import cn.ibdsr.web.common.constant.dictmap.factory.DictMapFactory;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.core.log.LogManager;
import cn.ibdsr.web.core.log.LogObjectHolder;
import cn.ibdsr.web.core.log.factory.LogTaskFactory;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.shiro.ShiroUser;
import cn.ibdsr.web.core.util.Contrast;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志记录
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午8:48:30
 */
@Aspect
@Component
public class LogAop {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "@annotation(cn.ibdsr.web.common.annotion.BussinessLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point) throws Exception {

        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();

        //如果当前用户未登录，不做日志
        ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            return;
        }

        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
        String bussinessName = annotation.name();
        String key = annotation.key();
        String dictClass = annotation.dict();
        //是否是详细日志
        String logType=annotation.logType();

        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param);
            sb.append(" & ");
        }

        //如果涉及到修改,比对变化
        String msg="";
        if (logType.equals(BussinessLogType.DETAILEDLOG)) {
            Object obj1 = LogObjectHolder.me().get();
            Map<String, String> obj2 = HttpKit.getRequestParameters();
            msg = Contrast.contrastObj(dictClass, key, obj1, obj2);
        } else if(logType.equals(BussinessLogType.REQ_SIMPLELOG)){
            Map<String, String> parameters = HttpKit.getRequestParameters();
            AbstractDictMap dictMap = DictMapFactory.createDictMap(dictClass);
            msg = Contrast.parseMutiKey(dictMap, key, parameters);
        }else {
            msg= ConstantFactory.me().getCacheObject("noreqsimplelog");
        }

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.getId(), bussinessName, className, methodName, msg));
    }
}