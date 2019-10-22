package com.yfr.config;

import jdk.nashorn.internal.ir.LiteralNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration//声明是一个spring配置类
public class GlobalCorsConfig {

    @Bean
    //这里要使用web包下的CorsFilter,在这里我们只要输入对应规则的CorsFilter过滤器对象，spring MVC会帮我们实现对应的跨域问题
    public CorsFilter corsFilter(){

        //配置CORS返回给浏览器的信息
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //设置可以进行跨域的域名
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com:8888");
        //跨域时可以携带cookie
        corsConfiguration.setAllowCredentials(true);
        //允许可以进行跨域的HTTP请求方式
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT"));
        //允许的头信息
        corsConfiguration.addAllowedHeader("*");

        //添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        //返回新的CorsFilter.
        return new CorsFilter(source);
    }
}
