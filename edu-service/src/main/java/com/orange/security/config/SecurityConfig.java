package com.orange.security.config;

import com.orange.security.filter.JsonAuthenticationFilter;
import com.orange.security.filter.JwtTokenFilter;
import com.orange.security.handler.MyAccessDeineHandler;
import com.orange.security.handler.MyAuthenticationEntryPoint;
import com.orange.security.handler.MyAuthenticationFailureHandler;
import com.orange.security.handler.MyAuthenticationSuccessHandler;
import com.orange.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

/**
 * @author orange
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAccessDeineHandler accessDeniedHandler;


    /**
     * 定制AuthenticationManager
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    /**
     * 更改HttpSecurity中的默认访问规则
     * 配置登录方式和访问权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 认证请求的配置
        http.authorizeRequests()
                // 1、访问权限
                /* permitAll表示放过匹配的请求 */
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                //需要的权限
                .antMatchers("whoim").hasRole("user")
                // 在access中以表达式方式调用
                .antMatchers("/edu/teacher/**").access("hasRole('admin')")
                .anyRequest().authenticated()   // 所有请求都需要认证后才能访问
                .and()     // 结束前面配置，返回HttpSecurity对象并开启新的配置
                // 2、登录方式
                .formLogin()   // 表单登录
                .and()
                //Spring Security5默认开启CSRF，这里禁用CSRF，否则无法提交表单
                //开启CSRF后，在请求中必须包含CSRF的token，否则会抛出异常
                .csrf().disable()
                //开启跨域
                .cors()
                //默认异常处理
                .and()
                .exceptionHandling()
                //未登录处理
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                //无权限异常处理
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                //禁止Session，否则有Session后可能会忽略Token，导致认证生效
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 替换UsernamePasswordAuthenticationFilter
        http.addFilterAt(jsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // // 解析JWT的Filter放在UsernamePasswordAuthenticationFilter前面
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public Filter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public Filter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter filter = new JsonAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        // 登录成功
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler());
        // 登录失败
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler());
        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeineHandler();
    }
    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    /**
     * 设置密码加密方式，默认bcrypt
     *
     * @return 返回加密后的密码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}