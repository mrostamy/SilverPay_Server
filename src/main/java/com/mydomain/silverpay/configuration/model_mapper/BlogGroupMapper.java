package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.site.panel.blogGroup.BlogGroupCreateUpdateDto;
import com.mydomain.silverpay.data.dto.site.panel.blogGroup.BlogGroupReturnDto;
import com.mydomain.silverpay.data.model.mainDB.blog.BlogGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogGroupMapper {

    BlogGroupMapper instance = Mappers.getMapper(BlogGroupMapper.class);

    BlogGroupReturnDto blogGroupToReturnDtoMapper(BlogGroup blogGroup);

    BlogGroup blogGroupMapper(BlogGroupCreateUpdateDto blogGroupReturnDto );

    List<BlogGroupReturnDto> BlogGroupMapper(List<BlogGroup> BlogGroup);

}
