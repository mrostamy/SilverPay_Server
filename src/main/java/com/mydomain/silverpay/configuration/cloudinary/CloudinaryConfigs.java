package com.mydomain.silverpay.configuration.cloudinary;

import com.cloudinary.Cloudinary;
import com.mydomain.silverpay.repository.MainDB.ISettingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfigs {

    private final ISettingRepository repository;

    public CloudinaryConfigs(ISettingRepository repository) {
        this.repository = repository;
    }

    @Bean
    @Scope(scopeName = "singleton")
    public Cloudinary cloudinaryConfig() {

        var setting=repository.findById(1).orElse(null);


        Map<String,String> config=new HashMap<>();

        assert setting != null;
        config.put("cloud_name",setting.getCloudinaryName());
        config.put("api_key",setting.getCloudinaryApiKey());
        config.put("api_secret",setting.getCloudinaryApiSecret());


        return new Cloudinary(config);
    }
}
