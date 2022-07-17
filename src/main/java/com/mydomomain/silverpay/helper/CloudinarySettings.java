package com.mydomomain.silverpay.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CloudinarySettings {

    @Value("${cloudinary-cloud-name}")
    public String cloudinary_cloud_name;

    @Value("${cloudinary-secret-key}")
    String cloudinary_secret_key;

    @Value("${cloudinary-api-key}")
    String cloudinary_api_key;

}
