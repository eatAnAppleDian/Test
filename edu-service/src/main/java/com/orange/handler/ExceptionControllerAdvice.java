package com.orange.handler;

import com.orange.exception.ApplicationException;
import com.orange.exception.ResourceNotFoundException;
import com.orange.vo.R;
import com.orange.vo.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    // 无权限时会执行该方法
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e){
        return R.fail().code(ResultCode.LOGIN_ACL).message("没有访问权限!");
    }

    // GET请求中@Valid验证参数失败后抛出异常BindException
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class})
    public R<Void> handlerBindException(BindException e) {
        StringBuilder messages = new StringBuilder(1024);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            messages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n\r");
        }
        return R.fail(ResultCode.PARAM_VALID_ERROR).message(messages.toString());
    }

    // @RequestParam参数校验失败后抛出异常ConstraintViolationException
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public R<Void> handlerMethodArgumentNotValidException(ConstraintViolationException e) {
        StringBuilder messages = new StringBuilder(1024);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation error : constraintViolations) {
            messages.append(error.getPropertyPath().toString()).append(": ").append(error.getMessage()).append("\n\r");
        }
        return R.fail(ResultCode.PARAM_VALID_ERROR).message(messages.toString());
    }

    // @RequestBody参数校验失败后抛出异常MethodArgumentNotValidException
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder messages = new StringBuilder(1024);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            messages.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n\r");
        }
        return R.fail(ResultCode.PARAM_VALID_ERROR).message(messages.toString());
    }

    // 不支持的请求方法
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<Void> httpRequestMethodNotSupportedException(HttpServletResponse response) {
        return R.fail(ResultCode.METHOD_NOT_SUPPORTED);
    }

    // 不支持的媒体类型
    @ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R<Void> httpMediaTypeNotSupportedException(HttpServletResponse response) {
        return R.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED);
    }

    // 处理资源未发现异常ResourceNotFoundException
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public R<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        return R.fail(ResultCode.NOT_FOUND).message(e.getMessage());
    }

    // 没有发现资源 404
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public R<Void> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return R.fail(ResultCode.NOT_FOUND).message(e.getMessage());
    }

    // 处理自定义异常ApplicationException
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApplicationException.class)
    public R<Void> ApplicationExceptionHandler(ApplicationException e) {
        // 异常中指定的错误代码，消息按优先级合并在异常的message中
        return R.fail(e.getResultCode()).message(e.getMessage());
    }

    // 通常在最后的方法处理最大的Exception，保证兜底
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public R<Void> exceptionHandler(HttpServletRequest request, Exception e) {
        return R.fail(ResultCode.UNKNOWN).message(e.getMessage());
    }
}