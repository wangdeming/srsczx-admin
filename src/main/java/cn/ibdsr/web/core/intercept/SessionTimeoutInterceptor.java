package cn.ibdsr.web.core.intercept;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.support.HttpKit;
import cn.ibdsr.web.core.shiro.ShiroKit;
import org.apache.shiro.session.InvalidSessionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 验证session超时的拦截器
 *
 * @author fengshuonan
 * @date 2017年6月7日21:08:48
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "guns", name = "session-open", havingValue = "true")
public class SessionTimeoutInterceptor extends BaseController {

    @Pointcut("execution(* cn.ibdsr.web.*..controller.*.*(..))")
    public void cutService() {
    }

    @Around("cutService()")
    public Object sessionTimeoutValidate(ProceedingJoinPoint point) throws Throwable {

        String servletPath = HttpKit.getRequest().getServletPath();

        if ("/kaptcha".equals(servletPath) || "/login".equals(servletPath) || "/global/sessionError".equals(servletPath)) {
            return point.proceed();
        } else {
            if (ShiroKit.getSession().getAttribute("sessionFlag") == null) {
                ShiroKit.getSubject().logout();
                throw new InvalidSessionException();
            } else {
                return point.proceed();
            }
        }
    }
}
