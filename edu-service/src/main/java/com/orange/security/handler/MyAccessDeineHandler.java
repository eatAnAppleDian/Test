package com.orange.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.vo.R;
import com.orange.vo.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 如果用户访问没有权限的功能，就会抛出AccessDeniedException异常。
 * Spring Security中ExceptionTranslationFilter用于捕获AccessDeniedException，
 * 然后使用AccessDeineHandler开启权限异常的处理流程。
 * @author orange
 */
@Component
public class MyAccessDeineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // 403 表示要求客户端认证
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // 返回错误消息
        ObjectMapper objectMapper = new ObjectMapper();
        R result = R.fail().code(ResultCode.LOGIN_AUTH).message("没有访问权限!");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}