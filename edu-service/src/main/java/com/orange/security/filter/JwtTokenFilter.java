package com.orange.security.filter;

import cn.hutool.core.util.StrUtil;
import com.orange.security.entity.SecurityUser;
import com.orange.security.entity.TokenPayLoad;
import com.orange.security.util.JwtTokenUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author orange
 * 判断Token是否超期，如果超期则抛出BadCredentialsException，
 * 它会被AuthenticationEntryPoint实现类处理。
 */
@Slf4j
public class JwtTokenFilter extends GenericFilterBean {
    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        try {
            // 获取Token头
            String jwtToken = request.getHeader("authorization");
            log.debug("后台检查令牌:{}", jwtToken);

            if (StrUtil.isNotBlank(jwtToken)) {
                // 获取Token
                jwtToken = jwtToken.replace("Bearer","").trim();
                // 解析Token
                TokenPayLoad tokenPayLoad = JwtTokenUtil.verifyTokenByRSA(jwtToken);

                // 获取当前登录用户名
                String username = tokenPayLoad.getSub();
                // 从数据库中查询用户信息
                SecurityUser securityUser = (SecurityUser)userDetailsService.loadUserByUsername(username);

                // 创建UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(username, null, securityUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            filterChain.doFilter(request, servletResponse);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            // 认证失败，则重新认证。
            // 注意：这里异常不能被配置的authenticationEntryPoint所处理
            this.authenticationEntryPoint.commence(request, response, e);
        }
    }
}