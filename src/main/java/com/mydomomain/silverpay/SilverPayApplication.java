package com.mydomomain.silverpay;

import com.mydomomain.silverpay.service.userService.seedService.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//(exclude = {SecurityAutoConfiguration.class})

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class })
public class SilverPayApplication implements CommandLineRunner {

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
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }

    @Override
    public void run(String... args) throws Exception {


       // seedService.seedUsers();

    }
}
