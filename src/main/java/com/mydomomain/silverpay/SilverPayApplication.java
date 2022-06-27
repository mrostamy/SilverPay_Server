package com.mydomomain.silverpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//(exclude = {SecurityAutoConfiguration.class})

@SpringBootApplication
public class SilverPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverPayApplication.class, args);
    }

}
