package com.sxrekord.chatting.config;

import com.sxrekord.chatting.interceptor.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Rekord
 * @date 2022/9/10 21:28
 */

@Component
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UriConfig uriConfig;
    @Autowired
    UserAuthInterceptor userAuthInterceptor;

    @Value("#{ T(java.lang.System).getProperty('os.name').contains('Windows') ? '${file.upload.location.windows}' : '${file.upload.location.linux}' }")
    private String FILE_STORE_PATH;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书 不再默认开启
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthInterceptor)
                .addPathPatterns(uriConfig.getIncludeUri())
                .excludePathPatterns(uriConfig.getExcludeUri());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /UploadFile/xxx --> ${uploadLocation}/xxx
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + FILE_STORE_PATH);

//        /UploadFile/xxx --> classpath:/UploadFile/xxx
//        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/");
    }
}