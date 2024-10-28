package com.viper.apiserver.config;

import com.viper.apiserver.controller.formatter.LocalDateFomatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("------------------------");
        log.info("addFormatters");

        registry.addFormatter(new LocalDateFomatter());
    }
}