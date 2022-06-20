package com.orange.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author orange
 */
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 登录请求必须是POST
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        /*
        1. 既支持JSON形式传递参数，也支持key/value形式传递参数
        2. 通过contentType来判断当前请求。
        3. JSON传参则解析JSON，否则super.attemptAuthentication调用父类处理逻辑
         */
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
    || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;

            try(InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = objectMapper.readValue(is, Map.class);
                // 构造 UsernamePasswordAuthenticationToken对象
                authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"),
                                        authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                // 出错后创建一个空Token
                authRequest = new UsernamePasswordAuthenticationToken("","");
            }

            // 调用父类方法
            super.setDetails(request, authRequest);
            return super.getAuthenticationManager().authenticate(authRequest);
        } else {
            // 调用super.attemptAuthentication方法进入父类的处理逻辑
            return super.attemptAuthentication(request, response);
        }
    }
}