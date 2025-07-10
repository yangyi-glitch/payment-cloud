package mav.shan.paymentframework.handler;

import lombok.extern.slf4j.Slf4j;
import mav.shan.paymentcommon.utils.ResultUtils;
import mav.shan.paymentcommon.utils.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static mav.shan.paymentcommon.utils.ResultUtils.error;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    public ResultUtils defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        return error(ex.getMessage());
    }
}
