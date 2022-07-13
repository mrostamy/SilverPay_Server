package com.mydomomain.silverpay;

import com.mydomomain.silverpay.configuration.security.TestInterceptor;
import com.mydomomain.silverpay.service.userService.seedService.SeedService;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//(exclude = {SecurityAutoConfiguration.class})

@SpringBootApplication
public class SilverPayApplication {

    final SeedService seedService;

    public SilverPayApplication(SeedService seedService) {
        this.seedService = seedService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SilverPayApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","OPTION");
            }

//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new TestInterceptor());
//            }


        };




    }





}
