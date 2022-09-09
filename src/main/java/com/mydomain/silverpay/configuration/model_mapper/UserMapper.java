package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserDetailDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserListDto;
import com.mydomain.silverpay.data.dto.app.panel.upload.users.UserUpdateDto;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import com.mydomain.silverpay.data.model.mainDB.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "photos", target = "photoUrl", qualifiedByName = "pUrl")
    @Mapping(source = "dateOfBirth", target = "age", qualifiedByName = "2age")
    UserDetailDto detailDto(User user);


    UserListDto userListDto(User user);


    User user(UserUpdateDto userUpdateDto);

//    UserDetailDto userReturnDto(User user);

    List<UserDetailDto> userReturnDto(List<User> user);


    @Named("pUrl")
    static String getPhotoUrl(List<Photo> photoLisT) {

        return photoLisT.stream().findFirst().get().getPhotoUrl();
    }

    @Named("2age")
    static int getAge(String date) {

        return 555;


    }


}
