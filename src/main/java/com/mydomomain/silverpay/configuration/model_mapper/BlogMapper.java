package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.blog.BlogCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.blog.BlogReturnDto;
import com.mydomomain.silverpay.model.User;
import com.mydomomain.silverpay.model.blog.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogMapper {

    BlogMapper instance = Mappers.getMapper(BlogMapper.class);


    @Mapping(source = "user", target = "name", qualifiedByName = "mapName")
    @Mapping(source = "user", target = "userName", qualifiedByName = "mapUserName")
    BlogReturnDto blogToReturnDto(Blog blog);

    List<BlogReturnDto> blogsToReturnDtoes(List<Blog> blog);

    Blog createDtoToBlog(BlogCreateUpdateDto createUpdateDto);

    Blog updateDtoToBlog(BlogCreateUpdateDto createUpdateDto);


    @Named("mapName")
    default String mapName(User user) {

        return user.getName();
    }

    @Named("mapUserName")
    default String mapNameUser(User user) {

        return user.getName();
    }


}
