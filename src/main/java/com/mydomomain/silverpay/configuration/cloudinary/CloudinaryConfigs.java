package com.mydomomain.silverpay.configuration.cloudinary;

import com.mydomomain.silverpay.helper.CloudinarySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfigs {

    @Bean
    public CloudinarySettings cloudinarySettings() {
        return new CloudinarySettings();
    }
}
