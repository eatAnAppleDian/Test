package com.orange.security.handler;

import org.springframework.security.access.AccessDeniedException;

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
public interface AccessDeniedHandler extends org.springframework.security.web.access.AccessDeniedHandler {

}