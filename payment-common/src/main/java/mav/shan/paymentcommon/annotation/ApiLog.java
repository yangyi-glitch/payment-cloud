package mav.shan.paymentcommon.annotation;

import java.lang.annotation.*;

/**
 * 接口日志注解
 * 用于标记需要记录日志的接口方法
 * 如果不使用此注解，默认所有Controller方法都会记录日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {
    /**
     * 是否记录请求参数，默认true
     */
    boolean logParams() default true;
    
    /**
     * 是否记录返回值，默认true
     */
    boolean logResult() default true;
    
    /**
     * 接口描述，用于日志记录
     */
    String description() default "";
}
