package com.viper.apiserver.config;

import com.viper.apiserver.controller.formatter.LocalDateFomatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .maxAge(500)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedOrigins("*");
        //어떤 경로에 대해서 cros를 적용할 것인가?
        //연결대기허용 시간
        //허용 메서드 OPTIONS -> pre flight (미리 한번 찔러봄) 시에 사용하므로 허용해줘야함.
        //어디에서부터 들어오는 경로에 대해서 허락해줄 것인가?
        //사실 addMapping과 allowedOrigins만 해줘도 문제는 없지만 추가 설정을 해주는 것이 좋다.

    }
}
