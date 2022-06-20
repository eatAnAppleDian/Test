package com.orange.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.vo.R;
import com.orange.vo.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author orange
 * 处理未登录或登录失败响应
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        // 401 表示要求客户端认证
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 返回错误消息
        ObjectMapper objectMapper = new ObjectMapper();
        R result = R.fail().code(ResultCode.LOGIN_AUTH).message("用户未登录或Token超期，请重新登录?");
        resp.getWriter().write(objectMapper.writeValueAsString(result));
    }
}