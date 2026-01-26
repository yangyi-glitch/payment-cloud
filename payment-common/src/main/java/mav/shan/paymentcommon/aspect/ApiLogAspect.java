package mav.shan.paymentcommon.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import mav.shan.paymentcommon.annotation.ApiLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 接口日志记录切面
 * 自动记录所有Controller方法的请求和响应信息
 */
@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    /**
     * 定义切点：拦截所有标注了 @RestController 或 @Controller 的类的方法
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) || " +
             "@within(org.springframework.stereotype.Controller)")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        
        // 获取方法信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        Object[] args = point.getArgs();
        
        // 获取注解信息（如果有）
        ApiLog apiLog = method.getAnnotation(ApiLog.class);
        boolean logParams = apiLog == null || apiLog.logParams();
        boolean logResult = apiLog == null || apiLog.logResult();
        String description = apiLog != null ? apiLog.description() : "";
        
        // 构建请求日志
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("\n========== 接口请求开始 ==========\n");
        
        if (request != null) {
            requestLog.append("请求URL: ").append(request.getRequestURL().toString()).append("\n");
            requestLog.append("请求方法: ").append(request.getMethod()).append("\n");
            requestLog.append("请求IP: ").append(getClientIp(request)).append("\n");
            String queryString = request.getQueryString();
            if (queryString != null && !queryString.isEmpty()) {
                requestLog.append("查询参数: ").append(queryString).append("\n");
            }
        }
        
        requestLog.append("类名: ").append(className).append("\n");
        requestLog.append("方法名: ").append(methodName).append("\n");
        
        if (!description.isEmpty()) {
            requestLog.append("接口描述: ").append(description).append("\n");
        }
        
        if (logParams && args != null && args.length > 0) {
            // 过滤敏感参数（可根据需要扩展）
            Object[] filteredArgs = filterSensitiveParams(args);
            requestLog.append("请求参数: ").append(JSON.toJSONString(filteredArgs)).append("\n");
        }
        
        log.info(requestLog.toString());
        
        Object result = null;
        try {
            // 执行目标方法
            result = point.proceed();
            
            // 构建响应日志
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            
            StringBuilder responseLog = new StringBuilder();
            responseLog.append("\n========== 接口响应成功 ==========\n");
            responseLog.append("类名: ").append(className).append("\n");
            responseLog.append("方法名: ").append(methodName).append("\n");
            
            if (logResult && result != null) {
                String resultStr = JSON.toJSONString(result);
                // 如果返回结果太长，截断
                if (resultStr.length() > 1000) {
                    resultStr = resultStr.substring(0, 1000) + "...(内容过长已截断)";
                }
                responseLog.append("返回值: ").append(resultStr).append("\n");
            }
            
            responseLog.append("执行时间: ").append(executeTime).append("ms\n");
            
            log.info(responseLog.toString());
            
            return result;
        } catch (Throwable e) {
            // 记录异常日志
            long endTime = System.currentTimeMillis();
            long executeTime = endTime - startTime;
            
            StringBuilder errorLog = new StringBuilder();
            errorLog.append("\n========== 接口异常 ==========\n");
            errorLog.append("类名: ").append(className).append("\n");
            errorLog.append("方法名: ").append(methodName).append("\n");
            errorLog.append("异常类型: ").append(e.getClass().getName()).append("\n");
            errorLog.append("异常信息: ").append(e.getMessage()).append("\n");
            errorLog.append("执行时间: ").append(executeTime).append("ms\n");
            
            log.error(errorLog.toString(), e);
            
            throw e;
        }
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多个IP，取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "unknown";
    }

    /**
     * 过滤敏感参数（可根据需要扩展）
     */
    private Object[] filterSensitiveParams(Object[] args) {
        if (args == null || args.length == 0) {
            return args;
        }
        
        // 这里可以实现敏感参数过滤逻辑
        // 例如：如果参数包含 password、token 等字段，则不记录或脱敏
        
        // 简单实现：直接返回原参数
        // 实际项目中可以根据参数类型或字段名进行过滤
        return args;
    }
}
