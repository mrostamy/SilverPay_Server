package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.users.UserDetailDto;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
@Lazy
public class ModelMapperConfig {

    @Bean
    @Scope(value = "singleton")
    public ModelMapper modelMapper() {

        Converter<List<Photo>,String> testConverter=mappingContext ->{
            System.out.println("????");
          return   mappingContext.getSource().stream().filter(Photo::getIsMain).findFirst().get().getPhotoUrl();
        };



        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDetailDto>() {
            @Override
            protected void configure() {
                using(testConverter);
                map().setAge(12);
            }
        });

        modelMapper.addConverter(testConverter);

        return modelMapper;



    }


}
