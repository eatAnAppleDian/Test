package com.orange.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.security.entity.SecurityUser;
import com.orange.security.util.JwtTokenUtil;
import com.orange.vo.R;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author orange
 * 登录成功
 */
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		//这里返回JSON数据
		String token = JwtTokenUtil.generateTokenByRSA(authentication.getName());
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("user", ((SecurityUser)authentication.getPrincipal()).getCurrentUser());
		R success = R.success().data(map);

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpStatus.OK.value());
		response.getWriter().write(objectMapper.writeValueAsString(success));
	}
}