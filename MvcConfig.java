package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry vcr) {
        vcr.addViewController("/login").setViewName("login");
        vcr.addViewController("/new-ad").setViewName("anunciar");
        vcr.addViewController("/search-ads").setViewName("procurar");
    }
}
