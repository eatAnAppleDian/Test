package com.orange.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @author orange
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    private static final String TIME_ZONE = "GMT+8";
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册权限拦截器
        // registry.addInterceptor(new RoleInterceptor())
        //         // 设置拦截资源路径
        //         .addPathPatterns("/**")
        //         // 设置排除资源路径
        //         .excludePathPatterns("/edu/login", "/edu/captcha")
        //         // 以下为设置swagger-ui相关资源的排除
        //         .excludePathPatterns("/swagger-resources/**", "/webjars/**")
        //         .excludePathPatterns("/v3/**", "/swagger-ui.html", "/swagger-ui/**")
        //         .excludePathPatterns("/h5/**", "/mgr/**");
    }

    //todo Cors它允许浏览器向跨源服务器，发出 XMLHttpRequest 请求(ajax请求)，从而克服了AJAX只能 同源 使用的限制。
    //该配置优先级低于拦截器，所以打开拦截器后会出现跨域问题，因为拦截器是特殊的过滤器，所以可以通过配置过滤器方式解决
    //@Override
    //public void addCorsMappings(CorsRegistry registry) {
    //    //添加映射路径，“/**”表示允许所有路径允许跨域访问
    //    registry.addMapping("/**")
    //            //开放哪些ip，端口，域名的访问权限
    //            //*不能满足携带cookie的访问，全匹配才能满足
    //            .allowedOrigins("http://localhost:3000")
    //            .allowedMethods("*")
    //            .allowedHeaders("*")
    //            .maxAge(3600)//表示在3600秒内不再发送预检验请求，用于缓存该结果
    //            .allowCredentials(true);//允许携带cookie
    //            //.exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
    //}
//    前端使用代理后后端可以不配置跨域设置
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        //获取跨域配置对象
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);    //cookie
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("http://localhost:3000");
//        //cors对所有接口都有效
//        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource));
//        registrationBean.setOrder(0);
//        return registrationBean;
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //todo 配置日期格式转换（MVC来处理，只能处理表单提交）
        // 配置Date类型的各种转换格式
        final DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.setFallbackPatterns("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy/MM/dd");
        registry.addFormatter(dateFormatter);
        // 配置JDK8的新日期API
        final DateTimeFormatterRegistrar formatterRegistrar = new DateTimeFormatterRegistrar();
        formatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm:ss"));
        formatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        formatterRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        formatterRegistrar.registerFormatters(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 在系统默认注册完成后，才会在这里注册HttpMessageConverter
        // 这里不能直接创建新的MappingJackson2HttpMessageConverter对象添加，会影响swagger-ui使用
        for (HttpMessageConverter<?> converter : converters) {
            //todo Jackson来处理（json方式）日期格式转换
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper objectMapper = new ObjectMapper();
                // 指定时区
                objectMapper.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
                // 日期类型字符串处理
                objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATETIME_PATTERN));
                //todo 设置参数为null时不序列化
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                // Java8日期日期处理
                JavaTimeModule javaTimeModule = new JavaTimeModule();
                javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
                javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
                javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
                javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
                javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
                javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
                //todo 解决JavaScript无法表示Long类型的问题，因为Long超出了JavaScript中number类型的范围导致精度丢失
                javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
                objectMapper.registerModule(javaTimeModule);
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
            }
        }
    }
}
