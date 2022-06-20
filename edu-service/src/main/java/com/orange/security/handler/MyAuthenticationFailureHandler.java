package com.orange.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.vo.R;
import com.orange.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author orange
 * 登录失败
 */
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String msg = "登录失败";
		if (exception instanceof LockedException) {
			msg = "账户被锁定，请联系管理员!";
		} else if (exception instanceof CredentialsExpiredException) {
			msg = "密码过期，请联系管理员!";
		} else if (exception instanceof AccountExpiredException) {
			msg = "账户过期，请联系管理员!";
		} else if (exception instanceof DisabledException) {
			msg = "账户被禁用，请联系管理员!";
		} else if (exception instanceof BadCredentialsException) {
			// 用户名或者密码输入错误，一般只给一个模糊的提示，即「用户名或者密码输入错误，请重新输入」
			msg = "用户名或者密码输入错误，请重新输入!";
		}

		response.setContentType("application/json;charset=UTF-8");
		// 认证失败后返回响应码401
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		//以Json格式返回
		R result = R.fail().code(ResultCode.LOGIN_ERROR).message(msg);
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}
}