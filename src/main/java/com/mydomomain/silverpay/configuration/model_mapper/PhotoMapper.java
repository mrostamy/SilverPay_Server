package com.mydomomain.silverpay.configuration.model_mapper;


import com.mydomomain.silverpay.dto.site.panel.photo.PhotoReturnProfileDto;
import com.mydomomain.silverpay.model.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper instance= Mappers.getMapper(PhotoMapper.class);

    PhotoReturnProfileDto photoReturnProfileMapper(Photo photo);

}
