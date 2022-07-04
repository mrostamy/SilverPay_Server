package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.users.UserDetailDto;
import com.mydomomain.silverpay.model.Photo;
import com.mydomomain.silverpay.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserMapping {

//    UserMapping instance=ge;
    @Mapping(source = "photos", target = "photoUrl", qualifiedByName = "pUrl")
    @Mapping(source = "dateOfBirth",target = "age",qualifiedByName = "2age")
    UserDetailDto detailDto(User user);

    @Named("pUrl")
    static String getPhotoUrl(List<Photo> photoLisT) {

        return photoLisT.stream().findFirst().get().getPhotoUrl();
    }

    @Named("2age")
    static int getAge(String date) {

        return 555;


    }





}
