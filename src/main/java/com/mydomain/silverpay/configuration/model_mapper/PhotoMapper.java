package com.mydomain.silverpay.configuration.model_mapper;


import com.mydomain.silverpay.data.dto.site.panel.photo.PhotoReturnProfileDto;
import com.mydomain.silverpay.data.model.mainDB.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper instance= Mappers.getMapper(PhotoMapper.class);

    PhotoReturnProfileDto photoReturnProfileMapper(Photo photo);

}
