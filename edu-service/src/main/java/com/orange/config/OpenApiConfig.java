package com.orange.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author orange
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        // 名字和创建的SecuritySchemes一致
        SecurityRequirement sr= new SecurityRequirement().addList("bearerAuth");
        List<SecurityRequirement> list = new ArrayList<>();
        list.add(sr);

        return new OpenAPI()
                //安全携带Authorize
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")))
                .security(list)
                .info(new Info().title("标题 API")
                        .description("描述")
                        .version("v1.0.0")
                        .license(new License().name("许可证").url("https://www.baidu.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("详情请点击此处")
                        .url("https://xxxx.4399.com"));
    }
}