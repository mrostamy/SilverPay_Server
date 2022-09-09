package com.mydomain.silverpay.configuration.model_mapper;

import com.mydomain.silverpay.data.dto.app.panel.blog.BlogReturnDto;
import com.mydomain.silverpay.data.model.mainDB.blog.Blog;
import com.mydomain.silverpay.data.model.mainDB.blog.BlogGroup;
import com.mydomain.silverpay.data.dto.app.panel.blog.BlogCreateUpdateDto;
import com.mydomain.silverpay.data.model.mainDB.User;
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
    @Mapping(source = "user", target = "userId", qualifiedByName = "mapUserId")
    @Mapping(source = "blogGroup", target = "blogGroupId", qualifiedByName = "mapBlogGroupId")
    @Mapping(source = "blogGroup", target = "blogGroupName", qualifiedByName = "mapBlogGroupName")
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

    @Named("mapUserId")
    default String mapIdUser(User user) {

        return user.getId();
    }

    @Named("mapBlogGroupId")
    default String mapIdBlogGroup(BlogGroup blogGroup) {

        return blogGroup.getId();
    }

    @Named("mapBlogGroupName")
    default String mapNameBlogGroup(BlogGroup blogGroup) {

        return blogGroup.getId();
    }




}
