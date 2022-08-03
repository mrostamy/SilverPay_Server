package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.document.DocumentCreateDto;
import com.mydomomain.silverpay.dto.site.panel.document.DocumentReturnDto;
import com.mydomomain.silverpay.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DocumentMapper {

    DocumentMapper instance = Mappers.getMapper(DocumentMapper.class);

    DocumentReturnDto docToReturnDto(Document document);

    Document dtoToDocument(DocumentCreateDto dto);

}
