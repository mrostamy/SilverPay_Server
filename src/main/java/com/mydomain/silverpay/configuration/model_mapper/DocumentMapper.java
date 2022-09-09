package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.model.mainDB.Document;
import com.mydomain.silverpay.data.dto.app.panel.document.DocumentCreateDto;
import com.mydomain.silverpay.data.dto.app.panel.document.DocumentReturnDto;
import com.mydomain.silverpay.data.model.mainDB.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentMapper {

    DocumentMapper instance = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "userName", source = "user", qualifiedByName = "mapUserId")
    @Mapping(target = "userId", source = "user", qualifiedByName = "mapUserName")
    DocumentReturnDto docToReturnDto(Document document);

    Document dtoToDocument(DocumentCreateDto dto);

    @Named("mapUserId")
    default String getUserId(User user) {

        return user.getId();

    }

    @Named("mapUserName")
    default String getUserName(User user) {

        return user.getName();

    }

}
